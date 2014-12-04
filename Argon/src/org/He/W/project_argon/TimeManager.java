package org.He.W.project_argon;

public class TimeManager {
	
	private int requirementTime;
	private int currentTime;
	private GameArgon currentGame;
	private boolean isCalculatingHighlighting;
	private int highlightingTime;
	
	public TimeManager(GameArgon currentGame){
		this.currentGame = currentGame;
		requirementTime = 60;
		currentTime = 0;
		isCalculatingHighlighting = false;
		highlightingTime = 0;
	}
	
	public synchronized void addTime(int time){
		if(currentTime - time >= 0){
			currentTime -= time;
		}else{
			currentTime = 0;
		}
	}
	
	public synchronized void subtractTime(int time){
		currentTime += time;
		calculateIsGameEnded();
	}
	
	public synchronized int getLeftTime(){
			return requirementTime - currentTime;
	}
	
	public synchronized void tick(){
		if(isCalculatingHighlighting){
			highlightingTime++;
			
			if(highlightingTime >= 5){
				currentGame.endHighlight();
				highlightingTime = 0;
				isCalculatingHighlighting = false;
			}
		}
		currentTime++;
		calculateIsGameEnded();
	}
	
	public void startHighlightingCalculation(){
		isCalculatingHighlighting = true;
	}
	
	public void calculateIsGameEnded(){
			if(currentTime >= requirementTime){
				currentGame.naturalEndGame();
			}	
	}
}
