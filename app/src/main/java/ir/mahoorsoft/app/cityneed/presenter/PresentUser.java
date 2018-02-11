package ir.mahoorsoft.app.cityneed.presenter;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.model.struct.ResponseOfServer;
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
    public void updateUser(String phone, String name) {
        User user = new User(this);
        user.updateUser(phone, name);
    }

    public void getUser(String phone) {
        User user = new User(this);
        user.getUser(phone);
    }

    public void logOut(String phone) {
        User user = new User(this);
        user.logOut(phone);

    }

    public void logUp(String phone, String name, int code) {
        User user = new User(this);
        user.logUp(phone, name, code);

    }

    public void logIn(String phone, int code) {
        User user = new User(this);
        user.logIn(phone, code);

    }


    @Override
    public void responseForLogIn(ArrayList<ResponseOfServer> res) {//0-->badCod  1-->okAndIsUser  2-->okAndIsTeacher  3--> badLogUp
        if(res.get(0).code == 0)
            sendMessage("کد وارد شده اشتباه است");
        onPresentUserLitener.LogIn(res.get(0));
    }

    @Override
    public void responseForLogUp(ArrayList<ResponseOfServer> res) {//0-->badCod  1-->ok  2--> badLogin
        if(res.get(0).code == 0)
            sendMessage("کد وارد شده اشتباه است");
        else
            onPresentUserLitener.LogUp(res.get(0));
    }

    @Override
    public void responseForLogOut(ArrayList<ResponseOfServer> res) {
        if(res.get(0).code == 0)
            sendMessage("خطا!!!");
        onPresentUserLitener.LogOut(true);
    }

    @Override
    public void sendMessage(String message) {
        onPresentUserLitener.sendMessageFUT(message);
    }

    public interface OnPresentUserLitener {
        void sendMessageFUT(String message);
        void LogOut(boolean flag);
        void LogIn(ResponseOfServer res);
        void LogUp(ResponseOfServer res);
    }
}
