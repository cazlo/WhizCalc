package org.apaettie.android.whizcalc;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class SettingsManager {
	private static final String TAG = "org.apaettie.android.whizcalc.SettingsManager";
	private static final String FILENAME = "whiz_calc_settings.json";
	
	//keys for preferences
	private static final String INPUT_MODE_KEY= "org.apaettie.android.whizcalc.input_mode";
	private static final String STARTUP_MODE_KEY= "org.apaettie.android.whizcalc.startup_mode";
	private static final String SHAKE_KEY= "org.apaettie.android.whizcalc.startup_mode";
	private static final String HISTORY_SIZE_KEY= "org.apaettie.android.whizcalc.startup_mode";
	
	//default settings
	private static final String DEFAULT_INPUT = InputMode.INFIX.name(); //string so that it can be saved to prefs easier
	private static final String DEFAULT_STARTUP = StartupMode.BASIC.name();
	private static final boolean DEFAULT_SHAKE = true;
	private static final int DEFAULT_HISTORY = 10;
	
	private WhizCalcJSONSerializer mSerializer;
	
	public static enum StartupMode {
		BASIC, ADVANCED, 
	};

	public static enum InputMode {
		POSTFIX, INFIX, CLASSIC 
	};
	
	private static SettingsManager sInstance;
		
	private boolean mAllowShake;
	private int mHistorySize;
	private StartupMode mStartupMode;
	private InputMode mInputMode;
	
	private Context mContext;//need this to write to json
	
	public static SettingsManager getInstance(Context c) {
		if (sInstance == null){
			sInstance = new SettingsManager(c.getApplicationContext());
		}
		return sInstance;
	}

	private SettingsManager(Context c){
		mContext = c;
		loadSettings();
	}
	
	public void loadSettings(){
		try{
			loadFromJSON();//try to load from json first
		}catch (Exception ex){
			Log.e(TAG, "Caught exception loading settings from json: ", ex);
			loadFromPrefs();//will load defaults if prefs are not setup
		}
	}
	
	private void loadFromPrefs(){
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
		mAllowShake = prefs.getBoolean(SHAKE_KEY, DEFAULT_SHAKE);
		mHistorySize = prefs.getInt(HISTORY_SIZE_KEY, DEFAULT_HISTORY);
		mInputMode = InputMode.valueOf(prefs.getString(INPUT_MODE_KEY, DEFAULT_INPUT));
		mStartupMode = StartupMode.valueOf(prefs.getString(STARTUP_MODE_KEY, DEFAULT_STARTUP));
	}
	
	private void loadFromJSON() throws JSONException, IOException{
		BufferedReader reader = null;
		try{
			InputStream in = mContext.openFileInput(FILENAME);
			reader = new BufferedReader(new InputStreamReader(in));
			StringBuilder jsonString = new StringBuilder();
			String line = null;
			while((line = reader.readLine()) != null){
				jsonString.append(line);
			}
			JSONTokener tok = new JSONTokener(jsonString.toString());
			JSONObject json = new JSONObject(tok);
			
			if (json.has(HISTORY_SIZE_KEY)){
				mHistorySize = json.getInt(HISTORY_SIZE_KEY);
			}else{
				mHistorySize = DEFAULT_HISTORY;
			}
			
			if (json.has(INPUT_MODE_KEY)){
				mInputMode = (InputMode)json.get(INPUT_MODE_KEY);
			}else{
				mInputMode = InputMode.valueOf(DEFAULT_INPUT);
			}
			
			if (json.has(SHAKE_KEY)){
				mAllowShake = json.getBoolean(SHAKE_KEY);
			}else{
				mAllowShake = DEFAULT_SHAKE;
			}
			
			if (json.has(STARTUP_MODE_KEY)){
				mStartupMode = (StartupMode) json.get(STARTUP_MODE_KEY);
			}else{
				mStartupMode = StartupMode.valueOf(DEFAULT_STARTUP);
			}
			
		}catch (FileNotFoundException fnfe){
			Log.d(TAG, "Settings json not found");
		} finally{
			if (reader != null){
				reader.close();
			}
		}
	}

	//saves settings in prefs and to json
	public void saveSettings(){
		saveToPrefs();
		try{
			saveToJSON();
		}catch(Exception ex){
			Log.e(TAG, "Exception caught saving to JSON: ", ex);
		}
	}
	
	public void saveToPrefs(){
		//save to shared preferences
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
		prefs.edit()
			.putString(INPUT_MODE_KEY, mInputMode.name())
			.putString(STARTUP_MODE_KEY, mStartupMode.name())
			.putBoolean(SHAKE_KEY, mAllowShake)
			.putInt(HISTORY_SIZE_KEY, mHistorySize)
			.commit();
	}
	
	public void saveToJSON() throws JSONException, IOException{
		Writer writer = null;
		try{
			OutputStream out = mContext
					.openFileOutput(FILENAME, Context.MODE_PRIVATE);
			writer = new OutputStreamWriter(out);
			writer.write(toJSON().toString());
		}catch (IOException ex){
			Log.e(TAG, "Error saving settings to json", ex);
		} finally{
			if (writer != null){
				writer.close();
			}
		}
	}
	
	private JSONObject toJSON() throws JSONException{
		JSONObject json = new JSONObject();
		
		json.put(INPUT_MODE_KEY, mInputMode);
		json.put(STARTUP_MODE_KEY, mStartupMode);
		json.put(SHAKE_KEY, mAllowShake);
		json.put(HISTORY_SIZE_KEY, mHistorySize);
		
		return json;
	}
	
	/******************************** getters and setters *****************************/
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
}
