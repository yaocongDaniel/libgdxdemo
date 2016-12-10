package edu.nju.wsj.libgdx;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

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
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

public class MyGame implements Screen, InputProcessor {
	private final static String TAG = "MyGame";
	
	private final static boolean SHOW_PARTICAL = true;
	
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
		InputMultiplexer multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(stage);
		multiplexer.addProcessor(this);
		Gdx.input.setInputProcessor(multiplexer);
	}

	private void initUI(int width, int height){
		if(!hasini){
			Log.d(TAG, "   show()    hasini = " + hasini);
			stage = new Stage(width, height, true);
			bf = new BitmapFont();
			tx2 = new Texture(Gdx.files.internal("button1_480.png"));
			tx1 = new Texture(Gdx.files.internal("button2_480.png"));
			tx3 = new Texture(Gdx.files.internal("button3_480.png"));
			int left = 14;
			int right = 34;
			int top = 18;
			int bottom = 38;
			NinePatch n1 = new NinePatch(tx1, left, right, top, bottom);
			NinePatch n2 = new NinePatch(tx2, left, right, top, bottom);
			NinePatch n3 = new NinePatch(tx3, left, right, top, bottom);

			TextureRegion txr = new TextureRegion(new Texture(Gdx.files.internal("dialog.png")), 512, 256);
			dialog = new Window("dialog", new Window.WindowStyle(bf, new Color(), new NinePatch(txr)));
			// 做一个简单的适配,乘以1.2是为了让图片显示出来的时候大一点
			dialog.width = 512 * 1.2f * width / 800f;
			dialog.height = 256 * 1.2f * height * 2 / 800f;
			// 为了让图片保持居中
			dialog.x = (width - dialog.width) / 2;
			dialog.y = (height - dialog.height) / 2;

			ok = new Button(new TextureRegion(new Texture(Gdx.files.internal("ok.png")), 160, 80));
			ok.width = dialog.width / 5;
			ok.height = dialog.height / 5;
			ok.x = dialog.width * 1 / 5;
			ok.y = dialog.height / 5;
			// 给ok这个按钮添加监听器
			ok.setClickListener(new ClickListener(){
				@Override
				public void click(Actor arg0, float arg1, float arg2) {
					// 关闭程序
					activity.finish();
					android.os.Process.killProcess(android.os.Process.myPid());
				}
			});
			cancel = new Button(new TextureRegion(new Texture(Gdx.files.internal("cancel.png")), 160, 80));
			cancel.width = dialog.width / 5;
			cancel.height = dialog.height / 5;
			cancel.x = dialog.width * 3 / 5;
			cancel.y = dialog.height / 5;
			cancel.setClickListener(new ClickListener() {
				@Override
				public void click(Actor arg0, float arg1, float arg2) {
					// TODO Auto-generated method stub
					// 移除对话框
					stage.removeActor(dialog);
					hasdialog = false;
				}
			});
			dialog.addActor(ok);
			dialog.addActor(cancel);
			button_start = new Button(new ButtonStyle(n1, n2, n3, 0f, 0f, 0f, 0f), "Start");
			button_start.setClickListener(new ClickListener() {
				@Override
				public void click(Actor arg0, float arg1, float arg2) {
					// TODO Auto-generated method stub
					activity.ag.setScreen(activity.progress);
				}
			});
			button_start.width = width / 6;
			button_start.height = height / 6;
			button_start.x = width / 5;
			button_start.y = height / 5;
			stage.addActor(button_start);
			batch = new SpriteBatch();

			// 初始化粒子变量
			particle = new ParticleEffect();
			particle.load(Gdx.files.internal("particle.p"),
					Gdx.files.internal(""));
			particlepool = new ParticleEffectPool(particle, 5, 10);
			particlelist = new ArrayList<ParticleEffect>();
			hasini = true;
		}
	}
	
	@Override
	public void resize(int width, int height) {
		Log.d(TAG, "   resize()  " + width + "   " + height);
		if(width != mWidth || height != mHeight){
			
		}
	}
	
	@Override
	public boolean keyDown(int arg0) {
		// TODO Auto-generated method stub
		if (arg0 == Input.Keys.BACK) {
			if (!hasdialog) {
				stage.addActor(dialog);
				hasdialog = true;
			}else {
				stage.removeActor(dialog);
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
	public boolean touchMoved(int arg0, int arg1) {
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
				// 当此触摸点与上一触摸点距离大于一定值的时候触发新的粒子系统，由此减小系统负担
				tem = particlepool.obtain();
				tem.setPosition(Gdx.input.getX(), Gdx.graphics.getHeight()
						- Gdx.input.getY());
				particlelist.add(tem);
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
}