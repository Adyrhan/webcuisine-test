package perez.adrian.webcuisine_test.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Adrian on 23/10/2016.
 */

public class PictureInfo implements Parcelable {
    @SerializedName("camera")
    private Camera mCamera;

    @SerializedName("earth_date")
    private String mEarthDate;

    @SerializedName("img_src")
    private String mPictureUrl;

    private PictureInfo(Builder builder) {
        mCamera = builder.mCamera;
        mEarthDate = builder.mEarthDate;
        mPictureUrl = builder.mPictureUrl;
    }

    public Camera getCamera() {
        return mCamera;
    }

    public String getEarthDate() {
        return mEarthDate;
    }

    public String getPictureUrl() {
        return mPictureUrl;
    }

    public static class Camera implements Parcelable {
        @SerializedName("full_name")
        private String mFullName;

        public String getFullName() {
            return mFullName;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.mFullName);
        }

        public Camera() {
        }

        protected Camera(Parcel in) {
            this.mFullName = in.readString();
        }

        public static final Creator<Camera> CREATOR = new Creator<Camera>() {
            @Override
            public Camera createFromParcel(Parcel source) {
                return new Camera(source);
            }

            @Override
            public Camera[] newArray(int size) {
                return new Camera[size];
            }
        };
    }

    public static final class Builder {
        private Camera mCamera;
        private String mEarthDate;
        private String mPictureUrl;

        public Builder() {
        }

        public Builder camera(Camera val) {
            mCamera = val;
            return this;
        }

        public Builder earthDate(String val) {
            mEarthDate = val;
            return this;
        }

        public Builder pictureUrl(String val) {
            mPictureUrl = val;
            return this;
        }

        public PictureInfo build() {
            return new PictureInfo(this);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.mCamera, flags);
        dest.writeString(this.mEarthDate);
        dest.writeString(this.mPictureUrl);
    }

    protected PictureInfo(Parcel in) {
        this.mCamera = in.readParcelable(Camera.class.getClassLoader());
        this.mEarthDate = in.readString();
        this.mPictureUrl = in.readString();
    }

    public static final Parcelable.Creator<PictureInfo> CREATOR = new Parcelable.Creator<PictureInfo>() {
        @Override
        public PictureInfo createFromParcel(Parcel source) {
            return new PictureInfo(source);
        }

        @Override
        public PictureInfo[] newArray(int size) {
            return new PictureInfo[size];
        }
    };
}
