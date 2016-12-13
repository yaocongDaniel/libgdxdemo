package edu.nju.wsj.libgdx.spirit;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Dart extends Image {

	private Vector2 target;
	private int power;

	public Dart(AtlasRegion region) {
		super(region);
		this.setOrigin(getWidth() / 2, getHeight() / 2);
		this.addAction(Actions.repeat(50, Actions.rotateBy(360, 0.5f)));
	}

	public void setTarget(Vector2 target) {
		this.target = target;
	}

	public void setTarget(float x, float y) {
		this.target = new Vector2(x, y);
	}

	public Vector2 getTarget() {
		return target;
	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}
}