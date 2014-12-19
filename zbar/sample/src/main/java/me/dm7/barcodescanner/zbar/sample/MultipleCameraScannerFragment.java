package me.dm7.barcodescanner.zbar.sample;


import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.dm7.barcodescanner.zbar.BarcodeFormat;
import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;


public class MultipleCameraScannerFragment extends Fragment implements
        MessageDialogFragment.MessageDialogListener,
        ZBarScannerView.ResultHandler
         {


    private ZBarScannerView mScannerView;
    private int cameraId;


    public static MultipleCameraScannerFragment newInstance(int cameraIdToStart) {
        final Bundle args = new Bundle();
        args.putInt(Constants.Extras.CAMERA_TO_START, cameraIdToStart);
        MultipleCameraScannerFragment f = new MultipleCameraScannerFragment();
        f.setArguments(args);
        return f;
    }

    public MultipleCameraScannerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mScannerView = new ZBarScannerView(getActivity());
        mScannerView.setFormats(BarcodeFormat.ALL_FORMATS);
        return mScannerView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(getArguments() != null) {
            cameraId = getArguments().getInt(Constants.Extras.CAMERA_TO_START);
        } else {
            throw new IllegalStateException(Constants.Extras.CAMERA_TO_START + " is required in arguments");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera(cameraId);
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
        closeMessageDialog();
        closeFormatsDialog();
    }

    @Override
    public void handleResult(Result rawResult) {
        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(getActivity().getApplicationContext(), notification);
            r.play();
        } catch (Exception e) {}
        showMessageDialog("Contents = " + rawResult.getContents() + ", Format = " + rawResult.getBarcodeFormat().getName());
    }

    public void showMessageDialog(String message) {
        DialogFragment fragment = MessageDialogFragment.newInstance("Scan Results", message, this);
        fragment.show(getActivity().getSupportFragmentManager(), "scan_results");
    }


    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        // Resume the camera
        mScannerView.startCamera();
    }

    public void closeMessageDialog() {
        closeDialog("scan_results");
    }

    public void closeFormatsDialog() {
        closeDialog("format_selector");
    }

    public void closeDialog(String dialogName) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        DialogFragment fragment = (DialogFragment) fragmentManager.findFragmentByTag(dialogName);
        if(fragment != null) {
            fragment.dismiss();
        }
    }

}
