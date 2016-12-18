package edu.nju.wsj.libgdx.spirit;

import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Scythe extends Actor{
	private static final String TAG = "Scythe";
	
	private TextureRegion[] walkFrames;
	private Animation animation;	
	private float stateTime;
	private TextureRegion currentFrame;
	
	private int margin =2;// Ѫ��������֮��ļ��
	private int pixHeight =10;// Ѫ���߶�
	private int maxHp = 2;// ��Ѫ��
	private int currentHp = maxHp;// ��ǰѪ��
	
	private int curCount = maxHp;
	private int curHitPower = maxHp / 2;

	public Scythe(AtlasRegion atlasRegion, int titleWidth, int titleHeight, int type){
		super();
		this.setWidth(titleWidth);//���ø߶�
		this.setHeight(titleHeight);//���ÿ��
		walkFrames = new TextureRegion[4];//��ȡ�ڶ��е�4֡
		if(TextureManager.getInstance().getTexture(type + "_0") == null){
			TextureRegion[][] temp = atlasRegion.split((int)titleWidth, (int)titleHeight);//�ָ�ͼ��
			for (int i = 0; i < 4; i++) {
				walkFrames[i] = temp[1][i];
				TextureManager.getInstance().addTexture(type + "_" + i, walkFrames[i]);
			}
		}else{for (int i = 0; i < 4; i++) {
			walkFrames[i] = TextureManager.getInstance().getTexture(type + "_" + i);
		}
		}
		animation = new Animation(0.1f, walkFrames);//����������֡���0.1
	}
	
	public void setAnimationDuration(float duration){
		if(animation != null){
			animation.setFrameDuration(duration / walkFrames.length / 10);
		}
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		stateTime += Gdx.graphics.getDeltaTime();// ��ȡ��ʱ��
		currentFrame = animation.getKeyFrame(stateTime, true);// ��ȡ��ǰ�ؼ�֡
		batch.draw(currentFrame, this.getX(), this.getY(), this.getWidth(), this.getHeight());// ����

		if(mPixmap == null){
			mPixmap = new Pixmap(64, 8, Format.RGBA8888);// ����һ��64*8��ͼƬ
			mPixmap.setColor(Color.BLACK);// ������ɫΪ��ɫ
			mPixmap.drawRectangle(0, 0, (int) getWidth(), pixHeight);// ���Ʊ߿�
			Texture pixmaptex = new Texture(mPixmap);// ����ͼƬ
			mTextureRegion = new TextureRegion(pixmaptex, (int) getWidth(), pixHeight);// �и�ͼƬ	

			mPixmapRed = new Pixmap((int)getWidth(), 8, Format.RGBA8888);// ����һ��64*8��ͼƬ
			mPixmapRed.setColor(Color.RED);// ������ɫΪ��ɫ
			int curwidth = (int) (getWidth() * currentHp / maxHp * 1.0f);
			mPixmapRed.fillRectangle(0, 1, curwidth, pixHeight -2);// ����Ѫ��
			Texture pixmaptexred = new Texture(mPixmapRed);// ����ͼƬ
			mTextureRegionRed = new TextureRegion(pixmaptexred, (int) getWidth(), pixHeight);// �и�ͼƬ
		}
		batch.draw(mTextureRegion, this.getX(), this.getY() + (int) getHeight() + this.margin);// ����	
		batch.draw(mTextureRegionRed, this.getX(), this.getY() + (int) getHeight() + this.margin);// ����		
	}
	
	private Pixmap mPixmap = null;
	private Pixmap mPixmapRed = null;
	private TextureRegion mTextureRegion = null;
	private TextureRegion mTextureRegionRed = null;

	public void beAttacked(int damage) {
		int tmpcurrentHp = currentHp;
		if (this.currentHp > damage) {// ���Ѫ�������˺��Ϳ۳���Ӧ��ֵ
			currentHp = currentHp - damage;
		} else if (this.currentHp > 0) {// ���Ѫ��С���˺���������Ѫ������Ѫ������
			currentHp = 0;
		}
		if(tmpcurrentHp != currentHp){
			int curwidth = (int) (getWidth() * currentHp / (maxHp * 1.0f));
			Log.d(TAG, "      beAttacked()      curwidth = " + curwidth);
			if(mPixmapRed != null){
				mPixmapRed.dispose();
			}
			mPixmapRed = new Pixmap((int)getWidth(), 8, Format.RGBA8888);// ����һ��64*8��ͼƬ
			mPixmapRed.setColor(Color.RED);// ������ɫΪ��ɫ
			mPixmapRed.fillRectangle(0, 1, curwidth, pixHeight -2);// ����Ѫ��
			Texture pixmaptexred = new Texture(mPixmapRed);// ����ͼƬ
			mTextureRegionRed = new TextureRegion(pixmaptexred, (int) getWidth(), pixHeight);// �и�ͼƬ
		}
	}

	public Boolean isAlive() {
		return this.currentHp > 0;
	}
	
	@Override
	public void clear() {
		super.clear();
		if(mPixmap != null){
			mPixmap.dispose();
			mPixmap = null;
		}
		if(mPixmapRed != null){
			mPixmapRed.dispose();
			mPixmapRed = null;
		}
	}
	

	/**
	 * ��ȡ������ʱ�ɻ�õķ���
	 * */
	public int getCurCount() {
		return curCount;
	}
	/**
	 * ���ñ�����ʱ�ɻ�õķ���
	 * */
	public void setCurCount(int curCount) {
		this.curCount = curCount;
	}

	/**
	 * ��ȡ�ɶ������ɵ��˺�
	 * */
	public int getCurHitPower() {
		return curHitPower;
	}
	/**
	 * ���ÿɶ������ɵ��˺�
	 * */
	public void setCurHitPower(int curHitPower) {
		this.curHitPower = curHitPower;
	}
}
