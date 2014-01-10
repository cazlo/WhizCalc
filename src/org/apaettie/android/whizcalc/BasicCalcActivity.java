package org.apaettie.android.whizcalc;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Window;

public class BasicCalcActivity extends SingleFragmentActivity {

	@Override
	public Fragment createFragment(){
		return new BasicCalcFragment();
	}
	
	public void onCreate(Bundle savedInstanceState){
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		
	}

}
