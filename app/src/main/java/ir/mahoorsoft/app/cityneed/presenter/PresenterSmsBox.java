package ir.mahoorsoft.app.cityneed.presenter;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.model.preferences.Pref;
import ir.mahoorsoft.app.cityneed.model.struct.Message;
import ir.mahoorsoft.app.cityneed.model.struct.PrefKey;
import ir.mahoorsoft.app.cityneed.model.struct.ResponseOfServer;
import ir.mahoorsoft.app.cityneed.model.struct.StNotifyData;
import ir.mahoorsoft.app.cityneed.model.struct.StSmsBox;
import ir.mahoorsoft.app.cityneed.model.tables.SmsBox;

/**
 * Created by M-gh on 27-Feb-18.
 */

public class PresenterSmsBox implements SmsBox.OnSmsBoxResponseListener {


    public interface OnPresentSmsBoxListener {
        void onResiveSms(ArrayList<StSmsBox> sms);

        void onResiveFlagFromSmsBox(boolean flag);

        void smsDeleteFlag(boolean flag);

        void sendingMessageFlag(boolean flag);

        void messageFromSmsBox(String message);
    }

    OnPresentSmsBoxListener onPresentSmsBoxListener;

    public PresenterSmsBox(OnPresentSmsBoxListener onPresentSmsBoxListener) {
        this.onPresentSmsBoxListener = onPresentSmsBoxListener;
    }

    public void saveSms(String smsText, String tsId, String rsId, int courseId, int howSending) {
        SmsBox smsBox = new SmsBox(this);
        smsBox.saveSms(smsText, tsId, rsId, courseId, howSending);
    }

    public void sendMoreSms(String data, String message) {
        SmsBox smsBox = new SmsBox(this);
        smsBox.sendMoreSms(data, message);
    }

    public void getRsSms(String rsId) {
        SmsBox smsBox = new SmsBox(this);
        smsBox.getRsSms(rsId);
    }

    public void getTsSms(String tsId) {
        SmsBox smsBox = new SmsBox(this);
        smsBox.getTsSms(tsId);
    }

    public void upDateSeen(int smsId) {
        SmsBox smsBox = new SmsBox(this);
        smsBox.upDateSeen(smsId);
    }

    public void deleteMessage(int smsId) {
        SmsBox smsBox = new SmsBox(this);
        smsBox.deleteSms(smsId, Pref.getStringValue(PrefKey.apiCode, "aaa"));
    }

    @Override
    public void resiveSms(ArrayList<StSmsBox> sms) {
        if (sms == null || sms.size() == 0)
            sendMessage("خطا");
        else
            onPresentSmsBoxListener.onResiveSms(sms);
    }

    @Override
    public void resiveFlag(ArrayList<ResponseOfServer> res) {
        if (res == null || res.size() == 0)
            sendMessage("خطا");
        else
            onPresentSmsBoxListener.onResiveFlagFromSmsBox(res.get(0).code == 0 ? false : true);
    }

    @Override
    public void deleteSmsFlag(ArrayList<ResponseOfServer> res) {
        if (res == null || res.size() == 0)
            sendMessage("خطا");
        else
            onPresentSmsBoxListener.smsDeleteFlag(res.get(0).code == 1);
    }

    @Override
    public void sendingMessageFlag(ArrayList<ResponseOfServer> res) {
        if (res == null || res.size() == 0)
            sendMessage("خطا");
        else
            onPresentSmsBoxListener.sendingMessageFlag(res.get(0).code == 0 ? false : true);
    }

    @Override
    public void sendMessage(String message) {
        onPresentSmsBoxListener.messageFromSmsBox(Message.getMessage(1));
    }

    @Override
    public void messageNotifyData(ArrayList<StNotifyData> data) {

    }
}
