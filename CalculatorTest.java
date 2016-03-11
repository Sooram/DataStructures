import java.io.*;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CalculatorTest
{
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
	}

	public static final Pattern DIGIT_ERROR_PATTERN = Pattern.compile("[0-9]+\\s+[0-9]+");
	public static final Pattern OPERATOR_ERROR_PATTERN = Pattern.compile("");
	public static final Pattern CALCULATE_ERROR_PATTERN = Pattern.compile("");
	public static final Pattern BRACES_ERROR_PATTERN = Pattern.compile("");
	
	private static void command(String input) throws Error
	{
		//check whether 'input' is correct or not
		boolean isErrorInput = false;
		Matcher m = DIGIT_ERROR_PATTERN.matcher(input);
    	if(m.find()) {
    		isErrorInput = m.matches();
    	}
		
    	if(isErrorInput) {
    		throw new Error();
    	}
    	
    	//check whether 'input' has balances braces
    	Stack<Character> myStack = new Stack<Character>();
    	
    	boolean balancedSoFar = true;
    	int i = 0;
    	char ch;
    	while(balancedSoFar && i<input.length()) {
    		ch = input.charAt(i);
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
    		throw new Error();
    	} // end if
    	
    	
    	
	}
}

