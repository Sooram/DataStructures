import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
 
 
public class BigInteger
{
    public static final String QUIT_COMMAND = "quit";
    public static final String MSG_INVALID_INPUT = "입력이 잘못되었습니다.";
    public static final Pattern EXPRESSION_PATTERN = Pattern.compile("^([\\+\\-]?)([0-9]+)[\\+\\-\\*]([\\+\\-]?)([0-9]+)$");
 
    private char[] charArray;
 
    // constructor
    public BigInteger(int i)
    {
    	charArray = ("" + i).toCharArray();
    }
 
    public BigInteger(char[] num1)
    {
    	charArray = new char[num1.length];
    	int i = 0;
    	for(i=0; i<num1.length; i++) {
    		charArray[i] = num1[i];
    	}
    }
 
    public BigInteger(String s)
    {
    	charArray = s.toCharArray();
    }
    // end constructor
    
    public char[] reverseArray() 
    { // reverse an array
    	char[] reversed = new char[256];
    	int i;
    	for(i=0; i<this.charArray.length; i++) {
    		reversed[i] = this.charArray[this.charArray.length-i-1];
    	}
    	reversed[i] = '0';
    	
    	return reversed;
    }
    
    public BigInteger add(BigInteger small)
	{ 
		char[] bigReversed = this.reverseArray();
		char[] smallReversed = small.reverseArray();
		
		// calculate one digit by one digit
		int i, j;
		for(i=0; i<small.charArray.length; i++) {
			if((bigReversed[i] + smallReversed[i]) > 105) { // if the calculated value has carry
				bigReversed[i] = (char)(bigReversed[i] + smallReversed[i] - 58);
				bigReversed[i+1] += 1;
				
				j=1;
				while(bigReversed[i+j] > 57) { // when the digit increased by carry also has carry
					bigReversed[i+j] = '0';
					bigReversed[i+j+1] += 1;
					j++;
				}
			}
			else {
				bigReversed[i] = (char)(bigReversed[i] + smallReversed[i] - 48);
			}
		}
		
		// get the length of the result
		int resultLength = 0;
		i = 0;
		while(bigReversed[i] != '\0') {
			i++;
		}
		resultLength = i;
		
		// if the length of result is equal to that of bigReversed
		// -> should take out '0' which was put in the method 'reverseArray' for carry
		if(bigReversed[resultLength-1] == '0') {
			bigReversed[resultLength-1] = '\0';
			resultLength -= 1;
		}
		
		// reverse the reversed result
		char[] resultArray = new char[resultLength];
		for(i=0; i<resultLength; i++) {
				resultArray[i] = bigReversed[resultLength-i-1];
		}
		
		BigInteger result = new BigInteger(resultArray);
		return result;
	}

	public BigInteger subtract(BigInteger small, boolean equal)
	{
		BigInteger result;
		
		if(equal == true) { // if two numbers have the same value
			result = new BigInteger(0);
		}
		else{
	    	char[] bigReversed = this.reverseArray();
	    	char[] smallReversed = small.reverseArray();
	    	
	    	// calculate one digit by one digit
	    	int i, j;
	    	for(i=0; i<small.charArray.length; i++) { // if the calculated value is below 0
	    		if((bigReversed[i] < smallReversed[i])) {
	    			bigReversed[i] = (char)(bigReversed[i] + 58 - smallReversed[i] );
	    			bigReversed[i+1] -= 1;
	    			
	    			j=1;
	    			while(bigReversed[i+j] < 48) {
	    				bigReversed[i+j] = '9';
	    				bigReversed[i+j+1] -= 1;
	    				j++;
	    			}
	    			
	    		}
	    		else {
	    			bigReversed[i] = (char)(bigReversed[i] - smallReversed[i] + 48);
	    		}
	    	}
	    	
	    	// get the length of the result
	    	int resultLength = 0;
	    	i = 0;
	    	while(bigReversed[i] != '\0') {
	    		i++;
	    	}
	    	resultLength = i;
	    	
	    	// if the length of 'result' is equal to the length of 'bigReversed'
	    	// -> should take out '0' which was put in the method 'reverseArray' for carry
	    	for(i=resultLength-1; bigReversed[i] == '0'; i--) {
	    		bigReversed[i] = '\0';
	    		resultLength -= 1;
	    	}
	    	
	    	// reverse the reversed result
	    	char[] resultArray = new char[resultLength];
	    	for(i=0; i<resultLength; i++) {
	    			resultArray[i] = bigReversed[resultLength-i-1];
	    	}
	    	
	    	result = new BigInteger(resultArray);
	    }
		
		return result;
	}

	public BigInteger multiply(BigInteger big)
	{
		char[] smallReversed = this.reverseArray();
		char[] bigReversed = big.reverseArray();
		
		int i, j, k, value, lengthOfValue, actualLength;
		BigInteger result = new BigInteger(0);
		
		for(i=0; i<this.charArray.length; i++) {
			for(j=0; j<big.charArray.length; j++) {
				value = (smallReversed[i] - 48) * (bigReversed[j] - 48);
				
				// find out whether calculated result('value') is one digit or two
				if(value > 9) {
					lengthOfValue = 2;
				}
				else {
					lengthOfValue = 1;
				}
				
				actualLength = lengthOfValue+i+j;
				
				char[] calExtendedArray = new char[actualLength];
				
				// convert 'value' of int type to char array
				for (k=lengthOfValue-1; k>= 0; k--) {
				    calExtendedArray[k] = (char) ('0' + (value % 10));
				    value /= 10;
				}
				
				// put '0' into the rest elements to operate 'add' method properly
				for(k=lengthOfValue; k<actualLength; k++) {
					calExtendedArray[k] = '0';
				}
	
			     BigInteger calValue = new BigInteger(calExtendedArray);
			     
			     if(calValue.compareTo(result) >= 0) { // calValue >= result
			    	 result = calValue.add(result);
			     }
			     else { // result >= calValue
			    	 result = result.add(calValue);
			     }
			}
		} // end for loop
		
		if(result.charArray[0] == '0') { // if the result is zero
			result = new BigInteger(0);
		}
		
		return result;
	}

	public BigInteger withSign(char sign) 
    { // make the value signed
    	if(sign == '+') {
    		return this;
    	}
    	else { // sign == '-'
    		if(this.charArray[0] == '0') { // if the result is 0, do not add '-'
    			return this;
    		}
    		char[] signedResultArray = new char[this.charArray.length+1];
    		signedResultArray[0] = sign;
    		int i;
    		for(i=0; i<this.charArray.length; i++) {
    			signedResultArray[i+1] = this.charArray[i];
    		}
    		
    		BigInteger signedResult = new BigInteger(signedResultArray);
    		return signedResult;
    	}
    }
    
    public int compareTo (BigInteger num) 
    { // compare two BigIntegers
    	int comp;
    	String num1, num2;
    	num1 = this.toString();
    	num2 = num.toString();
    	
    	if((num1.length() > num2.length()) || ((num1.length() == num2.length()) && (num1.compareTo(num2) > 0))) { // num1 > num2
    		comp = 1;
    	}
    	else if(num1.compareTo(num2) == 0) { //num1 = num2
    		comp = 0;
    	}
    	else { // num1 < num2
    		comp = -1;
    	}
    	
    	return comp;
    }
    
    @Override
    public String toString()
    {
    	String s = new String(charArray);
    	return s;
    }
    
    public static void isRightInput(String s)
    { // check whether the input is right or not
    	Matcher m = EXPRESSION_PATTERN.matcher(s);
    	boolean isRight = m.matches();
    	if(isRight != true) {
    		throw new IllegalArgumentException();
    	}  	
    }
    
    static BigInteger evaluate(String input) throws IllegalArgumentException
    {
        // remove all the white spaces
    	input = input.replaceAll(" ","");
    	
    	BigInteger.isRightInput(input);
    	
    	// get the index of the operator and store the value
    	int i = 1;
    	while(input.charAt(i) != '+' && input.charAt(i) != '-' && input.charAt(i) != '*') { 
    		i++;
    	}
    	int opIndex = i;
    	char operator = input.charAt(i);
    	
    	// split the input into two numbers and store the sign of each number
    	char num1Sign, num2Sign;
    	String num1, num2;
    	
    	if(input.charAt(0) == '+' || input.charAt(0) == '-') { // if num1 has a sign
    		num1Sign = input.charAt(0);
    		num1 = input.substring(1, opIndex);
    	}
    	else {
    		num1Sign = '+';
    		num1 = input.substring(0, opIndex);
    	} // end case num1
    	
    	if(input.charAt(opIndex+1) == '+' || input.charAt(opIndex+1) == '-') { // if num2 has a sign
    		num2Sign = input.charAt(opIndex+1);
    		num2 = input.substring(opIndex+2, input.length());
    	}
    	else {
    		num2Sign = '+';
    		num2 = input.substring(opIndex+1, input.length());
    	} // end case num2
    	
    	// compare two numbers
    	BigInteger firstNum = new BigInteger(num1);
    	BigInteger secondNum = new BigInteger(num2);
    	BigInteger big, small;
     	boolean isEqual = false;
     	
    	if(firstNum.compareTo(secondNum) > 0) { // num1 > num2
    		big = firstNum;
    		small = secondNum;
    	}
    	else if(firstNum.compareTo(secondNum) == 0){ // num1 = num2
    		big = firstNum;
    		small = secondNum;
    		isEqual = true;
    	}
    	else { // num1 < num2
    		big = secondNum;
    		small = firstNum;
    	}
    	
    	// determine which operation should be made and which sign the result value has
    	BigInteger resultWOSign, resultValue;
    	char signOfResult;
       	
    	if(((operator == '+') && (num1Sign == num2Sign)) || ((operator == '-') && (num1Sign != num2Sign))) {
    		// case add
    		resultWOSign = big.add(small);
    		signOfResult = num1Sign;
    	} 
    	else if(((operator == '+') && (num1Sign != num2Sign)) || ((operator == '-') && (num1Sign == num2Sign))) {
    		// case subtract
    		resultWOSign = big.subtract(small, isEqual);
    		
    		if((num1.length() > num2.length()) || ((num1.length() == num2.length()) && (num1.compareTo(num2) > 0))) {
    			// num1 > num2
    			signOfResult = num1Sign;
    		}
    		else if(isEqual == true) { // num1 = num2
				signOfResult = '+';
			}
    		else { // num1 < num2
    			if(operator == '+') {
    				signOfResult = num2Sign;
    			}
    			else { // operator == '-'
    				if(num2Sign == '+') {
    					signOfResult = '-';
    				}
    				else signOfResult = '+';
    			}
    		}
    	} 
    	else {
    		// case multiply
    		resultWOSign = small.multiply(big);
    		if((num1Sign == '-') || (num2Sign == '-')) {
    			if(num1Sign == num2Sign) {
    				signOfResult = '+';
    			}
    			else signOfResult = '-';
    		}
    		else signOfResult = '+';
    	} 
    	
    	resultValue = resultWOSign.withSign(signOfResult);
    	
    	return resultValue;
    }
 
    public static void main(String[] args) throws Exception
    {
        try (InputStreamReader isr = new InputStreamReader(System.in))
        {
            try (BufferedReader reader = new BufferedReader(isr))
            {
                boolean done = false;
                while (!done)
                {
                    String input = reader.readLine();
 
                    try
                    {
                        done = processInput(input);
                    }
                    catch (IllegalArgumentException e)
                    {
                        System.err.println(MSG_INVALID_INPUT);
                    }
                }
            }
        }
    }
 
    static boolean processInput(String input) throws IllegalArgumentException
    {
        boolean quit = isQuitCmd(input);
 
        if (quit)
        {
            return true;
        }
        else
        {
            BigInteger result = evaluate(input);
            System.out.println(result.toString());
 
            return false;
        }
    }
 
    static boolean isQuitCmd(String input)
    {
        return input.equalsIgnoreCase(QUIT_COMMAND);
    }
}
