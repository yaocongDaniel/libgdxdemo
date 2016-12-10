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
//		// 开始判断Y值是否符合要求
//		boolean flag =false;
//		do {
//		flag =false;
//		tempY = MathUtils.random(minY, maxY);// 生成Y值
//
//		Actor[] actors = this.getChildren().begin();// 获取当前已有的怪兽对象
//		for (int j =0; j <this.getChildren().size; j++) {
//		Actor tempActor = actors[j];
//		if (tempY == tempActor.getY()) {// 如果Y值相等，比如重合，所以舍弃,重新生成
//		flag =true;
//		break;
//		}else if (tempY < tempActor.getY()) {// 如果生成的Y值小于当前怪兽的Y值，则判断生成的Y值加上高度后是否合适
//		if ((tempY + region.getRegionHeight()) >= tempActor
//		.getY()) {
//		flag =true;
//		break;
//		}
//		}else {// 如果生成的Y值大于当前怪兽的Y值，则判断当前怪兽的Y值加上高度后是否合适
//		if (tempY){
//			
//		}
//		}
//		}
//		}
//		}
	}
}
