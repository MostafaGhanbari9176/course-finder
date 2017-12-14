package ir.mahoorsoft.app.cityneed.model.db;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.model.api.Api;
import ir.mahoorsoft.app.cityneed.model.api.ApiClient;
import ir.mahoorsoft.app.cityneed.model.struct.StCity;
import ir.mahoorsoft.app.cityneed.model.struct.StOstan;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by MAHNAZ on 11/18/2017.
 */

public class DB {

    public static class City {
        static ArrayList<StCity> data = new ArrayList<>();
        static StCity error = new StCity("error", -1, -1);
        static Api api = ApiClient.getClient().create(Api.class);
        static Call<ArrayList<StCity>> getCity;

        public static ArrayList<StCity> getCity(String flag) {

            getCity = api.getCity(flag);
            getCity.enqueue(new Callback<ArrayList<StCity>>() {
                @Override
                public void onResponse(Call<ArrayList<StCity>> call, Response<ArrayList<StCity>> response) {
                    data.clear();
                    data = response.body();
                }

                @Override
                public void onFailure(Call<ArrayList<StCity>> call, Throwable t) {
                    data.clear();
                    data.add(error);
                }
            });
            return data;
        }

        public static ArrayList<StCity> search(String flag) {

            getCity = api.searchCity(flag);
            getCity.enqueue(new Callback<ArrayList<StCity>>() {
                @Override
                public void onResponse(Call<ArrayList<StCity>> call, Response<ArrayList<StCity>> response) {
                    data.clear();
                    data = response.body();
                }

                @Override
                public void onFailure(Call<ArrayList<StCity>> call, Throwable t) {
                    data.clear();
                    data.add(error);
                }
            });

            return data;
        }

    }

    public static class Ostan {
        public static ArrayList<StOstan> data = new ArrayList<>();



        public static ArrayList<StOstan> getOstan() {

            Api api = ApiClient.getClient().create(Api.class);
            Call<ArrayList<StOstan>> getOstan = api.getOstan();
            getOstan.enqueue(new Callback<ArrayList<StOstan>>() {
                @Override
                public void onResponse(Call<ArrayList<StOstan>> call, Response<ArrayList<StOstan>> response) {
                    data.clear();
                    data = response.body();
                }

                @Override
                public void onFailure(Call<ArrayList<StOstan>> call, Throwable t) {
                    data.clear();

                }
            });
            return data;
        }

        public static ArrayList<StOstan> searchOstan(String flag) {
            Api api = ApiClient.getClient().create(Api.class);
            Call<ArrayList<StOstan>> searchOstan = api.serachOstan(flag);
            searchOstan.enqueue(new Callback<ArrayList<StOstan>>() {
                @Override
                public void onResponse(Call<ArrayList<StOstan>> call, Response<ArrayList<StOstan>> response) {

                    data.clear();
                    data = response.body();
                }

                @Override
                public void onFailure(Call<ArrayList<StOstan>> call, Throwable t) {
                    data.clear();

                }
            });
            return data;
        }
    }
}
