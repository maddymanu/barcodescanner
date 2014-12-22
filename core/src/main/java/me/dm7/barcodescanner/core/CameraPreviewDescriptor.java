package me.dm7.barcodescanner.core;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * A parcelable class that provides necessary data to manually create an additional preview. 
 * This class is implements Parcelable {@link android.os.Parcelable} so that it can cross process boundaries. 
 */
public class CameraPreviewDescriptor implements Parcelable {
    private byte[] data;
    private int previewFormat;
    private int width;
    private int height;

    public CameraPreviewDescriptor(byte[] data, int previewFormat, int width, int height) {
        this.data = data;
        this.previewFormat = previewFormat;
        this.width = width;
        this.height = height;
    }

    public byte[] getData() {
        return data;
    }

    public int getPreviewFormat() {
        return previewFormat;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    protected CameraPreviewDescriptor(Parcel in) {
        in.readByteArray(data);
        previewFormat = in.readInt();
        width = in.readInt();
        height = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByteArray(data);
        dest.writeInt(previewFormat);
        dest.writeInt(width);
        dest.writeInt(height);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<CameraPreviewDescriptor> CREATOR = new Parcelable.Creator<CameraPreviewDescriptor>() {
        @Override
        public CameraPreviewDescriptor createFromParcel(Parcel in) {
            return new CameraPreviewDescriptor(in);
        }

        @Override
        public CameraPreviewDescriptor[] newArray(int size) {
            return new CameraPreviewDescriptor[size];
        }
    };
}