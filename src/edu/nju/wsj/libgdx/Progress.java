package edu.nju.wsj.libgdx;

import javax.microedition.khronos.opengles.GL10;

import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Progress implements Screen, InputProcessor, GestureListener {
	ProgressBar bar;
	AnimalActor animal;
	Stage stage;
	AssetManager manager;
	boolean hasini;
	boolean BackHasTouched;
	LibgdxActivity activity;
	// 标记AnimalActor的缩放倍数
	float power = 1;

	@Override
	public void hide() {
	}

	@Override
	public void render(float arg0) {
		// TODO Auto-generated method stub
		if (!BackHasTouched) {
			Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
			Gdx.gl.glClearColor(1f, 1f, 1f, 0f);
			stage.act(Gdx.graphics.getDeltaTime());
			stage.draw();

			if (!manager.update()) {
				bar.setProgress(manager.getProgress() * 100);
			}
			// 加载完成且之前没有初始化过AnimalActor，且在手触摸屏幕时初始化AnimalActor,并将进度条从舞台中移除，并加入AnimalActor对象
			if (!hasini && manager.update()) {
				bar.setProgress(100);
				if (Gdx.input.isTouched()) {
					stage.removeActor(bar);
					animal.iniResource();
					stage.addActor(animal);
					hasini = true;
				}
			}
			if (hasini && manager.update()) {
				bar.setProgress(100);
				if (Gdx.input.isTouched()) {
					stage.removeActor(bar);
					stage.addActor(animal);
				}
			}
			// 我们做一个标记，看看未加载（Queued）完成的资源和已加载完成的资源的数量（Loaded）
			if (!manager.update()) {
				System.out.println("QueuedAssets:" + manager.getQueuedAssets());
				System.out.println("LoadedAssets:" + manager.getLoadedAssets());
				System.out.println("Progress:" + manager.getProgress());
			}
		}
	}

	@Override
	public void resize(int arg0, int arg1) {
	}

	@Override
	public void resume() {
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		BackHasTouched = false;
		// 第一次启动事做的工作
		if (!hasini) {
			bar = new ProgressBar(0, 0);
			// 新建一个舞台
			stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
			stage.addActor(bar);
			// 记得初始化一下AssetManager实例
			manager = new AssetManager();
			// 传入AssetManger的引用，便于animal的资源初始化，但是注意了，只有在调用iniResourse()后资源才被初始化
			animal = new AnimalActor(manager);
			// 把资源加入加载列表,这里我放了一个29帧的动画，在asset文件夹下animal下有29张图片

			for (int i = 1; i < 30; i++) {
				manager.load("animal/" + i + ".png", Texture.class);
			}
		}
		// 资源初始化以后再启动就不用做资源加载的工作了，只要做一些简单的工作即可
		if (hasini) {
			stage.addActor(bar);
		}
		InputMultiplexer multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(this);
		multiplexer.addProcessor(stage);
		multiplexer.addProcessor(new GestureDetector(this));
		Gdx.input.setInputProcessor(multiplexer);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		// 同样的，在结束时释放资源
		bar.dispose();
		animal.dispose();
		manager.clear();
		manager.dispose();
	}

	@Override
	public void pause() {
	}

	public Progress(LibgdxActivity activity) {
		super();
		this.activity = activity;
	}

	@Override
	public boolean keyDown(int arg0) {
		// TODO Auto-generated method stub
		// 这里还要检测一下按键的类型
		if (arg0 == Input.Keys.BACK) {
			System.out.println("Back Pressed");
			activity.ag.setScreen(activity.mg);
			stage.removeActor(animal);
		}
		return false;
	}

	@Override
	public boolean keyTyped(char arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchMoved(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean fling(float arg0, float arg1) {
		// TODO Auto-generated method stub
		Log.i("Testin-apkbus", "fling");
		return false;
	}

	@Override
	public boolean longPress(int arg0, int arg1) {
		// TODO Auto-generated method stub
		Log.i("Testin-apkbus", "longPress");
		return false;
	}

	@Override
	public boolean pan(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		Log.i("Testin-apkbus", "pan");
		return false;
	}

	@Override
	public boolean pinch(Vector2 arg0, Vector2 arg1, Vector2 arg2, Vector2 arg3) {
		// TODO Auto-generated method stub
		Log.i("Testin-apkbus", "pinch");
		return false;
	}

	@Override
	public boolean tap(int arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		Log.i("Testin-apkbus", "tap");
		return false;
	}

	@Override
	public boolean touchDown(int arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		Log.i("Testin-apkbus", "touchDown");
		return false;
	}

	@Override
	public boolean zoom(float arg0, float arg1) {
		// TODO Auto-generated method stub
		Log.i("Testin-apkbus", "zoom");
		animal.power = arg1 / arg0;
		return false;
	}

}
