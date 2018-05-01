package ir.mahoorsoft.app.cityneed.model.api;


import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.model.struct.ResponseOfServer;
import ir.mahoorsoft.app.cityneed.model.struct.StCity;
import ir.mahoorsoft.app.cityneed.model.struct.StComment;
import ir.mahoorsoft.app.cityneed.model.struct.StCourse;
import ir.mahoorsoft.app.cityneed.model.struct.StCustomCourseListHome;
import ir.mahoorsoft.app.cityneed.model.struct.StCustomTeacherListHome;
import ir.mahoorsoft.app.cityneed.model.struct.StMahoorAppData;
import ir.mahoorsoft.app.cityneed.model.struct.StOstan;
import ir.mahoorsoft.app.cityneed.model.struct.StGrouping;
import ir.mahoorsoft.app.cityneed.model.struct.StSmsBox;
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

    @GET("getAllTeacher")
    Call<ArrayList<StTeacher>> getAllTeacher();

    @GET("updateTeacher/{phone}/{landPhone}/{address}/{subject}/{cityId}/{m}")
    Call<ArrayList<ResponseOfServer>> updateTeacher(
            @Path("phone") String phone,
            @Path("landPhone") String landPhone,
            @Path("address") String address,
            @Path("subject") String subject,
            @Path("cityId") int cityId,
            @Path("m") int madrak
    );

    @GET("getTabaghe/{uperId}")
    Call<ArrayList<StGrouping>> getTabaghe(
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
            @Path("minOld") int minOld,
            @Path("maxOld") int maxOld
    );

    @GET("getAllCourse")
    Call<ArrayList<StCourse>> getAllCourse();

    @GET("getCourseByFilter/{minOld}/{maxOld}/{startDate}/{endDate}/{Grouping}/{days}")
    Call<ArrayList<StCourse>> getCourseByFilter(
            @Path("minOld") int minOld,
            @Path("maxOld") int maxOld,
            @Path("startDate") String startDate,
            @Path("endDate") String endDate,
            @Path("Grouping") int groupId,
            @Path("days") String days
    );

    @GET("getCustomCourseListForHome")
    Call<ArrayList<StCustomCourseListHome>> getCustomCourseListData();

    @GET("getCourseById/{id}")
    Call<ArrayList<StCourse>> getCourseById(
            @Path("id") int id
    );

    @GET("getCourseForListHome/{id}")
    Call<ArrayList<StCustomCourseListHome>> getCourseForListHome(
            @Path("id") int id
    );

    @GET("getCourseByGroupingId/{id}")
    Call<ArrayList<StCourse>> getCourseByGroupingId(
            @Path("id") int id
    );

    @GET("getCourseByTeacherId/{ac}")
    Call<ArrayList<StCourse>> getCourseByTeacherId(
            @Path("ac") String ac
    );

    @GET("getUserCourse/{ac}")
    Call<ArrayList<StCourse>> getUserCourse(
            @Path("ac") String ac
    );

    @GET("checkedServerStatuse")
    Call<ArrayList<ResponseOfServer>> checkedServerStatuse();

    @GET("checkedUserStatuse/{id}")
    Call<ArrayList<ResponseOfServer>> checkedUserStatuse(
            @Path("id") String id
    );

    @GET("sabtenam/{idCourse}/{idTeacher}/{idUser}")
    Call<ArrayList<ResponseOfServer>> sabtenam(
            @Path("idCourse") int idCourse,
            @Path("idTeacher") String idTeacher,
            @Path("idUser") String idUser
    );

    @GET("checkSabtenam/{idCourse}/{idUser}")
    Call<ArrayList<ResponseOfServer>> checkSabtenam(
            @Path("idCourse") int idCourse,
            @Path("idUser") String idUser
    );

    @GET("getRegistrationsName/{idCourse}/{idTeacher}")
    Call<ArrayList<StUser>> getRegistrationsName(
            @Path("idCourse") int idCourse,
            @Path("idTeacher") String ac
    );

    @GET("getMsAndRat/{ac}")
    Call<ArrayList<ResponseOfServer>> getMadrakStateAndRat(
            @Path("ac") String ac
    );

    @GET("upMs/{ac}")
    Call<ArrayList<ResponseOfServer>> upMadrakState(
            @Path("ac") String ac
    );

    @GET("saveSms/{text}/{tsId}/{rsId}/{courseId}/{howSending}")
    Call<ArrayList<ResponseOfServer>> saveSms(
            @Path("text") String smsText,
            @Path("tsId") String tsId,
            @Path("rsId") String rsId,
            @Path("courseId") int courseId,
            @Path("howSending") int howSending
    );

    @GET("getRsSms/{rsId}")
    Call<ArrayList<StSmsBox>> getRsSms(
            @Path("rsId") String rsId
    );

    @GET("getTsSms/{tsId}")
    Call<ArrayList<StSmsBox>> getTsSms(
            @Path("tsId") String rsId
    );

    @GET("upDateSeen/{id}")
    Call<ArrayList<ResponseOfServer>> upDateSeen(
            @Path("id") int smsId
    );

    @GET("deleteSms/{id}")
    Call<ArrayList<ResponseOfServer>> deleteSms(
            @Path("id") int smsId
    );

    @GET("updateDeletedFlag/{courseId}/{code}")
    Call<ArrayList<ResponseOfServer>> updateDeletedFlag(
            @Path("courseId") int courseId,
            @Path("courseId") int code
    );

    @GET("updateCanceledFlag/{sabtenamId}/{code}")
    Call<ArrayList<ResponseOfServer>> updateCanceledFlag(
            @Path("sabtenamId") int sabtenamId,
            @Path("code") int code
    );

    @GET("updateMoreCanceledFlag/{data}")
    Call<ArrayList<ResponseOfServer>> updateMoreCanceledFlag(
            @Path("data") String data
    );

    @GET("saveComment/{commentText}/{userId}/{courseId}/{teacherId}/{teacherRat}")
    Call<ArrayList<ResponseOfServer>> saveComment(
            @Path("commentText") String commentText,
            @Path("userId") String userId,
            @Path("courseId") int courseId,
            @Path("teacherId") String teacherId,
            @Path("teacherRat") float teacherRat
    );

    @GET("saveCourseRat/{userId}/{courseId}/{teacherId}/{courseRat}")
    Call<ArrayList<ResponseOfServer>> saveCourseRat(
            @Path("userId") String userId,
            @Path("courseId") int courseId,
            @Path("teacherId") String teacherId,
            @Path("courseRat") float courseRat
    );

    @GET("commentFeedBack/{userId}/{commentId}/{isLicked}")
    Call<ArrayList<ResponseOfServer>> commentFeedBack(
            @Path("userId") String userId,
            @Path("commentId") int commentId,
            @Path("isLicked") int isLicked
    );

    @GET("updateComment/{id}/{commentText}/{userId}/{courseId}/{teacherId}/{teacherRat}/{courseRat}")
    Call<ArrayList<ResponseOfServer>> upDateComment(
            @Path("id") int id,
            @Path("commentText") String commentText,
            @Path("userId") String userId,
            @Path("courseId") int courseId,
            @Path("teacherId") String teacherId,
            @Path("teacherRat") float teacherRat,
            @Path("courseRat") float courseRat
    );

    @GET("getCommentByTeacherId/{teacherId}")
    Call<ArrayList<StComment>> getCommentByTeacherId(
            @Path("teacherId") String teacherId
    );

    @GET("RePoRt/{signText}/{reportText}/{spamId}/{spamerId}/{reporterId}")
    Call<ArrayList<ResponseOfServer>> report(
            @Path("signText") String signText,
            @Path("reportText") String reportText,
            @Path("spamId") int spamId,
            @Path("spamerId") String spamerId,
            @Path("reporterId") String reporterId
    );

    @GET("confirmStudent/{sabtenamId}/{ac}/{courseId}")
    Call<ArrayList<ResponseOfServer>> confirmStudent(
            @Path("sabtenamId") int sabtenamId,
            @Path("courseId") int courseId,
            @Path("ac") String apiCode
    );

    @GET("confirmMoreStudent/{data}")
    Call<ArrayList<ResponseOfServer>> confirmMoreStudent(
            @Path("data") String jsonData
            );

    @GET("getSelectedTeacher")
    Call<ArrayList<StTeacher>> selectedTeacher();

    @GET("getCustomTeacherListData")
    Call<ArrayList<StCustomTeacherListHome>> getCustomTeacherListData();

    @GET("getMahoorAppData")
    Call<ArrayList<StMahoorAppData>> getMahoorAppData();

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
