package edu.nju.wsj.libgdx;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

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
	// �Ի����ϵ�������ť
	Button ok;
	Button cancel;
	// �����Button��com.badlogic.gdx.scenes.scene2d.ui.Button��ע������
	Button button;
	// Button�̳���Actor,�������Ҳ��Ҫһ����̨,stage
	Stage stage;
	boolean hasini;
	boolean hasdialog;

	public MyGame(LibgdxActivity activity) {
		super();
		this.activity = activity;
	}

	public void resize(int width, int height) {
		// STUB
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
		// ǧ��������ͷ��ڴ�
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
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
		if (true) {
			if (Gdx.input.isTouched()) {
				// ���˴���������һ������������һ��ֵ��ʱ�򴥷��µ�����ϵͳ���ɴ˼�Сϵͳ����
				tem = particlepool.obtain();
				tem.setPosition(Gdx.input.getX(), Gdx.graphics.getHeight()
						- Gdx.input.getY());
				particlelist.add(tem);
			}
			batch.begin();
			for (int i = 0; i < particlelist.size(); i++) {
				particlelist.get(i).draw(batch, Gdx.graphics.getDeltaTime());
			}
			batch.end();

			// ����Ѿ�������ɵ�����ϵͳ
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
	public void show() {
		// TODO Auto-generated method stub

		Gdx.input.setCatchBackKey(true);
		if (!hasini) {
			stage = new Stage(Gdx.graphics.getWidth(),
					Gdx.graphics.getHeight(), true);
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

			TextureRegion txr = new TextureRegion(new Texture(
					Gdx.files.internal("dialog.png")), 512, 256);
			dialog = new Window("dialog", new Window.WindowStyle(bf,
					new Color(), new NinePatch(txr)));
			// ��һ���򵥵�����,����1.2��Ϊ����ͼƬ��ʾ������ʱ���һ��
			dialog.width = 512 * 1.2f * Gdx.graphics.getWidth() / 800f;
			dialog.height = 256 * 1.2f * Gdx.graphics.getWidth() / 800f;
			// Ϊ����ͼƬ���־���
			dialog.x = (Gdx.graphics.getWidth() - dialog.width) / 2;
			dialog.y = (Gdx.graphics.getHeight() - dialog.height) / 2;

			ok = new Button(new TextureRegion(new Texture(
					Gdx.files.internal("ok.png")), 160, 80));
			ok.x = 260;
			ok.y = 80;
			// ��ok�����ť���Ӽ�����
			ok.setClickListener(new ClickListener() {
				@Override
				public void click(Actor arg0, float arg1, float arg2) {
					// TODO Auto-generated method stub
					// �رճ���
					activity.finish();
					android.os.Process.killProcess(android.os.Process.myPid());
				}
			});
			cancel = new Button(new TextureRegion(new Texture(
					Gdx.files.internal("cancel.png")), 160, 80));
			cancel.x = 460;
			cancel.y = 80;
			cancel.setClickListener(new ClickListener() {
				@Override
				public void click(Actor arg0, float arg1, float arg2) {
					// TODO Auto-generated method stub
					// �Ƴ��Ի���
					stage.removeActor(dialog);
					hasdialog = false;
				}
			});
			dialog.addActor(ok);
			dialog.addActor(cancel);
			button = new Button(new ButtonStyle(n1, n2, n3, 0f, 0f, 0f, 0f),
					"Start");
			button.setClickListener(new ClickListener() {
				@Override
				public void click(Actor arg0, float arg1, float arg2) {
					// TODO Auto-generated method stub
					activity.ag.setScreen(activity.progress);
				}
			});
			button.x = 200;
			button.y = 200;
			stage.addActor(button);
			batch = new SpriteBatch();

			// ��ʼ�����ӱ���
			particle = new ParticleEffect();
			particle.load(Gdx.files.internal("particle.p"),
					Gdx.files.internal(""));
			particlepool = new ParticleEffectPool(particle, 5, 10);
			particlelist = new ArrayList<ParticleEffect>();
			hasini = true;
		}

		InputMultiplexer multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(stage);
		multiplexer.addProcessor(this);
		Gdx.input.setInputProcessor(multiplexer);
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

}