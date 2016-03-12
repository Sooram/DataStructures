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
    	int i;
    	for(i=0; i<postfixIn.str.length()-1; i++) {
    		System.out.print(postfixIn.getChar(i)+" ");
    	}
    	System.out.println(postfixIn.getChar(i));
    	
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
		
		Pattern digitErrPattern = Pattern.compile("[0-9]+\\s+[0-9]+");
		Matcher digitErr = digitErrPattern.matcher(input);
		boolean isDigErr = digitErr.find();
		if(isDigErr) {
			isRight = false;
			return isRight;
		}
		
		input = input.replaceAll(" ","");
				
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
    		ch = this.str.charAt(i);
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
	
	public CalculatorTest toPostfix()
	{ //convert infix expressions to postfix expressions
		CalculatorTest postfixResult = new CalculatorTest();
		char[] postfixArray = new char[this.str.length()];
		
		Stack<Character> aStack = new Stack<Character>();
				
		char ch;
		int i;
		for(i = 0; i<this.str.length(); i++){
			ch = getChar(i);
			if (ch>='0' && ch<='9') {
				//append operand to end of postfixResult
				postfixArray[i] = ch;
			}
			else if(ch == '(') {	
				//save '(' on stack
				aStack.push(ch);
			}
			else if(ch == ')') {
				//pop stack until matching '('
				while(aStack.peek() != '(') {
					postfixArray[i] = aStack.pop();
				} 
				aStack.pop();	//remove the open parenthesis
			}
			else if(ch=='+' || ch=='-' || ch=='*' || ch=='^' || ch=='%' || ch=='/')	 {
				
				//process stack operators of greater precedence
				while(!aStack.isEmpty() && aStack.peek() != '(' && postfixResult.opPrecedence(ch) <= postfixResult.opPrecedence(aStack.peek())) {
					postfixArray[i] = aStack.pop(); 
				} 
				aStack.push(ch);	//save new operator
			}
			else{
				throw new Error();
			}
		} //end for
		
		//append to postfixResult the operators remaining in the stack
		while(!aStack.isEmpty()) {
			postfixArray[i] = aStack.pop();
			i++;
		} //end while
			
		return postfixResult;
	} //end toPostfix
	
	public int opPrecedence(char op) 
	{ 
		int precedence = 0;
		
		switch(op) {
		case '^':
			precedence = 0;
			break;
		case '~':
			precedence = 1;
			break;
		case '*':
		case '/':
		case '%':
			precedence = 2;
			break;
		case '+':
		case '-':
			precedence = 3;
			break;
		} //end switch
		
		return precedence;
	} //end opPrecedence
} // end class

