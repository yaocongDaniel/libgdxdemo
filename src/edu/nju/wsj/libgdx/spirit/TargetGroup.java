package edu.nju.wsj.libgdx.spirit;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class TargetGroup extends Group{
	
	private AtlasRegion region;
	public TargetGroup(AtlasRegion region){
		super();
	}
	
	private void init(){
//		int tempY =0;
//		for (int i =0; i <3; i++) {
//		Image image =new Image(region);
//		image.x = (480 - image.getWidth());
//		// ��ʼ�ж�Yֵ�Ƿ����Ҫ��
//		boolean flag =false;
//		do {
//		flag =false;
//		tempY = MathUtils.random(minY, maxY);// ����Yֵ
//
//		Actor[] actors = this.getChildren().begin();// ��ȡ��ǰ���еĹ��޶���
//		for (int j =0; j <this.getChildren().size; j++) {
//		Actor tempActor = actors[j];
//		if (tempY == tempActor.getY()) {// ���Yֵ��ȣ������غϣ���������,��������
//		flag =true;
//		break;
//		}else if (tempY < tempActor.getY()) {// ������ɵ�YֵС�ڵ�ǰ���޵�Yֵ�����ж����ɵ�Yֵ���ϸ߶Ⱥ��Ƿ����
//		if ((tempY + region.getRegionHeight()) >= tempActor
//		.getY()) {
//		flag =true;
//		break;
//		}
//		}else {// ������ɵ�Yֵ���ڵ�ǰ���޵�Yֵ�����жϵ�ǰ���޵�Yֵ���ϸ߶Ⱥ��Ƿ����
//		if (tempY){
//			
//		}
//		}
//		}
//		}
//		}
	}
}
