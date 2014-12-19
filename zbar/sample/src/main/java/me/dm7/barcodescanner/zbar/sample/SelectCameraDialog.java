package me.dm7.barcodescanner.zbar.sample;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Shows a list of cameras that the user can select for scanning.
 */
public class SelectCameraDialog extends DialogFragment {

    OnCameraSelected cameraSelectedListener;

    public static SelectCameraDialog newInstance(OnCameraSelected listener) {
        SelectCameraDialog f = new SelectCameraDialog();
        f.setOnCameraSelectedListener(listener);
        return f;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final List<String> cameras = new ArrayList<String>();
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();

        final int cameraCount = Camera.getNumberOfCameras();
        for ( int i = 0; i < cameraCount; i++ ) {
            Camera.getCameraInfo( i, cameraInfo );
            if ( cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT  ) {
                cameras.add(getString(R.string.front) + i);
            } else {
                cameras.add(getString(R.string.back) + i);
            }
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setItems(cameras.toArray(new String[cameras.size()]), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(cameraSelectedListener != null) {
                    cameraSelectedListener.onCameraSelected(which);
                } else {
                    Toast.makeText(getActivity(), R.string.error_camera_listener, Toast.LENGTH_LONG).show();
                }
                dialog.dismiss();
            }
        });



        return builder.create();

    }

    public void setOnCameraSelectedListener(OnCameraSelected cameraSelectedListener) {
        this.cameraSelectedListener = cameraSelectedListener;
    }



    public interface OnCameraSelected {
        void onCameraSelected(int cameraId);
    }


}
