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
		// ��ʼ�ж�Yֵ�Ƿ����Ҫ��
		boolean flag = false;
		do {
			flag = false;
			tempY = MathUtils.random(0, mTop);// ����Yֵ

			Actor[] actors = this.getChildren().begin();// ��ȡ��ǰ���еĹ��޶���
			for (int j = 0; j < this.getChildren().size; j++) {
				Actor tempActor = actors[j];
				if (tempY == tempActor.getY()) {// ���Yֵ��ȣ������غϣ���������,��������
					flag = true;
					break;
				} else if (tempY < tempActor.getY()) {// ������ɵ�YֵС�ڵ�ǰ���޵�Yֵ�����ж����ɵ�Yֵ���ϸ߶Ⱥ��Ƿ����
					if ((tempY + mRegion.getRegionHeight()) >= tempActor.getY()) {
						flag = true;
						break;
					}
				} else {// ������ɵ�Yֵ���ڵ�ǰ���޵�Yֵ�����жϵ�ǰ���޵�Yֵ���ϸ߶Ⱥ��Ƿ����
					if ((tempY - mRegion.getRegionHeight()) < tempActor.getY()) {
						flag = true;
						break;
					}
				}
			}
		} while (flag);
		image.setY(tempY);
		AddMove(image, MathUtils.random(3f,8f));//�����ƶ�Ч��
		addActor(image);
	}

	private void AddMove(Actor actor, float time) {
		actor.addAction(Actions.moveTo(0, actor.getY(), time));
//		actor.setRotation(90);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		Actor[] actors = this.getChildren().begin();// ��ȡ��ǰ���еĹ��޶���
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
