package org.He.W.project_argon;

import org.He.W.project_argon.activity.MainActivity;

public enum EnumItems {
	hidden(R.array.hidden), rotation(R.array.rotation), origin(R.array.origin);
	
	private String itemName;
	private String itemDesc;
	private String itemTip;
	
	private EnumItems(int resid){
		String[] itemArray = MainActivity.getStringArraybyResid(resid);
		itemName = itemArray[0];
		itemDesc = itemArray[1];
		itemTip = itemArray[2];
		itemArray = null;
	}
	
	public String getName(){
		return itemName;
	}

	public String getDesc(){
		return itemDesc;
	}
	
	public String getTip(){
		return itemTip;
	}
}



