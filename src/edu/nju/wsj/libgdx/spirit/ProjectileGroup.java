package edu.nju.wsj.libgdx.spirit;

import android.util.Log;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class ProjectileGroup extends Group{
	private static final String TAG = "ProjectileGroup";
	
	public static final String PROJECTILEGROUPNAME = "projectiles";
	
	private int mMaxProjectile = 2;
	private AtlasRegion mRegion;
	private int mWidth;
	private int mHeight;
	private int mLeft;
	private int mRight;
	private int mTop;
	private int mStart_X;
	private int mStart_Y;
	public ProjectileGroup(AtlasRegion region, int w, int h, int left, int right, int top){
		super();
		mRegion = region;
		mWidth = w;
		mHeight = h;
		mLeft = left;
		mRight = right;
		mTop = top - mHeight;
		setName(PROJECTILEGROUPNAME);
	}
	
	public void setStartPosition(int x, int y){
		mStart_X = x;
		mStart_Y = y;
	}
	
	public void addProjectile(int x, int y) {
		if(getChildren().size >= mMaxProjectile){
			return;
		}
		Image image = new Image(mRegion);
		float tmp_value = image.getWidth() / image.getHeight();
		image.setWidth(mHeight * tmp_value);
		image.setHeight(mHeight);
		image.setPosition(mStart_X, mStart_Y);
		image.setOrigin(image.getWidth() /2, image.getHeight() /2);
		float time = x / (mRight * 1.0f);		
		AddMove(image, time, (int)(x - image.getWidth() / 2), (int)(y - image.getHeight() / 2));//怪兽移动效果
		addActor(image);
	}

	private void AddMove(Actor actor, float time, int x, int y) {
		actor.addAction(Actions.moveTo(x, y, time));
		actor.addAction(Actions.repeat(50, Actions.rotateBy(360, 0.8f)));//设置飞镖的旋转
	}
	
	private boolean checkAlive(Actor actor){
		if(actor.getActions().size == 1){
			return false;
		}
		return true;
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		Actor[] actors = getChildren().begin();// 获取当前已有的怪兽对象
		for (int j = 0; j < getChildren().size; j++) {
			Actor tempActor = actors[j];
			if(!checkAlive(tempActor)){
				tempActor.remove();
				tempActor.clear();
			}
		}
	}

	public static Boolean attackAlive(Actor target, Actor projectile) {
		Rectangle rectangle = new Rectangle(target.getX(), target.getY(),
				target.getWidth(), target.getHeight());// 创建一个矩形
		return rectangle.contains(
				projectile.getX() + projectile.getWidth() / 2,
				projectile.getY() + projectile.getHeight() / 2);// 判断是否在矩阵中，即是否击中
	}
}
