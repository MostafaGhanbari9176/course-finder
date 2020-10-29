package ir.mahoorsoft.app.cityneed.model.struct;

import androidx.annotation.Keep;

/**
 * Created by MAHNAZ on 11/18/2017.
 */
@Keep
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
