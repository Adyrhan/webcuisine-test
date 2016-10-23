package perez.adrian.webcuisine_test.data;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by Adrian on 23/10/2016.
 */
public class PicturesConverter implements Converter<ResponseBody, Pictures> {
    @Override
    public Pictures convert(ResponseBody value) throws IOException {
        Gson gson = new Gson();
        return gson.fromJson(value.string(), Pictures.class);
    }
}
