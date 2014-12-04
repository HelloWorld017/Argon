package org.He.W.project_argon;

import java.util.Random;

public class GameArgon {
	private TimeManager timeManager;
	private TimeThread timeThread;
	private boolean isRotatingMode;
	private boolean isHiddenMode;
	private boolean isHighlighting;
	private int currentCombo;
	private long score;
	private double multiplier;
	private EnumRotation currentRotation;
	private EnumStatus gameStatus;
	private Random r;
	private EnumDirection nextEnumDirection;
	private EnumDirection currentEnumDirection;
	
	public GameArgon(boolean hidden, boolean rotating){
		gameStatus = EnumStatus.Prepared;
		timeManager = new TimeManager(this);
		timeThread = new TimeThread(timeManager);
		isHiddenMode = hidden;
		isRotatingMode = rotating;
		currentCombo = 0;
		score = 0;
		multiplier = 1;
		currentRotation = EnumRotation.rot0;
		r = new Random();
		nextEnumDirection = EnumDirection.findByInteger(r.nextInt(4));
		if(isHiddenMode){
			multiplier += 0.3;
		}
		
		if(isRotatingMode){
			multiplier += 0.1;
		}
		
		if(isHiddenMode && isRotatingMode){
			multiplier += 0.1;
		}
	}
	
	public void startGame() throws InterruptedException{
		for(int a = 0; a < 3; a++){
			//TODO Add countdown rendering
			Thread.sleep(1000);
		}
		timeThread.start();
		gameStatus = EnumStatus.Ingame;
	}
	
	public void pauseGame() throws InterruptedException{
		timeThread.wait();
		gameStatus = EnumStatus.Paused;
	}
	
	public void resumeGame(){
		timeThread.notify();
		gameStatus = EnumStatus.Ingame;
	}
	
	public void forceEndGame(){
		timeThread.requestStop();
		gameStatus = EnumStatus.Finished;
	}
	
	public void naturalEndGame(){
		timeThread.requestStop();
		gameStatus = EnumStatus.Finished;
	}
	
	public void userSelected(boolean isSelectedTrue){
		if(gameStatus != EnumStatus.Ingame){
			throw new IllegalStateException();
		}
		if(isSelectedTrue){
				if(isHighlighting){
					timeManager.addTime(3);
				}else{
					currentCombo++;
					//Combo is not growing when you're in highlighting
				}
			score += calculateScore();
		}else{
			currentCombo = 0;
			timeManager.subtractTime(3);
			if(isHighlighting){
				timeManager.subtractTime(2);
				//Time decrease more when you're in highlighting
			}
		}
		if(currentCombo % 10 == 0){
			isHighlighting = true;
			timeManager.startHighlightingCalculation();
		}
		getNext();
	}
	
	public void getNext(){
		if(isHighlighting){
			currentEnumDirection = EnumDirection.org;
		}else{
			currentEnumDirection = nextEnumDirection;
			nextEnumDirection = EnumDirection.findByInteger(r.nextInt(4));
		}
	}
	
	public void startHighlight(){
		if(gameStatus != EnumStatus.Ingame){
			throw new IllegalStateException();
		}
		isHighlighting = true;
	}
	
	public void endHighlight(){
		if(gameStatus != EnumStatus.Ingame){
			throw new IllegalStateException();
		}
		isHighlighting = false;
	}
	
	public void rotate(boolean isClockWise){
		if(gameStatus != EnumStatus.Ingame){
			throw new IllegalStateException();
		}
		currentRotation = EnumRotation.getRotation(currentRotation, isClockWise);
	}
	
	public long calculateScore(){
		
		if(!isHighlighting){return Math.round((currentCombo + 25) / 25 * multiplier);}
		else{return Math.round((currentCombo + 12.5) / 12.5 * multiplier);}
		
	}
	
	public boolean isHidden(){
		return isHiddenMode;
	}
	
	public boolean isRotating(){
		return isRotatingMode;
	}
	
	public long getGameScore(){
		return score;
	}
	
	public EnumDirection getCurrentDirection(){
		return currentEnumDirection;
	}
	
	public EnumDirection getNextDirection(){
		return nextEnumDirection;
	}
}
