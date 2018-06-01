package ir.mahoorsoft.app.cityneed.presenter;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.model.preferences.Pref;
import ir.mahoorsoft.app.cityneed.model.struct.Message;
import ir.mahoorsoft.app.cityneed.model.struct.PrefKey;
import ir.mahoorsoft.app.cityneed.model.struct.ResponseOfServer;
import ir.mahoorsoft.app.cityneed.model.tables.FeedBack;

/**
 * Created by M-gh on 01-Jun-18.
 */

public class PresentFeedBack implements FeedBack.OnFeedBackListener {


    public interface OnPresentFeedBackListener {
        void OnReceiveFlagFromFeedBack(Boolean flag);

        void sendMessageFromFeedBack(String Message);
    }

    private OnPresentFeedBackListener onPresentFeedBackListener;

    public PresentFeedBack(OnPresentFeedBackListener onPresentFeedBackListener) {
        this.onPresentFeedBackListener = onPresentFeedBackListener;
    }

    public void saveFeedBack(String feedBackMessage) {
        (new FeedBack(this)).saveFeedBack(Pref.getStringValue(PrefKey.apiCode, ""), feedBackMessage);
    }

    @Override
    public void OnReceiveFlag(ArrayList<ResponseOfServer> res) {
        if (res == null || res.size() == 0)
            onPresentFeedBackListener.sendMessageFromFeedBack(Message.getMessage(1));
        else
            onPresentFeedBackListener.OnReceiveFlagFromFeedBack(res.get(0).code == 1);
    }

    @Override
    public void OnReceiveMessage(String message) {
        onPresentFeedBackListener.sendMessageFromFeedBack(message);
    }
}
