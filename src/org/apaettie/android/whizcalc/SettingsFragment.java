package org.apaettie.android.whizcalc;

import org.apaettie.android.whizcalc.SettingsManager.InputMode;
import org.apaettie.android.whizcalc.SettingsManager.StartupMode;

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
		mSettingsManager = SettingsManager.getInstance(getActivity());
		mSettingsManager.loadSettings();
		
		mInputModeRadioGroup = (RadioGroup)v.findViewById(R.id.setting_inputModeRadioGroup);
		mInputModeRadioGroup.check(mSettingsManager.getInputMode().ordinal());//set checked button to the correct setting
		mInputModeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				mSettingsManager.setInputMode(InputMode.values()[checkedId]);
			}
		});
		
		mStartupModeRadioGroup = (RadioGroup)v.findViewById(R.id.setting_startupModeRadioGroup);
		mStartupModeRadioGroup.check(mSettingsManager.getStartupMode().ordinal());
		mStartupModeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				mSettingsManager.setStartupMode(StartupMode.values()[checkedId]);
			}
		});
		
		mShakeCheckbox = (CheckBox)v.findViewById(R.id.setting_shakeCheckBox);
		mShakeCheckbox.setChecked(mSettingsManager.isAllowShake());
		mShakeCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				mSettingsManager.setAllowShake(isChecked);
			}
		});
		
		mHistorySeekBar = (SeekBar)v.findViewById(R.id.setting_historySeekBar);
		mHistorySeekBar.setProgress(mSettingsManager.getHistorySize());
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
				mSettingsManager.setHistorySize(progress);
			}
		});
		
		return v;
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.basic_calc, menu);
		
		
	}
	
	@Override
	public void onStop(){
		mSettingsManager.saveSettings();
		super.onStop();
	}

}
