package ir.mahoorsoft.app.cityneed.presenter;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.model.CheckedSTatuse;
import ir.mahoorsoft.app.cityneed.model.struct.ResponseOfServer;

/**
 * Created by RCC1 on 2/1/2018.
 */

public class PresentCheckedStatuse implements CheckedSTatuse.OnCheckServer {

    public interface OnPresentCheckServrer{
        void serverChecked(boolean online);
        void userChecked(boolean online);
    }

    OnPresentCheckServrer onPresentCheckServrer;

    public PresentCheckedStatuse(OnPresentCheckServrer onPresentCheckServrer){this.onPresentCheckServrer = onPresentCheckServrer;}

    public void checkedServerStatuse(){
        CheckedSTatuse checkedSTatuse = new CheckedSTatuse(this);
        checkedSTatuse.checkServerStatuse();
    }

    public void checkedUserStatuse(){
        CheckedSTatuse checkedSTatuse = new CheckedSTatuse(this);
        checkedSTatuse.checkUserStatuse();
    }

    @Override
    public void ResponseForSarverStatuse(ArrayList<ResponseOfServer> res) {
        onPresentCheckServrer.serverChecked(res.get(0).code == 0 ? false : true);
    }

    @Override
    public void ResponseForUserStatuse(ArrayList<ResponseOfServer> res) {
        onPresentCheckServrer.userChecked(res.get(0).code == 0 ? false : true);
    }

    @Override
    public void sendMessage(String message) {
        onPresentCheckServrer.serverChecked(false);
    }
}
