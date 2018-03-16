package ir.mahoorsoft.app.cityneed.model.struct;

/**
 * Created by M-gh on 16-Mar-18.
 */

public class StMahoorAppData {

    public StMahoorAppData(String appName, String url, int pictureId, int sourceNumber){
        this.pictureId = pictureId;
        this.appName = appName;
        this.url = url;
        this.sourceNumber = sourceNumber;
    }
    public String appName;
    public int pictureId;
    public int sourceNumber;
    public String url;
    public int empty;
}
