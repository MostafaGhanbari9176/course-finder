package ir.mahoorsoft.app.cityneed.presenter;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.model.struct.ResponseOfServer;
import ir.mahoorsoft.app.cityneed.model.uploadFile.Upload;

/**
 * Created by RCC1 on 1/30/2018.
 */

public class PresentUpload implements Upload.OnUploadListener {

    public interface OnPresentUploadListener {

        void messageFromUpload(String message);
    }

    OnPresentUploadListener onPresentUploadListener;

    public PresentUpload(OnPresentUploadListener onPresentUploadListener) {
        this.onPresentUploadListener = onPresentUploadListener;
    }

    public void uploadFile(String directoryName, String fileName, String path) {
        Upload upload = new Upload(this);
        upload.uploadFile(directoryName, path, fileName);
    }

    @Override
    public void onReceiveFlag(ArrayList<ResponseOfServer> res) {
       if(res.get(0).code == 0)
           sendMessage("خطا");
        else if (res.get(0).code == 2)
           sendMessage("حجم فایل باید کمتر از پنج مگابایت باشد");
        else
           sendMessage("فایل بارگزاری شد");
    }

    @Override
    public void sendMessage(String message) {
        onPresentUploadListener.messageFromUpload(message);
    }
}
