package org.apaettie.android.whizcalc;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class Equation {
	private static final String TAG = "org.apaettie.android.whizcalc.Equation";
	
	private static final String JSON_EQ = "equationStr";
	private static final String JSON_IN = "infixStr";
	private static final String JSON_PO = "postfixStr";
	private static final String JSON_NUM = "resultDouble";
	private static final String JSON_VALID = "isValidBool";
	
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

	//construct from json
	public Equation(JSONObject json) throws JSONException{
		if (json.has(JSON_EQ))
			mFullEquation = json.getString(JSON_EQ);
		if (json.has(JSON_IN))
			mInfixString = json.getString(JSON_IN);
		if (json.has(JSON_PO))
			mPostfixString = json.getString(JSON_PO);
		if (json.has(JSON_NUM))
			mResult = json.getDouble(JSON_NUM);
		if (json.has(JSON_VALID))
			mIsValid = json.getBoolean(JSON_VALID);
	}
	
	public JSONObject toJSON()throws JSONException{
		JSONObject json = new JSONObject();
		json.put(JSON_EQ, mFullEquation);
		json.put(JSON_IN, mInfixString);
		json.put(JSON_NUM, mResult);
		json.put(JSON_PO, mPostfixString);
		json.put(JSON_VALID, mIsValid);
		
		return json;
	}
	
	/*********************** Overrides ***********************************/
	@Override
	public String toString(){
		if (mFullEquation == null)
			Log.e(TAG, "Equation.toString::fullEquation = null");
		return mFullEquation;
	}
	
	/************************ Getters and Setters *************************/
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

	public boolean isValid() {
		return mIsValid;
	}

	public void setIsValid(boolean isValid) {
		mIsValid = isValid;
	}

	public String getInfixString() {
		return mInfixString;
	}

	public void setInfixString(String infixString) {
		mInfixString = infixString;
	}

	public String getPostfixString() {
		return mPostfixString;
	}

	public void setPostfixString(String postfixString) {
		mPostfixString = postfixString;
	}
}
