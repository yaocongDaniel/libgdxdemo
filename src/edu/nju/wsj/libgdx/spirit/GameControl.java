package edu.nju.wsj.libgdx.spirit;

public class GameControl {
	
	private int mCurMaxDartsNum = 3;
	private float mCurDartsSpeed = 1;

	private static GameControl mGameControl;	
	public static GameControl getInstance(){
		if(mGameControl == null){
			mGameControl = new GameControl();
		}
		return mGameControl;
	}
	
	private GameControl(){
	}
	
	public int getCurMaxDartsNum(){
		return mCurMaxDartsNum;
	}

	public void setCurMaxDartsNum(int mCurMaxDartsNum) {
		this.mCurMaxDartsNum = mCurMaxDartsNum;
	}

	public float getCurDartsSpeed() {
		return mCurDartsSpeed;
	}

	public void setCurDartsSpeed(float mCurDartsSpeed) {
		this.mCurDartsSpeed = mCurDartsSpeed;
	}
}
