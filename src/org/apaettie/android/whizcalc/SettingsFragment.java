package org.apaettie.android.whizcalc;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SettingsFragment extends Fragment {

	/**
	 * settings ideas:
	 * 
	 * shake clears working field
	 * input mode:
	 * 		postfix
	 * 		infix
	 * 		classic calc
	 * history storage
	 * 	 1-100(slider?)
	 * 	 
	 * default startup mode:
	 * 	 basic
	 * 	 advanced
	 * 	 last used
	 * 
	 */
	
  @Override
  public View onCreateView(LayoutInflater inflater, 
			  ViewGroup parent, 
			  Bundle savedInstanceState){
    View v = inflater.inflate(R.layout.fragment_settings, parent, false);
    
    //TODO: mess with the view
    
    return v;
  }
}
