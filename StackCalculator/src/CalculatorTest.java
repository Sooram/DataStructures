import java.io.*;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.lang.Math;

public class CalculatorTest {
	private String str;

	// constructor
	public CalculatorTest() {
		str = "\0";
	}

	public CalculatorTest(String st) {
		str = st;
	}

	// end constructor

	public static void main(String args[]) {
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

	private static void command(String input) throws Error {
		//check whether operands have spaces
		Pattern digitErrPattern = Pattern.compile("[0-9]+\\s+[0-9]+");
		Matcher digitErr = digitErrPattern.matcher(input);
		boolean isDigErr = digitErr.find();
		if (isDigErr) {
			throw new Error();
		}

		//delete all the spaces
		input = input.replaceAll(" ", "");

		CalculatorTest in = new CalculatorTest(input);

		boolean isCorrectInput = in.isRightInput();
		if (isCorrectInput == false) {
			throw new Error();
		}

		boolean isBalBraces = in.isBalanced();
		if (isBalBraces == false) {
			throw new Error();
		}

		//convert unary '-' to '~'
		in = in.withUnary();

		// convert to postfix expressions and print them
		CalculatorTest postfixIn = in.toPostfix();
		System.out.println(postfixIn.str.substring(1));
		
		CalculatorTest result = postfixIn.calculate();
		System.out.println(result.str);
		
	} // end command

	public char getChar(int index) {
		char c;
		c = this.str.charAt(index);

		return c;
	} // end getChar

	public boolean isRightInput() { // check whether 'input' is correct or not
		boolean isRight = true;
		String input = this.str;

		boolean isBracesErr = input.contains("()");
		if (isBracesErr) {
			isRight = false;
			return isRight;
		}

		Pattern opRepPattern = Pattern.compile("[\\+\\*\\^\\/\\%]{2,}");
		Matcher opRep = opRepPattern.matcher(input);
		boolean isOpRepErr = opRep.find();
		if (isOpRepErr) {
			isRight = false;
			return isRight;
		}

		Pattern opFirstPattern = Pattern.compile("^[\\+\\*\\^\\/\\%]");
		Matcher opFirst = opFirstPattern.matcher(input);
		boolean isOpFirstErr = opFirst.find();
		if (isOpFirstErr) {
			isRight = false;
			return isRight;
		}

		Pattern opLastPattern = Pattern.compile("[\\+\\*\\^\\/\\%]$");
		Matcher opLast = opLastPattern.matcher(input);
		boolean isOpLastErr = opLast.find();
		if (isOpLastErr) {
			isRight = false;
			return isRight;
		}

		Pattern divZeroPattern = Pattern.compile("[0-9]+[\\/\\%][0]");
		Matcher divZero = divZeroPattern.matcher(input);
		boolean isDivZeroErr = divZero.find();
		if (isDivZeroErr) {
			isRight = false;
			return isRight;
		}

		Pattern powerPattern = Pattern.compile("[0][\\^][\\(]?[\\-]");
		Matcher power = powerPattern.matcher(input);
		boolean isPowerErr = power.find();
		if (isPowerErr) {
			isRight = false;
			return isRight;
		}

		return isRight;
	} // end isRight

	public boolean isBalanced() { // check whether 'input' has balances braces
		boolean isBalanced = true;

		Stack<Character> myStack = new Stack<Character>();

		boolean balancedSoFar = true;
		int i = 0;
		char ch;
		while (balancedSoFar && i < this.str.length()) {
			ch = this.getChar(i);
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

	public CalculatorTest withUnary()
	{ // find all unary '-' and replace them to '~'
		char[] withUnaryArray = new char[this.str.length()];
		
		Stack<Character> myStack = new Stack<Character>();
		int i = 0;
		char ch;
		for(i=this.str.length()-1; i>=0; i--) { //push string into the stack
			ch = this.getChar(i);
			myStack.push(ch);
		}
		
		i = 0;
		if(myStack.peek() == '-') { //if the first character is '-', replace it with '~'
			myStack.pop();
			myStack.push('~');
		}
		while(!myStack.isEmpty()) {
			if(myStack.peek() < '0' || myStack.peek() > '9') { //operators
				withUnaryArray[i] = myStack.pop();
				while(!myStack.isEmpty() && myStack.peek() == '-') { //'-'s after an operator
					i++;
					myStack.pop();
					withUnaryArray[i] = '~';
				}
			}
			else { //numbers
				withUnaryArray[i] = myStack.pop();
			}
			i++;
		}
		
		String result = new String(withUnaryArray);
		CalculatorTest withUnaryMinus = new CalculatorTest(result);

		return withUnaryMinus;
	} // end withUnary

	public int opPrecedence(char op) {
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

	public CalculatorTest toPostfix() { // convert infix expressions to postfix
										// expressions
		CalculatorTest postfixResult = new CalculatorTest();

		Stack<Character> aStack = new Stack<Character>();

		char ch;
		int i;
		for (i = 0; i < this.str.length(); i++) {
			ch = this.getChar(i);
			if (ch >= '0' && ch <= '9') {
				// append operand to end of postfixResult
				postfixResult.str += ch;
				
				if (i + 1 == this.str.length() || this.getChar(i + 1) < '0' || this.getChar(i + 1) > '9') {
					// if it's the end of an operand, add whitespace
					postfixResult.str += " ";
					
				}
			} else if (ch == '(') {
				// save '(' on stack
				aStack.push(ch);
				
			} else if (ch == ')') {
				// pop stack until matching '('
				while (aStack.peek() != '(') {
					postfixResult.str += aStack.pop() + " ";
				}
				aStack.pop(); // remove the open parenthesis
				
			} else if (ch == '+' || ch == '-' || ch == '*' || ch == '%'	|| ch == '/') {
				// process stack operators of greater precedence
				while (!aStack.isEmpty() && aStack.peek() != '(' && opPrecedence(aStack.peek()) >= opPrecedence(ch)) {
					postfixResult.str += aStack.pop() + " ";
				}
				aStack.push(ch); // save new operator
				
			} else if (ch == '^' || ch == '~') {
				while (!aStack.isEmpty() && aStack.peek() != '(' && opPrecedence(aStack.peek()) > opPrecedence(ch)) {
					postfixResult.str += aStack.pop() + " ";
				}
				aStack.push(ch); // save new operator
				
			} else {
				throw new Error();
			}
		} // end for

		// append to postfixResult the operators remaining in the stack
		while (!aStack.isEmpty()) {
			postfixResult.str += aStack.pop() + " ";
		} // end while

		return postfixResult;
	} // end toPostfix
	
	public CalculatorTest calculate() {
		Stack<String> calStack = new Stack<String>();
		
		int i = 0;
		int spaceIndex = this.str.length()-1;
		String value;
		for(i=this.str.length()-2; i>0; i--) {
			if(this.getChar(i) == ' ') {
				value = this.str.substring(i+1,spaceIndex);
				spaceIndex = i;
				calStack.push(value);
			}
		} // end for
		value = this.str.substring(i+1, spaceIndex);
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
				firstVal = tempStack.pop();
				tempStack.push(firstVal / secVal);
				calStack.pop();
				break;
			case "%":
				secVal = tempStack.pop();
				firstVal = tempStack.pop();
				tempStack.push(firstVal % secVal);
				calStack.pop();
				break;
			case "^":
				secVal = tempStack.pop();
				firstVal = tempStack.pop();
				tempStack.push((long) Math.pow(firstVal, secVal));
				calStack.pop();
				break;
			case "~":
				secVal = tempStack.pop();
				tempStack.push(secVal * (-1));
				calStack.pop();
				break;
			default:
				tempStack.push(Long.parseLong(calStack.peek()));
				calStack.pop();
			}
		}
		
		String resultValue = new String(String.valueOf(tempStack.pop()));
		
		CalculatorTest calculated = new CalculatorTest(resultValue);
		
		return calculated;
		
	} // end calculate
	
} // end class

