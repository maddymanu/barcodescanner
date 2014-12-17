package me.dm7.barcodescanner.zbar.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

public class MainActivity extends ActionBarActivity implements SelectCameraDialog.OnCameraSelected {
    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_main);
    }

    public void launchSimpleActivity(View v) {
        Intent intent = new Intent(this, SimpleScannerActivity.class);
        startActivity(intent);
    }

    public void launchSimpleFragmentActivity(View v) {
        Intent intent = new Intent(this, SimpleScannerFragmentActivity.class);
        startActivity(intent);
    }

    public void launchActivity(View v) {
        Intent intent = new Intent(this, ScannerActivity.class);
        startActivity(intent);
    }

    public void launchFragmentActivity(View v) {
        Intent intent = new Intent(this, ScannerFragmentActivity.class);
        startActivity(intent);
    }

    public void launchMultipleCameraFragment(View v) {

        SelectCameraDialog dialog = SelectCameraDialog.newInstance(this);
        dialog.show(getSupportFragmentManager(), SelectCameraDialog.class.getName());



    }

    @Override
    public void onCameraSelected(int cameraId) {
        Intent intent = new Intent(this, MultipleCameraScannerFragmentActivity.class);
        intent.putExtra(Constants.Extras.CAMERA_TO_START, cameraId);
        startActivity(intent);
    }
}