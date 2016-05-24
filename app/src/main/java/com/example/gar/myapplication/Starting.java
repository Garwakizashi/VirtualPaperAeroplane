package com.example.gar.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;


public class Starting extends Activity {

	
	//-------This is the starting screen, using the accelerometer to set the plane in flight-------------//
	
	
	SensorManager sensorManager;
	boolean sensorPresent;
	Sensor accelerometerSensor;
	float x, y, z;
	List <Sensor>  allSensors	;
	TextView nosensor;
	//----setting out the floats, sensors, lists----//
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		
		setContentView(R.layout.starting); 
		 nosensor = (TextView)findViewById(R.id.nosensor);
		//---Finding the accelerometer using sensor manager and get sensor --//
		
		  sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		   List<Sensor> accelerometer = sensorManager.getSensorList(Sensor.TYPE_ALL);
		   if(accelerometer.size() > 0){
	        	sensorPresent = true;
	        	accelerometerSensor = accelerometer.get(0);
	     
	        }
	        else{
	        	sensorPresent = false;	
	        	String line = "There is no accelerometer to set the plane in flight";
	        	nosensor.setText(line);
	        }
	    }

@Override
	protected void onResume() {
		// TODO Auto-generated method stub
	super.onResume();
		if(sensorPresent){
			sensorManager.registerListener(accelerometerListener, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);	
		}
	}

	@Override
	protected void onStop() {
	// TODO Auto-generated method stub
	super.onStop();
		if(sensorPresent){
			sensorManager.unregisterListener(accelerometerListener);	
			}
	}
	
	private SensorEventListener accelerometerListener = new SensorEventListener(){

		public void onAccuracyChanged(Sensor arg0, int arg1) {
			// TODO Auto-generated method stub
			
		}

		public void onSensorChanged( SensorEvent arg0) {
			// TODO Auto-generated method stub
			
			x = arg0.values[0];
			y = arg0.values[1];
			z = arg0.values[2];
		
			float accelationSquareRoot = (x * x + y * y + z * z)
					/ (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);
			/*long actualTime = System.currentTimeMillis();*/
			if (accelationSquareRoot >= 3) //
			{
			
		Intent openAndroidAppActivity = new Intent("com.example.gar.myapplication.MAINACTIVITY");
		startActivity(openAndroidAppActivity); 
		}
		}
	};

		   
		   
		   
		
		
	}
		
	
	
	
	
	
	
		