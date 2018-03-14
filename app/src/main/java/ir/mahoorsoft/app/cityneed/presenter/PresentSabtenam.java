package ir.mahoorsoft.app.cityneed.presenter;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.model.struct.ResponseOfServer;
import ir.mahoorsoft.app.cityneed.model.tables.sabtenam.Sabtenam;

/**
 * Created by RCC1 on 2/1/2018.
 */

public class PresentSabtenam implements Sabtenam.OnSabtenamListener {

    public interface OnPresentSabtenamListaener {
        void sendMessageFST(String message);

        void confirmSabtenam(boolean flag);

        void checkSabtenam(float ratBarValue);


    }

    OnPresentSabtenamListaener onPresentSabtenamListaener;

    public PresentSabtenam(OnPresentSabtenamListaener onPresentSabtenamListaener) {
        this.onPresentSabtenamListaener = onPresentSabtenamListaener;
    }

    public void add(int idCourse, String idTeacher, String iduser) {
        Sabtenam sabtenam = new Sabtenam(this);
        sabtenam.add(idCourse, idTeacher, iduser);
    }

    public void checkSabtenam(int idCourse, String iduser) {
        Sabtenam sabtenam = new Sabtenam(this);
        sabtenam.checkSabtenam(idCourse, iduser);
    }

    public void updateCanceledFlag(int sabtenamId, int code) {
        Sabtenam sabtenam = new Sabtenam(this);
        sabtenam.updateCanceledFlag(sabtenamId, code);
    }

    public void confirmStudent(int sabtenamId, String apiCode, int courseId) {
        Sabtenam sabtenam = new Sabtenam(this);
        sabtenam.confirmStudent(sabtenamId, apiCode, courseId);
    }

    @Override
    public void onReceiveFlag(ArrayList<ResponseOfServer> res) {
        if (res == null || res.size() == 0)
            sendMessage("خطا");
        else
            onPresentSabtenamListaener.confirmSabtenam(res.get(0).code == 0 ? false : true);
    }

    @Override
    public void sendMessage(String message) {
        onPresentSabtenamListaener.sendMessageFST(message);
    }

    @Override
    public void checkSabtenam(ArrayList<ResponseOfServer> res) {
        onPresentSabtenamListaener.checkSabtenam(res.get(0).code);
    }
}
