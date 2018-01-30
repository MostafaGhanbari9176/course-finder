package ir.mahoorsoft.app.cityneed.presenter;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.Items;
import ir.mahoorsoft.app.cityneed.model.struct.StTabaghe;
import ir.mahoorsoft.app.cityneed.model.tables.tabaghe.Tabaghe;

/**
 * Created by RCC1 on 1/29/2018.
 */

public class PresentTabaghe implements Tabaghe.OnTabagheListener {

    public interface OnPresentTabagheListener {
        void onResiveTabaghe(ArrayList<StTabaghe> data);

        void tabagheNahaei();

        void sendMessageFTabagheT(String message);
    }

    OnPresentTabagheListener onPresentTabagheListener;

    public PresentTabaghe(OnPresentTabagheListener onPresentTabagheListener) {
        this.onPresentTabagheListener = onPresentTabagheListener;
    }

    public void getTabaghe(int uperId) {
        Tabaghe tabaghe = new Tabaghe(this);
        tabaghe.getTabaghe(uperId);
    }

    @Override
    public void resiveData(ArrayList<StTabaghe> data) {
        if (data.get(0).subject.equalsIgnoreCase("-1"))
            onPresentTabagheListener.tabagheNahaei();
        else
            onPresentTabagheListener.onResiveTabaghe(data);
    }

    @Override
    public void sendMessage(String message) {
        onPresentTabagheListener.sendMessageFTabagheT(message);
    }
}
