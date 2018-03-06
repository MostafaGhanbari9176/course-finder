package ir.mahoorsoft.app.cityneed.model.tables;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.model.api.Api;
import ir.mahoorsoft.app.cityneed.model.api.ApiClient;
import ir.mahoorsoft.app.cityneed.model.struct.ResponseOfServer;
import ir.mahoorsoft.app.cityneed.model.struct.StComment;
import ir.mahoorsoft.app.cityneed.model.struct.StSmsBox;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by M-gh on 27-Feb-18.
 */

public class Comment {

    public interface OnCommentResponseListener {
        void resiveComment(ArrayList<StComment> comment);

        void resiveFlag(ResponseOfServer res);

        void sendMessage(String message);
    }

    private OnCommentResponseListener onCommentResponseListener;

    public Comment(OnCommentResponseListener onCommentResponseListener) {
        this.onCommentResponseListener = onCommentResponseListener;
    }

    public void saveComment(String commentText, String userId, int courseId, String teacherId, float teacherRat, float courseRat) {
        Api api = ApiClient.getClient().create(Api.class);
        Call<ArrayList<ResponseOfServer>> save = api.saveComment(commentText, userId, courseId, teacherId, teacherRat, courseRat);
        save.enqueue(new Callback<ArrayList<ResponseOfServer>>() {
            @Override
            public void onResponse(Call<ArrayList<ResponseOfServer>> call, Response<ArrayList<ResponseOfServer>> response) {
                onCommentResponseListener.resiveFlag(response.body().get(0));
            }

            @Override
            public void onFailure(Call<ArrayList<ResponseOfServer>> call, Throwable t) {
                onCommentResponseListener.sendMessage(t.getMessage());
            }
        });
    }

    public void upDateComment(int id, String commentText, String userId, int courseId, String teacherId, float teacherRat, float courseRat) {
        Api api = ApiClient.getClient().create(Api.class);
        Call<ArrayList<ResponseOfServer>> save = api.upDateComment(id, commentText, userId, courseId, teacherId, teacherRat, courseRat);
        save.enqueue(new Callback<ArrayList<ResponseOfServer>>() {
            @Override
            public void onResponse(Call<ArrayList<ResponseOfServer>> call, Response<ArrayList<ResponseOfServer>> response) {
                onCommentResponseListener.resiveFlag(response.body().get(0));
            }

            @Override
            public void onFailure(Call<ArrayList<ResponseOfServer>> call, Throwable t) {
                onCommentResponseListener.sendMessage(t.getMessage());
            }
        });
    }

    public void getCommentByTeacherId(String teacherId) {
        Api api = ApiClient.getClient().create(Api.class);
        Call<ArrayList<StComment>> save = api.getCommentByTeacherId(teacherId);
        save.enqueue(new Callback<ArrayList<StComment>>() {
            @Override
            public void onResponse(Call<ArrayList<StComment>> call, Response<ArrayList<StComment>> response) {
                onCommentResponseListener.resiveComment(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<StComment>> call, Throwable t) {
                onCommentResponseListener.sendMessage(t.getMessage());
            }
        });
    }

}
