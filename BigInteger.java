import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
 
 
public class BigInteger
{
    public static final String QUIT_COMMAND = "quit";
    public static final String MSG_INVALID_INPUT = "입력이 잘못되었습니다.";
    public static final Pattern EXPRESSION_PATTERN = Pattern.compile("^([\\+\\-]?)([0-9]+)[\\+\\-\\*]([\\+\\-]?)([0-9]+)$");
 
    public char[] charArray;
 
    //constructor
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
    //end constructor
    
    public char[] swapArray() 
    { //reverse the array
    	char[] swapped = new char[256];
    	int i;
    	for(i=0; i<this.charArray.length; i++) {
    		swapped[i] = this.charArray[this.charArray.length-i-1];
    	}
    	swapped[i] = '0';
    	
    	return swapped;
    }
    
    public BigInteger withSign(char sign) 
    { //make the value signed
    	if(sign == '+') {
    		return this;
    	}
    	else {
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
    { //compare two BigIntegers
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
    
    public BigInteger add(BigInteger small)
    { 
    	char[] bigSwapped = this.swapArray();
    	char[] smallSwapped = small.swapArray();
    	
    	//calculate one digit by one digit
    	int i, j;
    	for(i=0; i<small.charArray.length; i++) {
    		if((bigSwapped[i] + smallSwapped[i]) > 105) { //if the calculated value is over 10
    			bigSwapped[i] = (char)(bigSwapped[i] + smallSwapped[i] - 58);
    			bigSwapped[i+1] += 1;
    			
    			j=1;
    			while(bigSwapped[i+j] > 57) { //when the digit increased by former line is over 10
    				bigSwapped[i+j] = '0';
    				bigSwapped[i+j+1] += 1;
    				j++;
    			}
    		}
    		else {
    			bigSwapped[i] = (char)(bigSwapped[i] + smallSwapped[i] - 48);
    		}
    	}
    	
    	//get the length of the result
    	int resultLength = 0;
    	i = 0;
    	while(bigSwapped[i] != '\0') {
    		i++;
    	}
    	resultLength = i;
    	
    	//if the length of result is equal to the length of bigSwapped
    	if(bigSwapped[resultLength-1] == '0') {
    		bigSwapped[resultLength-1] = '\0';
    		resultLength -= 1;
    	}
    	
    	//reverse the swapped result
    	char[] resultArray = new char[resultLength];
    	for(i=0; i<resultLength; i++) {
    			resultArray[i] = bigSwapped[resultLength-i-1];
    	}
    	
    	BigInteger result = new BigInteger(resultArray);
    	return result;
    }
 
    public BigInteger subtract(BigInteger small, boolean equal)
    {
    	BigInteger result;
    	
    	if(equal == true) { //if two numbers are the same
    		result = new BigInteger(0);
    	}
    	else{
	    	char[] bigSwapped = this.swapArray();
	    	char[] smallSwapped = small.swapArray();
	    	
	    	//calculate one digit by one digit
	    	int i, j;
	    	for(i=0; i<small.charArray.length; i++) { //if the calculated value is below 0
	    		if((bigSwapped[i] < smallSwapped[i])) {
	    			bigSwapped[i] = (char)(bigSwapped[i] + 58 - smallSwapped[i] );
	    			bigSwapped[i+1] -= 1;
	    			
	    			j=1;
	    			while(bigSwapped[i+j] < 48) {
	    				bigSwapped[i+j] = '9';
	    				bigSwapped[i+j+1] -= 1;
	    				j++;
	    			}
	    			
	    		}
	    		else {
	    			bigSwapped[i] = (char)(bigSwapped[i] - smallSwapped[i] + 48);
	    		}
	    	}
	    	
	    	//get the length of the result
	    	int resultLength = 0;
	    	i = 0;
	    	while(bigSwapped[i] != '\0') {
	    		i++;
	    	}
	    	resultLength = i;
	    	
	    	//if the length of 'result' is equal to the length of 'bigSwapped'
	    	for(i=resultLength-1; bigSwapped[i] == '0'; i--) {
	    		bigSwapped[i] = '\0';
	    		resultLength -= 1;
	    	}
	    	
	    	//reverse the swapped result
	    	char[] resultArray = new char[resultLength];
	    	for(i=0; i<resultLength; i++) {
	    			resultArray[i] = bigSwapped[resultLength-i-1];
	    	}
	    	
	    	result = new BigInteger(resultArray);
	    }
    	
    	return result;
    }
 
    public BigInteger multiply(BigInteger big)
    {
    	char[] smallSwapped = this.swapArray();
    	char[] bigSwapped = big.swapArray();
    	
    	int i, j, k, value, lengthOfValue, actualLength;
    	BigInteger rowCalculated = new BigInteger(0);
    	
    	for(i=0; i<this.charArray.length; i++) {
    		for(j=0; j<big.charArray.length; j++) {
    			value = (smallSwapped[i] - 48) * (bigSwapped[j] - 48);
    			
    			//find out whether the value is one digit or two
    			if(value > 9) {
    				lengthOfValue = 2;
    			}
    			else {
    				lengthOfValue = 1;
    			}
    			
    			actualLength = lengthOfValue+i+j;
    			
    			char[] calExtendedArray = new char[actualLength];
    			
    			//convert 'value' of int type to char array
    			for (k=lengthOfValue-1; k>= 0; k--) {
    			    calExtendedArray[k] = (char) ('0' + (value % 10));
    			    value /= 10;
    			}
    			
    			//put '0' into the rest elements to operate 'add' method properly
    			for(k=lengthOfValue; k<actualLength; k++) {
    				calExtendedArray[k] = '0';
    			}

    		     BigInteger calValue = new BigInteger(calExtendedArray);
    		     
    		     if(calValue.compareTo(rowCalculated) >= 0) { //if calValue >= rowCalculated
    		    	 rowCalculated = calValue.add(rowCalculated);
    		     }
    		     else {
    		    	 rowCalculated = rowCalculated.add(calValue);
    		     }
    		}
    	}
    	
    	return rowCalculated;
    }
 
    @Override
    public String toString()
    {
    	String s = new String(charArray);
    	return s;
    }
 
    static BigInteger evaluate(String input) throws IllegalArgumentException
    {
        //remove all the spaces
    	input = input.replaceAll(" ","");
    	
    	//check if it is right input
    	Matcher m = EXPRESSION_PATTERN.matcher(input);
    	boolean isRightInput = m.matches();
    	if(isRightInput != true) {
    		throw new IllegalArgumentException();
    	}
    	
    	//get the index of the operator and store the value
    	int i = 1;
    	while(input.charAt(i) != '+' && input.charAt(i) != '-' && input.charAt(i) != '*') {
    		i++;
    	}
    	int opIndex = i;
    	char operator = input.charAt(i);
    	
    	//split the input into two numbers and store the sign of each number
    	char num1Sign, num2Sign;
    	String num1, num2;
    	
    	if(input.charAt(0) == '+' || input.charAt(0) == '-') {
    		num1Sign = input.charAt(0);
    		num1 = input.substring(1, opIndex);
    	}
    	else {
    		num1Sign = '+';
    		num1 = input.substring(0, opIndex);
    	}
    	
    	if(input.charAt(opIndex+1) == '+' || input.charAt(opIndex+1) == '-') {
    		num2Sign = input.charAt(opIndex+1);
    		num2 = input.substring(opIndex+2, input.length());
    	}
    	else {
    		num2Sign = '+';
    		num2 = input.substring(opIndex+1, input.length());
    	}
    	
    	//compare two numbers
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
    	
    	//depending on the signs and operator, determine which operation should be made
    	BigInteger resultWOSign, resultValue;
    	char signOfResult;
       	
    	if(((operator == '+') && (num1Sign == num2Sign)) || ((operator == '-') && (num1Sign != num2Sign))) {
    		resultWOSign = big.add(small);
    		signOfResult = num1Sign;
    	}
    	else if(((operator == '+') && (num1Sign != num2Sign)) || ((operator == '-') && (num1Sign == num2Sign))) {
    		resultWOSign = big.subtract(small, isEqual);
    		
    		if((num1.length() > num2.length()) || ((num1.length() == num2.length()) && (num1.compareTo(num2) > 0))) {
    			//if num1 is larger than num2
    			signOfResult = num1Sign;
    		}
    		else if(isEqual == true) {
				signOfResult = '+';
			}
    		else {
    			if(operator == '+') {
    				signOfResult = num2Sign;
    			}
    			else {
    				if(num2Sign == '+') {
    					signOfResult = '-';
    				}
    				else signOfResult = '+';
    			}
    		}
    	}
    	else {
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

