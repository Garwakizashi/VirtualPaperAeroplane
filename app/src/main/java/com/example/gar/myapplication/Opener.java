package com.example.gar.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Opener extends Activity {

	
	//-------This is the opening screen, just to show name of coder, university, course, module etc-------------//
	//---doesn't really contribute to the actual project, but it makes things more professional and organised--//
	protected void onCreate(Bundle OpeningScreen) {
		// TODO Auto-generated method stub
		super.onCreate(OpeningScreen);
		
		setContentView(R.layout.opening); 
		
		//------------------------------------------------------------------//
		//-----Now setting up a timer, so that opening page goes on --------//
		//-----for 5 seconds before moving on to the main code--------------//
		//------------------------------------------------------------------//
		

		//--Setting a new thread for a timer--//
		
		Thread timer = new Thread(){
			public void run(){
			
				try{
					sleep(3000);//--Set sleep to 3000 (3000milli seconds)
				}
				catch (InterruptedException e){
					e.printStackTrace();
				}
				finally {    //--This part sets the intent to the main code--//
					Intent openStarting = new Intent("com.example.gar.myapplication.STARTING");
					startActivity(openStarting); 
					//--after intent has been set as the same as the one in manifest, this will start the activity of the main code--//
				}
			}
		};
		timer.start();
	}
	
	//----Now to make it so that when you press back in the main screen, it wont go back to the opening screen---//

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
		
		//----finish will terminate the opening activity, it also reduce memory usage when it is on the main screen--//
	}
	
	

}
