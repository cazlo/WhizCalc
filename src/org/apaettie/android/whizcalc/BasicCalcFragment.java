package org.apaettie.android.whizcalc;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BasicCalcFragment extends Fragment {
	
	@SuppressWarnings("unused")
	private static final String TAG = "org.apaettie.android.whizcalc.BasicCalcFragment";
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup conatiner, Bundle savedInstanceState){
		View v = inflater.inflate(R.layout.fragment_basic_calculator, conatiner, false);
		
		return v;
	}
}
