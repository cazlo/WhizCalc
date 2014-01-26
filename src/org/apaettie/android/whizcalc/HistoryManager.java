package org.apaettie.android.whizcalc;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;

public class HistoryManager {
	private static final String TAG = "HistoryManager";
	
	public static final String HISTORY_FILENAME = "history.json";
	
	private static HistoryManager sInstance;
	
	private Context mAppContext;
	private WhizCalcJSONSerializer mSerializer;
	private ArrayList<Equation> mResultList;
	
	private HistoryManager(Context c){
		mAppContext = c;
		mResultList = new ArrayList<Equation>();
		mSerializer = new WhizCalcJSONSerializer(c, HISTORY_FILENAME);
		
		try{
			mResultList = mSerializer.loadResults();
		} catch (Exception e) {
			mResultList = new ArrayList<Equation>();
			Log.e(TAG, "Error loading results", e);
		}
	}
	
	public static HistoryManager get(Context c){
		if (sInstance == null){
			sInstance = new HistoryManager(c.getApplicationContext());
		}
		return sInstance;
	}
	
	public void addResult(Equation result){
		mResultList.add(result);
	}
	
	public void removeItem(Equation eq){
		mResultList.remove(eq);
	}
	
	public ArrayList<Equation> getResults(){
		return mResultList;
	}
	
	public boolean saveHistory(){
		try{
			mSerializer.saveResults(mResultList);
			Log.d(TAG, "History succesfully saved");
			return true;
		} catch (Exception e){
			Log.e(TAG, "Error saving history");
			return false;
		}
	}
}
