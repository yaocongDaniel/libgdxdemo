package edu.nju.wsj.libgdx.spirit;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class DartsDetector extends GestureDetector {

	Stage stage;

	public DartsDetector(Stage stage, GestureListener listener) {
		super(listener);
		this.stage = stage;
	}

	@Override
	public boolean touchUp(float x, float y, int pointer, int button) {
		DartsController dartsController = (DartsController) stage.getRoot().findActor(DartsController.DARTSCONTROLLERNAME);
		if (dartsController.getChildren().size >= 5) {// ���Ʒ��ڵ�����Ϊ5��
			return false;
		}
		Vector3 vector3 = new Vector3(x, y, 0);
		stage.getCamera().unproject(vector3);// ����ת��
		Actor man = stage.getRoot().findActor("player");
		if (vector3.x < man.getX() + 10) {// �������̫�������Ͳ���Ӧ
			return super.touchUp(x, y, pointer, button);
		}
		Dart dart = dartsController.createDart();
		dart.setX(man.getX() + man.getWidth() / 2);
		dart.setY(man.getY() + man.getHeight() / 2);
		dart.setTarget(vector3.x, vector3.y);
		if (this.isLongPressed()) {// ����ǳ����ͱ�ɺ�ɫ����
			dart.setPower(2);// ����ɱ����Ϊ2
			dart.setColor(Color.RED);// ���óɺ�ɫ
		}else{
			dart.setPower(1);
		}
		dartsController.AddDarts(dart);
		return super.touchUp(x, y, pointer, button);
	}
}