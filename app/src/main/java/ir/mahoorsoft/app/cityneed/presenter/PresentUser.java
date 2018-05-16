package ir.mahoorsoft.app.cityneed.presenter;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.model.struct.Message;
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

    public void getRegistrationsName(int courseId) {
        User user = new User(this);
        user.getRegistrationsName(courseId);
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
    public void responseForLogIn(ArrayList<ResponseOfServer> res) {//0-->badCod  1-->okAndIsUser  2-->okAndIsTeacher  3--> badLogU
        if (res == null || res.size() == 0)
            sendMessage("خطا,پیش بینی نشده!!!");
        else
            onPresentUserLitener.LogIn(res.get(0));
    }

    @Override
    public void responseForLogUp(ArrayList<ResponseOfServer> res) {//0-->badCod  1-->ok  2--> badLogin
        if (res == null || res.size() == 0)
            sendMessage("خطا,پیش بینی نشده!!!");
        else
            onPresentUserLitener.LogUp(res.get(0));
    }

    @Override
    public void responseForLogOut(ArrayList<ResponseOfServer> res) {
        if (res == null || res.size() == 0)
            sendMessage("خطا,پیش بینی نشده!!!");
        else if (res.get(0).code == 0)
            sendMessage("خطا!!!");
        else
            onPresentUserLitener.LogOut(true);
    }

    @Override
    public void onReceiveUser(ArrayList<StUser> students) {
        if (students == null || students.size() == 0)
            sendMessage("خطا,پیش بینی نشده!!!");
        else
            onPresentUserLitener.onReceiveUser(students);
    }

    @Override
    public void sendMessage(String message) {
        onPresentUserLitener.sendMessageFUT(Message.getMessage(1));
    }

    public interface OnPresentUserLitener {
        void sendMessageFUT(String message);

        void LogOut(boolean flag);

        void LogIn(ResponseOfServer res);

        void LogUp(ResponseOfServer res);

        void onReceiveUser(ArrayList<StUser> students);
    }
}
