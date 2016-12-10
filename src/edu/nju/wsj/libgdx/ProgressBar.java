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
	//����һ���򵥵����䣬powerx��powery�ֱ�ǰ�豸�ֱ��ʵ�Ȩ�أ�������������800*480Ϊ��׼
	float powerx;
	float powery;
	AssetManager manager;
	@Override
	public void draw(SpriteBatch batch, float arg1) {
		// TODO Auto-generated method stub
		batch.draw(platform, (Gdx.graphics.getWidth()-bar.getWidth()*powerx)/2, 0,platform.getWidth()*powerx,platform.getHeight()*powery);
		batch.draw(bar,(Gdx.graphics.getWidth()-bar.getWidth()*powerx)/2,0,bar.getWidth()*progress/100f*powerx,bar.getHeight()*powery);
	}

	@Override
	public Actor hit(float arg0, float arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	public ProgressBar(int x,int y) {
		super();
		//�趨Actor��λ�ã����ﲢû��ʲô�ã�����Ϊ�˺ʹ�ҽ���һ��
		this.x=x;
		this.y=y;
		//�½�һ��AssetManager
		manager=new AssetManager();
		platform=new Texture(Gdx.files.internal("black.png"));
		bar=new Texture(Gdx.files.internal("green.png"));
		height=Gdx.graphics.getHeight();
		width=Gdx.graphics.getWidth();
		//����һ���򵥵����䣬powerx��powery�ֱ�ǰ�豸�ֱ��ʵ�Ȩ�أ�������������800*480Ϊ��׼
		powerx=Gdx.graphics.getWidth()/800f;
		powery=Gdx.graphics.getHeight()/480f;
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
