package edu.nju.wsj.libgdx;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ActivityForSwitch extends Activity {
	Button switchbutton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		switchbutton=(Button)findViewById(R.id.button1);
		switchbutton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) { 
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setClass(ActivityForSwitch.this,LibgdxActivity.class);
				ActivityForSwitch.this.startActivity(intent);
			}
		});

	}
	
}
