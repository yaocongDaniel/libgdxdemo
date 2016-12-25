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
	 * ��ȡ��ǰ��������
	 * */
	public int getCurMaxDartsNum(){
		return mCurMaxDartsNum;
	}
	/**
	 * ���õ�ǰ��������
	 * */
	public void setCurMaxDartsNum(int num) {
		this.mCurMaxDartsNum = num;
	}

	/**
	 * ���õ�ǰ�����ٶ�
	 * */
	public float getCurDartsSpeed() {
		return mCurDartsSpeed;
	}
	/**
	 * ���õ�ǰ�����ٶ�
	 * */
	public void setCurDartsSpeed(float speed) {
		this.mCurDartsSpeed = speed;
	}

	/**
	 * ��ȡ��ǰ������С�ƶ��ٶ�
	 * */
	public float getMinScytheSpeed() {
		return mMinScytheSpeed;
	}
	/**
	 * ���õ�ǰ������С�ƶ��ٶ�
	 * */
	public void setMinScytheSpeed(float speed) {
		this.mMinScytheSpeed = speed;
	}
	/**
	 * ��ȡ��ǰ��������ƶ��ٶ�
	 * */
	public float getMaxScytheSpeed() {
		return mMaxScytheSpeed;
	}
	/**
	 * ���õ�ǰ��������ƶ��ٶ�
	 * */
	public void setMaxScytheSpeed(float speed) {
		this.mMaxScytheSpeed = speed;
	}

	/**
	 * ��ȡ��ǰ��Ϸ����
	 * */
	public int getTotalCount() {
		return mTotalCount;
	}
	/**
	 * ���õ�ǰ��Ϸ����
	 * */
	public void setTotalCount(int totalcount) {
		this.mTotalCount = totalcount;
	}
	/**
	 * �ۼƵ�ǰ��Ϸ����
	 * */
	public void setTotalCountPerHit(int curhitcount) {
		this.mTotalCount += curhitcount;
	}

	/**
	 * ��ȡ��ǰ���Hp
	 * */
	public int getPlayHp() {
		return mPlayHp;
	}
	/**
	 * ���õ�ǰ���Hp
	 * */
	public void setPlayHp(int hp) {
		this.mPlayHp = hp;
	}
	/**
	 * �ۼƵ�ǰ���Hp
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
