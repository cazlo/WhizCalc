package org.apaettie.android.whizcalc;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public class SettingsActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		return new SettingsFragment();
	}
	
	@Override
	public void onCreate (Bundle savedInstanceState){
		this.setTitle(R.string.settings_title);
	}

}
