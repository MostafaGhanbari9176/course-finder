package ir.mahoorsoft.app.cityneed.presenter;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.model.preferences.Pref;
import ir.mahoorsoft.app.cityneed.model.struct.Message;
import ir.mahoorsoft.app.cityneed.model.struct.PrefKey;
import ir.mahoorsoft.app.cityneed.model.struct.ResponseOfServer;
import ir.mahoorsoft.app.cityneed.model.struct.StNotifyData;
import ir.mahoorsoft.app.cityneed.model.tables.Notify;

/**
 * Created by RCC1 on 7/12/2018.
 */

public class PresentNotify implements Notify.OnNotifyResponseListener {

    public interface OnPresentNotifyListener {

        void onReceiveData(ArrayList<StNotifyData> res);

        void onReceiveFlagFromNotify(boolean flag);

        void messageFromNotify(String message);
    }

    private OnPresentNotifyListener onPresentNotifyListener;

    public PresentNotify(OnPresentNotifyListener onPresentNotifyListener) {
        this.onPresentNotifyListener = onPresentNotifyListener;
    }

    public void saveNotifySetting(int courseId, int weakNotify, int startNotify) {

        (new Notify(this)).saveNotifySetting(Pref.getStringValue(PrefKey.apiCode, ""), courseId, weakNotify, startNotify);
    }

    public void getNotifySettingData(int courseId) {

        (new Notify(this)).getSettingNotifyData(Pref.getStringValue(PrefKey.apiCode, ""), courseId);
    }

    @Override
    public void onReceiveData(ArrayList<StNotifyData> res) {

        if (res == null || res.size() == 0)
            onPresentNotifyListener.messageFromNotify(Message.getMessage(1));
        else
            onPresentNotifyListener.onReceiveData(res);

    }

    @Override
    public void onReceiveWeakNotifyData(ArrayList<StNotifyData> res) {

    }

    @Override
    public void onReceiveStartDateNotifyData(ArrayList<StNotifyData> res) {

    }

    @Override
    public void onReceiveFlag(ArrayList<ResponseOfServer> res) {

        if (res == null || res.size() == 0)
            onPresentNotifyListener.messageFromNotify(Message.getMessage(1));
        else
            onPresentNotifyListener.onReceiveFlagFromNotify(res.get(0).code == 1);
    }

    @Override
    public void sendMessage(String message) {
        onPresentNotifyListener.messageFromNotify(Message.getMessage(1));
    }
}
