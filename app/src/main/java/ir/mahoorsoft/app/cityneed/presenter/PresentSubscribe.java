package ir.mahoorsoft.app.cityneed.presenter;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.model.struct.Message;
import ir.mahoorsoft.app.cityneed.model.struct.StSubscribe;
import ir.mahoorsoft.app.cityneed.model.tables.Subscribe;

/**
 * Created by RCC1 on 5/8/2018.
 */

public class PresentSubscribe implements Subscribe.OnSubscribeListener {

    public void getSubscribeList() {
        (new Subscribe(this)).getSubscribeList();
    }

    @Override
    public void onResiveSubscribeList(ArrayList<StSubscribe> data) {
        if (data == null || data.size() == 0)
            sendMessage(Message.getMessage(1));
        else
            onPresentSubscribeListener.onResiveSubscribeList(data);
    }

    @Override
    public void sendMessage(String message) {
        onPresentSubscribeListener.sendMessageFromSubscribe(Message.getMessage(1));
    }

    public PresentSubscribe(OnPresentSubscribeListener onPresentSubscribeListener) {
        this.onPresentSubscribeListener = onPresentSubscribeListener;
    }

    OnPresentSubscribeListener onPresentSubscribeListener;

    public interface OnPresentSubscribeListener {
        void onResiveSubscribeList(ArrayList<StSubscribe> data);

        void sendMessageFromSubscribe(String message);
    }
}
