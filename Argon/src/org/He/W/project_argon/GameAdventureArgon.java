package org.He.W.project_argon;

public class GameAdventureArgon extends GameArgon{

	public GameAdventureArgon(boolean hidden, boolean rotating, boolean origin) {
		super(hidden, rotating, origin);
		this.timeManager.setReqTime(1);
	}
	
	@Override
	public void userSelected(EnumDirection selectedDirection){
		super.userSelected(selectedDirection);
		if(selectedDirection.equals(super.currentEnumDirection)){
			this.timeManager.addTime(1);
		}
	}

	//TODO Update AdventureMode
}
