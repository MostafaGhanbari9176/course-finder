package ir.mahoorsoft.app.cityneed.view.activity_profile.fragment_profile_karbar;


import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.preferences.Pref;
import ir.mahoorsoft.app.cityneed.model.struct.PrefKey;
import ir.mahoorsoft.app.cityneed.model.struct.ResponseOfServer;
import ir.mahoorsoft.app.cityneed.model.struct.StUser;
import ir.mahoorsoft.app.cityneed.presenter.PresentUser;
import ir.mahoorsoft.app.cityneed.view.CharCheck;
import ir.mahoorsoft.app.cityneed.view.dialog.DialogProgres;


/**
 * Created by MAHNAZ on 10/23/2017.
 */

public class FragmentProfileKarbar extends Fragment implements PresentUser.OnPresentUserLitener {

    TextView txtName;
    TextView txtPhone;
    View view;
    DialogProgres dialogProgres;
    Typeface typeface;
    String name;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile_karbar, container, false);
        init();
        return view;
    }

    private void init() {
        dialogProgres = new DialogProgres(G.context, "درحال بروزرسانی");
        pointers();
        setFont();
        txtPhone.setText(Pref.getStringValue(PrefKey.phone, ""));
        txtName.setText(Pref.getStringValue(PrefKey.userName, ""));

    }

    private void setFont() {
        typeface = Typeface.createFromAsset(getResources().getAssets(), "fonts/Far_Nazanin.ttf");
        txtName.setTypeface(typeface);
        txtPhone.setTypeface(typeface);
    }

    private void pointers() {

        dialogProgres = new DialogProgres(G.context);
        txtName = (TextView) view.findViewById(R.id.txtNAme_karbar);
        txtPhone = (TextView) view.findViewById(R.id.txtPhone_Karbar);
/*        txtName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });*/


    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(G.context);
        final EditText editText = new EditText(G.context);
        editText.setTypeface(typeface);
        builder.setView(editText);
        builder.setTitle("تغییر نام کاربری");
        builder.setMessage("از این نام برای ثبت نام شما در دوره های انتخابی استفاده می شود.");
        builder.setPositiveButton("تایید", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(editText.getText() != null) {
                    name = CharCheck.faCheck(editText.getText().toString().trim());
                    if (name.length() == 0)
                        showDialog();
                    else {
                        updateName(name);
                        dialog.cancel();
                    }
                }else
                    showDialog();
            }
        });

        builder.setNegativeButton("رد کردن", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    @Override
    public void sendMessageFUT(String message) {
        dialogProgres.closeProgresBar();
        dialogProgres.closeProgresBar();
        Toast.makeText(G.context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void LogOut(boolean flag) {

    }

    @Override
    public void LogIn(ResponseOfServer res) {

    }

    @Override
    public void LogUp(ResponseOfServer res) {

    }

    @Override
    public void onReceiveUser(ArrayList<StUser> students) {

    }


    private void updateName(String name) {
        dialogProgres.showProgresBar();
        PresentUser presentUser = new PresentUser(this);
        presentUser.updateUser(Pref.getStringValue(PrefKey.phone, ""), name);
    }

}



