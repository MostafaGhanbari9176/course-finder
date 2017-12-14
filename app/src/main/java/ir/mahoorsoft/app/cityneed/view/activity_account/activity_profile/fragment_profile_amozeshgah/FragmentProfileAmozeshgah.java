package ir.mahoorsoft.app.cityneed.view.activity_account.activity_profile.fragment_profile_amozeshgah;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.AdverFeature;
import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.view.activity_account.activity_registering.ActivityRegistering;
import ir.mahoorsoft.app.cityneed.view.adapter.AdapterEduIns;

/**
 * Created by MAHNAZ on 10/23/2017.
 */

public class FragmentProfileAmozeshgah extends Fragment implements AdapterEduIns.setOnClickItem{
    ArrayList<AdverFeature> surce = new ArrayList<>();
    AdapterEduIns adapterEduIns;
    RecyclerView list;
    ImageView img;
    CheckBox cbx;
    TextView txt;
    Button btnEdit;
    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile_amozeshgah,container,false);
        init();
        return view;
    }

    private void init(){

        pointers();
        setingUpTextViews();
        setingUpImage();
        settingUpList();
        cbxManager();
        cbx.setChecked(false);
    }

    private void settingUpList(){

        list= (RecyclerView)view.findViewById(R.id.RV_Profile);
        RecyclerView.LayoutManager  layoutManager = new LinearLayoutManager(G.context
                ,LinearLayoutManager.HORIZONTAL,true);
        adapterEduIns = new AdapterEduIns(G.context,surce,this);
        list.setLayoutManager(layoutManager);
        list.setAdapter(adapterEduIns);
        adapterEduIns.notifyDataSetChanged();
        setSurce();


    }

    @Override
    public void itemClick(int id)
    {
        if(cbx.isChecked()){

            surce.remove(id);
            cbxManager();
            adapterEduIns.notifyDataSetChanged();

        }
    }

    private void setSurce() {


        for (int i = 1; i <= 4; i++) {
            AdverFeature character = new AdverFeature();
            switch (i) {
                case 1:
                    character.image = R.drawable.aa;
                    character.collegeName = "مجری برکزار کننده دروه های اموزشی کنکور";
                    character.kind = i * 29 + "";
                    break;
                case 2:
                    character.image = R.drawable.bb;
                    character.collegeName = "برگزار کننده کلاس های تمرین";
                    character.kind = i * 29 + "";
                    break;
                case 3:
                    character.image = R.drawable.cc;
                    character.collegeName = "برگزار کننده کلاس امتحانات نهایی";
                    character.kind = "مجری برکزار کننده دروه های اموزشی کنکور";
                    break;
                case 4:
                    character.image = R.drawable.dd;
                    character.collegeName = i * 20 + "";
                    character.kind = i * 29 + "";
                    break;
            }

            surce.add(character);
            adapterEduIns.notifyDataSetChanged();

        }
    }

    private void pointers(){
        img = (ImageView) view.findViewById(R.id.img_profile);
        cbx = (CheckBox) view.findViewById(R.id.cbxDelete);
        txt = (TextView) view.findViewById(R.id.txtCbx_Profile);
        btnEdit = (Button) view.findViewById(R.id.btnEditProfile);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivitys(ActivityRegistering.class , false);
            }
        });
    }

    private void setingUpTextViews(){}

    private void setingUpImage(){

        Glide.with(this)
                .load(R.drawable.aa)
                .centerCrop()
                .into(img);
    }

    private void cbxManager(){

        if(surce.size()==0) {
            cbx.setVisibility(View.GONE);
            txt.setVisibility(View.GONE);
        }
    }

    public void startActivitys(Class aClass , boolean flag) {

        Intent intent = new Intent(G.context, aClass);
        startActivity(intent);
        if (flag) {
            getActivity().finish();
        }
    }

}
