package org.apaettie.android.whizcalc;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

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
	
	private static final String INPUT_MODE_KEY= "org.apaettie.android.whizcalc.input_mode";
	private static final String STARTUP_MODE_KEY= "org.apaettie.android.whizcalc.startup_mode";
	private static final String SHAKE_KEY= "org.apaettie.android.whizcalc.startup_mode";
	private static final String HISTORY_SIZE_KEY= "org.apaettie.android.whizcalc.startup_mode";

	
	private RadioGroup mInputModeRadioGroup;
	private RadioGroup mStartupModeRadioGroup;
	private CheckBox mShakeCheckbox;
	private SeekBar mHistorySeekBar;
	private SettingsManager mSettingsManager;
	
	@Override
	public View onCreateView(LayoutInflater inflater, 
			ViewGroup parent, 
			Bundle savedInstanceState){
		View v = inflater.inflate(R.layout.fragment_settings, parent, false);

		mInputModeRadioGroup = (RadioGroup)v.findViewById(R.id.setting_inputModeRadioGroup);
		mInputModeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				
			}
		});
		
		mStartupModeRadioGroup = (RadioGroup)v.findViewById(R.id.setting_startupModeRadioGroup);
		mStartupModeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				
			}
		});
		
		mShakeCheckbox = (CheckBox)v.findViewById(R.id.setting_shakeCheckBox);
		mShakeCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				
			}
		});
		
		mHistorySeekBar = (SeekBar)v.findViewById(R.id.setting_historySeekBar);
		mHistorySeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				
			}
		});
		
		return v;
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.basic_calc, menu);
		
		
	}

}
