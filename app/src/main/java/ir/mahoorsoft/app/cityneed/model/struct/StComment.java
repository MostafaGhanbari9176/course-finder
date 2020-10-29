package ir.mahoorsoft.app.cityneed.model.struct;

import androidx.annotation.Keep;

/**
 * Created by RCC1 on 3/6/2018.
 */
@Keep
public class StComment {

    public int id;
    public int courseId;
    public String userId;
    public String teacherId;
    public String courseName;
    public String userName;
    public String commentText;
    public float courseRat;
    public float teacherRat;
    public float totalRat;
    public String date;
    public String startDate;
    public int empty;
    public int likeNum;
    public int disLikeNum;

}
