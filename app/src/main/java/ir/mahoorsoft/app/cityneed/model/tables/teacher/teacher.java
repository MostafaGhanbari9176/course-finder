package ir.mahoorsoft.app.cityneed.model.tables.teacher;

import java.util.ArrayList;
import java.util.zip.Adler32;

import ir.mahoorsoft.app.cityneed.model.api.Api;
import ir.mahoorsoft.app.cityneed.model.api.ApiClient;
import ir.mahoorsoft.app.cityneed.model.struct.StTeacher;
import retrofit2.Call;

/**
 * Created by RCC1 on 12/21/2017.
 */

public class teacher {

    public void addTeacher(){

        Api api = ApiClient.getClient().create(Api.class);
      //  Call<ArrayList<StTeacher>> addTeacher = api.
    }
}
