package edu.nju.wsj.libgdx.spirit;

import java.util.ArrayList;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Disposable;

public class AnimalActor extends Actor implements Disposable{
	ArrayList<Texture> TexArray=new ArrayList<Texture>();
	ArrayList<TextureRegion> TexReArray=new ArrayList<TextureRegion>();
	Animation animation;
	//标记AnimalActor的缩放倍数
	float power=1;

	TextureRegion[] walksFrame;
	float stateTime;
	TextureRegion currentFrame;//当前帧
	AssetManager manager;//保存Progress里面保存的manager实例
	
	boolean hasinit = false;

	@Override
	public void draw(Batch arg0, float arg1) {
		// TODO Auto-generated method stub
		stateTime += Gdx.graphics.getDeltaTime();
		//得到下一帧
		currentFrame = animation.getKeyFrame(stateTime, true);
		//以(0,0)绘制为起点（左下角为0，0）画出动画，大小128*128
		arg0.draw(currentFrame, getX(), getY(), getWidth(), getHeight());
	}
	
	
	public AnimalActor(AssetManager manager) {
		super();
		//关联Progress内的AssetManager
		this.manager=manager;
	}



	//初始化方法，在Progress中的AssetManager初始化完成后通知AnimalActor初始化
	public void iniResource(){
		Texture tex;
		int j;
		for(int i=1;i<30;i++){
			TexArray.add(manager.get("animal/"+i+".png", Texture.class));
		}
		// TODO Auto-generated constructor stub
		
		for(int i=0;i<TexArray.size();i++){
				tex=TexArray.get(i);
				TextureRegion temtex=new TextureRegion(tex);
				TexReArray.add(temtex);
		}
		
		j=TexReArray.size();
		walksFrame=new TextureRegion[j];
		for(int i=0;i<j;i++)
		walksFrame[i]=TexReArray.get(i);
		
		//设置的是0.06s一帧
		animation = new Animation(0.06f, walksFrame);
		hasinit = true;
	}

	public boolean hasInit(){
		return hasinit;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		//释放AnimalActor中持有的资源
		for(int i=0;i<TexArray.size();i++)
			TexArray.get(i).dispose();
		hasinit = false;
	}
	
	public void setPower(float width){
		power = width / 500;
	}
}