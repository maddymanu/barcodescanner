package me.dm7.barcodescanner.core;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.util.Log;

public class CameraUtils {
    /** A safe way to get an instance of the Camera object. */
    public static Camera getCameraInstance(){
        return getCameraInstance(null);
    }

    /** A safe way to get an instance of the Camera object. */
    public static Camera getCameraInstance(Integer cameraId){
        Camera c = null;
        try {
            // attempt to get a Camera instance
            c = cameraId == null ? Camera.open() : Camera.open(cameraId);
        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
            Log.e(CameraUtils.class.getName(), "Could not open camera.", e);
        }
        return c; // returns null if camera is unavailable
    }

    public static boolean isFlashSupported(Context context){
        PackageManager packageManager = context.getPackageManager();
        // if device support camera flash?
        if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
            return true;
        }
        return false;
    }
}
