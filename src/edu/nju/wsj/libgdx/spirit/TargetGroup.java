package edu.nju.wsj.libgdx.spirit;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class TargetGroup extends Group{
	
	private AtlasRegion mRegion;
	private int mWidth;
	private int mHeight;
	private int mLeft;
	private int mRight;
	private int mTop;
	public TargetGroup(AtlasRegion region, int w, int h, int left, int right, int top){
		super();
		mRegion = region;
		mWidth = w;
		mHeight = h;
		mLeft = left;
		mRight = right;
		mTop = top - mHeight;
		addMan();
		addMan();
		addMan();
		addMan();
		addMan();
	}
	
	public void addMan() {
		int tempY = 0;
		Image image = new Image(mRegion);
		float tmp_value = image.getWidth() / image.getHeight();
		image.setWidth(mHeight * tmp_value);
		image.setHeight(mHeight);
		image.setX((mRight - image.getWidth()));
		// 开始判断Y值是否符合要求
		boolean flag = false;
		do {
			flag = false;
			tempY = MathUtils.random(0, mTop);// 生成Y值

			Actor[] actors = this.getChildren().begin();// 获取当前已有的怪兽对象
			for (int j = 0; j < this.getChildren().size; j++) {
				Actor tempActor = actors[j];
				if (tempY == tempActor.getY()) {// 如果Y值相等，比如重合，所以舍弃,重新生成
					flag = true;
					break;
				} else if (tempY < tempActor.getY()) {// 如果生成的Y值小于当前怪兽的Y值，则判断生成的Y值加上高度后是否合适
					if ((tempY + mRegion.getRegionHeight()) >= tempActor.getY()) {
						flag = true;
						break;
					}
				} else {// 如果生成的Y值大于当前怪兽的Y值，则判断当前怪兽的Y值加上高度后是否合适
					if ((tempY - mRegion.getRegionHeight()) < tempActor.getY()) {
						flag = true;
						break;
					}
				}
			}
		} while (flag);
		image.setY(tempY);
		AddMove(image, MathUtils.random(3f,8f));//怪兽移动效果
		addActor(image);
	}

	private void AddMove(Actor actor, float time) {
		actor.addAction(Actions.moveTo(0, actor.getY(), time));
//		actor.setRotation(90);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		Actor[] actors = this.getChildren().begin();// 获取当前已有的怪兽对象
		for (int j = 0; j < this.getChildren().size; j++) {
			Actor tempActor = actors[j];
			if(tempActor.getX() < mLeft){
				tempActor.remove();
				tempActor.clear();
				addMan();
			}
		}
	}
}
