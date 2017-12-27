package ir.mahoorsoft.app.cityneed.model.struct;

/**
 * Created by RCC1 on 12/13/2017.
 */

public class StUser {
    public long phone;
    public String name;
    public String family;
    public int status;
    public int type;//0-->student && 1-->teacher
    public int cityId;
    public int apiCode;
    public String location;

    public  StUser(Long phone,String name ,String family){
        this.phone = phone;
        this.name = name;
        this.family = family;
    }
    public StUser(){
        status = 0;
        type = 0;

    }
}
