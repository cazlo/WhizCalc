package org.apaettie.android.whizcalc;

import java.util.List;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

public class ExpandableListAdapter extends BaseExpandableListAdapter {
	@SuppressWarnings("unused")
	private static final String TAG = "org.apaettie.android.whizcalc.ExpandableListAdapter";
//	public static final ArrayList<String> ITEM_NAMES = new ArrayList<String>(){{
//		add("ROOT_");
//	}};
	private List<CalcResult> mResultList;
	//private int 
	
	private int mItemLayoutId = R.layout.child_item;
	private int mGroupLayoutId = R.layout.group_item;
	private static TextView sResultLabelTV;
	private Context ctx;
	
	public ExpandableListAdapter(List<CalcResult> resultList, Context ctx) {
		
		//this.mItemLayoutId = R.layout.child_item;
		//this.mGroupLayoutId = R.layout.group_item;
		this.mResultList = resultList;
		this.ctx = ctx;
	}
	
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return mResultList.get(groupPosition).getEquationList().get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return mResultList.get(groupPosition).getEquationList().get(childPosition).hashCode();
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		
		View v = convertView;
		
		if (v == null) {
			LayoutInflater inflater = (LayoutInflater)ctx.getSystemService
				      (Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(mItemLayoutId, parent, false);
		}
		
		final TextView resultTV = (TextView) v.findViewById(R.id.child_textView);
		
		final Equation res = mResultList.get(groupPosition).getEquationList().get(childPosition);
		
		resultTV.setText(Double.toString(res.getResult()));
		
		ImageButton deleteButton = (ImageButton)v.findViewById(R.id.child_delete_button);
		final int finalPos = childPosition;//childPosition needs to be final to be accessed in the onclicklistener
		deleteButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mResultList.remove(finalPos);
				if (mResultList.size() == 0){
					mResultList.add(new CalcResult());
					sResultLabelTV.setText(R.string.resultLabel);
				}
				notifyDataSetChanged();
			}
		} );
		
		ImageButton copyButton = (ImageButton)v.findViewById(R.id.child_copy_button);
		copyButton.setOnClickListener(new OnClickListener() {
			
			@TargetApi(Build.VERSION_CODES.HONEYCOMB)//don't worry we'll check this programatically
			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				if (android.os.Build.VERSION.SDK_INT >= 
						android.os.Build.VERSION_CODES.HONEYCOMB){
					android.content.ClipboardManager cm = 
							(android.content.ClipboardManager)ctx.getSystemService(Context.CLIPBOARD_SERVICE);
					ClipData clip = ClipData.newPlainText(resultTV.getText(),//label
														  resultTV.getText());//text
					cm.setPrimaryClip(clip);
				} else{
					android.text.ClipboardManager cm = 
							(android.text.ClipboardManager)ctx.getSystemService(Context.CLIPBOARD_SERVICE);
					cm.setText(resultTV.getText());
				}
			}
		});
		
		//setup on click listener for child item
		//clicking the item toggles only showing result and showing full eq
		View.OnClickListener switchEQListener = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (res.isFullEqActive()){
					resultTV.setText(Double.toString(res.getResult()));
					res.setIsFullEqActive(false);
				}
				else{
					resultTV.setText(res.getFullEquation());
					res.setIsFullEqActive(true);
				}
			}
		};
		
		v.setOnClickListener(switchEQListener);
		
		return v;
		
	}
	
	@Override
	public int getChildrenCount(int groupPosition) {
		int size = mResultList.get(groupPosition).getEquationList().size();
		Log.i(TAG, "Child for group ["+groupPosition+"] is ["+size+"]");
		return size;
	}

	@Override
	public Object getGroup(int groupPosition) {
		return mResultList.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
	  return mResultList.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return mResultList.get(groupPosition).hashCode();
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {

		View v = convertView;
		
		if (v == null) {
			LayoutInflater inflater = (LayoutInflater)ctx.getSystemService
				      (Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.group_item, parent, false);
		}
		
		sResultLabelTV = (TextView) v.findViewById(R.id.group_labelTextView);
		
		TextView resultTV = (TextView) v.findViewById(R.id.group_textView);
		
		CalcResult res = mResultList.get(groupPosition);
		
		int tvWidth = resultTV.getWidth();
		String currResultString = Double.toString(res.getCurrentResult());
//		resultTV.setText("Result:" +
//						genSpaces(tvWidth - currResultString.length() - 7 -20) + //7 for the chars in "Result:"
		resultTV.setText(currResultString);
		
		return v;

	}

	private String genSpaces(int num){
		String spaces = "";
		if (num<0){
			return "";
		}
		for (int i = 0; i < num; i++){
			spaces = spaces.concat(" ");
		}
		return spaces;
	}
	
	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

}
