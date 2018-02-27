package ir.mahoorsoft.app.cityneed.model.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by MAD on 30/10/2017.
 */

public class ApiClient {


    public static final String serverAddress = "http://192.168.1.101";
    public static final String BASE_URL = serverAddress + "/city_need/v1/";
    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
