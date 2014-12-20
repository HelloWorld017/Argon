package org.He.W.project_argon;

public class ThreadAdventureEndHighlight extends Thread{
	private GameArgon currentGame;
	
	public ThreadAdventureEndHighlight(GameArgon currentGame){
		this.currentGame = currentGame;
	}
	
	public void run(){
		currentGame.highlightingTimeManager.reset();
		currentGame.endHighlight();
		currentGame.needsCombo += 5;
	}
	
}
