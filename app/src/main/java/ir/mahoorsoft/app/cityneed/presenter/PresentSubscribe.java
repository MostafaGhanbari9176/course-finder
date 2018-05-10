package ir.mahoorsoft.app.cityneed.presenter;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.model.preferences.Pref;
import ir.mahoorsoft.app.cityneed.model.struct.Message;
import ir.mahoorsoft.app.cityneed.model.struct.PrefKey;
import ir.mahoorsoft.app.cityneed.model.struct.ResponseOfServer;
import ir.mahoorsoft.app.cityneed.model.struct.StBuy;
import ir.mahoorsoft.app.cityneed.model.struct.StSubscribe;
import ir.mahoorsoft.app.cityneed.model.tables.Subscribe;

/**
 * Created by RCC1 on 5/8/2018.
 */

public class PresentSubscribe implements Subscribe.OnSubscribeListener {

    public void getSubscribeList() {
        (new Subscribe(this)).getSubscribeList();
    }

    public void getUserBuy() {
        (new Subscribe(this)).getUserBuy(Pref.getStringValue(PrefKey.apiCode, ""));
    }

    @Override
    public void onReceiveSubscribeList(ArrayList<StSubscribe> data) {
        if (data == null || data.size() == 0)
            sendMessage(Message.getMessage(1));
        else
            onPresentSubscribeListener.onResiveSubscribeList(data);
    }

    @Override
    public void onReceiveUserBuy(ArrayList<StBuy> data) {
        if (data == null || data.size() == 0) {
            sendMessage(Message.getMessage(1));
            return;
        }
        onPresentSubscribeListener.onReceiveUserBuy(data);
    }

    @Override
    public void sendMessage(String message) {
        onPresentSubscribeListener.sendMessageFromSubscribe(Message.getMessage(1));
    }

    @Override
    public void onReceiveFlag(ArrayList<ResponseOfServer> res) {

    }

    public PresentSubscribe(OnPresentSubscribeListener onPresentSubscribeListener) {
        this.onPresentSubscribeListener = onPresentSubscribeListener;
    }

    OnPresentSubscribeListener onPresentSubscribeListener;

    public interface OnPresentSubscribeListener {
        void onResiveSubscribeList(ArrayList<StSubscribe> data);

        void sendMessageFromSubscribe(String message);

        void onReceiveUserBuy(ArrayList<StBuy> data);
    }
}
