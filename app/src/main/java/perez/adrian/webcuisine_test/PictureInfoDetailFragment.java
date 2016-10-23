package perez.adrian.webcuisine_test;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import perez.adrian.webcuisine_test.data.PictureInfo;

/**
 * A fragment representing a single PictureInfo detail screen.
 * This fragment is either contained in a {@link PictureInfoListActivity}
 * in two-pane mode (on tablets) or a {@link PictureInfoDetailActivity}
 * on handsets.
 */
public class PictureInfoDetailFragment extends Fragment {

    public static final String DATA_KEY = "DATA_KEY";
    private PictureInfo mPictureInfo;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PictureInfoDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(DATA_KEY)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mPictureInfo = getArguments().getParcelable(DATA_KEY);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ImageView rootView = (ImageView)inflater.inflate(R.layout.pictureinfo_detail, container, false);
        Glide.with(this)
                .load(Uri.parse(mPictureInfo.getPictureUrl()))
                .fitCenter();
        return rootView;
    }
}
