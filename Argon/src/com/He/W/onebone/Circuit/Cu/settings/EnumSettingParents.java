package com.He.W.onebone.Circuit.Cu.settings;

import org.He.W.project_argon.activity.MainActivity;

public enum EnumSettingParents {
	LooknFeel("Look & Feel"), Sound("Sound");
	
	private String namae;
	
	private EnumSettingParents(String namae){
		this.namae = namae;
	}
	
	private EnumSettingParents(int resid){
		namae = MainActivity.getStringbyResid(resid);
	}
	
	public String getName(){
		return namae;
	}
}
