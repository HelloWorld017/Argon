package org.He.W.project_argon;

public class GamePureArgon extends GameArgon {

	public GamePureArgon(boolean hidden, boolean rotating, boolean origin) {
		super(false, false, false);
	}
	
	@Override
	public void startHighlight(){
		if(gameStatus != EnumStatus.Ingame){
			throw new IllegalStateException();
		}
		isHighlighting = false;
	}

}
