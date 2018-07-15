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

    @GET("logOut/{email}")
    Call<ArrayList<ResponseOfServer>> logOut(
            @Path("email") String phone
    );

    //  @GET()

    @GET("createAndSaveSmsCode/{email}")
    Call<ArrayList<ResponseOfServer>> createSmsCode(
            @Path("email") String phone
    );

    @GET("logUp/{email}/{name}/{code}")
    Call<ArrayList<ResponseOfServer>> logUp(
            @Path("email") String phone,
            @Path("name") String name,
            @Path("code") int code
    );

    @GET("logIn/{email}/{code}")
    Call<ArrayList<ResponseOfServer>> logIn(
            @Path("email") String phone,
            @Path("code") int code
    );

    @GET("addTeacher/{ac}/{landPhone}/{subject}/{tozihat}/{type}/{lat}/{lon}/{address}")
    Call<ArrayList<ResponseOfServer>> addTeacher(
            @Path("ac") String ApiCode,
            @Path("landPhone") String landPhone,
            @Path("subject") String subject,
            @Path("tozihat") String tozihat,
            @Path("type") int type,
            @Path("lat") String lat,
            @Path("lon") String lon,
            @Path("address") String address
    );

    @Multipart
    @POST("upload.php")
    Call<ArrayList<ResponseOfServer>> upload(
            @Part("description") RequestBody description,
            @Part MultipartBody.Part file
    );

    @GET("getTeacher/{teacherApi}/{userApi}")
    Call<ArrayList<StTeacher>> getTeacher(
            @Path("teacherApi") String teacherApi,
            @Path("userApi") String userApi
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

    @GET("addCourse/{ac}/{teacherName}/{subject}/{tabagheId}/{type}/{capacity}/{mony}/{sharayet}/{tozihat}/{startDate}/{endDate}/{day}/{hours}/{minOld}/{maxOld}")
    Call<ArrayList<ResponseOfServer>> addCourse(
            @Path("ac") String ac,
            @Path("teacherName") String teacherName,
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

    @GET("getCourseById/{id}/{userApi}")
    Call<ArrayList<StCourse>> getCourseById(
            @Path("id") int id,
            @Path("userApi") String userApi
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

    @GET("checkedUserStatuse/{id}")
    Call<ArrayList<ResponseOfServer>> checkedUserStatuse(
            @Path("id") String id
    );

    @GET("sabtenam/{idCourse}/{idTeacher}/{idUser}/{cellPhone}")
    Call<ArrayList<ResponseOfServer>> sabtenam(
            @Path("idCourse") int idCourse,
            @Path("idTeacher") String idTeacher,
            @Path("idUser") String idUser,
            @Path("cellPhone") String cellPhone
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

    @GET("sendMoreSms/{data}/{message}")
    Call<ArrayList<ResponseOfServer>> sendMoreSms(
            @Path("data") String data,
            @Path("message") String message
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
            @Path("code") int code
    );

    @GET("updateCanceledFlag/{sabtenamId}/{code}/{courseId}/{message}/{tsId}/{rsId}")
    Call<ArrayList<ResponseOfServer>> updateCanceledFlag(
            @Path("sabtenamId") int sabtenamId,
            @Path("code") int code,
            @Path("courseId") int courseId,
            @Path("message") String message,
            @Path("tsId") String tsId,
            @Path("rsId") String rsId
    );

    @GET("updateMoreCanceledFlag/{data}/{message}")
    Call<ArrayList<ResponseOfServer>> updateMoreCanceledFlag(
            @Path("data") String data,
            @Path("message") String message
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

    @GET("confirmStudent/{sabtenamId}/{courseId}/{message}/{tsId}/{rsId}")
    Call<ArrayList<ResponseOfServer>> confirmStudent(
            @Path("sabtenamId") int sabtenamId,
            @Path("courseId") int courseId,
            @Path("message") String message,
            @Path("tsId") String tsId,
            @Path("rsId") String rsId
    );

    @GET("confirmMoreStudent/{data}/{message}")
    Call<ArrayList<ResponseOfServer>> confirmMoreStudent(
            @Path("data") String jsonData,
            @Path("message") String message
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

    @GET("getUserBuySubscribe/{ac}")
    Call<ArrayList<StBuy>> getUserSubscribe(
            @Path("ac") String ac
    );

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
