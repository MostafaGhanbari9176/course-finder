package ir.mahoorsoft.app.cityneed.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.struct.StCity;
import ir.mahoorsoft.app.cityneed.presenter.PresentCity;
import ir.mahoorsoft.app.cityneed.presenter.PresentOstan;
import ir.mahoorsoft.app.cityneed.view.registering.ActivityTeacherRegistering;
import ir.mahoorsoft.app.cityneed.view.adapter.AdapterChosePrvince;

/**
 * Created by M_gh on 12/10/2017.
 */

public class DialogPrvince implements PresentCity.OnPresentCityListener, PresentOstan.OnPresentOstanListener, AdapterChosePrvince.ItemClick {
    private Context context;
    private TextView txtSerch;
    private View dialogView;
    private Dialog dialog;
    private RecyclerView list;
    private AdapterChosePrvince adapter;
    private ArrayList<StCity> firstSource = new ArrayList<>();
    private ArrayList<StCity> lastSource = new ArrayList<>();
    private DialogProgres dialogProgres;
    private boolean flag = false;
    private String map;

    OnDialogPrvinceListener onDialogPrvinceListener;

    public DialogPrvince(Context context ,OnDialogPrvinceListener onDialogPrvinceListener) {
        this.onDialogPrvinceListener = onDialogPrvinceListener;
        this.context = context;
        dialogProgres = new DialogProgres(context);
        dialog = new Dialog(context);
        final LayoutInflater lI = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        dialogView = lI.inflate(R.layout.dialog_chose_province, null, false);
        resetSource(firstSource);
    }

    public void showDialog() {
        map = "";
        flag = false;
        dialogProgres.showProgresBar();
        PresentOstan presentOstan = new PresentOstan(this);
        presentOstan.getOstan();
        setingUpList();
        setingUptxtSearch();
        txtSerch.setSelected(true);
        dialog.setContentView(dialogView);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

    }

    private void setingUpList() {
        list = (RecyclerView) dialogView.findViewById(R.id.RVDialogProvince);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(context);
        list.setLayoutManager(manager);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void setingUptxtSearch() {

        txtSerch = (TextView) dialogView.findViewById(R.id.txtSearchChoseProvince);
        txtSerch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (count != 0) {

                    searching(s.toString());

                } else {
                    resetSource(firstSource);
                    searching("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    public void resetSource(ArrayList<StCity> source) {
        lastSource.clear();
        for (int i = 0 ;i<source.size();i++){
            lastSource.add(source.get(i));
        }
        adapter = new AdapterChosePrvince(context, source, this);
        setingUpList();
    }

    private void resetSource() {
        adapter = new AdapterChosePrvince(context, lastSource, this);
        setingUpList();
    }

    public void clossDialog() {
        dialog.cancel();
    }

    private void searching(String key) {
        lastSource.clear();
        for (int i = 0; i < firstSource.size(); i++) {
            if (firstSource.get(i).name.contains(key)) {
                lastSource.add(firstSource.get(i));
            }
        }
        if (lastSource.size() != 0) {
            resetSource();
        } else {

            resetSource(firstSource);
        }

    }

    @Override
    public void onReceiveCity(ArrayList<StCity> cityName) {
        dialogProgres.closeProgresBar();
        dialog.show();
        firstSource.clear();
        firstSource = cityName;
        resetSource(cityName);
    }

    @Override
    public void sendMessageFCT(String message) {
        dialogProgres.closeProgresBar();
        ActivityTeacherRegistering.showMessage(message);
    }

    @Override
    public void onReceiveOstan(ArrayList<StCity> ostanName) {
        dialogProgres.closeProgresBar();
        dialog.show();
        firstSource.clear();
        firstSource = ostanName;
        resetSource(ostanName);
    }

    @Override
    public void sendMessageFOT(String message) {
        dialogProgres.closeProgresBar();
        showMessage(message);
    }

    @Override
    public void showCity(int position) {

        if (!flag) {
            map += lastSource.get(position).name;
            flag = true;
            dialogProgres.showProgresBar();
            PresentCity cityNames = new PresentCity(this);
            cityNames.getCity(lastSource.get(position).name);
        } else {
            dialog.cancel();
            map += " , " + lastSource.get(position).name;
            onDialogPrvinceListener.locationInformation(map,lastSource.get(position).cityId );
        }
    }

    public void showMessage(String message) {
        dialogProgres.closeProgresBar();
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public interface OnDialogPrvinceListener{
         void locationInformation(String location, int cityId);
    }
}
