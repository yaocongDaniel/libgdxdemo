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
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

import edu.nju.wsj.libgdx.spirit.AnimalActor;
import edu.nju.wsj.libgdx.spirit.DartsController;
import edu.nju.wsj.libgdx.spirit.DartsDetector;
import edu.nju.wsj.libgdx.spirit.DartsListener;
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
	DartsController mDartsController = null;
	
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
					bar.remove();
					// ���������֮ǰû�г�ʼ����AnimalActor�������ִ�����Ļʱ��ʼ��AnimalActor,��������������̨���Ƴ���������AnimalActor����
				}else{
					bar.setProgress(manager.getProgress() * 100);
				}
				fpslabel.setText("FPS:" + Gdx.graphics.getFramesPerSecond());
				fpslabel.setX(Gdx.graphics.getWidth() - fpslabel.getPrefWidth());//����Xֵ����ʾΪ���һ���ֽ�����Ļ���Ҳ�
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
				stage.addActor(mTargetGroup);
				stage.addActor(mDartsController);
				initgame = true;
			}
			if(initgame){
				// ��ʼ�������
				mTargetGroup.update(stage);
				mDartsController.update(stage);
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
			stage = new Stage();
			stage.addActor(bar);
			// �ǵó�ʼ��һ��AssetManagerʵ��
			manager = new AssetManager();
			// ����AssetManger�����ã�����animal����Դ��ʼ��������ע���ˣ�ֻ���ڵ���iniResourse()����Դ�ű���ʼ��
			animal = new AnimalActor(manager);
			// ����Դ��������б�,�����ҷ���һ��29֡�Ķ�������asset�ļ�����animal����29��ͼƬ
			for (int i = 1; i < 30; i++) {
				manager.load("animal/" + i + ".png", Texture.class);
			}
			animal.setWidth(Gdx.graphics.getHeight() / 3);
			animal.setHeight(animal.getWidth());
			animal.setX(0);
			animal.setY(Gdx.graphics.getHeight() / 2 - animal.getHeight() / 2);
			animal.setName("player");
			LabelStyle labelStyle =new LabelStyle(new BitmapFont(), Color.BLACK);//����һ��Label��ʽ��ʹ��Ĭ�Ϻ�ɫ����
			fpslabel =new Label("FPS:", labelStyle);//������ǩ����ʾ��������FPS��
			fpslabel.setY(0);			
			fpslabel.setX(Gdx.graphics.getWidth() - fpslabel.getPrefWidth());//����Xֵ����ʾΪ���һ���ֽ�����Ļ���Ҳ�
			stage.addActor(fpslabel);//����ǩ��ӵ���̨
			
			mAtlas =new TextureAtlas("mans/mans.pack");
//			AtlasRegion atlasregion = mAtlas.findRegion("man0");
			AtlasRegion atlasregion = mAtlas.findRegion("ryuk");
			mTargetGroup = new TargetGroup(atlasregion, true,
					Gdx.graphics.getHeight() / 7, Gdx.graphics.getHeight() / 7, 
					Gdx.graphics.getWidth() * 2 / 7, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			
//			mProjectileGroup = new ProjectileGroup(mAtlas.findRegion("gameball"), Gdx.graphics.getHeight() / 7, Gdx.graphics.getHeight() / 7, 
//					Gdx.graphics.getWidth() / 7, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//			mProjectileGroup.setStartPosition((int)(animal.getX() + animal.getWidth()), (int)(animal.getY() + animal.getHeight()/2));
			
			mDartsController = new DartsController(mAtlas.findRegion("gameball"), Gdx.graphics.getHeight() / 7, Gdx.graphics.getHeight() / 7, 
					Gdx.graphics.getWidth() / 7, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			
			hasini = true;
		}
		InputMultiplexer multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(this);
		multiplexer.addProcessor(stage);
		multiplexer.addProcessor(new DartsDetector(stage, this));// �������ʶ��
		
		Gdx.input.setInputProcessor(multiplexer);
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int arg2, int arg3) {
		return false;
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
//			stage.removeActor(animal);
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
	public boolean touchDragged(int arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean pinch(Vector2 arg0, Vector2 arg1, Vector2 arg2, Vector2 arg3) {
		// TODO Auto-generated method stub
		Log.i("Testin-apkbus", "pinch");
		return false;
	}


	@Override
	public boolean zoom(float arg0, float arg1) {
		// TODO Auto-generated method stub
		Log.i("Testin-apkbus", "zoom");
//		animal.power = arg1 / arg0;
		return false;
	}

	@Override
	public boolean fling(float arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean longPress(float arg0, float arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pan(float arg0, float arg1, float arg2, float arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean panStop(float arg0, float arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean tap(float arg0, float arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(float arg0, float arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return false;
	}

}
