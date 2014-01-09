package org.apaettie.android.whizcalc;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class BasicCalcActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_basic_calc);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.basic_calc, menu);
		return true;
	}

}
