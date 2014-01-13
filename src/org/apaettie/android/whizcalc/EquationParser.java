package org.apaettie.android.whizcalc;

import java.util.Stack;

import android.util.Log;

public class EquationParser {
	private static final String TAG = "org.apaettie.android.whizcalc.EquationParser";
//	private static final int STACK_SIZE = 100;//the maximum s size which can be parsed
												//(in characters)
	public enum VALID_CHARS{
		BASIC
	}
	public static final char[][] VALID_CHARS_SET = {
		//Basic
		{'+', '-', '/', '*',
		'%', '(', ')', '.'},
		
		//Advanced
		
	};
	
	private Equation mResult;
	private VALID_CHARS mAllowedChars;
	private Stack<Character> operationsStack;
	private String postFixString;
	private char currChar;
	
	public EquationParser(String infixString, VALID_CHARS charSetIndex){
		mResult = new Equation(infixString, 0);
		getEquationFromInfix(infixString);
		mAllowedChars = charSetIndex;
	}
	
	private void getEquationFromInfix(String infixString){
		String postfixString = infixToPostfix(infixString);
		if (postfixString == null){//an error occured in the infix->postifx conversion
			mResult.setIsValid(false);
			return;
		} else{
			solvePostfix(postfixString);
		}
		
	}
	
	//returns null if there was an error
	private String infixToPostfix(String infixString){
		//String postFixString = "";
		postFixString = "";
		//char currChar;
		currChar = ' ';
		
		//Stack<Double> numStack = new Stack<Double>();
		operationsStack = new Stack<Character>();
		
		for (int i = 0; i < infixString.length(); i++){
			currChar = infixString.charAt(i);
			switch (currChar){
            case '0': case '1': case '2': case '3': case '4':
            case '5': case '6': case '7': case '8': case '9':
            	boolean alreadyADecimal = false;
            	while (Character.isDigit(currChar)){//read all numbers until decimal point or other operator
            		postFixString = postFixString + currChar;
            		i++;
            		if (i < infixString.length()){
            			currChar = infixString.charAt(i); 
            		} else{
            			break;
            		}
            		if (currChar == '.'){
            			if (alreadyADecimal){
            				Log.e(TAG, "Too many decimals");
            				return null;
            			} else{
            				alreadyADecimal = true;
	            			postFixString = postFixString + currChar;
	            			if ((i + 1) < infixString.length()){
	            				i++;
	            				currChar = infixString.charAt(i);
	            				if (!Character.isDigit(currChar)){
	            					postFixString = postFixString + "0 ";
	            					i--;
	            				}
	            			}
	            			else{//reached the end with a number without trailing zeros (like 42.)
	            				postFixString = postFixString + "0 ";
	            				emptyStack();
	            				return postFixString;
	            				//break;
	            			}
            			}
            		}
            	}
        		postFixString = postFixString + " ";
            	
            	if(currChar == '+' || 
            	   currChar == '-'){
            		handlePlusMinus();
            	}else if (currChar == '*' ||
            			   currChar == '/'){ 
            		handleMultiplyDivide();
            		//break;
            	}else if (currChar == ')'){
            		if (! handledParentheses()) return null;
            		//break;
            	}else if (currChar == '('){
            		currChar = '*';
            		handleMultiplyDivide();
            		operationsStack.push('(');
            	}else if(currChar == '.'){
            		Log.e(TAG, "unexpected decimal: ");
            		return null;
            	}else if (Character.isDigit(currChar)){
            		Log.e(TAG, "APPLYING HACK; FIX THIS");
            		i--;
            	}else{//not a digit and not a .
            		Log.e(TAG, "unexpected input: "+currChar);
            		return null;
            	}
            	Log.i(TAG, "postFixString: "+ postFixString);
                
                break;
            case '+':
            case '-':
                handlePlusMinus();
                break;
            case '*':
            case '/':
                handleMultiplyDivide();
                break;
            case '(':
                operationsStack.push('(');
                break;
            case ')':
                if (!handledParentheses()) return null;
                break;
            case ' ':
            case '\r':
            case '\n':
            case '\t':
            	Log.i(TAG, "whitespace encountered");
            	break;
            default://some illegal character is in the input string
                Log.e(TAG, "ERROR: Illegal character encountered");
                return null;
	        }
	        //output currentCharacter, the stack contents, and the output
	        Log.i(TAG, "character: " + currChar);
	        Log.i(TAG, "stack: " +  operationsStack.toString());
	        Log.i(TAG, "output: " + postFixString);
	    }
	    
	    emptyStack();
		
		Log.i(TAG, "inToPo returning: "+postFixString);
		return postFixString;
	}
	
	private void handlePlusMinus(){
		//while (opStack.peek() == + || - || * || /){
        //pop the top of the stack and out it in the output}
        //push currentChar to stack
        if (!operationsStack.isEmpty()) {//don't want to throw exception 
                                //from an empty stack
            while (operationsStack.peek() == '+' ||
            		operationsStack.peek() == '-' ||
            		operationsStack.peek() == '*' || 
            		operationsStack.peek() == '/'){
            	postFixString = postFixString + operationsStack.pop() + " ";
                
                if (operationsStack.isEmpty()){
                    break;//get out of the loop if stack is empty
                }
            }
        }
        
        operationsStack.push(currChar);
	}
	
	private void handleMultiplyDivide(){
		//while (opStack.peek() == * || / ){
        //pop the top of the stack and put it in the output}
        //push currentChar onto stack
        if (!operationsStack.isEmpty()){//don't want to throw exception 
                                //from an empty stack
            while (operationsStack.peek() == '*' || 
                   operationsStack.peek() == '/'){
            	postFixString = postFixString + operationsStack.pop() + " ";
                if (operationsStack.isEmpty()){
                    break;//get out of the loop if stack is empty 
                }
              
            }
        }
        operationsStack.push(currChar);
	}
	
	private boolean handledParentheses(){
		if (!operationsStack.isEmpty()){
            while(operationsStack.peek()!= '('){
                if (operationsStack.peek() == null){//reached end of stack 
                    Log.e(TAG, "ERROR: unmatched parentheses");
                    return false;
                }
                else{
                    postFixString = postFixString + operationsStack.pop() + " ";
                }
            }
            //pop the '('
            if (operationsStack.peek() == '('){
            	operationsStack.pop();
            }
        }
		return true;
	}
	
	private void emptyStack(){
		while (!operationsStack.isEmpty()){
	        char currOP = operationsStack.pop();
	        if (currOP == '+'){
	        	postFixString = postFixString + "+" + " ";
	        }
	        else if (currOP == '-'){
	        	postFixString = postFixString + '-' + " ";
	        }
	        else if (currOP == '*'){
	        	postFixString = postFixString + '*' + " ";
	        }
	        else if (currOP == '/'){
	        	postFixString = postFixString + '/' + " ";
	        }
	        Log.i(TAG, "stack: "+ operationsStack.toString());
	        Log.i(TAG, "output: " + postFixString);
	    }
	}
	
	/********************************solving the posfix equation *******************************/
	//takes a space delimited postfix string as input
	private void solvePostfix(String postfixString){
		String fullEquationString = "";
		Stack<Double> numStack = new Stack<Double>();
		Double tokDouble;
		
		for (String token : postfixString.split(" ")){
			try{
				tokDouble = Double.parseDouble(token);
				numStack.push(tokDouble);
			} catch (NumberFormatException ex){
				if (numStack.size() < 2){
					Log.e(TAG, "insufficient values for operation");
					mResult.setIsValid(false);
					mResult.setResult(0);
					return;
				} else{
					double pop1 = numStack.pop();
					double pop2 = numStack.pop();
					
					switch (token.charAt(0)){
					case '+':
						numStack.push(pop1 + pop2);
						break;
					case '-':
						numStack.push(pop2 - pop1);
						break; 
					case '*':
						numStack.push(pop1 * pop2);
						break; 
					case '/':
						if (pop1 == 0){
							Log.e(TAG, "Divide by zero");
							return;
						}
						numStack.push(pop2 / pop1);
						break;
					default:
						Log.e(TAG, "An unexpected character is in the postfix string");
						return;
					}
				}
			} finally{
				
			}
		}
		
		if (numStack.size() == 1){
			mResult.setResult(numStack.pop());
			mResult.setIsValid(true);
			mResult.setFullEquation(mResult.getFullEquation() + "="+mResult.getResult());
		} else{
			Log.e(TAG, "Too many values input");
			Log.i(TAG, "stack content: "+ numStack.toString());
			mResult.setIsValid(false);
			mResult.setResult(0);
		}
	}

	public Equation getResult() {
		return mResult;
	}

	public void setResult(Equation result) {
		mResult = result;
	}
	
//	private class PostfixEquation(){
//		private ArrayList<Double>numberList;
//		private String postfixString;
//		
//		public PostfixEquation(){
//			
//		}
//	}
}
