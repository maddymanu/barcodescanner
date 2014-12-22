package me.dm7.barcodescanner.zbar.sample;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

import me.dm7.barcodescanner.core.CameraPreviewDescriptor;
import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;


public class MultiplePreviewActivity extends ActionBarActivity
        implements ZBarScannerView.ResultHandler, ZBarScannerView.PreviewCallback {

    private ZBarScannerView mScannerView;
    private ImageView secondaryPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_preview);

        mScannerView = new ZBarScannerView(this);

        FrameLayout scannerPreview = (FrameLayout) findViewById(R.id.scanner_preview);
        scannerPreview.addView(mScannerView);
        secondaryPreview = (ImageView) findViewById(R.id.secondary_preview);



    }

    @Override
    protected void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.setPreviewCallback(this);

        mScannerView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result rawResult) {

    }


    @Override
    public void onPreviewFrame(CameraPreviewDescriptor descriptor) {
        Log.d("PREVIEW", "Preview Frame Data Length: " + (descriptor != null ? descriptor.getData().length : 0));
        YuvImage yuvImage = new YuvImage(descriptor.getData(), descriptor.getPreviewFormat(), descriptor.getWidth(), descriptor.getHeight(), null);
        final Rect rect = new Rect(0, 0, descriptor.getWidth(), descriptor.getHeight());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        yuvImage.compressToJpeg(rect, 50, out);
        byte[] imageBytes = out.toByteArray();
        Bitmap image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        secondaryPreview.setImageBitmap(image);
    }
}
