package com.example.questdot.flashlightexample;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    Button btnSwitchOn,btnSwitchOff;

    private Camera camera;

    private boolean hasFlash;
    Parameters params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // flash switch button
        btnSwitchOn = (Button) findViewById(R.id.btnSwitchOn);
        btnSwitchOff = (Button) findViewById(R.id.btnSwitchOff);

		/*
		 * First check if device is supporting flashlight or not
		 */
        hasFlash = getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        if (!hasFlash) {
            // device doesn't support flash
            // Show alert message and close the application
            AlertDialog alert = new AlertDialog.Builder(MainActivity.this)
                    .create();
            alert.setTitle("Error");
            alert.setMessage("Sorry, your device doesn't support flash light!");
            alert.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // closing the application
                    finish();
                }
            });
            alert.show();
            return;
        }

        btnSwitchOn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                    turnOnFlash();

            }
        });

        btnSwitchOff.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

               turnOffFlash();

            }
        });
    }

    /*
     * Get the camera
     */


    /*
     * Turning On flash
     */
    private void turnOnFlash() {

            camera = Camera.open();
            params = camera.getParameters();
            params.setFlashMode(Parameters.FLASH_MODE_TORCH);
            camera.setParameters(params);
            camera.startPreview();




    }

    /*
     * Turning Off flash
     */
    private void turnOffFlash() {


            params = camera.getParameters();
            params.setFlashMode(Parameters.FLASH_MODE_OFF);
            camera.setParameters(params);
            camera.stopPreview();



            camera.release();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();

        // on pause turn off the flash
        turnOffFlash();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // on resume turn on the flash
        if(hasFlash)
            turnOnFlash();
    }


}
