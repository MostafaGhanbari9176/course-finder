package ir.mahoorsoft.app.cityneed.presenter;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.model.struct.ResponseOfServer;
import ir.mahoorsoft.app.cityneed.model.struct.StSmsBox;
import ir.mahoorsoft.app.cityneed.model.tables.SmsBox;

/**
 * Created by M-gh on 27-Feb-18.
 */

public class PresenterSmsBox implements SmsBox.OnSmsBoxResponseListener{



    public interface OnPresentSmsBoxListener{
        void onResiveSms(ArrayList<StSmsBox> sms);
        void onResiveFlagFromSmsBox(ResponseOfServer res);
        void messageFromSmsBox(String message);
    }
    OnPresentSmsBoxListener onPresentSmsBoxListener;
    public PresenterSmsBox(OnPresentSmsBoxListener onPresentSmsBoxListener){
        this.onPresentSmsBoxListener = onPresentSmsBoxListener;
    }

    public void saveSms(String smsText, String tsId, String rsId, int courseId, int howSending){
        SmsBox smsBox = new SmsBox(this);
        smsBox.saveSms(smsText, tsId, rsId, courseId, howSending);
    }

    public void getRsSms(String rsId){
        SmsBox smsBox = new SmsBox(this);
        smsBox.getRsSms(rsId);
    }

    public void getTsSms(String tsId){
        SmsBox smsBox = new SmsBox(this);
        smsBox.getRsSms(tsId);
    }

    public void upDateSeen(int smsId){
        SmsBox smsBox = new SmsBox(this);
        smsBox.upDateSeen(smsId);
    }

    @Override
    public void resiveSms(ArrayList<StSmsBox> sms) {
        onPresentSmsBoxListener.onResiveSms(sms);
    }

    @Override
    public void resiveFlag(ResponseOfServer res) {
        onPresentSmsBoxListener.onResiveFlagFromSmsBox(res);
    }

    @Override
    public void sendMessage(String message) {
        onPresentSmsBoxListener.messageFromSmsBox(message);
    }
}
