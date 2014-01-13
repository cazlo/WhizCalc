package org.apaettie.android.whizcalc;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
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
		
		TextView resultTV = (TextView) v.findViewById(R.id.child_textView);
		
		Equation res = mResultList.get(groupPosition).getEquationList().get(childPosition);
		
		resultTV.setText(Double.toString(res.getResult()));
		
		Button deleteButton = (Button)v.findViewById(R.id.child_button);
		deleteButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		} );
		
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
