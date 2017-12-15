package ir.mahoorsoft.app.cityneed.model.api;


import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.model.struct.Response;
import ir.mahoorsoft.app.cityneed.model.struct.StCity;
import ir.mahoorsoft.app.cityneed.model.struct.StOstan;
import ir.mahoorsoft.app.cityneed.model.struct.StUser;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by MAD on 30/10/2017.
 */

public interface Api {

    @GET("getOstan")
    Call<ArrayList<StOstan>> getOstan();

    @GET("getCity/{flag}")
    Call<ArrayList<StCity>> getCity(
            @Path("flag") String flag
    );

    @GET("savePhone/{phone}")
    Call<ArrayList<Response>> savePhone(
            @Path("phone") long phone
    );

    @GET("updateUser/{phone}/{name}/{family}/{status}/{type}/{cityId}/{apiCode}")
    Call<ArrayList<Response>> updateUser(
            @Path("phone") long phone,
            @Path("name") String name,
            @Path("family") String family,
            @Path("status") int status,
            @Path("type") int type,
            @Path("cityId") int cityId,
            @Path("apiCode") int apiCode
    );

    @GET("getUser/{phone}")
    Call<ArrayList<StUser>> getUser(
            @Path ("phone") long phone
    );



    @GET("remaining_credit/{tableName}/{phone}")
    Call<String> remainCredit(
            @Path("tableName") String tableName,
            @Path("phone") String phone);


    @FormUrlEncoded
    @POST("use_credit")
    Call<ArrayList> useCredit(
            @Field("table_name") String tableName,
            @Field("phone") String phone);


}
