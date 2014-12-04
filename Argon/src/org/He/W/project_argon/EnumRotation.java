package org.He.W.project_argon;

public enum EnumRotation {
	rot0,rot90,rot180,rot270;
	
	public static EnumRotation getRotation(EnumRotation currentRotation, boolean isCW){
		switch(currentRotation){
			case rot0:
				if(isCW){
					return rot90;
				}else{
					return rot270;
				}
			case rot90:
				if(isCW){
					return rot180;
				}else{
					return rot0;
				}
			case rot180:
				if(isCW){
					return rot270;
				}else{
					return rot90;
				}
			case rot270:
				if(isCW){
					return rot0;
				}else{
					return rot180;
				}
			default:return rot0;
		}
	}
}
