package perez.adrian.webcuisine_test.api;

import perez.adrian.webcuisine_test.data.Pictures;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Adrian on 23/10/2016.
 */

public interface NasaAPI {
    @GET("mars-photos/api/v1/rovers/curiosity/photos?sol=1000&api_key=DEMO_KEY")
    Call<Pictures> getPictures();
}
