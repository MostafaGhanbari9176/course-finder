package ir.mahoorsoft.app.cityneed.model.struct;

/**
 * Created by RCC1 on 12/21/2017.
 */

public class StTeacher {

    public long phone;
    public int type;//0-->public && 1-->single
    public String address;
    public int madrak;//0-->yes && 1-->no
    public String subject;
    public int vaziat;//0-->yes && 1-->no
    public String startDate;
    public String taiedDate;
    public String endDate;
    public String tozihat;
    public int cityId;

    public StTeacher() {
    }

    public StTeacher(long phone, String address, String subject, int cityId, String startDate,String tozihat,int type) {
        this.phone = phone;
        this.address = address;
        this.cityId = cityId;
        this.subject = subject;
        this.cityId = cityId;
        this.startDate = startDate;
        this.tozihat = tozihat;
        this.type = type;
    }
}
