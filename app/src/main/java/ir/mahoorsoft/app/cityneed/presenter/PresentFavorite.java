package ir.mahoorsoft.app.cityneed.presenter;

import android.preference.PreferenceFragment;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.model.preferences.Pref;
import ir.mahoorsoft.app.cityneed.model.struct.Message;
import ir.mahoorsoft.app.cityneed.model.struct.PrefKey;
import ir.mahoorsoft.app.cityneed.model.struct.ResponseOfServer;
import ir.mahoorsoft.app.cityneed.model.tables.Favorite;

/**
 * Created by M-gh on 24-Jun-18.
 */

public class PresentFavorite implements Favorite.OnFavoriteResponseListener {


    public interface OnPresentFavoriteListener {
        void messageFromFavorite(String message);

        void flagFromFavorite(boolean flag);
    }

    private OnPresentFavoriteListener onPresentFavoriteListener;

    public PresentFavorite(OnPresentFavoriteListener onPresentFavoriteListener) {
        this.onPresentFavoriteListener = onPresentFavoriteListener;
    }

    public void saveFavorite(String teacherId) {

        (new Favorite(this)).saveFavorite(teacherId, Pref.getStringValue(PrefKey.apiCode, ""));
    }

    @Override
    public void sendMessage(String message) {
        onPresentFavoriteListener.messageFromFavorite(Message.getMessage(1));
    }

    @Override
    public void onReceiveFlag(ArrayList<ResponseOfServer> res) {
        if (res == null || res.size() == 0)
            sendMessage("خطا !");
        else
            onPresentFavoriteListener.flagFromFavorite(res.get(0).code == 1);
    }
}
