package ir.mahoorsoft.app.cityneed.presenter;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.model.preferences.Pref;
import ir.mahoorsoft.app.cityneed.model.struct.Message;
import ir.mahoorsoft.app.cityneed.model.struct.PrefKey;
import ir.mahoorsoft.app.cityneed.model.struct.ResponseOfServer;
import ir.mahoorsoft.app.cityneed.model.struct.StNotifyData;
import ir.mahoorsoft.app.cityneed.model.tables.sabtenam.Sabtenam;

/**
 * Created by RCC1 on 2/1/2018.
 */

public class PresentSabtenam implements Sabtenam.OnSabtenamListener {

    public interface OnPresentSabtenamListaener {
        void sendMessageFST(String message);

        void confirmSabtenam(boolean flag);

        void confirmDelete(boolean flag);

        void checkSabtenam(float ratBarValue);


    }

    OnPresentSabtenamListaener onPresentSabtenamListaener;

    public PresentSabtenam(OnPresentSabtenamListaener onPresentSabtenamListaener) {
        this.onPresentSabtenamListaener = onPresentSabtenamListaener;
    }

    public void add(int idCourse, String idTeacher, String iduser, String cellPhone) {
        Sabtenam sabtenam = new Sabtenam(this);
        sabtenam.add(idCourse, idTeacher, iduser, cellPhone);
    }

    public void checkSabtenam(int idCourse, String iduser) {
        Sabtenam sabtenam = new Sabtenam(this);
        sabtenam.checkSabtenam(idCourse, iduser);
    }
    public void updateCanceledFlag(int sabtenamId, int code, int courseId, String message, String tsId, String rsId) {
        Sabtenam sabtenam = new Sabtenam(this);
        sabtenam.updateCanceledFlag(sabtenamId, code, courseId, message, tsId, rsId);
    }

    public void updateMoreCanceledFlag(String jsonData, String message) {
        Sabtenam sabtenam = new Sabtenam(this);
        sabtenam.updateMoreCanceledFlag(jsonData, message);
    }

    public void confirmStudent(int sabtenamId, int courseId, String message, String tsId, String rsId) {
        Sabtenam sabtenam = new Sabtenam(this);
        sabtenam.confirmStudent(sabtenamId, courseId, message, tsId, rsId);
    }

    public void confirmMoreStudent(String jsonData, String message) {
        Sabtenam sabtenam = new Sabtenam(this);
        sabtenam.confirmMoreStudent(jsonData, message);
    }

    @Override
    public void onReceiveFlag(ArrayList<ResponseOfServer> res) {
        if (res == null || res.size() == 0)
            sendMessage("خطا");
        else
            onPresentSabtenamListaener.confirmSabtenam(res.get(0).code == 0 ? false : true);
    }

    @Override
    public void onReceiveFlagForDelete(ArrayList<ResponseOfServer> res) {
        if (res == null || res.size() == 0)
            sendMessage("خطا");
        else
            onPresentSabtenamListaener.confirmDelete(res.get(0).code == 0 ? false : true);
    }

    @Override
    public void sendMessage(String message) {
        onPresentSabtenamListaener.sendMessageFST(Message.getMessage(1));
    }

    @Override
    public void checkSabtenam(ArrayList<ResponseOfServer> res) {
        if (res == null || res.size() == 0)
            sendMessage("خطا");
        else
            onPresentSabtenamListaener.checkSabtenam(res.get(0).code);
    }

    @Override
    public void SabtenamNotifyData(ArrayList<StNotifyData> data) {

    }
}
