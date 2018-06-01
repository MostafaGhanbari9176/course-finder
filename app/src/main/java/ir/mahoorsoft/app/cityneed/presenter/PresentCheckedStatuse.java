package ir.mahoorsoft.app.cityneed.presenter;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.model.CheckedSTatuse;
import ir.mahoorsoft.app.cityneed.model.struct.ResponseOfServer;


public class PresentCheckedStatuse implements CheckedSTatuse.OnCheckServer {

    public interface OnPresentCheckServrer {
        void versionChecked(ResponseOfServer rse);

        void userChecked(ResponseOfServer rse);

        void messageFromStatuse(String message);
    }

    private OnPresentCheckServrer onPresentCheckServrer;

    public PresentCheckedStatuse(OnPresentCheckServrer onPresentCheckServrer) {
        this.onPresentCheckServrer = onPresentCheckServrer;
    }

    public void checkedUserStatuse() {
        CheckedSTatuse checkedSTatuse = new CheckedSTatuse(this);
        checkedSTatuse.checkUserStatuse();
    }

    public void checkVersion() {
        (new CheckedSTatuse(this)).checkVersionName();
    }

    @Override
    public void ResponseForVersion(ArrayList<ResponseOfServer> res) {
        if (res == null || res.size() == 0)
            sendMessage("خطا");
        else
            onPresentCheckServrer.versionChecked(res.get(0));
    }

    @Override
    public void ResponseForUserStatuse(ArrayList<ResponseOfServer> res) {
        if (res == null || res.size() == 0)
            sendMessage("خطا");
        else
            onPresentCheckServrer.userChecked(res.get(0));
    }

    @Override
    public void sendMessage(String message) {
        onPresentCheckServrer.messageFromStatuse(message);
    }
}
