package ir.mahoorsoft.app.cityneed.presenter;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.model.preferences.Pref;
import ir.mahoorsoft.app.cityneed.model.struct.Message;
import ir.mahoorsoft.app.cityneed.model.struct.PrefKey;
import ir.mahoorsoft.app.cityneed.model.struct.ResponseOfServer;
import ir.mahoorsoft.app.cityneed.model.tables.Gift;

/**
 * Created by RCC1 on 6/27/2018.
 */

public class PresentGift implements Gift.OnGiftResponseListener {

    public interface OnPresentGiftListener {
        void flagFromGift(boolean flag);

        void messageFromGift(String message);
    }

    private OnPresentGiftListener onPresentGiftListener;

    public PresentGift(OnPresentGiftListener onPresentGiftListener) {
        this.onPresentGiftListener = onPresentGiftListener;
    }

    public void checkGiftCode(String giftCode) {
        (new Gift(this)).checkGiftCode(giftCode, Pref.getStringValue(PrefKey.apiCode, ""));
    }

    @Override
    public void onReceiveFlag(ArrayList<ResponseOfServer> res) {
        if (res == null || res.size() == 0) {
            sendMessage(Message.getMessage(1));
            return;
        }
        if (res.get(0).code == 0)
            onPresentGiftListener.messageFromGift("کد نامعتبر است.");
        else if (res.get(0).code == -1)
            onPresentGiftListener.messageFromGift("شما قبلا هدیه خود را دریافت کرده اید.");
        else {
            onPresentGiftListener.flagFromGift(true);
            onPresentGiftListener.messageFromGift("هدیه فعال سازی شد.");
        }
    }

    @Override
    public void sendMessage(String message) {
        onPresentGiftListener.messageFromGift(Message.getMessage(1));
    }
}
