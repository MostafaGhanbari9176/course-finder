package ir.mahoorsoft.app.cityneed.model.struct;

/**
 * Created by RCC1 on 1/29/2018.
 */

public class StTabaghe {
    public int sourceNumber;
    public int id;
    public int uperId;
    public String subject;

    public StTabaghe() {
    }

    public StTabaghe(int sourceNumber, int id, int uperId, String subject) {
        this.subject = subject;
        this.id = id;
        this.sourceNumber = sourceNumber;
        this.uperId = uperId;
    }
}
