package edu.nju.wsj.libgdx;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;

public class LibgdxActivity extends  AndroidApplication {
	Progress progress;
	MyGame  mg;
	ApplicationGame ag;
    @Override
    public void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState); 
        progress=new Progress(this);
        mg=new MyGame(this);
        ag=new ApplicationGame(mg);
        initialize(ag); 
    }
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	} 
    
}