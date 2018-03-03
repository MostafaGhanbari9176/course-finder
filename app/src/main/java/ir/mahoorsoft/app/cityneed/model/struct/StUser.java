package ir.mahoorsoft.app.cityneed.model.struct;

/**
 * Created by RCC1 on 12/13/2017.
 */

public class StUser {
    public String phone = "";
    public String name = "";
    public String family = "";
    public int status;
    public int type;//0-->student && 1-->Teacher
    public int cityId;
    public String apiCode = "";
    public String location = "";
    public int empty;
    public int sabtenamId;

    public StUser(String phone, String name, String family) {
        this.phone = phone;
        this.name = name;
        this.family = family;
    }

    public StUser() {
        status = 0;
        type = 0;
    }
}
