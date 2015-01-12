package org.He.W.project_argon;

import org.He.W.project_argon.activity.MainActivity;

public enum EnumModes {
	pure(R.array.pure), classic(R.array.classic), adventure(R.array.adventure);
	
	private String modeName;
	private String modeTip;
	
	private EnumModes(int resid){
		String[] modeArray = MainActivity.getStringArraybyResid(resid);
		modeName = modeArray[0];
		modeTip = modeArray[1];
		modeArray = null;
	}
	
	public String getName(){
		return modeName;
	}
	
	public String getTip(){
		return modeTip;
	}
}
