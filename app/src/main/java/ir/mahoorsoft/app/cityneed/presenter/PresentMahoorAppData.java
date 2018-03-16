package ir.mahoorsoft.app.cityneed.presenter;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.model.struct.StMahoorAppData;
import ir.mahoorsoft.app.cityneed.model.tables.MahoorAppData;

/**
 * Created by M-gh on 16-Mar-18.
 */

public class PresentMahoorAppData implements MahoorAppData.OnMahoorAppDataListener {


    public interface OnPresentMahoorAppDataListener {
        void resieveAppData(ArrayList<StMahoorAppData> data);

        void messageFromAppData(String message);

    }

    private OnPresentMahoorAppDataListener onPresentMahoorAppDataListener;

    public PresentMahoorAppData(OnPresentMahoorAppDataListener onPresentMahoorAppDataListener) {
        this.onPresentMahoorAppDataListener = onPresentMahoorAppDataListener;
    }

    public void getMahoorAppData() {
        MahoorAppData mahoorAppData = new MahoorAppData(this);
        mahoorAppData.getMahoorAppData();
    }

    @Override
    public void resiveData(ArrayList<StMahoorAppData> data) {
        if (data == null || data.size() == 0)
            sendMessage("خطا");
        else
            onPresentMahoorAppDataListener.resieveAppData(data);
    }

    @Override
    public void sendMessage(String message) {
        onPresentMahoorAppDataListener.messageFromAppData(message);
    }
}
