package org.apaettie.android.whizcalc;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Equation implements Serializable {
	private String mFullEquation;//includes the result
	private String mInfixString;//the equation in infix notation;
	private String mPostfixString;//the equation in postfix notation; 
	private double mResult;
	private boolean mIsValid;
	
	public Equation(String fullEquation, long result){
		mFullEquation = fullEquation;
		mResult = result;
		mIsValid = true;//assume it to be valid unless explicitly stated it is not
	}
	
	public Equation(String fullEquation, long result, boolean isValid){
		mFullEquation = fullEquation;
		mResult = result;
		mIsValid = isValid;
	}

	public String getFullEquation() {
		return mFullEquation;
	}

	public void setFullEquation(String fullEquation) {
		mFullEquation = fullEquation;
	}

	public double getResult() {
		return mResult;
	}

	public void setResult(double result) {
		mResult = result;
	}

	public boolean isIsValid() {
		return mIsValid;
	}

	public void setIsValid(boolean isValid) {
		mIsValid = isValid;
	}
}
