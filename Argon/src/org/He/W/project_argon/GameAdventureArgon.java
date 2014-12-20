package org.He.W.project_argon;

public class GameAdventureArgon extends GameArgon{

	public GameAdventureArgon(boolean hidden, boolean rotating, boolean origin) {
		super(hidden, rotating, origin);
		this.timeManager.setReqTime(1);
		this.highlightingTimeManager = null;
		this.highlightingTimeThread = null;
		this.highlightingTimeManager = new TimeManager(5, new ThreadAdventureEndHighlight(this));
		this.highlightingTimeThread = new TimeThread(highlightingTimeManager);
	}
	
	@Override
	public void userSelected(EnumDirection selectedDirection){
		super.userSelected(selectedDirection);
		if(selectedDirection.equals(this.currentEnumDirection)){
			this.timeManager.addTime(1);
		}
	}

}
