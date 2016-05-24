package com.example.gar.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;


public class MainActivity extends Activity implements SensorEventListener {

    AudioManager audioManager;
    SensorManager sensorManager;
    SensorManager sensorManager2;
    boolean sensorPresent;
    boolean orientationPresent;
    Sensor orientationSensor;
    Sensor Sensor;
    Sensor accSensor;
    TextView first;
    TextView textViewAvailableSensors;
    TextView x, y, z, x2 ;
    List<Sensor> allSensors	;
    TextView size;
    float x_value;
    float z_value;
    float degrees;
    ProgressBar altitude, height;
    int origin = 50;
    int heightvalue;
    ImageButton plane;
    Bitmap bMap;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        x = (TextView)findViewById(R.id.x);
        y = (TextView)findViewById(R.id.y);
        z = (TextView)findViewById(R.id.z);
        //--get the ids for the xml file--//
        // String sizenow = "";
        first = (TextView)findViewById(R.id.first);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        textViewAvailableSensors= (TextView) findViewById(R.id.textViewAvailableSensors);
        altitude = (ProgressBar)findViewById(R.id.altitudebar);
        height = (ProgressBar)findViewById(R.id.heightbar);
        size = (TextView)findViewById(R.id.size);
        x2 = (TextView)findViewById(R.id.x2);
        /*y2 = (TextView)findViewById(R.id.y2);
        z2 = (TextView)findViewById(R.id.z2);*/


      /*  ImageView image = (ImageView) findViewById(R.id.drawable);
        Bitmap bMap = BitmapFactory.decodeResource(getResources(), R.drawable.aeroplane);
        Matrix mat = new Matrix();
        degrees = (-10*(int)x_value);
        mat.postRotate(degrees);

        Bitmap bMapRotate = Bitmap.createBitmap(bMap, 0, 0, bMap.getWidth(), bMap.getHeight(), mat, true);
        image.setImageBitmap(bMapRotate);*/

        //--------I had ALOT of problems for rotation, but finally realised that its because I'm setting-------------------//
        //------rotation degree before I even register the sensors, so the x_value at this point will be 0-----------------//

       /* allSensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        String strSensors = "";
        for (Sensor s : allSensors)
        {
        	strSensors += "Found sensor " + s.getName() + "\n";
     	 //  	Log.i(yoyo, "Found sensor " + s.getName());
        }

        textViewAvailableSensors.setText(strSensors);
        */

        //---BMA220 found on the phone = accelerometer---//
        //---MMC314X = Magnetic Field----//
        //---Mag and Acc Combo Orientation Sensor----//
        //---TAOS = Light Sensor?---//
        //---Gravity Sensor---//
        //---Linear Acceleration Sensor---//
        //---Rotation Vector Sensor---//

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        sensorManager2 = (SensorManager)getSystemService(SENSOR_SERVICE);
        List<Sensor> sensorList = sensorManager.getSensorList(android.hardware.Sensor.TYPE_ALL);
        //     accSensor = sensorManager3.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        orientationSensor = sensorManager2.getDefaultSensor(android.hardware.Sensor.TYPE_ORIENTATION);

        if(sensorList.size() > 0){
            sensorPresent = true;
            Sensor = sensorList.get(0);
            // 	orientationPresent = true;
            first.setText("Sensors are present");
            float test;
            test = sensorList.size();
            first.setText("There is a total of " + Float.toString(test) + " sensors.");

            plane = (ImageButton) findViewById(R.id.drawable);
            plane.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    origin -= 10;
                    if (origin <= 0) {
                        startActivity(new Intent("com.example.gar.myapplication.STARTING"));
                    }
                }
            });
            bMap = BitmapFactory.decodeResource(getResources(), R.drawable.aeroplane); //getting the aeroplane picture up from drawable folder
        }
        else{
            sensorPresent = false;
            first.setText("Sensor NOT present");
        }

       /*

        MediaRecorder recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setOutputFile(yoyo);
        recorder.prepare();
        recorder.start();   // Recording is now started

        recorder.stop();
        recorder.reset();   // You can reuse the object by going back to setAudioSource() step
        */
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        if(sensorPresent){
            //sensorManager.registerListener(accelerometerListener, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);

            sensorManager2.registerListener(this, orientationSensor, SensorManager.SENSOR_DELAY_NORMAL);
            //	sensorManager3.registerListener(this, accSensor, SensorManager.SENSOR_DELAY_NORMAL);//
        }
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        if(sensorPresent){
            //		sensorManager.unregisterListener(accelerometerListener);
            sensorManager2.unregisterListener(this);
        }
    }

	/*private SensorEventListener accelerometerListener = new SensorEventListener(){*/

    public void onAccuracyChanged(Sensor arg0, int arg1) {
        // TODO Auto-generated method stub

    }

    public void onSensorChanged( SensorEvent arg0) {
        if (origin <= 0){
            startActivity(new Intent("com.example.gar.myapplication.STARTING"));
        }

        x_value = arg0.values[0];
        float y_value = arg0.values[1];
        z_value = arg0.values[2];
        x.setText(String.valueOf(x_value));
        y.setText(String.valueOf(y_value));
        z.setText(String.valueOf(z_value));

        //--Instead of using canvas draw, I used bitmap image instead, using ImageView--//
        //--When I was working on the canvas draw style, I had alot of problems rotating, plus, I had problems adding text on as well--//

        Matrix m = new Matrix();
        degrees = (float) (-1*z_value); //setting degrees,
        m.postRotate(degrees); //matrix rotate, I have read out of set,pre,and post....post is the recommended method of rotation


        Bitmap bMapRotate = Bitmap.createBitmap(bMap, 0, 0, bMap.getWidth(), bMap.getHeight(), m, true);
        //Set up tocreate the picture at certain angle called from postrotate......this will make it look like its rotating

        plane.setImageBitmap(bMapRotate);

        int number = (((int) y_value * -1) + 47); //making sure hte number set will rise and decrease due to orientation

        //progress seems to only like int and not float, thus changing the y_value to integer.
        altitude.setProgress(number);
        //  Button imagebutton =(Button)findViewById(R.id.drawable);

        height.setProgress(origin);

        if (number > 60) {
            origin += 5;
        } else if (number < 40) {
            origin -= 5;
        }

        x2.setText("Turning at " + String.valueOf(degrees) + " degrees");

        //setting some limits, if angle exceeds 70 degrees, then the plane will 'crash
        if (degrees > 70) {
            startActivity(new Intent("com.example.gar.myapplication.STARTING"));
        } else if (degrees < -70) {
            startActivity(new Intent("com.example.gar.myapplication.STARTING"));
        }

	         /*-------------Now I need to find  a few to view microphone activity, it seems the solution is not as simple as I thought------*/
	        /*-----------But first, onclick listener can be implemented, however I am still thinking of how to show the height of the plane properly---*/
	        /*----the Altitude using orientation just shows how much the plane is rising or descending in space (theoretically)-----*/

        //--changing floats to string, and then to be displayed for the id's of x y z --//
        //--using the values from the accelerometer to manipulate the size of the text.--//
		/*	String sizenow = "look at this!";
			size.setTextSize(y_value*6);
			size.setText(sizenow);*/
    }
};

