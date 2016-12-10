package edu.nju.wsj.libgdx;

import java.util.ArrayList;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Disposable;

public class AnimalActor extends Actor implements Disposable{
	ArrayList<Texture> TexArray=new ArrayList<Texture>();
	ArrayList<TextureRegion> TexReArray=new ArrayList<TextureRegion>();
	Animation animation;
	//���AnimalActor�����ű���
	float power=1;

	TextureRegion[] walksFrame;
	float stateTime;
	TextureRegion currentFrame;//��ǰ֡
	AssetManager manager;//����Progress���汣���managerʵ��
	@Override
	public void draw(SpriteBatch arg0, float arg1) {
		// TODO Auto-generated method stub
		stateTime += Gdx.graphics.getDeltaTime();
		//�õ���һ֡
		currentFrame = animation.getKeyFrame(stateTime, true);
		//��(0,0)����Ϊ��㣨���½�Ϊ0��0��������������С128*128
		arg0.draw(currentFrame,0, 0,128*power,128*power);
	}
	
	
	public AnimalActor(AssetManager manager) {
		super();
		//����Progress�ڵ�AssetManager
		this.manager=manager;
	}


	@Override
	public Actor hit(float x, float y) {
		// TODO Auto-generated method stub
            return this; 
	}

	@Override
	public boolean touchDown(float arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void touchDragged(float arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void touchUp(float arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	//��ʼ����������Progress�е�AssetManager��ʼ����ɺ�֪ͨAnimalActor��ʼ��
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
		
		//���õ���0.06sһ֡
		animation = new Animation(0.06f, walksFrame);
		
	}


	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
		//�ͷ�AnimalActor�г��е���Դ
		for(int i=0;i<TexArray.size();i++)
			TexArray.get(i).dispose();
	}
	
	
	}
	
	
	


