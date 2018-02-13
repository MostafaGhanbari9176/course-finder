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

        void flagFromUpload(ResponseOfServer res);
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
        onPresentUploadListener.flagFromUpload(res.get(0));
    }

    @Override
    public void sendMessage(String message) {
        onPresentUploadListener.messageFromUpload(message);
    }
}
