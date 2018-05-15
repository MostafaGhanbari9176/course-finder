package ir.mahoorsoft.app.cityneed.presenter;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.model.struct.Message;
import ir.mahoorsoft.app.cityneed.model.struct.StGrouping;
import ir.mahoorsoft.app.cityneed.model.tables.grouping.Grouping;

/**
 * Created by RCC1 on 1/29/2018.
 */

public class PresentGrouping implements Grouping.OnTabagheListener {

    public interface OnPresentTabagheListener {
        void onResiveTabaghe(ArrayList<StGrouping> data);


        void sendMessageFTabagheT(String message);
    }

    OnPresentTabagheListener onPresentTabagheListener;

    public PresentGrouping(OnPresentTabagheListener onPresentTabagheListener) {
        this.onPresentTabagheListener = onPresentTabagheListener;
    }

    public void getTabaghe(int uperId) {
        Grouping grouping = new Grouping(this);
        grouping.getTabaghe(uperId);
    }

    @Override
    public void resiveData(ArrayList<StGrouping> data) {
        if (data == null || data.size() == 0) {
            sendMessage("خطا");
            return;
        }
        if (data.get(0).empty == 0)
            onPresentTabagheListener.onResiveTabaghe(data);
        else
            sendMessage("زیر دسته ایی موجود نیست");
    }

    @Override
    public void sendMessage(String message) {
        onPresentTabagheListener.sendMessageFTabagheT(Message.getMessage(1));
    }
}
