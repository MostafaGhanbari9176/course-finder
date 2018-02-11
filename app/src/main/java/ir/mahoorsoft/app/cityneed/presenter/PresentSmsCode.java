package ir.mahoorsoft.app.cityneed.presenter;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.model.struct.Message;
import ir.mahoorsoft.app.cityneed.model.struct.ResponseOfServer;
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

    public interface OnPresentSmsCodeListener {

        void confirmSmsCode(boolean flag);

        void sendMessageFScT(String message);
    }

    @Override
    public void responseOfServer(ArrayList<ResponseOfServer> responseOfServer) {
        if(responseOfServer.get(0).code == 0)
            onPresentSmsCodeListener.confirmSmsCode(false);
        else
            onPresentSmsCodeListener.confirmSmsCode(true);
    }

    @Override
    public void sendMessage(String message) {

        onPresentSmsCodeListener.sendMessageFScT(Message.convertRetrofitMessage(message));
    }
}
