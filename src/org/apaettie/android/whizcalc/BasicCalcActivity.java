package org.apaettie.android.whizcalc;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.widget.ExpandableListView;

public class BasicCalcActivity extends SingleFragmentActivity {

	@Override
	public Fragment createFragment(){
		return new BasicCalcFragment();
	}
	
	public void onCreate(Bundle savedInstanceState){
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		
	}
	
	@Override
	@TargetApi(18)
	public void onWindowFocusChanged(boolean hasFocus){
		View basicCalcView = findViewById(R.id.fragmentContainer);
		ExpandableListView elv = (ExpandableListView) basicCalcView.findViewById(R.id.basicCalc_result_ListView);
//		int left = (int) (getResources().getDisplayMetrics().widthPixels 
//				   - TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60, getResources().getDisplayMetrics()));
//		int right = (int) (getResources().getDisplayMetrics().widthPixels 
//						   - TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics()));
		int left = elv.getWidth() - getDipsFromPixel(30f);
		int right = elv.getWidth() - getDipsFromPixel(5f);
		if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN_MR2){
			elv.setIndicatorBounds(left, right);
		} else{
			elv.setIndicatorBoundsRelative(left, right);
		}
	}
	
	private int getDipsFromPixel(float pixels){
		final float scale = getResources().getDisplayMetrics().density;
		return (int)(pixels * scale + 0.5f);
	}

}
