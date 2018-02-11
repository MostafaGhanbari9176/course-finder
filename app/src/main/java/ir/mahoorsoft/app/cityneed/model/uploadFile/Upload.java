package ir.mahoorsoft.app.cityneed.model.uploadFile;

import java.io.File;
import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.model.api.Api;
import ir.mahoorsoft.app.cityneed.model.api.ApiClient;
import ir.mahoorsoft.app.cityneed.model.struct.ResponseOfServer;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;


/**
 * Created by RCC1 on 1/30/2018.
 */

public class Upload {

    public interface OnUploadListener{
        void onReceiveFlag(ArrayList<ResponseOfServer> res);
        void sendMessage(String message);
    }
    OnUploadListener onUploadListener;
    public Upload(OnUploadListener onUploadListener)
    {this.onUploadListener = onUploadListener;}

    public void uploadFile(String directoryName,String path, String fileName) {
        // create upload service client
        Api service = ApiClient.getClient().create(Api.class);

        // use the FileUtils to get the actual file by uri
        File file = new File(path);

        // create RequestBody instance from file
        ProgressRequestBody fileBody = new ProgressRequestBody(file, new ProgressRequestBody.UploadCallbacks() {
            @Override
            public void onProgressUpdate(int percentage) {

            }
        });
       /* RequestBody requestFile =
                RequestBody.create(MediaType.parse(getContentResolver().getType(fileUri)), file);*/

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body = MultipartBody.Part.createFormData(directoryName, fileName, fileBody);

        // add another part within the multipart request
        String descriptionString = "hello, this is description speaking";
        RequestBody description =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, descriptionString);

        // finally, execute the request
        Call<ArrayList<ResponseOfServer>> call = service.upload(description, body);
        call.enqueue(new Callback<ArrayList<ResponseOfServer>>() {
            @Override
            public void onResponse(Call<ArrayList<ResponseOfServer>> call, retrofit2.Response<ArrayList<ResponseOfServer>> response) {
                onUploadListener.onReceiveFlag(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<ResponseOfServer>> call, Throwable t) {
                onUploadListener.sendMessage(t.getMessage());
            }
        });
    }
}
