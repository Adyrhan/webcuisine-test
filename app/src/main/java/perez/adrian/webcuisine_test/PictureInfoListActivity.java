package perez.adrian.webcuisine_test;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

import perez.adrian.webcuisine_test.api.NasaAPI;
import perez.adrian.webcuisine_test.data.PictureInfo;
import perez.adrian.webcuisine_test.data.Pictures;
import perez.adrian.webcuisine_test.data.PicturesConverterFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * An activity representing a list of Pictures. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link PictureInfoDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class PictureInfoListActivity extends AppCompatActivity {

    private static final String PICTURES_KEY = "PICTURES_KEY";
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private PictureRecyclerViewAdapter mRecyclerViewAdapter;
    private Pictures mPictures;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pictureinfo_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        View recyclerView = findViewById(R.id.pictureinfo_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        if (findViewById(R.id.pictureinfo_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        if (savedInstanceState != null) {
            mPictures = savedInstanceState.getParcelable(PICTURES_KEY);
            feedAdapter();
        } else {
            retrieveData();
        }
    }

    private void retrieveData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.nasa.gov")
                .addConverterFactory(new PicturesConverterFactory())
                .build();
        NasaAPI nasaAPI = retrofit.create(NasaAPI.class);
        nasaAPI.getPictures().enqueue(new Callback<Pictures>() {
            @Override
            public void onResponse(Call<Pictures> call, Response<Pictures> response) {
                mPictures = response.body();
                feedAdapter();
            }

            @Override
            public void onFailure(Call<Pictures> call, Throwable t) {

            }
        });
    }

    private void feedAdapter() {
        ArrayList<PictureInfo> pictures = mPictures.getPictures();
        if (pictures != null) {
            for (PictureInfo picture : mPictures.getPictures()) {
                mRecyclerViewAdapter.add(picture);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle out) {
        super.onSaveInstanceState(out);
        out.putParcelable(PICTURES_KEY, mPictures);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        mRecyclerViewAdapter = new PictureRecyclerViewAdapter();
        recyclerView.setAdapter(mRecyclerViewAdapter);
    }

    public class PictureRecyclerViewAdapter
            extends RecyclerView.Adapter<PictureRecyclerViewAdapter.ViewHolder> {

        private final ArrayList<PictureInfo> mValues = new ArrayList<>();

        public PictureRecyclerViewAdapter() {
        }

        public void clear() {
            mValues.clear();
            notifyDataSetChanged();
        }

        public void add(PictureInfo info) {
            mValues.add(info);
            notifyItemInserted(mValues.size() - 1);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.pictureinfo_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
            holder.mEarthDate.setText(holder.mItem.getEarthDate());
            holder.mCameraFullName.setText(holder.mItem.getCamera().getFullName());
            Glide.with(PictureInfoListActivity.this)
                    .load(Uri.parse(holder.mItem.getPictureUrl()))
                    .centerCrop()
                    .into(holder.mImagePreview);

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putParcelable(PictureInfoDetailFragment.DATA_KEY, holder.mItem);
                        PictureInfoDetailFragment fragment = new PictureInfoDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.pictureinfo_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, PictureInfoDetailActivity.class);
                        intent.putExtra(PictureInfoDetailFragment.DATA_KEY, holder.mItem);

                        context.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mEarthDate;
            public final TextView mCameraFullName;
            public final RoundedImageView mImagePreview;
            public PictureInfo mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mEarthDate = (TextView) view.findViewById(R.id.earth_date);
                mCameraFullName = (TextView) view.findViewById(R.id.camera_full_name);
                mImagePreview = (RoundedImageView) view.findViewById(R.id.image_preview);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mCameraFullName.getText() + "'";
            }
        }
    }
}
