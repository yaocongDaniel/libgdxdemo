package edu.nju.wsj.libgdx.spirit;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class TargetController extends IController{

	@Override
	public int update(Stage stage) {
		int removetargetnum = 0;
		Group projectiles = (Group) stage.getRoot().findActor(ProjectileGroup.PROJECTILEGROUPNAME);// 获取飞镖所在Group
		Actor[] projectile = projectiles.getChildren().begin();
		Actor[] targets = this.getChildren().begin();
		for (int i = 0; i < projectiles.getChildren().size; i++) {
			Actor actor = projectile[i];
			for (int j = 0; j < this.getChildren().size; j++) {
				Actor target = targets[j];
				if (ProjectileGroup.attackAlive(target, actor)) {
					if(target instanceof Scythe){
						Scythe scythe = (Scythe) target;
						scythe.beAttacked(1);
						projectiles.removeActor(actor);
						projectiles.clear();
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
