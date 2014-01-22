package org.apaettie.android.whizcalc;

import java.util.ArrayList;

import android.content.Context;

public class SettingsManager {
	private static final String TAG = "org.apaettie.android.whizcalc.SettingsManager";
	private static final String FILENAME = "settings.json";
	
	private WhizCalcJSONSerializer mSerializer;
	
	public static enum StartupMode {
		BASIC, ADVANCED, 
	};

	public static enum InputMode {
		POSTFIX, INFIX, CLASSIC 
	};
	
	private static SettingsManager mInstance;
		
	private boolean mAllowShake;
	private int mHistorySize;
	private StartupMode mStartupMode;
	private InputMode mInputMode;
	private Context mContext;
	
	public boolean isAllowShake() {
		return mAllowShake;
	}

	public void setAllowShake(boolean allowShake) {
		mAllowShake = allowShake;
	}

	public int getHistorySize() {
		return mHistorySize;
	}

	public void setHistorySize(int historySize) {
		mHistorySize = historySize;
	}

	public StartupMode getStartupMode() {
		return mStartupMode;
	}

	public void setStartupMode(StartupMode startupMode) {
		mStartupMode = startupMode;
	}

	public InputMode getInputMode() {
		return mInputMode;
	}

	public void setInputMode(InputMode inputMode) {
		mInputMode = inputMode;
	}

	public static SettingsManager getInstance() {
		return mInstance;
	}

	private SettingsManager(Context c){
		if (mInstance == null){
			mInstance = new SettingsManager(c);
		}
		mContext = c;
	}
	
}
