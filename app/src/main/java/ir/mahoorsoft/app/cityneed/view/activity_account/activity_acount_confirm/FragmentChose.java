package ir.mahoorsoft.app.cityneed.view.activity_account.activity_acount_confirm;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import ir.mahoorsoft.app.cityneed.R;

/**
 * Created by MAHNAZ on 10/22/2017.
 */

public class FragmentChose extends Fragment {
    View view;

    Button btnEmail;
    Button btnPhone;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container
            , @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_chose_phone_or_email, container, false);
        init();
        return view;
    }

    private void init(){
        pointers();
        setFont();
    }

    private void setFont() {
        Typeface typeface = Typeface.createFromAsset(getResources().getAssets(), "fonts/Far_Nazanin.ttf");
        btnEmail.setTypeface(typeface);
        btnPhone.setTypeface(typeface);
    }

    private void pointers() {
        btnPhone = (Button) view.findViewById(R.id.btnPhoneAcountConfirm);
        btnEmail = (Button) view.findViewById(R.id.btnEmailAcountConfirm);
    }
}
