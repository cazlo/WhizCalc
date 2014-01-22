package org.apaettie.android.whizcalc;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import android.content.Context;

public class WhizCalcJSONSerializer {
	private Context mContext;
	private String mFilename;
	
	public WhizCalcJSONSerializer(Context c, String f){
		mContext = c;
		mFilename = f;
	}
	
	public void saveResults(ArrayList<Equation> results)
		throws JSONException, IOException{
		
		//build JSON array
		JSONArray array = new JSONArray();
		for (Equation e : results){
			array.put(e.toJSON());
		}
		
		//write to disk
		Writer writer = null;
		try{
			OutputStream out = mContext
					.openFileOutput(mFilename, Context.MODE_PRIVATE);
			writer = new OutputStreamWriter(out);
			writer.write(array.toString());
		}
		finally{
			if (writer != null){
				writer.close();
			}
		}
	}
	
	public ArrayList<Equation> loadResults() throws IOException, JSONException{
		ArrayList<Equation> results = new ArrayList<Equation>();
		BufferedReader reader = null;
		try{
			//open and read file
			InputStream in = mContext.openFileInput(mFilename);
			reader = new BufferedReader(new InputStreamReader(in));
			StringBuilder jsonString = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null){
				//line breaks are ommitted
				jsonString.append(line);
			}
			JSONArray array = (JSONArray) new JSONTokener(jsonString.toString())
				.nextValue();
			for (int i = 0; i < array.length(); i++){
				results.add(new Equation (array.getJSONObject(i)));
			}
		} catch(FileNotFoundException e){
			//this happens when starting fresh, so just ignore
		} finally{
			if (reader != null){
				reader.close();
			}
		}
		return results;
	}
}
