package ir.mahoorsoft.app.cityneed.model.struct;

import androidx.annotation.Keep;

/**
 * Created by RCC1 on 12/21/2017.
 */
@Keep
public class StTeacher {
    public String landPhone;
    public String ac;
    public String phone;
    public int type;//0-->college && 1-->privateEducation
    public String address;
    public int m;//0 --> no && 1 --> upload 2 --> uploadAndAccept
    public String subject;
    public int vaziat;//0-->no && 1-->yes
    public String definitionDate;
    public String tozihat;
    public int cityId;
    public String location;
    public String lt;
    public String lg;
    public String pictureId;
    public float teacherRat;
    public int empty;
    public int favorite;

    public StTeacher() {
    }

    public StTeacher(String phone,String landPhone, String address, String subject, int cityId,String tozihat,int type) {
        this.landPhone = landPhone;
        this.phone = phone;
        this.address = address;
        this.subject = subject;
        this.cityId = cityId;
        this.tozihat = tozihat;
        this.type = type;


    }
}
