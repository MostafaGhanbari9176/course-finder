package ir.mahoorsoft.app.cityneed.presenter;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.model.struct.ResponseOfServer;
import ir.mahoorsoft.app.cityneed.model.struct.StComment;
import ir.mahoorsoft.app.cityneed.model.struct.StSmsBox;
import ir.mahoorsoft.app.cityneed.model.tables.Comment;
import ir.mahoorsoft.app.cityneed.model.tables.SmsBox;

/**
 * Created by M-gh on 27-Feb-18.
 */

public class PresenterComment implements Comment.OnCommentResponseListener {

    public interface OnPresentCommentListener {
        void onResiveComment(ArrayList<StComment> comment);

        void onResiveFlagFromComment(boolean flag);

        void messageFromComment(String message);
    }

    OnPresentCommentListener onPresentCommentListener;

    public PresenterComment(OnPresentCommentListener onPresentCommentListener) {
        this.onPresentCommentListener = onPresentCommentListener;
    }

    public void saveComment(String commentText, String userId, int courseId, String teacherId, float teacherRat) {
        Comment comment = new Comment(this);
        comment.saveComment(commentText, userId, courseId, teacherId, teacherRat);
    }

    public void saveCourseRat(String userId, int courseId, String teacherId, float courseRat) {
        Comment comment = new Comment(this);
        comment.saveCourseRat(userId, courseId, teacherId, courseRat);
    }

    public void commentFeedBack(String userId, int commentId, int isLicked) {
        Comment comment = new Comment(this);
        comment.commentFeedBack(userId, commentId, isLicked);
    }

    public void upDateComment(int id, String commentText, String userId, int courseId, String teacherId, float teacherRat, float courseRat) {
        Comment comment = new Comment(this);
        comment.upDateComment(id, commentText, userId, courseId, teacherId, teacherRat, courseRat);
    }

    public void getCommentByTeacherId(String teacherId) {
        Comment comment = new Comment(this);
        comment.getCommentByTeacherId(teacherId);
    }


    @Override
    public void resiveComment(ArrayList<StComment> comment) {
        onPresentCommentListener.onResiveComment(comment);
    }

    @Override
    public void resiveFlag(ArrayList<ResponseOfServer> res) {
        if (res == null || res.size() == 0)
            sendMessage("خطا");
        else
            onPresentCommentListener.onResiveFlagFromComment(res.get(0).code == 0 ? false : true);
    }

    @Override
    public void sendMessage(String message) {
        onPresentCommentListener.messageFromComment(message);
    }
}
