import java.io.*;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CalculatorTest
{	
	private String str;
	
	//constructor
	public CalculatorTest() 
	{
		str = "\0";
	}
	
	
	public CalculatorTest(String st) 
	{
		str = st;
	}
	//end constructor
	
	public static void main(String args[])
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		while (true)
		{
			try
			{
				String input = br.readLine();
				if (input.compareTo("q") == 0)
					break;
				try
				{
				command(input);
				}
				catch(Error e) {
					System.out.println("ERROR");
				}
			}
			catch (Exception e)
			{
				System.out.println("입력이 잘못되었습니다. 오류 : " + e.toString());
			}
		}
	} // end main

	
	
	private static void command(String input) throws Error
	{
		Pattern digitErrPattern = Pattern.compile("[0-9]+\\s+[0-9]+");
		Matcher digitErr = digitErrPattern.matcher(input);
		boolean isDigErr = digitErr.find();
		if(isDigErr) {
			throw new Error();
		}
		
		input = input.replaceAll(" ","");
		
		CalculatorTest in = new CalculatorTest(input);
		
		boolean isCorrectInput = in.isRightInput();
		if(isCorrectInput == false) {
			throw new Error();
		}
		
    	boolean isBalBraces = in.isBalanced();
    	if(isBalBraces == false) {
			throw new Error();
		}
    	
    	//convert to postfix expressions and print them
    	CalculatorTest postfixIn = in.toPostfix();
    	System.out.println(postfixIn.str.substring(1));

	} // end command
	
	public char getChar(int index) 
	{
		char c;
		c = this.str.charAt(index);
		
		return c;
	}
	
	public boolean isRightInput() 
	{ //check whether 'input' is correct or not
		boolean isRight = true; 
		String input = this.str;
					
		boolean isBracesErr = input.contains("()");
		if(isBracesErr) {
			isRight = false;
			return isRight;
		}
		
		Pattern opRepPattern = Pattern.compile("[\\+\\*\\^\\/\\%]{2,}");
		Matcher opRep = opRepPattern.matcher(input);
		boolean isOpRepErr = opRep.find();
		if(isOpRepErr) {
			isRight = false;
			return isRight;
		}
		
		Pattern opFirstPattern = Pattern.compile("^[\\+\\*\\^\\/\\%]");
		Matcher opFirst = opFirstPattern.matcher(input);
		boolean isOpFirstErr = opFirst.find();
		if(isOpFirstErr) {
			isRight = false;
			return isRight;
		}
		
		Pattern opLastPattern = Pattern.compile("[\\+\\*\\^\\/\\%]$");
		Matcher opLast = opLastPattern.matcher(input);
		boolean isOpLastErr = opLast.find();
		if(isOpLastErr) {
			isRight = false;
			return isRight;
		}
		
		Pattern divZeroPattern = Pattern.compile("[0-9]+[\\/\\%][0]");
		Matcher divZero = divZeroPattern.matcher(input);
		boolean isDivZeroErr = divZero.find();
		if(isDivZeroErr) {
			isRight = false;
			return isRight;
		}
		
		Pattern powerPattern = Pattern.compile("[0][\\^][\\(]?[\\-]");
		Matcher power = powerPattern.matcher(input);
		boolean isPowerErr = power.find();
		if(isPowerErr) {
			isRight = false;
			return isRight;
		}
			
		return isRight;
	}

	public boolean isBalanced() 
	{ //check whether 'input' has balances braces
		boolean isBalanced = true;
		
    	Stack<Character> myStack = new Stack<Character>();
    	
    	boolean balancedSoFar = true;
    	int i = 0;
    	char ch;
    	while(balancedSoFar && i<this.str.length()) {
    		ch = this.getChar(i);
    		++i;
    		if(ch == '(') {
    			myStack.push('(');
    		}
    		else if(ch == ')') {
    			if(!myStack.isEmpty()) {
    				myStack.pop();
    			}
    			else {
    				balancedSoFar = false;
    			}
    		}
    	} // end while
    	
    	if(!balancedSoFar || !myStack.isEmpty()) {
    	//'input' does not have balanced braces
    		isBalanced = false;
    		return isBalanced;
    	} // end if
    	
    	return isBalanced;
	}
	
	public int opPrecedence(char op) 
	{ 
		int precedence = 0;
		
		switch(op) {
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
		} //end switch
		
		return precedence;
	} //end opPrecedence


	public CalculatorTest withUnary() 
	{
		CalculatorTest withUnaryMinus;
		
		Pattern headMinusPattern = Pattern.compile("^[\\-]");
		Pattern afterOpPattern = Pattern.compile("[\\+\\-\\*\\^\\/\\%\\(][\\-]");
		Matcher headMinus = headMinusPattern.matcher(this.str);
		Matcher afterOp = afterOpPattern.matcher(this.str);
		boolean isUnary = (headMinus.find() || afterOp.find());
		
		return withUnaryMinus;
	}
	
	public CalculatorTest toPostfix()
	{ //convert infix expressions to postfix expressions
		CalculatorTest postfixResult = new CalculatorTest();
		
		Stack<Character> aStack = new Stack<Character>();
				
		char ch;
		int i;
		for(i = 0; i<this.str.length(); i++){
			ch = this.getChar(i);
			if (ch>='0' && ch<='9') {
				//append operand to end of postfixResult
				postfixResult.str += ch;
				if(i+1==this.str.length() || this.getChar(i+1)<'0' || this.getChar(i+1)>'9') {
					//if it's the end of an operand, add whitespace
					postfixResult.str += " ";
				}
			}
			else if(ch == '(') {	
				//save '(' on stack
				aStack.push(ch);
			}
			else if(ch == ')') {
				//pop stack until matching '('
				while(aStack.peek() != '(') {
					postfixResult.str += aStack.pop() + " ";
				} 
				aStack.pop();	//remove the open parenthesis
			}
			else if(ch=='+' || ch=='-' || ch=='*' || ch=='%' || ch=='/')	 {
				//process stack operators of greater precedence
				while(!aStack.isEmpty() && aStack.peek() != '(' && opPrecedence(aStack.peek()) >= opPrecedence(ch)) {
					postfixResult.str += aStack.pop() + " "; 
				} 
				aStack.push(ch);	//save new operator
			}
			else if(ch=='^') {
				while(!aStack.isEmpty() && aStack.peek() != '(' && opPrecedence(aStack.peek()) > opPrecedence(ch)) {
					postfixResult.str += aStack.pop() + " "; 
				} 
				aStack.push(ch);	//save new operator
			}
			else{
				throw new Error();
			}
		} //end for
		
		//append to postfixResult the operators remaining in the stack
		while(!aStack.isEmpty()) {
			postfixResult.str += aStack.pop() + " ";
		} //end while
			
		return postfixResult;
	} //end toPostfix
} // end class

