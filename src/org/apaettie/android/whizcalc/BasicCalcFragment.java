package org.apaettie.android.whizcalc;

import java.util.ArrayList;
import java.util.List;

import org.apaettie.android.whizcalc.EquationParser.VALID_CHARS;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

public class BasicCalcFragment extends Fragment {
	
	private static final String TAG = "org.apaettie.android.whizcalc.BasicCalcFragment";
	
	private ExpandableListView mResultListView;
	private TextView mWorkingTextView;
	private ArrayList<CalcResult> mResultList;
	private String mWorkingText;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		mWorkingText = "0";
		initData();
		
	}
	
	private void initData(){
		mResultList = new ArrayList<CalcResult>();
		//testData();
		CalcResult results = new CalcResult(0);
		mResultList.add(results);
	}
	private void testData(){

		CalcResult results = new CalcResult(0);
		results.setEquationList(genEquations(27));
		mResultList.add(results);
	}
	private List<Equation> genEquations(int num){
		List <Equation> eqs = new ArrayList<Equation>();
		
		for (int i = 0; i < num; i++){
			Equation eq = new Equation(i+"+"+i+1+"="+(i+i+1), (i+i+1));
			eqs.add(eq);
		}
		
		return eqs;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup conatiner, Bundle savedInstanceState){
		final View v = inflater.inflate(R.layout.fragment_basic_calculator_scroll, conatiner, false);
		
		mResultListView =  (ExpandableListView)v.findViewById(R.id.basicCalc_result_ListView);
		
		final ExpandableListAdapter adapter = new ExpandableListAdapter(mResultList, getActivity());

		mWorkingTextView = (TextView)v.findViewById(R.id.basicCalc_working_TextView);
		mWorkingTextView.setText(mWorkingText);
		
		mResultListView.setAdapter(adapter);
		//mResultListView.setAdapter(wAdapter);
		/*
		mResultListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
			
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				// TODO Auto-generated method stub
				Log.i(TAG, "Child: "+childPosition+" clicked");
				return false;
			}
		});
		*/
		mResultListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
			
			@Override
			public boolean onGroupClick(ExpandableListView parent, View groupV,
					int groupPosition, long id) {
				TextView resultLabel = (TextView)v.findViewById(R.id.basicCalc_resultLabelTextView);
				
				if (mResultListView.isGroupExpanded(groupPosition)){
					resultLabel.setText(R.string.resultLabel);
				} else{
					resultLabel.setText(R.string.resultsLabel);
				}
//				adapter.onGroupExpanded(groupPosition);
//				mResultListView.expandGroup(groupPosition);
//				adapter.notifyDataSetChanged();
				Log.i(TAG, "Group: "+groupPosition+" clicked");
				return false;
			}
		});
		
		
		/**************************  action buttons ******************************************/
		
		Button acButton = (Button)v.findViewById(R.id.basicCalc_buttonAC);
		acButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mWorkingText = "0";
				mWorkingTextView.setText(mWorkingText);
			}
		});
		
		Button cButton = (Button)v.findViewById(R.id.basicCalc_buttonC);
		
		Button backspaceButton = (Button)v.findViewById(R.id.basicCalc_buttonUNDETERMINED);
		backspaceButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mWorkingText == "0"){
					//do nothing
				} else{
					mWorkingText = mWorkingText.substring(0, mWorkingText.length() - 1);
					mWorkingTextView.setText(mWorkingText);
				}
			}
		});
		
		Button plusMinusButton = (Button)v.findViewById(R.id.basicCalc_buttonPlusMinus);
		plusMinusButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//TODO
			}
		});
		
		Button percentButton = (Button)v.findViewById(R.id.basicCalc_buttonPERCENT);
		percentButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mWorkingText != "0"){
					mWorkingText = mWorkingText.concat("%");
					mWorkingTextView.setText(mWorkingText);
				}
			}
		});
		
		Button divideButton = (Button)v.findViewById(R.id.basicCalc_buttonDIV);
		divideButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//if (mWorkingText != "0"){
					mWorkingText = mWorkingText.concat("/");
					mWorkingTextView.setText(mWorkingText);
				//}
			}
		});
		
		Button multiplyButton = (Button)v.findViewById(R.id.basicCalc_buttonMULT);
		multiplyButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//if (mWorkingText != "0"){
					mWorkingText = mWorkingText.concat("*");
					mWorkingTextView.setText(mWorkingText);
				//}
			}
		});
		
		Button subtractButton = (Button)v.findViewById(R.id.basicCalc_buttonSUB);
		subtractButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//if (mWorkingText != "0"){
					mWorkingText = mWorkingText.concat("-");
					mWorkingTextView.setText(mWorkingText);
				//}
			}
		});
		
		Button plusButton = (Button)v.findViewById(R.id.basicCalc_buttonPLUS);
		plusButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//if (mWorkingText != "0"){
				mWorkingText = mWorkingText.concat("+");
				mWorkingTextView.setText(mWorkingText);
				//}
			}
		});
		
		Button equalsButton = (Button)v.findViewById(R.id.basicCalc_buttonEQUALS);
		equalsButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Equation solvedEq = new EquationParser(mWorkingText, VALID_CHARS.BASIC )
												.getResult();
				if (solvedEq.isIsValid()){
					CalcResult newResult = new CalcResult(solvedEq.getResult());
					CalcResult oldResult = mResultList.remove(0);
					List<Equation> eqList = oldResult.getEquationList();
					eqList.add(solvedEq);
					newResult.setEquationList(eqList);
					
					mResultList.add(newResult);
					((BaseExpandableListAdapter)mResultListView.getExpandableListAdapter()).notifyDataSetChanged();
				}
				else{
					Toast.makeText(getActivity(), 
									"Error processing input",
									Toast.LENGTH_LONG)
						 .show();
				}
				mWorkingText = "0";
				mWorkingTextView.setText(mWorkingText);
			}
		});
		
		Button decimalButton = (Button)v.findViewById(R.id.basicCalc_buttonDECIMAL);
		decimalButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mWorkingText = mWorkingText + ".";
				mWorkingTextView.setText(mWorkingText);
				Log.i(TAG, "Decimal clicked");
			}
		});
		
		/*******************  number buttons   *************************/
		
		View.OnClickListener numberListener = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String currButtonText = ((Button)v).getText().toString();
				if (mWorkingText.equals("0")){
					mWorkingText = currButtonText;
				} else{
					mWorkingText = mWorkingText.concat(currButtonText);
				}
				mWorkingTextView.setText(mWorkingText);

				Log.i(TAG, "Number: "+currButtonText+" clicked");
			}
		};
		
		Button zeroButton = (Button)v.findViewById(R.id.basicCalc_button0);
		zeroButton.setOnClickListener(numberListener);
		Button oneButton = (Button)v.findViewById(R.id.basicCalc_button1);
		oneButton.setOnClickListener(numberListener);
		Button twoButton = (Button)v.findViewById(R.id.basicCalc_button2);
		twoButton.setOnClickListener(numberListener);
		Button threeButton = (Button)v.findViewById(R.id.basicCalc_button3);
		threeButton.setOnClickListener(numberListener);
		Button fourButton = (Button)v.findViewById(R.id.basicCalc_button4);
		fourButton.setOnClickListener(numberListener);		
		Button fiveButton = (Button)v.findViewById(R.id.basicCalc_button5);
		fiveButton.setOnClickListener(numberListener);		
		Button sixButton = (Button)v.findViewById(R.id.basicCalc_button6);
		sixButton.setOnClickListener(numberListener);		
		Button sevenButton = (Button)v.findViewById(R.id.basicCalc_button7);
		sevenButton.setOnClickListener(numberListener);		
		Button eightButton = (Button)v.findViewById(R.id.basicCalc_button8);
		eightButton.setOnClickListener(numberListener);		
		Button nineButton = (Button)v.findViewById(R.id.basicCalc_button9);
		nineButton.setOnClickListener(numberListener);
		
		return v;
	}
	

	
}
