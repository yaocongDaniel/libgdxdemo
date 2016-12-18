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
	
	private int margin =2;// 血条和人物之间的间隔
	private int pixHeight =10;// 血条高度
	private int maxHp = 2;// 总血量
	private int currentHp = maxHp;// 当前血量
	
	private int curCount = maxHp;
	private int curHitPower = maxHp / 2;

	public Scythe(AtlasRegion atlasRegion, int titleWidth, int titleHeight, int type){
		super();
		this.setWidth(titleWidth);//设置高度
		this.setHeight(titleHeight);//设置宽度
		walkFrames = new TextureRegion[4];//获取第二行的4帧
		if(TextureManager.getInstance().getTexture(type + "_0") == null){
			TextureRegion[][] temp = atlasRegion.split((int)titleWidth, (int)titleHeight);//分割图块
			for (int i = 0; i < 4; i++) {
				walkFrames[i] = temp[1][i];
				TextureManager.getInstance().addTexture(type + "_" + i, walkFrames[i]);
			}
		}else{for (int i = 0; i < 4; i++) {
			walkFrames[i] = TextureManager.getInstance().getTexture(type + "_" + i);
		}
		}
		animation = new Animation(0.1f, walkFrames);//创建动画，帧间隔0.1
	}
	
	public void setAnimationDuration(float duration){
		if(animation != null){
			animation.setFrameDuration(duration / walkFrames.length / 10);
		}
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		stateTime += Gdx.graphics.getDeltaTime();// 获取总时间
		currentFrame = animation.getKeyFrame(stateTime, true);// 获取当前关键帧
		batch.draw(currentFrame, this.getX(), this.getY(), this.getWidth(), this.getHeight());// 绘制

		if(mPixmap == null){
			mPixmap = new Pixmap(64, 8, Format.RGBA8888);// 生成一张64*8的图片
			mPixmap.setColor(Color.BLACK);// 设置颜色为黑色
			mPixmap.drawRectangle(0, 0, (int) getWidth(), pixHeight);// 绘制边框
			Texture pixmaptex = new Texture(mPixmap);// 生成图片
			mTextureRegion = new TextureRegion(pixmaptex, (int) getWidth(), pixHeight);// 切割图片	

			mPixmapRed = new Pixmap((int)getWidth(), 8, Format.RGBA8888);// 生成一张64*8的图片
			mPixmapRed.setColor(Color.RED);// 设置颜色为红色
			int curwidth = (int) (getWidth() * currentHp / maxHp * 1.0f);
			mPixmapRed.fillRectangle(0, 1, curwidth, pixHeight -2);// 绘制血条
			Texture pixmaptexred = new Texture(mPixmapRed);// 生成图片
			mTextureRegionRed = new TextureRegion(pixmaptexred, (int) getWidth(), pixHeight);// 切割图片
		}
		batch.draw(mTextureRegion, this.getX(), this.getY() + (int) getHeight() + this.margin);// 绘制	
		batch.draw(mTextureRegionRed, this.getX(), this.getY() + (int) getHeight() + this.margin);// 绘制		
	}
	
	private Pixmap mPixmap = null;
	private Pixmap mPixmapRed = null;
	private TextureRegion mTextureRegion = null;
	private TextureRegion mTextureRegionRed = null;

	public void beAttacked(int damage) {
		int tmpcurrentHp = currentHp;
		if (this.currentHp > damage) {// 如果血量大于伤害就扣除响应数值
			currentHp = currentHp - damage;
		} else if (this.currentHp > 0) {// 如果血量小于伤害但是仍有血量就让血量归零
			currentHp = 0;
		}
		if(tmpcurrentHp != currentHp){
			int curwidth = (int) (getWidth() * currentHp / (maxHp * 1.0f));
			Log.d(TAG, "      beAttacked()      curwidth = " + curwidth);
			if(mPixmapRed != null){
				mPixmapRed.dispose();
			}
			mPixmapRed = new Pixmap((int)getWidth(), 8, Format.RGBA8888);// 生成一张64*8的图片
			mPixmapRed.setColor(Color.RED);// 设置颜色为红色
			mPixmapRed.fillRectangle(0, 1, curwidth, pixHeight -2);// 绘制血条
			Texture pixmaptexred = new Texture(mPixmapRed);// 生成图片
			mTextureRegionRed = new TextureRegion(pixmaptexred, (int) getWidth(), pixHeight);// 切割图片
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
	 * 获取被消灭时可获得的分数
	 * */
	public int getCurCount() {
		return curCount;
	}
	/**
	 * 设置被消灭时可获得的分数
	 * */
	public void setCurCount(int curCount) {
		this.curCount = curCount;
	}

	/**
	 * 获取可对玩家造成的伤害
	 * */
	public int getCurHitPower() {
		return curHitPower;
	}
	/**
	 * 设置可对玩家造成的伤害
	 * */
	public void setCurHitPower(int curHitPower) {
		this.curHitPower = curHitPower;
	}
}
