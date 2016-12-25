package edu.nju.wsj.libgdx.spirit;

public class GameControl {
	
	private int mCurMaxDartsNum = 3;
	private float mCurDartsSpeed = 1;

	private float mMinScytheSpeed = 8;

	private float mMaxScytheSpeed = 12;

	private int mCurLevel = 1;
	private int mTotalCount = 0;
	private int mPlayHp = 50;
	
	private static GameControl mGameControl;	
	public static GameControl getInstance(){
		if(mGameControl == null){
			mGameControl = new GameControl();
		}
		return mGameControl;
	}
	
	private GameControl(){
	}
	
	/**
	 * 获取当前最多飞镖数
	 * */
	public int getCurMaxDartsNum(){
		return mCurMaxDartsNum;
	}
	/**
	 * 设置当前最多飞镖数
	 * */
	public void setCurMaxDartsNum(int num) {
		this.mCurMaxDartsNum = num;
	}

	/**
	 * 设置当前飞镖速度
	 * */
	public float getCurDartsSpeed() {
		return mCurDartsSpeed;
	}
	/**
	 * 设置当前飞镖速度
	 * */
	public void setCurDartsSpeed(float speed) {
		this.mCurDartsSpeed = speed;
	}

	/**
	 * 获取当前怪物最小移动速度
	 * */
	public float getMinScytheSpeed() {
		return mMinScytheSpeed;
	}
	/**
	 * 设置当前怪物最小移动速度
	 * */
	public void setMinScytheSpeed(float speed) {
		this.mMinScytheSpeed = speed;
	}
	/**
	 * 获取当前怪物最大移动速度
	 * */
	public float getMaxScytheSpeed() {
		return mMaxScytheSpeed;
	}
	/**
	 * 设置当前怪物最大移动速度
	 * */
	public void setMaxScytheSpeed(float speed) {
		this.mMaxScytheSpeed = speed;
	}

	/**
	 * 获取当前游戏分数
	 * */
	public int getTotalCount() {
		return mTotalCount;
	}
	/**
	 * 设置当前游戏分数
	 * */
	public void setTotalCount(int totalcount) {
		this.mTotalCount = totalcount;
	}
	/**
	 * 累计当前游戏分数
	 * */
	public void setTotalCountPerHit(int curhitcount) {
		this.mTotalCount += curhitcount;
	}

	/**
	 * 获取当前玩家Hp
	 * */
	public int getPlayHp() {
		return mPlayHp;
	}
	/**
	 * 设置当前玩家Hp
	 * */
	public void setPlayHp(int hp) {
		this.mPlayHp = hp;
	}
	/**
	 * 累计当前玩家Hp
	 * */
	public void setPlayHpPerHit(int perhithp) {
		if(this.mPlayHp - perhithp < 0){
			this.mPlayHp = 0;
		}else{
			this.mPlayHp -= perhithp;
		}
	}
	
	public void Update(){
		if(mPlayHp <= 0){
			//GameOver
		}
	}
}
