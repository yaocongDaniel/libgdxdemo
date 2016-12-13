package edu.nju.wsj.libgdx.spirit;

import android.util.Log;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class TargetController extends IController{
	private final static String TAG = "TargetController";
	private Sound great = null;

	public Sound getGreat() {
		return great;
	}
	public void setGreat(Sound great) {
		this.great = great;
	}

	@Override
	public int update(Stage stage) {
		int removetargetnum = 0;
//		Group projectiles = (Group) stage.getRoot().findActor(ProjectileGroup.PROJECTILEGROUPNAME);// 获取飞镖所在Group
		Group dartsgroup = (Group) stage.getRoot().findActor(DartsController.DARTSCONTROLLERNAME);// 获取飞镖所在Group
		Actor[] darts = dartsgroup.getChildren().begin();
		Actor[] targets = this.getChildren().begin();
		for (int i = 0; i < dartsgroup.getChildren().size; i++) {
			Actor dart = darts[i];
			for (int j = 0; j < this.getChildren().size; j++) {
				Actor target = targets[j];
				int power = 1;
				if(dart instanceof Dart){
					power = ((Dart)dart).getPower();
				}
				if (DartsController.attackAlive(target, dart)) {
					Log.d(TAG, "     update()    attackAlive~~~     power" + power);
					if(target instanceof Scythe){
						Scythe scythe = (Scythe) target;
						scythe.beAttacked(power);
						dartsgroup.removeActor(dart);
						dart.clear();
						if(great != null){
							great.play();
						}
						Log.d(TAG, "     update()    power = " + power);
						if (!scythe.isAlive()) {
							this.removeActor(target);
							target.clear();
							removetargetnum ++;
						}
					}
					break;
				}
			}
		}
		return removetargetnum;
	}
	
}
