package edu.nju.wsj.libgdx.spirit;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.input.GestureDetector.GestureAdapter;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class DartsListener extends GestureAdapter {

	private Stage stage;

	public DartsListener(Stage stage) {
		this.stage = stage;
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		DartsController dartsController = (DartsController) stage.getRoot().findActor(DartsController.DARTSCONTROLLERNAME);
		if (dartsController.getChildren().size >= GameControl.getInstance().getCurMaxDartsNum()) {// 限制飞镖的数量为5个
			return false;
		}
		Vector3 vector3 = new Vector3(x, y, 0);
		stage.getCamera().unproject(vector3);// 坐标转化
		Actor man = stage.getRoot().findActor("player");
		if (vector3.x < man.getX() + 10) {// 如果触摸太靠近左侧就不响应
			return false;
		}
		Dart dart = dartsController.createDart();
		dart.setPower(1);
		dart.setX(man.getX() + man.getWidth() / 2);
		dart.setY(man.getY() + man.getHeight() / 2);
		dart.setTarget(vector3.x, vector3.y);
		dartsController.AddDarts(dart);
		return true;
	}

	@Override
	public boolean longPress(float x, float y) {
		DartsController dartsController = (DartsController) stage.getRoot().findActor(DartsController.DARTSCONTROLLERNAME);
		if (dartsController.getChildren().size >= GameControl.getInstance().getCurMaxDartsNum()) {// 限制飞镖的数量为5个
			return false;
		}
		Vector3 vector3 = new Vector3(x, y, 0);
		stage.getCamera().unproject(vector3);// 坐标转化
		Actor man = stage.getRoot().findActor("player");
		if (vector3.x < man.getX() + 10) {// 如果触摸太靠近左侧就不响应
			return false;
		}
		Dart dart = dartsController.createDart();
		dart.setPower(2);
		dart.setColor(Color.RED);
		dart.setX(man.getX() + man.getWidth() / 2);
		dart.setY(man.getY() + man.getHeight() / 2);
		dart.setTarget(vector3.x, vector3.y);
		dartsController.AddDarts(dart);
		return true;
	}
}