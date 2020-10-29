package ir.mahoorsoft.app.cityneed.model.struct;

import androidx.annotation.Keep;

/**
 * Created by MAHNAZ on 11/18/2017.
 **/
@Keep
public class StOstan {

    public int id;
    public String name;

    public StOstan() {
    }

    public StOstan(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
