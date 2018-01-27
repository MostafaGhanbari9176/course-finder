package ir.mahoorsoft.app.cityneed.view.activity_account.activity_profile.fragment_profile_amozeshgah;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.AdverFeature;
import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.preferences.Pref;
import ir.mahoorsoft.app.cityneed.model.struct.PrefKey;
import ir.mahoorsoft.app.cityneed.view.activity_account.activity_profile.ActivityProfile;
import ir.mahoorsoft.app.cityneed.view.activity_account.activity_registering.ActivityRegistering;
import ir.mahoorsoft.app.cityneed.view.adapter.AdapterEduIns;
import ir.mahoorsoft.app.cityneed.view.dialog.DialogAnswer;
import ir.mahoorsoft.app.cityneed.view.dialog.DialogProgres;
import ir.mahoorsoft.app.cityneed.view.dialog.DialogPrvince;

/**
 * Created by MAHNAZ on 10/23/2017.
 */

public class FragmentProfileAmozeshgah extends Fragment implements AdapterEduIns.setOnClickItem, DialogPrvince.OnDialogPrvinceListener, View.OnClickListener{
    ArrayList<AdverFeature> surce = new ArrayList<>();
    AdapterEduIns adapterEduIns;
    RecyclerView list;
    TextView txtPhone;
    TextView txtLocation;
    TextView txtSubject;
    TextView txtAddress;
    LinearLayout btnEdit;
    LinearLayout btnSave;
    LinearLayout btnAddCourse;
    DialogPrvince dialogPrvince;
    int cityId;
    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile_teacher,container,false);
        init();
        return view;
    }

    private void init(){
        pointers();
        dialogPrvince = new DialogPrvince(G.context,this);
        txtAddress.setText(Pref.getStringValue(PrefKey.address,""));
        txtSubject.setText(Pref.getStringValue(PrefKey.subject,""));
        txtLocation.setText(Pref.getStringValue(PrefKey.location,""));
        txtPhone.setText(Pref.getStringValue(PrefKey.landPhone,""));

        settingUpList();


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

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btnEditProfile_Karbar:
                editProfile();
                break;
            case R.id.btnAddCurse_Karbar:
                addCourse();
                break;
            case R.id.btnSave_Karbar:
                checkValidInf();
                break;
        }
    }

    private void editProfile() {

        btnSave.setVisibility(View.VISIBLE);
        Toast.makeText(G.context, "لطفا اطلاعات جدید را وارد کنید.", Toast.LENGTH_SHORT).show();
        txtSubject.setEnabled(true);
        txtAddress.setEnabled(true);
        txtPhone.setEnabled(true);
        txtLocation.setEnabled(true);
    }

    private void addCourse(){


    }

    private void checkValidInf() {
        if ((txtSubject.getText() != null && txtSubject.getText().length() != 0) &&
                (txtSubject.getText() != null && txtSubject.getText().length() != 0)&&
                (txtPhone.getText() != null && txtPhone.getText().length() != 0)&&
                (txtLocation.getText() != null && txtLocation.getText().length() != 0)) {
            //updateTeacher(Pref.getStringValue(PrefKey.phone, ""), txtName.getText().toString().trim(), txtFamilyName.getText().toString().trim());
        } else {
            Toast.makeText(G.context, "لطفا اطلاعات را کامل وارد کنید...", Toast.LENGTH_SHORT).show();
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
        txtAddress = (TextView) view.findViewById(R.id.txtAddressProfileTeacher);
        txtPhone = (TextView) view.findViewById(R.id.txtPhoneProfileTeacher);
        (txtLocation = (TextView) view.findViewById(R.id.txtLocationProfileTeacher)).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                dialogPrvince.showDialog();
                return false;
            }
        });
        txtSubject = (TextView) view.findViewById(R.id.txtSubjectProfileTeacher);

        btnEdit = (LinearLayout) view.findViewById(R.id.btnEditProfileTeacher);
        btnAddCourse = (LinearLayout) view.findViewById(R.id.btnAddCuorseProfileTeacher);
        btnSave = (LinearLayout) view.findViewById(R.id.btnSaveProfileTeacher);
        btnAddCourse.setOnClickListener(this);
        btnEdit.setOnClickListener(this);
        btnSave.setOnClickListener(this);
    }
    public void startActivitys(Class aClass , boolean flag) {

        Intent intent = new Intent(G.context, aClass);
        startActivity(intent);
        if (flag) {
            getActivity().finish();
        }
    }

    @Override
    public void locationInformation(String location, int cityId) {
        this.cityId = cityId;
        txtLocation.setText(location);
    }
}
