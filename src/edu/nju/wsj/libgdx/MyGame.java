package edu.nju.wsj.libgdx;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import android.os.Handler;
import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MyGame implements Screen, InputProcessor {
	private final static String TAG = "MyGame";
	
	private final static boolean SHOW_PARTICAL = true;
	
	private Skin m_Skin;
	
	SpriteBatch batch;
	BitmapFont bf;
	ParticleEffect particle;
	ParticleEffect tem;
	ParticleEffectPool particlepool;
	ArrayList<ParticleEffect> particlelist;
	Texture tx1;
	Texture tx2;
	Texture tx3;
	LibgdxActivity activity;
	Window dialog;
	// 对话框上的两个按钮
	Button ok;
	Button cancel;
	// 这里的Button是com.badlogic.gdx.scenes.scene2d.ui.Button，注意区分
	Button button_start;
	// Button继承自Actor,因此我们也需要一个舞台,stage
	Stage stage;
	boolean hasini;
	boolean hasdialog;
	
	int mWidth = 0;
	int mHeight = 0;

	public MyGame(LibgdxActivity activity) {
		super();
		this.activity = activity;
	}

	public void pause() {
		// STUB
	}

	public void resume() {
		// STUB
	}

	public void dispose() {
		// STUB
		batch.dispose();
		bf.dispose();
		// 千万别忘了释放内存
		particle.dispose();
		if (tem != null)
			tem.dispose();
		particlepool.clear();
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
	}
	@Override
	public void render(float arg0) {
		// TODO Auto-generated method stub
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glClearColor(0f, 0f, 0f, 0f);
		if(stage != null){
			stage.act(Gdx.graphics.getDeltaTime());
			stage.draw();
		}
		if(batch != null){
			showParticle(batch);
		}
	}

	@Override
	public void show() {
		Gdx.input.setCatchBackKey(true);
		if(mWidth != Gdx.graphics.getWidth() || mHeight != Gdx.graphics.getHeight()){
			mWidth = Gdx.graphics.getWidth();
			mHeight = Gdx.graphics.getHeight();
			initUI(mWidth, mHeight);
		}
		InputMultiplexer multiplexer = new InputMultiplexer(stage, this);
		Gdx.input.setInputProcessor(multiplexer);
	}

	private void initUI(int width, int height){
		if(!hasini){
			Log.d(TAG, "   show()    hasini = " + hasini);
			stage = new Stage(new ScreenViewport());
			
			Image backgroud = new Image(new Texture(Gdx.files.internal("game_bk.jpg")));
			backgroud.setWidth(width);
			backgroud.setHeight(height);
			stage.addActor(backgroud);
			
//			tx2 = new Texture(Gdx.files.internal("button1_480.png"));
//			tx1 = new Texture(Gdx.files.internal("button2_480.png"));
//			tx3 = new Texture(Gdx.files.internal("button3_480.png"));
//			button_start = new Button(new ButtonStyle(new TextureRegionDrawable(new TextureRegion(tx1,0, 0, tx1.getWidth(), tx1.getHeight())), 
//					new TextureRegionDrawable(new TextureRegion(tx2, 0, 0, tx2.getWidth(), tx2.getHeight())), 
//					new TextureRegionDrawable(new TextureRegion(tx3, 0, 0, tx3.getWidth(), tx3.getHeight()))));
//			button_start.addListener(new ClickListener() {
//				@Override
//				public void clicked(InputEvent event, float x, float y) {
//					// TODO Auto-generated method stub
//					activity.ag.setScreen(activity.progress);
//				}
//			});
//			button_start.setWidth(width / 6);
//			button_start.setHeight(height / 6);
//			button_start.setX(width / 5);
//			button_start.setY(height / 5);
//			stage.addActor(button_start);
			m_Skin = new Skin(Gdx.files.internal("data/uiskin.json"));
			TextButton startBut = new TextButton("Start", m_Skin, "default");
			startBut.setWidth(width / 8);
			startBut.setHeight(height / 8);
			startBut.setX(width / 2 - startBut.getWidth() / 2);
			startBut.setY(height * 3 / 5);
			startBut.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					activity.ag.setScreen(activity.progress);
				}
			});
			stage.addActor(startBut);
			TextButton exitBut = new TextButton("Exit", m_Skin, "default");
			exitBut.setWidth(width / 8);
			exitBut.setHeight(height / 8);
			exitBut.setX(width / 2 - startBut.getWidth() / 2);
			exitBut.setY(height * 2 / 5);
			exitBut.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					if (!hasdialog) {
						stage.addActor(dialog);
						hasdialog = true;
					}
				}
			});
			stage.addActor(exitBut);
			
			
			
			batch = new SpriteBatch();
			// 初始化粒子变量
			particle = new ParticleEffect();
			particle.load(Gdx.files.internal("particle.p"), Gdx.files.internal(""));
			particlepool = new ParticleEffectPool(particle, 5, 10);
			particlelist = new ArrayList<ParticleEffect>();
			
			initExitDialog(width, height);
			hasini = true;
			mShowParticleHandler.sendEmptyMessageDelayed(0, 1000);
		}
	}
	
	
	private Handler mShowParticleHandler = new Handler(){
		public void dispatchMessage(android.os.Message msg) {
			synchronized (mShowParticleHandler) {
				tem = particlepool.obtain();
				float x = MathUtils.random(Gdx.graphics.getWidth() / 3, Gdx.graphics.getWidth() * 2 / 3);
				float y = MathUtils.random(0, Gdx.graphics.getHeight());
				tem.setPosition(x, y);
				particlelist.add(tem);
			}
			mShowParticleHandler.sendEmptyMessageDelayed(0, MathUtils.random(10, 500));
		};
	};
	
	private void initExitDialog(int width, int height){
		bf = new BitmapFont();
		TextureRegion txr = new TextureRegion(new Texture(Gdx.files.internal("dialog.png")), 512, 256);
		dialog = new Window("dialog", new Window.WindowStyle(bf, new Color(), new TextureRegionDrawable(txr)));
		// 做一个简单的适配,乘以1.2是为了让图片显示出来的时候大一点
		dialog.setWidth( 512 * 1.2f * width / 800f);
		dialog.setHeight(256 * 1.2f * height * 2 / 800f);
		// 为了让图片保持居中
		dialog.setX((width - dialog.getWidth()) / 2);
		dialog.setY((height - dialog.getHeight()) / 2);

		ok = new Button(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ok.png")), 160, 80)));
		ok.setWidth(dialog.getWidth() / 5);
		ok.setHeight(dialog.getHeight() / 5);
		ok.setX(dialog.getWidth() * 1 / 5);
		ok.setY(dialog.getHeight() / 5);
		// 给ok这个按钮添加监听器
		ok.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// 关闭程序
				activity.finish();
				android.os.Process.killProcess(android.os.Process.myPid());
			}
		});
		cancel = new Button(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("cancel.png")), 160, 80)));
		cancel.setWidth(dialog.getWidth() / 5);
		cancel.setHeight(dialog.getHeight() / 5);
		cancel.setX(dialog.getWidth() * 3 / 5);
		cancel.setY(dialog.getHeight() / 5);
		cancel.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				// 移除对话框
				dialog.remove();
				hasdialog = false;
			}
		});
		dialog.addActor(ok);
		dialog.addActor(cancel);
	}
	
	@Override
	public void resize(int width, int height) {
		Log.d(TAG, "   resize()  " + width + "   " + height);
	}
	
	@Override
	public boolean keyDown(int arg0) {
		// TODO Auto-generated method stub
		if (arg0 == Input.Keys.BACK) {
			if (!hasdialog) {
				stage.addActor(dialog);
				hasdialog = true;
			}else {
//				stage.removeActor(dialog);
				dialog.remove();
				hasdialog = false;
			}
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
	public boolean touchUp(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	private void showParticle(SpriteBatch batch_){
		if (SHOW_PARTICAL) {
			if (Gdx.input.isTouched()) {
				synchronized(mShowParticleHandler){
					// 当此触摸点与上一触摸点距离大于一定值的时候触发新的粒子系统，由此减小系统负担
					tem = particlepool.obtain();
					tem.setPosition(Gdx.input.getX(), Gdx.graphics.getHeight()
							- Gdx.input.getY());
					particlelist.add(tem);
				}
			}
			batch_.begin();
			for (int i = 0; i < particlelist.size(); i++) {
				particlelist.get(i).draw(batch_, Gdx.graphics.getDeltaTime());
			}
			batch_.end();

			// 清除已经播放完成的粒子系统
			ParticleEffect temparticle;
			for (int i = 0; i < particlelist.size(); i++) {
				temparticle = particlelist.get(i);
				if (temparticle.isComplete()) {
					particlelist.remove(i);
				}
			}
		}
	}

	@Override
	public boolean mouseMoved(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return false;
	}
}