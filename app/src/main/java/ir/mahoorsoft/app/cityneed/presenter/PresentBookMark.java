package ir.mahoorsoft.app.cityneed.presenter;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.model.preferences.Pref;
import ir.mahoorsoft.app.cityneed.model.struct.Message;
import ir.mahoorsoft.app.cityneed.model.struct.PrefKey;
import ir.mahoorsoft.app.cityneed.model.struct.ResponseOfServer;
import ir.mahoorsoft.app.cityneed.model.tables.BookMark;
import ir.mahoorsoft.app.cityneed.model.tables.Favorite;

/**
 * Created by M-gh on 26-Jun-18.
 */

public class PresentBookMark implements BookMark.OnBookMarkResponseListener {

    public interface OnPresentBookMarkListener {
        void messageFromBookMark(String message);

        void flagFromBookMark(boolean flag);
    }

    private OnPresentBookMarkListener onPresentBookMarkListener;

    public PresentBookMark(OnPresentBookMarkListener onPresentBookMarkListener) {
        this.onPresentBookMarkListener = onPresentBookMarkListener;
    }

    public void saveBookMark(int courseId) {

        (new BookMark(this)).saveBookMark(courseId, Pref.getStringValue(PrefKey.apiCode, ""));
    }

    public void removeBookMark(int courseId) {

        (new BookMark(this)).removeBookMark(courseId, Pref.getStringValue(PrefKey.apiCode, ""));
    }

    @Override
    public void sendMessage(String message) {
        onPresentBookMarkListener.messageFromBookMark(Message.getMessage(1));
    }

    @Override
    public void onReceiveFlag(ArrayList<ResponseOfServer> res) {
        if (res == null || res.size() == 0)
            onPresentBookMarkListener.messageFromBookMark(Message.getMessage(1));
        else
            onPresentBookMarkListener.flagFromBookMark(res.get(0).code == 1);
    }
}
