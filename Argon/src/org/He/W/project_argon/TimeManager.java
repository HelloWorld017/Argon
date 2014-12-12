package org.He.W.project_argon;

public class TimeManager {
	
	private int requirementTime;
	private int currentTime;
	private int fixedCurrentTime;
	private Thread endWork;
	
	public TimeManager(GameArgon currentGame){
		requirementTime = 60;
		currentTime = 0;
		fixedCurrentTime = 0;
		this.endWork = new ThreadEndGame(currentGame);
	}
	public TimeManager(int requirement, Thread endWork){
		requirementTime = requirement;
		currentTime = 0;
		fixedCurrentTime = 0;
		this.endWork = endWork;
	}
	
	public synchronized void setReqTime(int reqTime){
		requirementTime = reqTime;
		calculateIsGameEnded();
	}
	public synchronized void reset(){
		fixedCurrentTime = 0;
		currentTime = 0;
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
		currentTime++;
		fixedCurrentTime++;
		calculateIsGameEnded();
	}
	
	public int getWholeTime(){
		return fixedCurrentTime;
	}
	
	public void calculateIsGameEnded(){
			if(currentTime >= requirementTime){
				endWork.run();
			}	
	}
}
