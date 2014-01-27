package org.apaettie.android.whizcalc;

import java.util.ArrayList;
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
	
	//these are only pulled out for easier private function communication
	private Stack<Character> operationsStack;
	private String mPostFixString;
	private String mInfixString;
	
	private ArrayList<Character> postfixList;//consists of the string in list form
											//numbers are indicated by an 'n' and
											//ops are indicated by an 'o'
	private ArrayList<Double> postfixNums;
	private ArrayList<Character> postfixOps;
	
	private char currChar;
	
	public EquationParser(String infixString, VALID_CHARS charSetIndex){
		mResult = new Equation(infixString, 0);
		mResult.setInfixString(infixString);
		getEquationFromInfix(infixString);
		mAllowedChars = charSetIndex;
		mInfixString = infixString;
	}
	
	private void getEquationFromInfix(String infixString){
		String postfixString = infixToPostfix(infixString);
		if (postfixString == null){//an error occured in the infix->postifx conversion
			mResult.setIsValid(false);
			return;
		} else{
			mResult.setPostfixString(postfixString);
			solvePostfix(postfixString);
		}
		
	}
	
	/****************************************methods for conversion from infix to postfix **********************/
	//returns null if there was an error
	private String infixToPostfix(String infixString){
		mInfixString = infixString;
		//String postFixString = "";
		mPostFixString = "";
		//char currChar;
		currChar = ' ';
		
		postfixList = new ArrayList<Character>();
		postfixNums = new ArrayList<Double>();
		postfixOps = new ArrayList<Character>();
		
		//Stack<Double> numStack = new Stack<Double>();
		operationsStack = new Stack<Character>();
		
		for (int i = 0; i < infixString.length(); i++){
			currChar = infixString.charAt(i);
			switch (currChar){
			//handle a number; grab anything in between operations
            case '0': case '1': case '2': case '3': case '4':
            case '5': case '6': case '7': case '8': case '9':
            	boolean alreadyADecimal = false;
            	int returnIndex = handleNumber(alreadyADecimal, i);
            	if (returnIndex != -1){
            		i = returnIndex;         
            		postfixList.add('n');
            	} else//
            		return null;
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
            	//whitespace encountered
            	//ignore it for now
            	Log.i(TAG, "whitespace encountered");
            	break;
            case '.':
            	Log.i(TAG, "decimal point encountered");
            	//TODO?
            	break;
            default://some illegal character is in the input string
                Log.e(TAG, "ERROR: Illegal character encountered");
                return null;
	        }
	        //output currentCharacter, the stack contents, and the output
	        Log.i(TAG, "character: " + currChar);
	        Log.i(TAG, "stack: " +  operationsStack.toString());
	        //Log.i(TAG, "output: " + mPostFixString);
	    }
	    
		//get any remaining operators from the stack
	    emptyStack();
		
	    int numIndex = 0;
	    int opIndex = 0;
	    for (char c : postfixList){
	    	if (c == 'o'){
	    		mPostFixString = mPostFixString+postfixOps.get(opIndex)+" ";
	    		opIndex++;
	    	} else if (c == 'n'){
	    		mPostFixString = mPostFixString+postfixNums.get(numIndex)+" ";
	    		numIndex++;
	    	} else{
	    		Log.e(TAG, "Internal error(Unexpected character:'"+c+"')");
	    	}
	    }
	    
		Log.i(TAG, "inToPo returning: "+mPostFixString);
		return mPostFixString;
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
            	postfixOps.add(operationsStack.pop());
            	postfixList.add('o');
            	//mPostFixString = mPostFixString + operationsStack.pop() + " ";
                
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
            	postfixOps.add(operationsStack.pop());
            	postfixList.add('o');
            	//mPostFixString = mPostFixString + operationsStack.pop() + " ";
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
                	postfixOps.add(operationsStack.pop());
                	postfixList.add('o');
                    //mPostFixString = mPostFixString + operationsStack.pop() + " ";
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
			postfixOps.add(operationsStack.pop());
        	postfixList.add('o');
//	        char currOP = operationsStack.pop();
//	        if (currOP == '+'){
//	        	mPostFixString = mPostFixString + "+" + " ";
//	        }
//	        else if (currOP == '-'){
//	        	mPostFixString = mPostFixString + '-' + " ";
//	        }
//	        else if (currOP == '*'){
//	        	mPostFixString = mPostFixString + '*' + " ";
//	        }
//	        else if (currOP == '/'){
//	        	mPostFixString = mPostFixString + '/' + " ";
//	        }
	        Log.i(TAG, "stack: "+ operationsStack.toString());
	        Log.i(TAG, "output: " + mPostFixString);
	    }
	}
	
	//returns index of the last digit in this number (relative to infixString)
	//returns -1 if there is an error in processing
	private int handleNumber(boolean alreadyADecimal, int numStart){

		int j = numStart + 1;
		while (j < mInfixString.length()){
			try{
				Double.parseDouble(mInfixString.substring(numStart, j));
				j++;
			} catch (NumberFormatException ex){//found the end of the number
				Log.i(TAG, "NumberFormatExeception caught in the parser:");
				Log.i(TAG, ""+ex);
				Log.i(TAG, "tried to parse '"+mInfixString.substring(numStart, j)+"'");
				
				j--;
				try{
					double d = Double.parseDouble(mInfixString.substring(numStart, j));
					postfixNums.add(d);
					
				} catch(NumberFormatException e){
					Log.e(TAG, "Unexpected NumberFormatException caught: ");
					Log.e(TAG, "", e);
					return -1;
				}
				
				//handle special case: '(' right next to number 
				char c= mInfixString.charAt(j);
				if (c=='('){
					currChar = '*';
	    			handleMultiplyDivide();
	    			operationsStack.push('(');
	    			return(j);
				} else{
					return (j-1);//-2 because the character of interest is at j-1
					 //and this index will be incremented in the calling for loop
				}
			}
		}
		//if weve made it down this far, the number parsed ok from numStart to the length
		try{
			//String debug = mInfixString.substring(numStart);
			double d = Double.parseDouble(mInfixString.substring(numStart));
			postfixNums.add(d);
		}catch(NumberFormatException e){
			Log.e(TAG, "Unexpected NumberFormatException caught: ");
			Log.e(TAG, "", e);
			return -1;
		}
    	return j;
	}
	
	/********************************solving the posfix equation *******************************/
	//takes a space delimited postfix string as input
	private void solvePostfix(String postfixString){
		//String fullEquationString = "";
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
	
	public static boolean isValidOperation(char c, VALID_CHARS opsMode){
		for (int i = 0; i < VALID_CHARS_SET[opsMode.ordinal()].length; i ++){
			if (c == VALID_CHARS_SET[opsMode.ordinal()][i]){
				return true;
			}
		}
		return false;
	}
}
