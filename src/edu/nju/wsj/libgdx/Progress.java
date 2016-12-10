package edu.nju.wsj.libgdx;

import javax.microedition.khronos.opengles.GL10;

import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

import edu.nju.wsj.libgdx.spirit.AnimalActor;
import edu.nju.wsj.libgdx.spirit.TargetGroup;

public class Progress implements Screen, InputProcessor, GestureListener {
	ProgressBar bar;
	AnimalActor animal;
	Stage stage;
	AssetManager manager;
	boolean hasini;
	boolean BackHasTouched;
	LibgdxActivity activity;
	// ���AnimalActor�����ű���
	Label fpslabel;

	boolean playing = false;
	boolean initgame = false;
	
	TargetGroup mTargetGroup = null;
	
	TextureAtlas mAtlas = null;
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

			if (hasini) {
				if(manager.update()){
					playing = true;
					bar.setProgress(100);
					stage.removeActor(bar);
					// ���������֮ǰû�г�ʼ����AnimalActor�������ִ�����Ļʱ��ʼ��AnimalActor,��������������̨���Ƴ���������AnimalActor����
				}else{
					bar.setProgress(manager.getProgress() * 100);
				}
				fpslabel.setText("FPS:" + Gdx.graphics.getFramesPerSecond());
				fpslabel.x = (Gdx.graphics.getWidth() - fpslabel.getTextBounds().width);
			}
			// ������һ����ǣ�����δ���أ�Queued����ɵ���Դ���Ѽ�����ɵ���Դ��������Loaded��
			if (!manager.update()) {
				System.out.println("QueuedAssets:" + manager.getQueuedAssets());
				System.out.println("LoadedAssets:" + manager.getLoadedAssets());
				System.out.println("Progress:" + manager.getProgress());
			}
			if(playing && !initgame){
				if(!animal.hasInit()){
					animal.iniResource();
					stage.addActor(animal);
				}
				initgame = true;
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
		// ��һ�����������Ĺ���
		if (!hasini) {
			int w = Gdx.graphics.getWidth() * 3 / 4;
			int h = Gdx.graphics.getHeight() / 7;
			int x = (Gdx.graphics.getWidth() - w) / 2;
			int y = (Gdx.graphics.getHeight() - h) / 2;
			bar = new ProgressBar(x, y, w, h);
			// �½�һ����̨
			stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
			stage.addActor(bar);
			// �ǵó�ʼ��һ��AssetManagerʵ��
			manager = new AssetManager();
			// ����AssetManger�����ã�����animal����Դ��ʼ��������ע���ˣ�ֻ���ڵ���iniResourse()����Դ�ű���ʼ��
			animal = new AnimalActor(manager);
			// ����Դ��������б�,�����ҷ���һ��29֡�Ķ�������asset�ļ�����animal����29��ͼƬ
			for (int i = 1; i < 30; i++) {
				manager.load("animal/" + i + ".png", Texture.class);
			}
			animal.width = Gdx.graphics.getHeight() / 3;
			animal.height = animal.width;
			animal.x = 0;
			animal.y = Gdx.graphics.getHeight() / 2 - animal.height / 2;
			LabelStyle labelStyle =new LabelStyle(new BitmapFont(), Color.BLACK);//����һ��Label��ʽ��ʹ��Ĭ�Ϻ�ɫ����
			fpslabel =new Label("FPS:", labelStyle);//������ǩ����ʾ��������FPS��
			fpslabel.y = 0;
			fpslabel.x = (Gdx.graphics.getWidth() - fpslabel.getTextBounds().width);//����Xֵ����ʾΪ���һ���ֽ�����Ļ���Ҳ�
			stage.addActor(fpslabel);//����ǩ��ӵ���̨
			
			
			mAtlas =new TextureAtlas("mans/mans.atlas");
			
			mTargetGroup = new TargetGroup(mAtlas.findRegion("main0"));
			
			hasini = true;
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
		// ͬ���ģ��ڽ���ʱ�ͷ���Դ
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
		// ���ﻹҪ���һ�°���������
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
//		animal.power = arg1 / arg0;
		return false;
	}

}
