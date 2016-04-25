import java.io.*;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.lang.Math;


public class CalculatorTest {
	private String str;

	// constructor
	public CalculatorTest() 
	{
		str = "\0";
	}

	public CalculatorTest(String st) 
	{
		str = st;
	}

	// end constructor

	public static void main(String args[]) 
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		while (true) {
			try {
				String input = br.readLine();
				if (input.compareTo("q") == 0)
					break;
				try {
					command(input);
				} catch (Error e) {
					System.out.println("ERROR");
				}
			} catch (Exception e) {
				System.out.println("입력이 잘못되었습니다. 오류 : " + e.toString());
			}
		}
	} // end main

	private static void command(String input) throws Error 
	{
		//check whether operands have spaces
		Pattern digitErrPattern = Pattern.compile("[0-9]+\\s+[0-9]+");
		Matcher digitErr = digitErrPattern.matcher(input);
		boolean isDigErr = digitErr.find();
		if (isDigErr) {
			throw new Error();
		}

		//delete all the spaces
		input = input.replaceAll(" ", "");

		//check input
		CalculatorTest calInput = new CalculatorTest(input);

		boolean isCorrectInput = calInput.isRightInput();
		boolean isBalBraces = calInput.isBalanced();
		if (isCorrectInput == false || isBalBraces == false) {
			throw new Error();
		}

		//convert unary '-' to '~'
		calInput = calInput.convertUnaryMinus();

		// convert to postfix expression
		CalculatorTest postfixExp = calInput.toPostfix();
		
		
		// do calculation
		CalculatorTest result = postfixExp.calculate();
		
		
		// print postfix expression and the result
		System.out.println(postfixExp.str);
		System.out.println(result.str);
		
	} // end command

	public char charAt(int index) 
	{
		char c;
		c = this.str.charAt(index);

		return c;
	} // end charAt

	public boolean isRightInput() 
	{
		boolean isRight = true;
		Stack<Character> inputStack = new Stack<Character>();
		int index = 0;
		String input = this.str;
	
		char firstCh = input.charAt(0);
		inputStack.push(firstCh);
		if(firstCh == '+' || firstCh == '*' || firstCh == '/' || firstCh == '%' || firstCh == '^' || firstCh == ')') {
			// if the first character is one of the operators
			return false;
		}
		index += 1;
	
		while((index < input.length()) && (isRight == true)) {
			isRight = false;
			char former = inputStack.peek();  // former character of current character
			switch (input.charAt(index)) {
			case '(' :
				if(former == '+' || former == '-' || former == '*' || former == '/' || former == '%' || former == '^') {
					// operators
					isRight = true;
				}
				else if(former == '(') {
					isRight = true;
				}
				break;
			case ')' :
				if(former == ')') { // ((E))
					isRight = true;
				}
				else if(former >= '0' && former <= '9') { // (D) (E)
					isRight = true;
				}
				break;
			case '-' :
				if(former == '(') { // (-E)
					isRight = true;
				}
				else if(former == '+' || former == '-' || former == '*' || former == '/' || former == '%' || former == '^') {
					// Eop(-E)
					isRight = true;
				}
			case '+' :
			case '*' :
			case '/' :
			case '%' :
			case '^' :
				if(former == ')') { // (E)opE
					isRight = true;
				}
				else if(former >= '0' && former <= '9') { // DopE
					isRight = true;
				}
				break;
			default : // digits
				if(former == '(') { // (D)
					isRight = true;
				}
				else if(former == '+' || former == '-' || former == '*' || former == '/' || former == '%' || former == '^') {
					// EopD
					isRight = true;
				}
				else if(former >= '0' && former <= '9') { // DD
					isRight = true;
				}
			} // end switch
			inputStack.push(input.charAt(index));
			index += 1;
		} // end while
		
		// if the operators are at the end of the input
		Pattern opLastPattern = Pattern.compile("[\\+\\-\\*\\^\\/\\%]$");
		Matcher opLast = opLastPattern.matcher(input);
		boolean isOpLastErr = opLast.find();
		if (isOpLastErr) {
			return false;
		}
	
		// x/0  x%0
		Pattern divZeroPattern = Pattern.compile("[0-9]+[\\/\\%][0]");
		Matcher divZero = divZeroPattern.matcher(input);
		boolean isDivZeroErr = divZero.find();
		if (isDivZeroErr) {
			return false;
		}
		
		// 0^x(x<0)
		Pattern zeroExpMinus = Pattern.compile("[0][\\^][\\-][0-9]+");
		Matcher zeroExp =zeroExpMinus.matcher(input);
		boolean isZeroExpErr = zeroExp.find();
		if(isZeroExpErr) {
			return false;
		}
	
		return isRight;
	}

	
	public boolean isBalanced() 
	{ // modification from the book 'Data Abstraction & problem solving with Java'
	  // check whether 'input' has balances braces
		boolean isBalanced = true;
		Stack<Character> myStack = new Stack<Character>();

		boolean balancedSoFar = true;
		int i = 0;
		char ch;
		while (balancedSoFar && i < this.str.length()) {
			ch = this.charAt(i);
			++i;
			if (ch == '(') {
				myStack.push('(');
			} else if (ch == ')') {
				if (!myStack.isEmpty()) {
					myStack.pop();
				} else {
					balancedSoFar = false;
				}
			}
		} // end while

		if (!balancedSoFar || !myStack.isEmpty()) {
			// 'input' does not have balanced braces
			isBalanced = false;
			return isBalanced;
		} // end if

		return isBalanced;
	} // end isBalanced

	public CalculatorTest convertUnaryMinus()
	{ // find all unary '-' and replace them to '~'
		int length = this.str.length();
		char[] withUnaryMinusArray = new char[length];
		
		Stack<Character> myStack = new Stack<Character>();
		int i = 0;
		char ch;
		for(i=length-1; i>=0; i--) { //push string into the stack
			ch = this.charAt(i);
			if(ch == '(') { 
				if(myStack.peek() == ')') { // empty braces
					myStack.pop();
				}
				else {
					myStack.push(ch);
				}
			}
			else {
				myStack.push(ch);
			}
		}
		
		i = 0; 
		if(myStack.peek() == '-') { //if the first character is '-', replace it with '~'
			myStack.pop();
			myStack.push('~');
		}
		while(!myStack.isEmpty()) {
			ch = myStack.peek();  // character on top of the stack
			if(ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '%' || ch == '^' || ch == '~' || ch == '(') {
				// operators
				withUnaryMinusArray[i] = myStack.pop();
				while(!myStack.isEmpty() && myStack.peek() == '-') { // '-'s after an operator
					i++;
					myStack.pop();
					withUnaryMinusArray[i] = '~';
				}
			}
			else { // numbers and ')'
				withUnaryMinusArray[i] = myStack.pop();
			}
			i++;
		}
		
		String result = new String(withUnaryMinusArray);
		CalculatorTest withUnaryMinus = new CalculatorTest(result);

		return withUnaryMinus;
	} // end withUnary

	public static int opPrecedence(char op) 
	{
		int precedence = 0;
	
		switch (op) {
		case '^':
			precedence = 3;
			break;
		case '~':
			precedence = 2;
			break;
		case '*':
		case '/':
		case '%':
			precedence = 1;
			break;
		case '+':
		case '-':
			precedence = 0;
			break;
		} // end switch
	
		return precedence;
	} // end opPrecedence

	public CalculatorTest toPostfix() 
	{ // modification from the book 'Data Abstraction & problem solving with Java'
	  // convert infix expressions to postfix expressions
		String postfixResultStr = new String();
		Stack<Character> aStack = new Stack<Character>();

		char ch;
		int i;
		for (i = 0; i < this.str.length(); i++) {
			ch = this.charAt(i);
			if (ch >= '0' && ch <= '9') {
				// if it's a digit, append it to the end of postfixResult
				postfixResultStr += ch;
				
				if (i + 1 == this.str.length() || this.charAt(i + 1) < '0' || this.charAt(i + 1) > '9') {
					// if it's the end of an operand, add whitespace
					postfixResultStr += " ";
					
				}
			} else if (ch == '(') {
				// save '(' on stack
				aStack.push(ch);
				
			} else if (ch == ')') {
				// pop operators from stack and append them to postfixResult until we meet '('
				while (aStack.peek() != '(') {
					postfixResultStr += aStack.pop() + " ";
				}
				aStack.pop(); // remove the open parenthesis
				
			} else if (ch == '+' || ch == '-' || ch == '*' || ch == '%'	|| ch == '/') {
				// if operators in stack have higher(or equal) precedence than the new operator(ch),
				// pop and append them to postfixResult 
				while (!aStack.isEmpty() && aStack.peek() != '(' && CalculatorTest.opPrecedence(aStack.peek()) >= CalculatorTest.opPrecedence(ch)) {
					postfixResultStr += aStack.pop() + " ";
				}
				aStack.push(ch); // save the new operator on stack
				
			} else if (ch == '^' || ch == '~') {
				while (!aStack.isEmpty() && aStack.peek() != '(' && CalculatorTest.opPrecedence(aStack.peek()) > CalculatorTest.opPrecedence(ch)) {
					postfixResultStr += aStack.pop() + " ";
				}
				aStack.push(ch); // save the new operator on stack
				
			} else {
				throw new Error();
			}
		} // end for

		// append the remaining operators to postfixResult 
		while (!aStack.isEmpty()) {
			postfixResultStr += aStack.pop() + " ";
		} 
		
		postfixResultStr = postfixResultStr.trim();
		
		CalculatorTest postfixResult = new CalculatorTest(postfixResultStr);
		
		return postfixResult;
	} // end toPostfix
	
	public CalculatorTest calculate() {
		Stack<String> calStack = new Stack<String>();
		
		// copy current postfixed string into 'calStack' in reversed order
		// not as a digit, but as a number
		int i = 0;
		int spaceIndex = this.str.length();
		String value;
		for(i=this.str.length()-1; i>0; i--) {
			if(this.charAt(i) == ' ') {
				value = this.str.substring(i+1,spaceIndex);
				spaceIndex = i;
				calStack.push(value);
			}
		} // end for
		value = this.str.substring(i, spaceIndex);
		calStack.push(value);
		
		Stack<Long> tempStack = new Stack<Long>();
		
		long firstVal, secVal;
		while(!calStack.isEmpty()) {
			switch(calStack.peek()) {
			case "+":
				secVal = tempStack.pop();
				firstVal = tempStack.pop();
				tempStack.push(firstVal + secVal);
				calStack.pop();
				break;
			case "-":
				secVal = tempStack.pop();
				firstVal = tempStack.pop();
				tempStack.push(firstVal - secVal);
				calStack.pop();
				break;
			case "*":
				secVal = tempStack.pop();
				firstVal = tempStack.pop();
				tempStack.push(firstVal * secVal);
				calStack.pop();
				break;
			case "/":
				secVal = tempStack.pop();
				if(secVal == 0) {
					throw new Error();
				}
				firstVal = tempStack.pop();
				tempStack.push(firstVal / secVal);
				calStack.pop();
				break;
			case "%":
				secVal = tempStack.pop();
				if(secVal == 0) {
					throw new Error();
				}
				firstVal = tempStack.pop();
				tempStack.push(firstVal % secVal);
				calStack.pop();
				break;
			case "^":
				secVal = tempStack.pop();
				firstVal = tempStack.pop();
				if(firstVal == 0 && secVal < 0) {
					throw new Error();
				}
				tempStack.push((long) Math.pow(firstVal, secVal));
				calStack.pop();
				break;
			case "~":
				secVal = tempStack.pop();
				tempStack.push(secVal * (-1));
				calStack.pop();
				break;
			default: // case number:
				tempStack.push(Long.parseLong(calStack.peek()));
				calStack.pop();
			}
		}
		
		String resultValue = new String(String.valueOf(tempStack.pop()));
		
		CalculatorTest calculated = new CalculatorTest(resultValue);
		
		return calculated;
		
	} // end calculate
	
} // end class

