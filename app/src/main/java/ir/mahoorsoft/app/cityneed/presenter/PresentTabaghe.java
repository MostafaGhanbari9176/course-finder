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
        void onResiveTabaghe(ArrayList<Items> data);

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
        ArrayList<Items> itemses = new ArrayList<>();
        if (data.size() == 1) {
            onPresentTabagheListener.tabagheNahaei();
            return;
        }
        for (int i = 0; i < data.size(); i++) {
            Items items = new Items();
            items.id = data.get(i).id;
            items.MasterName = data.get(i).subject;
            itemses.add(items);
        }
        onPresentTabagheListener.onResiveTabaghe(itemses);
    }

    @Override
    public void sendMessage(String message) {
        onPresentTabagheListener.sendMessageFTabagheT(message);
    }
}
