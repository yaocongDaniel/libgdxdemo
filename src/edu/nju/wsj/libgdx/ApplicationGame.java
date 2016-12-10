package edu.nju.wsj.libgdx;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

public class ApplicationGame extends Game {
	private Screen sc;
	public void create() {
		// TODO Auto-generated method stub
		setScreen(sc);
	}

	public ApplicationGame(Screen sc) {
		super();
		this.sc=sc; 
		// TODO Auto-generated constructor stub
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		super.resume();
	}
	
}
