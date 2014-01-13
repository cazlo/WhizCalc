package org.apaettie.android.whizcalc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class CalcResult implements Serializable {
	private double mCurrentResult;
	private List<Equation> mEquationList = new ArrayList<Equation>();//the formulas that led to the results
	
	public CalcResult(){
		mCurrentResult = 0;
	}
	
	public CalcResult(double currentResult){
		mCurrentResult = currentResult;
	}

	public CalcResult(double currentResult, List<Equation> equationList){
		mCurrentResult = currentResult;
		mEquationList = equationList;
	}
	
	public double getCurrentResult() {
		return mCurrentResult;
	}

	public void setCurrentResult(double id) {
		mCurrentResult = id;
	}

	public List<Equation> getEquationList() {
		return mEquationList;
	}

	public void setEquationList(List<Equation> equationList) {
		mEquationList = equationList;
	}

}
