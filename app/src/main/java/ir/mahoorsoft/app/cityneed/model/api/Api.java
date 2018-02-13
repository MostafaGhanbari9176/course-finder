package ir.mahoorsoft.app.cityneed.model.api;


import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.model.struct.ResponseOfServer;
import ir.mahoorsoft.app.cityneed.model.struct.StCity;
import ir.mahoorsoft.app.cityneed.model.struct.StCourse;
import ir.mahoorsoft.app.cityneed.model.struct.StOstan;
import ir.mahoorsoft.app.cityneed.model.struct.StTabaghe;
import ir.mahoorsoft.app.cityneed.model.struct.StTeacher;
import ir.mahoorsoft.app.cityneed.model.struct.StUser;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

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

    @GET("updateUser/{phone}/{name}")
    Call<ArrayList<ResponseOfServer>> updateUser(
            @Path("phone") String phone,
            @Path("name") String name
    );

    @GET("getUser/{phone}")
    Call<ArrayList<StUser>> getUser(
            @Path("phone") String phone
    );

    @GET("logOut/{phone}")
    Call<ArrayList<ResponseOfServer>> logOut(
            @Path("phone") String phone
    );

  //  @GET()

    @GET("createAndSaveSmsCode/{phone}")
    Call<ArrayList<ResponseOfServer>> createSmsCode(
            @Path("phone") String phone
    );

    @GET("logUp/{phone}/{name}/{code}")
    Call<ArrayList<ResponseOfServer>> logUp(
            @Path("phone") String phone,
            @Path("name") String name,
            @Path("code") int code
    );

    @GET("logIn/{phone}/{code}")
    Call<ArrayList<ResponseOfServer>> logIn(
            @Path("phone") String phone,
            @Path("code") int code
    );

    @GET("addTeacher/{ac}/{landPhone}/{subject}/{tozihat}/{type}/{lat}/{lon}")
    Call<ArrayList<ResponseOfServer>> addTeacher(
            @Path("ac") String ApiCode,
            @Path("landPhone") String landPhone,
            @Path("subject") String subject,
            @Path("tozihat") String tozihat,
            @Path("type") int type,
            @Path("lat") String lat,
            @Path("lon") String lon
    );

    @Multipart
    @POST("upload.php")
    Call<ArrayList<ResponseOfServer>> upload(
            @Part("description") RequestBody description,
            @Part MultipartBody.Part file
    );

    @GET("getTeacher/{phone}")
    Call<ArrayList<StTeacher>> getTeacher(
            @Path("phone") String phone
    );

    @GET("updateTeacher/{phone}/{landPhone}/{address}/{subject}/{cityId}/{m}")
    Call<ArrayList<ResponseOfServer>> updateTeacher(
            @Path("phone") String phone,
            @Path("landPhone") String landPhone,
            @Path("address") String address,
            @Path("subject") String subject,
            @Path("cityId") int cityId ,
            @Path("m") int madrak
            );

    @GET("getTabaghe/{uperId}")
    Call<ArrayList<StTabaghe>> getTabaghe(
            @Path("uperId") int uperId
    );

    @GET("addCourse/{ac}/{subject}/{tabagheId}/{type}/{capacity}/{mony}/{sharayet}/{tozihat}/{startDate}/{endDate}/{day}/{hours}/{minOld}/{maxOld}")
    Call<ArrayList<ResponseOfServer>> addCourse(
            @Path("ac") String ac,
            @Path("subject") String subject,
            @Path("tabagheId") int tabagheId,
            @Path("type") int type,
            @Path("capacity") int capacity,
            @Path("mony") int mony,
            @Path("sharayet") String sharayet,
            @Path("tozihat") String tozihat,
            @Path("startDate") String startDate,
            @Path("endDate") String endDate,
            @Path("day") String day,
            @Path("hours") String hours,
            @Path("minOld") String minOld,
            @Path("maxOld") String maxOld
    );

    @GET("getAllCourse")
    Call<ArrayList<StCourse>> getAllCourse();

    @GET("getCourseById/{id}")
    Call<ArrayList<StCourse>> getCourseById(
            @Path("id") int id
    );

    @GET("getCourseByTeacherId/{id}")
    Call<ArrayList<StCourse>> getCourseByTeacherId(
            @Path("id") String id
    );

    @GET("checkedServer")
    Call<ArrayList<ResponseOfServer>> checkedServer();

    @GET("sabtenam/{idCourse}/{idTeacher}/{idUser}")
    Call<ArrayList<ResponseOfServer>> sabtenam(
            @Path("idCourse") int idCourse,
            @Path("idTeacher") String idTeacher,
            @Path("idUser") String idUser
    );

    @GET("getUserCourse/{id}")
    Call<ArrayList<StCourse>> getUserCourse(
            @Path("id") int id
    );

    @GET("getMs/{ac}")
    Call<ArrayList<ResponseOfServer>> getMadrakState(
            @Path("ac") String ac
    );

    @GET("upMs/{ac}")
    Call<ArrayList<ResponseOfServer>> upMadrakState(
            @Path("ac") String ac
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
