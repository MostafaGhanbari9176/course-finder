package ir.mahoorsoft.app.cityneed.presenter;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.model.CheckedServer;
import ir.mahoorsoft.app.cityneed.model.struct.ResponseOfServer;

/**
 * Created by RCC1 on 2/1/2018.
 */

public class PresentCheckedServer implements CheckedServer.OnCheckServer {

    public interface OnPresentCheckServrer{
        void serverChecked(boolean online);
    }

    OnPresentCheckServrer onPresentCheckServrer;

    public PresentCheckedServer(OnPresentCheckServrer onPresentCheckServrer){this.onPresentCheckServrer = onPresentCheckServrer;}

    public void checkedServer(){
        CheckedServer checkedServer = new CheckedServer(this);
        checkedServer.checkServer();
    }
    @Override
    public void serverChecked(ArrayList<ResponseOfServer> res) {
        onPresentCheckServrer.serverChecked(res.get(0).code == 0 ? false : true);
    }

    @Override
    public void sendMessage(String message) {
        onPresentCheckServrer.serverChecked(false);
    }
}
