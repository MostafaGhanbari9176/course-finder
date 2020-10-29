package ir.mahoorsoft.app.cityneed.model.struct;

import androidx.annotation.Keep;

/**
 * Created by RCC1 on 12/13/2017.
 */
@Keep
public class StUser {
    public String phone = "";
    public String name = "";
    public String family = "";
    public int status;
    public int type;//0-->student && 1-->Teacher
    public int cityId;
    public String apiCode = "";
    public String location = "";
    public String cellPhone = "";
    public int empty;
    public int sabtenamId;
    public int isCanceled = 0;

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
