package ir.mahoorsoft.app.cityneed.presenter;

import java.util.ArrayList;
import ir.mahoorsoft.app.cityneed.model.struct.StCity;
import ir.mahoorsoft.app.cityneed.model.tables.city.City;

/**
 * Created by MAHNAZ on 11/18/2017.
 */

public class PresentCity implements City.OnCityListener {


    private OnPresentCityListener onPresentCityListener;

    public PresentCity(OnPresentCityListener onPresentCityListener) {
        this.onPresentCityListener = onPresentCityListener;
    }

    public void getCity(String flag) {
        City city = new City(this);
        city.getCity(flag);
    }

    @Override
    public void onReceiveCity(ArrayList<StCity> citys) {

        onPresentCityListener.onReceiveCity(citys);
    }

    @Override
    public void sendMessage(String message) {
        onPresentCityListener.sendMessageFCT(message);
    }

    public interface OnPresentCityListener {

        void onReceiveCity(ArrayList<StCity> cityName);
        void sendMessageFCT(String message);
    }
}
