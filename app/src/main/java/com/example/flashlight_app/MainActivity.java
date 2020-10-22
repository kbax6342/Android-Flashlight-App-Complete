package com.example.flashlight_app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private CameraManager mCameraManager;
    private String mCameraId;
    private Button mOnOffButton;
    private Boolean isCameraOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mOnOffButton = (Button) findViewById(R.id.button_on_off);
        isCameraOn = false;

        Boolean isFlashAvailable = getApplication().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA_FLASH
        );

        /*
        if(!isFlashAvailable){
            AlertDialog alert = new AlertDialog.Builder(MainActivity.this).create();

            alert.setTitle("Error !!!");
            alert.setMessage("Device does not support Flashlight");
            alert.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                            System.exit(0);
                        }
                    });
            alert.show();
            return;
        }//end if

         */

        mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try{
            mCameraId = mCameraManager.getCameraIdList()[0];
        }catch (CameraAccessException ex){
            ex.printStackTrace();
        }

        mOnOffButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                try{
                    if(isCameraOn){
                        turnOffFlashLight();
                        isCameraOn = false;
                    }
                    else
                    {
                        turnOnFlashLight();
                        isCameraOn = true;
                    }
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        });


    }//end onCreate

    public void turnOnFlashLight(){
        try{
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) // TODO What is this doing
            {
                mCameraManager.setTorchMode(mCameraId, true);

               mOnOffButton.setText("ON");

            }

        }catch( Exception ex){
            ex.printStackTrace();
        }
    }//end turnOnFlashLight

    public void turnOffFlashLight(){
        try{
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) // TODO What is this doing
            {
                mCameraManager.setTorchMode(mCameraId, false);

                mOnOffButton.setText("OFF");
            }

        }catch( Exception ex){
            ex.printStackTrace();
        }
    }//end turnOnFlashLight
    //TODO implement turnOffFlashLight

}//end class