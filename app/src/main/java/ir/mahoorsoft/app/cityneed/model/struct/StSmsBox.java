package ir.mahoorsoft.app.cityneed.model.struct;

import androidx.annotation.Keep;

/**
 * Created by M-gh on 27-Feb-18.
 */
@Keep
public class StSmsBox {

    public int id;
    public int seen;
    public int empty;
    public int courseId;
    public String text;
    public String courseName;
    public String date;
    public String rsName;
    public String tsName;
    public String rsId;
    public String tsId;

    public StSmsBox(int id, int seen, int empty, int courseId, String text, String courseName, String date, String rsName, String tsName, String rsId, String tsId) {
        this.id = id;
        this.seen = seen;
        this.empty = empty;
        this.courseId = courseId;
        this.text = text;
        this.courseName = courseName;
        this.date = date;
        this.rsName = rsName;
        this.tsName = tsName;
        this.rsId = rsId;
        this.tsId = tsId;
    }

    public StSmsBox() {
    }
}
