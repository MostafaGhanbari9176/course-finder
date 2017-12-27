package ir.mahoorsoft.app.cityneed.model.struct;

/**
 * Created by RCC1 on 12/21/2017.
 */

public class StTeacher {
    public long landPhone;
    public long phone;
    public int type;//0-->college && 1-->privateEducation
    public String address;
    public int madrak;//0-->no && 1-->yes
    public String subject;
    public int vaziat;//0-->no && 1-->yes
    public String definitionDate;
    public String taiedDate;
    public String endDate;
    public String tozihat;
    public int cityId;

    public StTeacher() {
    }

    public StTeacher(long phone,long landPhone, String address, String subject, int cityId,String tozihat,int type) {
        this.landPhone = landPhone;
        this.phone = phone;
        this.address = address;
        this.subject = subject;
        this.cityId = cityId;
        this.tozihat = tozihat;
        this.type = type;


    }
}