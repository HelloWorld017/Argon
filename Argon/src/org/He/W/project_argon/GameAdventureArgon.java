package org.He.W.project_argon;

public class GameAdventureArgon extends GameArgon{

	long currentTime = 0;
	long lastTime = 0;
	
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
	
	@Override
	public long calculateScore(){
		double timeMultiplier = (currentTime - lastTime) / 1000000000;
		if(!isHighlighting){
			return Math.round(((2 * currentCombo + 50) / 25 * multiplier)*timeMultiplier);
		}
		else{
			return Math.round(((2 * currentCombo + 50) / 12.5 * multiplier)*timeMultiplier);
		}
		
	}
	
	@Override
	public void startGame() throws InterruptedException{
		for(int a = 0; a < 3; a++){
			//TODO Add countdown rendering
			Thread.sleep(1000);
		}
		timeThread.start();
		gameStatus = EnumStatus.Ingame;
		lastTime = System.nanoTime();
	}

}
