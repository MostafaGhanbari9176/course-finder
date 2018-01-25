package ir.mahoorsoft.app.cityneed.presenter;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.model.struct.Response;
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

    public void addTeacher(String landPhone, String address, String subject, String tozihat, int type){
        Teacher teacher = new Teacher(this);
        teacher.addTeacher(landPhone, address, subject, tozihat, type);
    }
    @Override
    public void onReceiveFlag(ArrayList<Response> res) {

    }

    @Override
    public void onReceiveData(ArrayList<StTeacher> data) {

    }

    @Override
    public void sendMessage(String message) {

    }

    public interface OnPresentTeacherListener {
        void sendMessageFUT(String message);

        void confirmUser(boolean flag);

        void onReceiveUser(ArrayList<StTeacher> users);
    }
}
