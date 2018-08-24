package ir.mahoorsoft.app.cityneed.model.struct;

/**
 * Created by RCC1 on 1/30/2018.
 */

public class StCourse {
    public int id;
    public String courseListId;
    public int empty;
    public int idTabaghe;
    public String CourseName = "";
    public int type;
    public int capacity;
    public int mony;
    public int minOld;
    public int maxOld;
    public String idTeacher = "";
    public String tabaghe = "";
    public String startDate = "";
    public String sharayet = "";
    public String tozihat = "";
    public String endDate = "";
    public String day = "";
    public String hours = "";
    public String MasterName = "";
    public String teacherName = "";
    public int isDeleted;//1-->true  0-->false  2-->seen
    public int isCanceled;//1-->true  0-->false   2-->seen
    public int sabtenamId;
    public int vaziat;
    public int endOfList;
    public int numberOfWaitingStudent;
    public int bookMark;
    public int state;//1-->درحال ثبت نام 2--> شروع دوره 3--> خاتمه دوره 4--> ظرفیت تکمیل

}
