package ir.mahoorsoft.app.cityneed.model.struct;

/**
 * Created by RCC1 on 1/29/2018.
 */

public class StGrouping {

    public int id;
    public int uperId;
    public int rootId;
    public String subject;
    public int empty;
    public int isFinaly;

    public StGrouping() {
    }

    public StGrouping(int id, int uperId, String subject) {
        this.subject = subject;
        this.id = id;
        this.uperId = uperId;
    }
}
