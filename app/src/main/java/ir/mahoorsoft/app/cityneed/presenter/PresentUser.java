package ir.mahoorsoft.app.cityneed.presenter;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.model.struct.Message;
import ir.mahoorsoft.app.cityneed.model.struct.Response;
import ir.mahoorsoft.app.cityneed.model.struct.StUser;
import ir.mahoorsoft.app.cityneed.model.tables.user.User;

/**
 * Created by MAHNAZ on 11/30/2017.
 */

public class PresentUser implements User.OnUserLitener {
    OnPresentUserLitener onPresentUserLitener;

    public PresentUser(OnPresentUserLitener onPresentUserLitener) {
        this.onPresentUserLitener = onPresentUserLitener;
    }

    private long phone;
    private boolean flag = false;

    public void savePhone(long phone) {
        this.phone = phone;
        User user = new User(this);
        user.savePhone(phone);
    }

    public void updateUser(long phone, String name, String family, int status, int type, int cityIde, int apiCode) {
        User user = new User(this);
        user.updateUser(phone, name, family, status, type, cityIde, apiCode);
    }

    public void getUser(long phone) {
        User user = new User(this);
        user.getUser(phone);
    }

    @Override
    public void onReceiveFlag(ArrayList<Response> res) {
        if (res.get(0).code == 0) {
            onPresentUserLitener.confirm(true);
        } else {
            onPresentUserLitener.confirm(false);
        }
    }

    @Override
    public void onReceiveData(ArrayList<StUser> data) {

        onPresentUserLitener.onReceiveUser(data);
    }

    @Override
    public void sendMessage(String message) {
        onPresentUserLitener.sendMessageFUT(message);
    }

    public interface OnPresentUserLitener {
        void sendMessageFUT(String message);

        void confirm(boolean flag);

        void onReceiveUser(ArrayList<StUser> users);
    }
}
