package perez.adrian.webcuisine_test.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Adrian on 23/10/2016.
 */

public class Pictures implements Parcelable {
    @SerializedName("photos")
    private ArrayList<PictureInfo> mPictures;

    public ArrayList<PictureInfo> getPictures() {
        return mPictures;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.mPictures);
    }

    public Pictures() {
    }

    protected Pictures(Parcel in) {
        this.mPictures = new ArrayList<PictureInfo>();
        in.readList(this.mPictures, PictureInfo.class.getClassLoader());
    }

    public static final Parcelable.Creator<Pictures> CREATOR = new Parcelable.Creator<Pictures>() {
        @Override
        public Pictures createFromParcel(Parcel source) {
            return new Pictures(source);
        }

        @Override
        public Pictures[] newArray(int size) {
            return new Pictures[size];
        }
    };
}
