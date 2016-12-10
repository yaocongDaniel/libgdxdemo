package edu.nju.wsj.libgdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Disposable;

public class ProgressBar extends Actor implements Disposable{
	Texture platform;
	Texture bar;
	int height;
	int width;
	float progress;
	AssetManager manager;
	@Override
	public void draw(Batch batch, float arg1) {
		// TODO Auto-generated method stub
		batch.draw(platform, getX(), getY(), width, height);
		batch.draw(bar, getX(), getY(), width * progress / 100f, height);
	}


	public ProgressBar(int x,int y, int w, int h) {
		super();
		// �趨Actor��λ�ã����ﲢû��ʲô�ã�����Ϊ�˺ʹ�ҽ���һ��
		setX(x);
		setY(y);
		// �½�һ��AssetManager
		manager = new AssetManager();
		platform = new Texture(Gdx.files.internal("black.png"));
		bar = new Texture(Gdx.files.internal("green.png"));
		width = w;
		height = h;
	}
	public void setProgress(float progress){
		this.progress=progress;
	}
	public void dispose() {
		// TODO Auto-generated method stub
		platform.dispose();
		bar.dispose();
	}
	
}
