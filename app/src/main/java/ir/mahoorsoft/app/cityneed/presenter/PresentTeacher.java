package ir.mahoorsoft.app.cityneed.presenter;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.model.preferences.Pref;
import ir.mahoorsoft.app.cityneed.model.struct.Message;
import ir.mahoorsoft.app.cityneed.model.struct.PrefKey;
import ir.mahoorsoft.app.cityneed.model.struct.ResponseOfServer;
import ir.mahoorsoft.app.cityneed.model.struct.StCustomTeacherListHome;
import ir.mahoorsoft.app.cityneed.model.struct.StTeacher;
import ir.mahoorsoft.app.cityneed.model.tables.teacher.Teacher;

/**
 * Created by RCC1 on 1/25/2018.
 */

public class PresentTeacher implements Teacher.OnTeacherListener {

    OnPresentTeacherListener onPresentTeacherListener;

    public PresentTeacher(OnPresentTeacherListener onPresentTeacherListener) {
        this.onPresentTeacherListener = onPresentTeacherListener;
    }

    public void addTeacher(String landPhone, String subject, String tozihat, int type, String lat, String lon, String address) {
        Teacher teacher = new Teacher(this);
        teacher.addTeacher(landPhone, subject, tozihat, type, lat, lon, address);
    }

    public void getTeacher(String teacherId) {
        Teacher teacher = new Teacher(this);
        teacher.getTeacher(teacherId, Pref.getStringValue(PrefKey.apiCode, "aaa"));
    }

    public void getSelectedTeacher() {
        Teacher teacher = new Teacher(this);
        teacher.getSelectedTeacher();
    }

    public void getCustomTeacherListData() {
        Teacher teacher = new Teacher(this);
        teacher.getCustomTeacherListData();
    }

    public void getAllTeacher() {
        Teacher teacher = new Teacher(this);
        teacher.getAllTeacher();
    }

    public void getMadrakStateAndRat() {
        Teacher teacher = new Teacher(this);
        teacher.getMadrakStateAndRat();
    }

    public void upMs() {
        Teacher teacher = new Teacher(this);
        teacher.upMadrakState();
    }

    public void updateTeacher(String landPhone, String subject, String address, int cityId, int madrak) {
        Teacher teacher = new Teacher(this);
        teacher.updateTeacher(landPhone, subject, address, cityId, madrak);
    }

    @Override
    public void responseForMadrak(ArrayList<ResponseOfServer> res) {
        if (res == null || res.size() == 0)
            sendMessage("خطا,پیش بینی نشده!!!");
        else
            onPresentTeacherListener.responseForMadrak(res.get(0));
    }

    @Override
    public void onReceiveFlag(ArrayList<ResponseOfServer> res) {
        if (res == null || res.size() == 0)
            sendMessage("خطا,پیش بینی نشده!!!");
        else
            onPresentTeacherListener.confirmTeacher(res.get(0).code == 0 ? false : true);
    }

    @Override
    public void onReceiveData(ArrayList<StTeacher> data) {
        if (data == null || data.size() == 0)
            sendMessage("خطا,پیش بینی نشده!!!");
        else
            onPresentTeacherListener.onReceiveTeacher(data);
    }

    @Override
    public void onReceiveSelectedTeacher(ArrayList<StTeacher> data) {
        if (data == null || data.size() == 0)
            sendMessage("خطا,پیش بینی نشده!!!");
        else
            onPresentTeacherListener.onReceiveSelectedTeacher(data);
    }

    @Override
    public void onReceiveCustomTeacherListData(ArrayList<StCustomTeacherListHome> data) {
        if (!(data == null || data.size() == 0))
            onPresentTeacherListener.onReceiveCustomeTeacherListData(data);
    }

    @Override
    public void sendMessage(String message) {
        onPresentTeacherListener.sendMessageFTT(Message.getMessage(1));
    }

    public interface OnPresentTeacherListener {
        void sendMessageFTT(String message);

        void confirmTeacher(boolean flag);

        void onReceiveTeacher(ArrayList<StTeacher> teachers);

        void onReceiveCustomeTeacherListData(ArrayList<StCustomTeacherListHome> data);

        void onReceiveSelectedTeacher(ArrayList<StTeacher> teachers);

        void responseForMadrak(ResponseOfServer res);
    }
}
