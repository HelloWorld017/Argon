package org.He.W.project_argon;

public class ThreadEndGame extends Thread {
	private GameArgon currentGame;
	
	public ThreadEndGame(GameArgon currentGame){
		this.currentGame = currentGame;
	}
	
	public void run(){
		currentGame.naturalEndGame();
	}
}
