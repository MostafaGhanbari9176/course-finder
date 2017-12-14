package ir.mahoorsoft.app.cityneed.model.struct;

/**
 * Created by MAHNAZ on 11/18/2017.
 */

public class StCity {

    public int cityId;
    public int ostanId;
    public String name;

    public StCity(){}

    public StCity(String name,int cityId ,int ostanId){
        this.name = name;
        this.cityId = cityId;
        this.ostanId = ostanId;
    }
}
