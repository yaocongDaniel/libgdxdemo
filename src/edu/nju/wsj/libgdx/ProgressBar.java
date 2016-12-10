package edu.nju.wsj.libgdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
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
	public void draw(SpriteBatch batch, float arg1) {
		// TODO Auto-generated method stub
		batch.draw(platform, x, y, width, height);
		batch.draw(bar, x, y, width * progress / 100f, height);
	}

	@Override
	public Actor hit(float arg0, float arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	public ProgressBar(int x,int y, int w, int h) {
		super();
		// 设定Actor的位置，这里并没有什么用，纯粹为了和大家介绍一下
		this.x = x;
		this.y = y;
		// 新建一个AssetManager
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
