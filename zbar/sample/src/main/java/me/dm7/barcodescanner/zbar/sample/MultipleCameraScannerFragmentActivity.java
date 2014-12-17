package me.dm7.barcodescanner.zbar.sample;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

public class MultipleCameraScannerFragmentActivity extends ActionBarActivity {

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_multiple_camera_scanner_fragment);

        int cameraToStart = getIntent().getIntExtra(Constants.Extras.CAMERA_TO_START, -1);
        if(cameraToStart != -1) {
            MultipleCameraScannerFragment f = MultipleCameraScannerFragment.newInstance(cameraToStart);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_frame, f)
                    .commit();
        } else {
            Toast.makeText(this, R.string.error_no_camera_id, Toast.LENGTH_LONG).show();

        }
    }


}
