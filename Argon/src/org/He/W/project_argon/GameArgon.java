package org.He.W.project_argon;

import java.util.Random;

public class GameArgon {
	protected TimeManager timeManager;
	protected TimeManager highlightingTimeManager;
	private TimeThread timeThread;
	protected TimeThread highlightingTimeThread;
	private boolean isRotatingMode;
	private boolean isHiddenMode;
	protected boolean isHighlighting;
	private boolean isOriginMultiplier;
	private int currentCombo;
	protected int needsCombo;
	private long score;
	private double multiplier;
	private EnumRotation currentRotation;
	protected EnumStatus gameStatus;
	private Random r;
	private EnumDirection nextEnumDirection;
	protected EnumDirection currentEnumDirection;
	
	public GameArgon(boolean hidden, boolean rotating, boolean origin){
		gameStatus = EnumStatus.Prepared;
		timeManager = new TimeManager(this);
		timeThread = new TimeThread(timeManager);
		isHiddenMode = hidden;
		isRotatingMode = rotating;
		isOriginMultiplier = origin;
		currentCombo = 0;
		needsCombo = 10;
		score = 0;
		multiplier = 1;
		currentRotation = EnumRotation.rot0;
		r = new Random();
		nextEnumDirection = EnumDirection.findByInteger(r.nextInt(4));
		if(isHiddenMode){
			multiplier += 0.2;
		}
		
		if(isRotatingMode){
			multiplier += 0.1;
		}
		
		if(isHiddenMode && isRotatingMode){
			multiplier += 0.2;
		}
		highlightingTimeManager = new TimeManager(5, new ThreadEndHighlight(this));
		highlightingTimeThread = new TimeThread(highlightingTimeManager);
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
	
	public void userSelected(EnumDirection selected){
		boolean isSelectedTrue;
		
		if(selected.equals(currentEnumDirection)){
			isSelectedTrue = true;
		}else{
			isSelectedTrue = false;
		}
		if(gameStatus != EnumStatus.Ingame){
			throw new IllegalStateException();
		}
		if(isSelectedTrue){
			if(currentEnumDirection.equals(EnumDirection.org) && isOriginMultiplier){
				score += 3;
			}
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
			
			if(isOriginMultiplier){
				timeManager.subtractTime(5);
			}
		}
		if(currentCombo % needsCombo == 0){
			isHighlighting = true;
			highlightingTimeThread.run();
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
	
	public void rotate(){
		if(gameStatus != EnumStatus.Ingame){
			throw new IllegalStateException();
		}
		if(r.nextBoolean()){
			currentRotation = EnumRotation.getRotation(currentRotation, true);
		}else{
			currentRotation = EnumRotation.getRotation(currentRotation, false);
		}
		
	}
	
	public long calculateScore(){
		
		if(!isHighlighting){return Math.round((2 * currentCombo + 50) / 25 * multiplier);}
		else{return Math.round((2 * currentCombo + 50) / 12.5 * multiplier);}
		
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
