package edu.nju.wsj.libgdx.spirit;

import java.util.HashMap;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TextureManager {

	HashMap<String, TextureRegion> mTextureMaps = new HashMap<String, TextureRegion>();
	
	private static TextureManager mTextureManager;	
	public static TextureManager getInstance(){
		if(mTextureManager == null){
			mTextureManager = new TextureManager();
		}
		return mTextureManager;
	}
	
	private TextureManager(){
	}
	
	public void addTexture(String name, TextureRegion texture){
		if(mTextureMaps.get(name) != null){
			return;
		}
		mTextureMaps.put(name, texture);
	}
	
	public TextureRegion getTexture(String name){
		return mTextureMaps.get(name);
	}
}
