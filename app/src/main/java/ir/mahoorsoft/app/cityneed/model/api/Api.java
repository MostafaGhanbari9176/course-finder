package ir.mahoorsoft.app.cityneed.model.api;


import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.model.struct.Response;
import ir.mahoorsoft.app.cityneed.model.struct.StCity;
import ir.mahoorsoft.app.cityneed.model.struct.StCourse;
import ir.mahoorsoft.app.cityneed.model.struct.StOstan;
import ir.mahoorsoft.app.cityneed.model.struct.StTabaghe;
import ir.mahoorsoft.app.cityneed.model.struct.StTeacher;
import ir.mahoorsoft.app.cityneed.model.struct.StUser;
import ir.mahoorsoft.app.cityneed.model.struct.UploadRes;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
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

    @GET("logIn/{phone}")
    Call<ArrayList<Response>> logIn(
            @Path("phone") long phone
    );

    @GET("updateUser/{phone}/{name}")
    Call<ArrayList<Response>> updateUser(
            @Path("phone") String phone,
            @Path("name") String name
    );

    @GET("getUser/{phone}")
    Call<ArrayList<StUser>> getUser(
            @Path("phone") String phone
    );

    @GET("logOut/{phone}")
    Call<ArrayList<Response>> logOut(
            @Path("phone") String phone
    );

  //  @GET()

    @GET("createAndSaveSmsCode/{phone}")
    Call<ArrayList<Response>> createSmsCode(
            @Path("phone") String phone
    );

    @GET("checkedSmsCode/{phone}/{code}")
    Call<ArrayList<Response>> checkedSmsCode(
            @Path("phone") String phone,
            @Path("code") int code
    );

    @GET("addTeacher/{phone}/{landPhone}/{subject}/{tozihat}/{type}/{lat}/{lon}")
    Call<ArrayList<Response>> addTeacher(
            @Path("phone") String phone,
            @Path("landPhone") String landPhone,
            @Path("subject") String subject,
            @Path("tozihat") String tozihat,
            @Path("type") int type,
            @Path("lat") String lat,
            @Path("lon") String lon
    );

    @Multipart
    @POST("upload.php")
    Call<ArrayList<Response>> upload(
            @Part("description") RequestBody description,
            @Part MultipartBody.Part file
    );

    @GET("getTeacher/{phone}")
    Call<ArrayList<StTeacher>> getTeacher(
            @Path("phone") String phone
    );

    @GET("updateTeacher/{phone}/{landPhone}/{address}/{subject}/{cityId}/{madrak}")
    Call<ArrayList<Response>> updateTeacher(
            @Path("phone") String phone,
            @Path("landPhone") String landPhone,
            @Path("address") String address,
            @Path("subject") String subject,
            @Path("cityId") int cityId ,
            @Path("madrak") int madrak
            );

    @GET("getTabaghe/{uperId}")
    Call<ArrayList<StTabaghe>> getTabaghe(
            @Path("uperId") int uperId
    );

    @GET("addCourse/{teacherId}/{subject}/{tabagheId}/{type}/{capacity}/{mony}/{sharayet}/{tozihat}/{startDate}/{endDate}/{day}/{hours}/{oldRange}")
    Call<ArrayList<Response>> addCourse(
            @Path("teacherId") String teacherId,
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
            @Path("oldRange") int oldRange
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
    Call<ArrayList<Response>> checkedServer();

    @GET("sabtenam/{idCourse}/{idTeacher}/{idUser}")
    Call<ArrayList<Response>> sabtenam(
            @Path("idCourse") int idCourse,
            @Path("idTeacher") String idTeacher,
            @Path("idUser") String idUser
    );

    @GET("getUserCourse/{id}")
    Call<ArrayList<StCourse>> getUserCourse(
            @Path("id") int id
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
