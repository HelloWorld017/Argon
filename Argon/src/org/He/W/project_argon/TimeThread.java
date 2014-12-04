package org.He.W.project_argon;

public class TimeThread extends Thread{
	
	private TimeManager time;
	private boolean isCloseRequested = false;
	
	public TimeThread(TimeManager time){
		this.time = time;
	}
	
	public void run(){
		try {
			while(!isCloseRequested){
				Thread.sleep(1000);
				time.tick();
			}
		
		} catch (InterruptedException e) {
			LogException.log(e);
		}
	
	}
	
	public void requestStop(){
		isCloseRequested = true;
	}
	
}
