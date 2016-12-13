package edu.nju.wsj.libgdx.spirit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class DartsController extends IController {
	
	public static final String DARTSCONTROLLERNAME = "dartsController";

	private AtlasRegion mRegion;
	
	private Sound bing;

	private AssetManager mManager;
	private int mWidth;
	private int mHeight;
	private int mLeft;
	private int mRight;
	private int mTop;
	@Override
	public int update(Stage stage) {
		// 如果飞镖已经飞到则刪除
		Actor[] darts = this.getChildren().begin();
		for (int j = 0; j < this.getChildren().size; j++) {
			Actor actor = darts[j];
			if (!this.checkAlive(actor)) {
				this.removeActor(actor);
			}
		}
		return 0;
	}

	public DartsController(AtlasRegion region, AssetManager manager, int w, int h, int left, int right, int top) {
		setName(DARTSCONTROLLERNAME);
		mRegion = region;
		mManager = manager;
		mWidth = w;
		mHeight = h;
		mLeft = left;
		mRight = right;
		mTop = top - mHeight;
		bing = manager.get("audio/bing.ogg", Sound.class);
	}

	public void AddDarts(Dart dart) {
		if (this.getChildren().size >= GameControl.getInstance().getCurMaxDartsNum()) {// 如果飞镖数量大于等于5个就结束
			return;
		}
		float tmp_value = dart.getWidth() / dart.getHeight();
		dart.setWidth(mHeight * tmp_value);
		dart.setHeight(mHeight);
		dart.setOrigin(dart.getWidth() / 2, dart.getHeight() / 2);
		float r = (dart.getTarget().y - dart.getHeight() / 2 - dart.getY())
				/ (dart.getTarget().x - dart.getWidth() / 2 - dart.getX());// 获取斜率
		float detY = r * Gdx.graphics.getHeight();// 获取Y的变动量
		dart.addAction(Actions.moveTo(Gdx.graphics.getHeight() + dart.getX(), detY + dart.getY(), GameControl.getInstance().getCurDartsSpeed()));// 设置飞镖的移动
		this.addActor(dart);
		bing.play();
	}

	public static Boolean attackAlive(Actor target, Actor dart) {
		Rectangle rectangle = new Rectangle(target.getX(), target.getY(),
				target.getWidth(), target.getHeight());// 创建一个矩形
		return rectangle.contains(
				dart.getX() + dart.getWidth() / 2,
				dart.getY() + dart.getHeight() / 2);// 判断是否在矩阵中，即是否击中
	}

	public Boolean checkAlive(Actor dart) {
		if (dart.getActions().size == 1) {
			return false;
		}
		return true;
	}

	public Dart createDart() {
		return new Dart(mRegion);
	}
}
