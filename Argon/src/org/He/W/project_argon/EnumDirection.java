package org.He.W.project_argon;

public enum EnumDirection {
	up, down, left, right, org;
	
	public static EnumDirection findByInteger(int i){
		return EnumDirection.values()[i];
		
	}
}
