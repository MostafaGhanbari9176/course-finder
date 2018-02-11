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


    }

    OnPresentSabtenamListaener onPresentSabtenamListaener;

    public PresentSabtenam(OnPresentSabtenamListaener onPresentSabtenamListaener) {
        this.onPresentSabtenamListaener = onPresentSabtenamListaener;
    }

    public void add(int idCourse, String idTeacher, String iduser) {
        Sabtenam sabtenam = new Sabtenam(this);
        sabtenam.add(idCourse, idTeacher, iduser);
    }

    @Override
    public void onReceiveFlag(ArrayList<ResponseOfServer> res) {
        if(res.get(0).code == 2){
            sendMessage("قبلا ثبت نام شده");
            return;
        }
        onPresentSabtenamListaener.confirmSabtenam(res.get(0).code == 0 ? false : true);
    }

    @Override
    public void sendMessage(String message) {
        onPresentSabtenamListaener.sendMessageFST(message);
    }
}
