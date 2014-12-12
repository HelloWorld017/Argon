package org.He.W.project_argon;

public class ThreadEndHighlight extends Thread {
	private GameArgon currentGame;
	
	public ThreadEndHighlight(GameArgon currentGame){
		this.currentGame = currentGame;
	}
	
	public void run(){
		currentGame.highlightingTimeManager.reset();
		currentGame.endHighlight();
	}
}
