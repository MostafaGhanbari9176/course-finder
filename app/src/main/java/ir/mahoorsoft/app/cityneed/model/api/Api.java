package ir.mahoorsoft.app.cityneed.model.api;


import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.model.struct.ResponseOfServer;
import ir.mahoorsoft.app.cityneed.model.struct.StBuy;
import ir.mahoorsoft.app.cityneed.model.struct.StCity;
import ir.mahoorsoft.app.cityneed.model.struct.StComment;
import ir.mahoorsoft.app.cityneed.model.struct.StCourse;
import ir.mahoorsoft.app.cityneed.model.struct.StCustomCourseListHome;
import ir.mahoorsoft.app.cityneed.model.struct.StCustomTeacherListHome;
import ir.mahoorsoft.app.cityneed.model.struct.StMahoorAppData;
import ir.mahoorsoft.app.cityneed.model.struct.StNotifyData;
import ir.mahoorsoft.app.cityneed.model.struct.StOstan;
import ir.mahoorsoft.app.cityneed.model.struct.StGrouping;
import ir.mahoorsoft.app.cityneed.model.struct.StSmsBox;
import ir.mahoorsoft.app.cityneed.model.struct.StSubscribe;
import ir.mahoorsoft.app.cityneed.model.struct.StTeacher;
import ir.mahoorsoft.app.cityneed.model.struct.StUser;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
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

    @FormUrlEncoded
    @POST("logOut")
//
    Call<ArrayList<ResponseOfServer>> logOut(
            @Field("email") String phone
    );


    @FormUrlEncoded
    @POST("createAndSaveSmsCode")
//
    Call<ArrayList<ResponseOfServer>> createSmsCode(
            @Field("email") String phone
    );

    @FormUrlEncoded
    @POST("logUp")
//
    Call<ArrayList<ResponseOfServer>> logUp(
            @Field("email") String phone,
            @Field("name") String name,
            @Field("code") int code
    );

    @FormUrlEncoded
    @POST("logIn")
//
    Call<ArrayList<ResponseOfServer>> logIn(
            @Field("email") String phone,
            @Field("code") int code
    );

    @FormUrlEncoded
    @POST("addTeacher")
//
    Call<ArrayList<ResponseOfServer>> addTeacher(
            @Field("ac") String ApiCode,
            @Field("landPhone") String landPhone,
            @Field("subject") String subject,
            @Field("tozihat") String tozihat,
            @Field("type") int type,
            @Field("lat") String lat,
            @Field("lon") String lon,
            @Field("address") String address
    );

    @Multipart
    @POST("upload.php")
    Call<ArrayList<ResponseOfServer>> upload(
            @Part("description") RequestBody description,
            @Part MultipartBody.Part file
    );

    @FormUrlEncoded
    @POST("getTeacher")
//
    Call<ArrayList<StTeacher>> getTeacher(
            @Field("teacherApi") String teacherApi,
            @Field("userApi") String userApi
    );

    @GET("getAllTeacher")
    Call<ArrayList<StTeacher>> getAllTeacher();

    @GET("updateTeacher/{email}/{landPhone}/{address}/{subject}/{cityId}/{m}")
    Call<ArrayList<ResponseOfServer>> updateTeacher(
            @Path("email") String phone,
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

    @FormUrlEncoded
    @POST("addCourse")
//
    Call<ArrayList<ResponseOfServer>> addCourse(
            @Field("ac") String ac,
            @Field("teacherName") String teacherName,
            @Field("subject") String subject,
            @Field("tabagheId") int tabagheId,
            @Field("type") int type,
            @Field("capacity") int capacity,
            @Field("mony") int mony,
            @Field("sharayet") String sharayet,
            @Field("tozihat") String tozihat,
            @Field("startDate") String startDate,
            @Field("endDate") String endDate,
            @Field("day") String day,
            @Field("hours") String hours,
            @Field("minOld") int minOld,
            @Field("maxOld") int maxOld
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

    @FormUrlEncoded
    @POST("getCourseById")
//
    Call<ArrayList<StCourse>> getCourseById(
            @Field("id") int id,
            @Field("userApi") String userApi
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

    @FormUrlEncoded
    @POST("getUserCourse")
//
    Call<ArrayList<StCourse>> getUserCourse(
            @Field("ac") String ac
    );

    @FormUrlEncoded
    @POST("checkedUserStatuse")
//
    Call<ArrayList<ResponseOfServer>> checkedUserStatuse(
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("sabtenam")
//
    Call<ArrayList<ResponseOfServer>> sabtenam(
            @Field("idCourse") int idCourse,
            @Field("idTeacher") String idTeacher,
            @Field("idUser") String idUser,
            @Field("cellPhone") String cellPhone
    );

    @FormUrlEncoded
    @POST("checkSabtenam")
//
    Call<ArrayList<ResponseOfServer>> checkSabtenam(
            @Field("idCourse") int idCourse,
            @Field("idUser") String idUser
    );

    @FormUrlEncoded
    @POST("getRegistrationsName")
//
    Call<ArrayList<StUser>> getRegistrationsName(
            @Field("idCourse") int idCourse,
            @Field("idTeacher") String ac
    );

    @FormUrlEncoded
    @POST("getMsAndRat")
//
    Call<ArrayList<ResponseOfServer>> getMadrakStateAndRat(
            @Field("ac") String ac
    );

    @FormUrlEncoded
    @POST("upMs")
//
    Call<ArrayList<ResponseOfServer>> upMadrakState(
            @Field("ac") String ac
    );

    @FormUrlEncoded
    @POST("saveSms")
//
    Call<ArrayList<ResponseOfServer>> saveSms(
            @Field("text") String smsText,
            @Field("tsId") String tsId,
            @Field("rsId") String rsId,
            @Field("courseId") int courseId,
            @Field("howSending") int howSending
    );

    @FormUrlEncoded
    @POST("sendMoreSms")
//
    Call<ArrayList<ResponseOfServer>> sendMoreSms(
            @Field("data") String data,
            @Field("message") String message
    );

    @FormUrlEncoded
    @POST("getRsSms")
//
    Call<ArrayList<StSmsBox>> getRsSms(
            @Field("rsId") String rsId
    );

    @FormUrlEncoded
    @POST("getTsSms")
//
    Call<ArrayList<StSmsBox>> getTsSms(
            @Field("tsId") String rsId
    );

    @GET("upDateSeen/{id}")
    Call<ArrayList<ResponseOfServer>> upDateSeen(
            @Path("id") int smsId
    );

    @FormUrlEncoded
    @POST("deleteSms")
//
    Call<ArrayList<ResponseOfServer>> deleteSms(
            @Field("id") int smsId,
            @Field("ac") String ac
    );

    @GET("updateDeletedFlag/{courseId}/{code}")
    Call<ArrayList<ResponseOfServer>> updateDeletedFlag(
            @Path("courseId") int courseId,
            @Path("code") int code
    );

    @FormUrlEncoded
    @POST("updateCanceledFlag")
//
    Call<ArrayList<ResponseOfServer>> updateCanceledFlag(
            @Field("sabtenamId") int sabtenamId,
            @Field("code") int code,
            @Field("courseId") int courseId,
            @Field("message") String message,
            @Field("tsId") String tsId,
            @Field("rsId") String rsId
    );

    @FormUrlEncoded
    @POST("updateMoreCanceledFlag")
//
    Call<ArrayList<ResponseOfServer>> updateMoreCanceledFlag(
            @Field("data") String data,
            @Field("message") String message
    );

    @FormUrlEncoded
    @POST("saveComment")
//
    Call<ArrayList<ResponseOfServer>> saveComment(
            @Field("commentText") String commentText,
            @Field("userId") String userId,
            @Field("courseId") int courseId,
            @Field("teacherId") String teacherId,
            @Field("teacherRat") float teacherRat
    );

    @FormUrlEncoded
    @POST("saveCourseRat")
//
    Call<ArrayList<ResponseOfServer>> saveCourseRat(
            @Field("userId") String userId,
            @Field("courseId") int courseId,
            @Field("teacherId") String teacherId,
            @Field("courseRat") float courseRat
    );

    @FormUrlEncoded
    @POST("commentFeedBack")
//
    Call<ArrayList<ResponseOfServer>> commentFeedBack(
            @Field("userId") String userId,
            @Field("commentId") int commentId,
            @Field("isLicked") int isLicked
    );

    //*notUsed*
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

    @FormUrlEncoded
    @POST("RePoRt")
//
    Call<ArrayList<ResponseOfServer>> report(
            @Field("signText") String signText,
            @Field("reportText") String reportText,
            @Field("spamId") int spamId,
            @Field("spamerId") String spamerId,
            @Field("reporterId") String reporterId
    );

    @FormUrlEncoded
    @POST("confirmStudent")
//
    Call<ArrayList<ResponseOfServer>> confirmStudent(
            @Field("sabtenamId") int sabtenamId,
            @Field("courseId") int courseId,
            @Field("message") String message,
            @Field("tsId") String tsId,
            @Field("rsId") String rsId
    );

    @FormUrlEncoded
    @POST("confirmMoreStudent")//
    Call<ArrayList<ResponseOfServer>> confirmMoreStudent(
            @Field("data") String jsonData,
            @Field("message") String message
    );

    @GET("getSelectedTeacher")
    Call<ArrayList<StTeacher>> selectedTeacher();

    @GET("getCustomTeacherListData")
    Call<ArrayList<StCustomTeacherListHome>> getCustomTeacherListData();

    @GET("getMahoorAppData")
    Call<ArrayList<StMahoorAppData>> getMahoorAppData();

    @GET("getSubscribeList")
    Call<ArrayList<StSubscribe>> getSubscribeList();

    @FormUrlEncoded
    @POST("saveUserBuy")
    Call<ArrayList<ResponseOfServer>> saveUserBuy(
            @Field("ac") String ac,
            @Field("refId") String refId,
            @Field("subId") String subId
    );

    @FormUrlEncoded
    @POST("getUserBuySubscribe")//
    Call<ArrayList<StBuy>> getUserSubscribe(
            @Field("ac") String ac
    );
//////////////////////////////tainga
    @GET("saveFeedBack/{ac}/{feedBackText}")
    Call<ArrayList<ResponseOfServer>> saveFeedBack(
            @Path("ac") String ac,
            @Path("feedBackText") String feedBackText
    );

    @GET("SaveFavorite/{teacherApi}/{userApi}")
    Call<ArrayList<ResponseOfServer>> saveFavorite(
            @Path("teacherApi") String teacherApi,
            @Path("userApi") String userApi
    );

    @GET("RemoveFavorite/{teacherApi}/{userApi}")
    Call<ArrayList<ResponseOfServer>> removeFavorite(
            @Path("teacherApi") String teacherApi,
            @Path("userApi") String userApi
    );

    @GET("SaveBookMark/{courseId}/{userApi}")
    Call<ArrayList<ResponseOfServer>> saveBookMark(
            @Path("courseId") int courseId,
            @Path("userApi") String userApi
    );

    @GET("RemoveBookMark/{courseId}/{userApi}")
    Call<ArrayList<ResponseOfServer>> removeBookMark(
            @Path("courseId") int courseId,
            @Path("userApi") String userApi
    );

    @GET("getFavoriteTeachers/{userApi}")
    Call<ArrayList<StTeacher>> getFavoriteTeachers(
            @Path("userApi") String userApi
    );

    @GET("getBookMarkCourses/{userApi}")
    Call<ArrayList<StCourse>> getBookMarkCourses(
            @Path("userApi") String userApi
    );

    @GET("checkUpdate")
    Call<ArrayList<ResponseOfServer>> checkUpdate();

    @GET("remaining_credit/{tableName}/{email}")
    Call<String> remainCredit(
            @Path("tableName") String tableName,
            @Path("email") String phone);

    @GET("checkGiftCode/{giftCode}/{userApi}")
    Call<ArrayList<ResponseOfServer>> checkGiftCode(
            @Path("giftCode") String giftCode,
            @Path("userApi") String userApi
    );

    @GET("upDateCourse/{teacherApi}/{courseId}/{startDate}/{endDate}/{hours}/{days}/{state}")
    Call<ArrayList<ResponseOfServer>> upDateCourse(
            @Path("teacherApi") String teacherApi,
            @Path("courseId") int courseId,
            @Path("startDate") String startDate,
            @Path("endDate") String endDate,
            @Path("hours") String hours,
            @Path("days") String days,
            @Path("state") int state
    );

    @GET("sendEmail/{code}")
    Call<ArrayList<ResponseOfServer>> sendEmail(
            @Path("code") String code
    );

    @GET("getSabtenamNotifyData/{apiCode}/{lastId}")
    Call<ArrayList<StNotifyData>> getSabtenamNotifyData(
            @Path("apiCode") String apiCode,
            @Path("lastId") int lastId
    );

    @GET("getMessageNotifyData/{apiCode}/{lastId}")
    Call<ArrayList<StNotifyData>> getMessageNotifyData(
            @Path("apiCode") String apiCode,
            @Path("lastId") int lastId
    );

    @GET("getNewCourseNotifyData/{lastId}")
    Call<ArrayList<StNotifyData>> getNewCourseNotifyData(
            @Path("lastId") int lastId
    );

    @GET("getNewTeacherNotifyData")
    Call<ArrayList<StNotifyData>> getNewTeacherNotifyData(

    );

    @GET("notifySetting/{apiCode}/{courseId}/{weakNotify}/{startNotify}")
    Call<ArrayList<ResponseOfServer>> notifySetting(
            @Path("apiCode") String apiCode,
            @Path("courseId") int courseId,
            @Path("weakNotify") int weakNotify,
            @Path("startNotify") int startNotify
    );

    @GET("getWeakNotifyData/{apiCode}")
    Call<ArrayList<StNotifyData>> getWeakNotifyData(
            @Path("apiCode") String apiCode
    );

    @GET("getStartNotifyData/{apiCode}/{tomDate}")
    Call<ArrayList<StNotifyData>> getStartNotifyData(
            @Path("apiCode") String apiCode,
            @Path("tomDate") String tomDate

    );

    @GET("getSettingNotifyData/{apiCode}/{courseId}")
    Call<ArrayList<StNotifyData>> getSettingNotifyData(
            @Path("apiCode") String apiCode,
            @Path("courseId") int courseId

    );


    @FormUrlEncoded
    @POST("use_credit")
    Call<ArrayList> useCredit(
            @Field("table_name") String tableName,
            @Field("email") String phone);


}
