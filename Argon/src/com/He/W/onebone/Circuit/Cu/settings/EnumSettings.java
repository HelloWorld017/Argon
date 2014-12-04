package com.He.W.onebone.Circuit.Cu.settings;

import org.He.W.project_argon.R;
import org.He.W.project_argon.activity.MainActivity;

public enum EnumSettings {
	
	play_bgm(EnumSettingParents.Sound, R.string.setting_play_bgm, 2, 1, true, true, R.string.setting_play_bgm_tip, R.array.default_true_false);
	
	
	private EnumSettingParents parent;
	private int name;
	private int id;
	private boolean needsRestart;
	private boolean visibility;
	private int tip;
	private int values;
	private boolean isDefaultTrueFalse;
	private int origValue;
	
	private EnumSettings(EnumSettingParents parent, int name_resid, int id,  int origValue, boolean needsRestart, boolean visibility, int tip_resid, int values_resid){
		this.parent = parent;
		this.name = name_resid;
		this.id = id;
		this.needsRestart = needsRestart;
		this.visibility = visibility;
		this.tip = tip_resid;
		this.values = values_resid;
		this.origValue = origValue;
		this.isDefaultTrueFalse = false;
	}
	
	private EnumSettings(EnumSettingParents parent, int name_resid, int id, int origValue, boolean needsRestart, boolean visibility, int tip_resid){
		this.parent = parent;
		this.name = name_resid;
		this.id = id;
		this.needsRestart = needsRestart;
		this.visibility = visibility;
		this.tip = tip_resid;
		this.isDefaultTrueFalse = true;
		this.origValue = origValue;
	}
	
	public int getId(){
		return id;
	}
	
	public int getOriginalValue(){
		return origValue;
	}
	
	public String getName(){
		return MainActivity.getStringbyResid(name);
	}
	
	public String getTip(){
		return MainActivity.getStringbyResid(tip);
	}
	
	public String[] getValues(){
		return MainActivity.getStringArraybyResid(values);
	}
	
	public boolean needsRestart(){
		return needsRestart;
	}
	
	public boolean isVisible(){
		return visibility;
	}
	
	public boolean isUsingDefaultTrueFalse(){
		return isDefaultTrueFalse;
	}
	
	public EnumSettingParents getParent(){
		return parent;
	}
}
