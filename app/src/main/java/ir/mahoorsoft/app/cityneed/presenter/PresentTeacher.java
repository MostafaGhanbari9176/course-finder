package ir.mahoorsoft.app.cityneed.presenter;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.model.struct.ResponseOfServer;
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

    public void addTeacher(String landPhone, String subject, String tozihat, int type, String lat, String lon) {
        Teacher teacher = new Teacher(this);
        teacher.addTeacher(landPhone, subject, tozihat, type, lat, lon);
    }

    public void getTeacher() {
        Teacher teacher = new Teacher(this);
        teacher.getTeacher();
    }

    public void getMs() {
        Teacher teacher = new Teacher(this);
        teacher.getMadrakState();
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
    public void responseForMadrak(ResponseOfServer res) {
        onPresentTeacherListener.responseForMadrak(res);
    }

    @Override
    public void onReceiveFlag(ArrayList<ResponseOfServer> res) {
        onPresentTeacherListener.confirmTeacher(res.get(0).code == 0 ? false : true);
    }

    @Override
    public void onReceiveData(ArrayList<StTeacher> data) {
        onPresentTeacherListener.onReceiveTeacher(data);
    }

    @Override
    public void sendMessage(String message) {
        onPresentTeacherListener.sendMessageFTT(message);
    }

    public interface OnPresentTeacherListener {
        void sendMessageFTT(String message);

        void confirmTeacher(boolean flag);

        void onReceiveTeacher(ArrayList<StTeacher> users);

        void responseForMadrak(ResponseOfServer res);
    }
}
