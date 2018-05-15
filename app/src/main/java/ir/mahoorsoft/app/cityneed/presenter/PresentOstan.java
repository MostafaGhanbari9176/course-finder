package ir.mahoorsoft.app.cityneed.presenter;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.model.struct.Message;
import ir.mahoorsoft.app.cityneed.model.struct.StCity;
import ir.mahoorsoft.app.cityneed.model.struct.StOstan;
import ir.mahoorsoft.app.cityneed.model.tables.ostan.Ostan;

/**
 * Created by MAHNAZ on 11/18/2017.
 */

public class PresentOstan implements Ostan.OnOstanListener {

    private OnPresentOstanListener onPresentOstanListener;

    public PresentOstan(OnPresentOstanListener onPresentOstanListener) {
        this.onPresentOstanListener = onPresentOstanListener;
    }

    public void getOstan() {
        Ostan ostan = new Ostan(this);
        ostan.getOstan();
    }

    @Override
    public void onReceiveOstan(ArrayList<StOstan> ostans) {
        ArrayList<StCity> ostanName = new ArrayList<>();
        for (int i = 0; i < ostans.size(); i++) {
            StCity help = new StCity();
            help.name = ostans.get(i).name;
            help.ostanId = ostans.get(i).id;
            ostanName.add(help);
        }
        onPresentOstanListener.onReceiveOstan(ostanName);
    }

    @Override
    public void sendMessage(String message) {
        onPresentOstanListener.sendMessageFOT(Message.getMessage(1));
    }

    public interface OnPresentOstanListener {
        void onReceiveOstan(ArrayList<StCity> ostanName);
        void sendMessageFOT(String message);
    }
}
