package perez.adrian.webcuisine_test.data;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by Adrian on 23/10/2016.
 */

public class PicturesConverterFactory extends Converter.Factory {
    public Converter<ResponseBody, Pictures> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        if (type == Pictures.class) {
            return new PicturesConverter();
        } else {
            return null;
        }
    }
}
