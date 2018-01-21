package ir.mahoorsoft.app.cityneed.presenter;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.model.struct.Message;
import ir.mahoorsoft.app.cityneed.model.struct.Response;
import ir.mahoorsoft.app.cityneed.model.tables.user.SmsCode;

/**
 * Created by RCC1 on 12/17/2017.
 */

public class PresentSmsCode implements SmsCode.OnSmsCodeListener {

    OnPresentSmsCodeListener onPresentSmsCodeListener;

    public PresentSmsCode(OnPresentSmsCodeListener onPresentSmsCodeListener) {
        this.onPresentSmsCodeListener = onPresentSmsCodeListener;
    }

    public void createAndSaveSmsCode(String phone) {
        SmsCode smsCode = new SmsCode(this);
        smsCode.createAndSaveSmsCode(phone);
    }

    public void checkedSmsCode(String phone, int code) {
        SmsCode smsCode = new SmsCode(this);
        smsCode.checkedSmsCode(phone, code);
    }

    public interface OnPresentSmsCodeListener {

        void confirmSmsCode(boolean flag);

        void sendMessageFScT(String message);

        void confirmSmsCodeAndExistUser(int code);

    }

    @Override
    public void onRecirveFlag(ArrayList<Response> response) {

        if (response.get(0).code == 2) {
            onPresentSmsCodeListener.confirmSmsCodeAndExistUser(2);
        } else if (response.get(0).code == 3) {
            onPresentSmsCodeListener.confirmSmsCodeAndExistUser(3);
        } else {
            onPresentSmsCodeListener.confirmSmsCode((response.get(0).code) == 0 ? false : true);
        }
    }

    @Override
    public void sendMessage(String message) {

        onPresentSmsCodeListener.sendMessageFScT(Message.convertRetrofitMessage(message));
    }
}
