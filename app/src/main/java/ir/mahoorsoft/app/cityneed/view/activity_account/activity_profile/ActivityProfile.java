package ir.mahoorsoft.app.cityneed.view.activity_account.activity_profile;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLStreamHandlerFactory;
import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.api.ApiClient;
;
import ir.mahoorsoft.app.cityneed.model.preferences.Pref;
import ir.mahoorsoft.app.cityneed.model.struct.PrefKey;
import ir.mahoorsoft.app.cityneed.model.struct.StUser;
import ir.mahoorsoft.app.cityneed.presenter.PresentUser;
import ir.mahoorsoft.app.cityneed.view.BlurBuilder;
import ir.mahoorsoft.app.cityneed.view.activity_account.activity_profile.fragment_profile_amozeshgah.FragmentProfileAmozeshgah;
import ir.mahoorsoft.app.cityneed.view.activity_account.activity_profile.fragment_profile_karbar.FragmentProfileKarbar;
import ir.mahoorsoft.app.cityneed.view.dialog.DialogProgres;


/**
 * Created by MAHNAZ on 10/16/2017.
 */

public class ActivityProfile extends AppCompatActivity implements View.OnClickListener, PresentUser.OnPresentUserLitener {
    TextView txtUserType;
    ImageView imgProfile;
    Button btnBack;
    Button btnLogOut;
    DialogProgres dialogProgres;
    ImageView backImage;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        G.activity = this;
        G.context = this;
        dialogProgres = new DialogProgres(this);

        setContentView(R.layout.activity_profile);
        pointer();
        checkUserType();

    }

    private void pointer() {
        backImage = (ImageView) findViewById(R.id.backImageProfile);
        imgProfile = (ImageView) findViewById(R.id.imgProfile);
        txtUserType = (TextView) findViewById(R.id.txtUserType);
        (btnBack = (Button) findViewById(R.id.btnBack_Profile)).setOnClickListener(this);
        (btnLogOut = (Button) findViewById(R.id.btnLogOut)).setOnClickListener(this);

    }

    @Override
    public void onBackPressed() {

        this.finish();
        super.onBackPressed();
    }

    public static void replaceContentWith(Fragment fragment) {

        G.activity.getSupportFragmentManager().beginTransaction().
                replace(R.id.contentProfile, fragment).commit();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack_Profile:
                onBackPressed();
                break;
            case R.id.btnLogOut:
                queryForLogOut();
                break;
        }
    }

    private void queryForLogOut() {

        dialogProgres.showProgresBar();
        PresentUser presentUser = new PresentUser(this);
        presentUser.logOut(Pref.getStringValue(PrefKey.phone, ""));
    }

    @Override
    public void sendMessageFUT(String message) {
        dialogProgres.closeProgresBar();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onReceiveUser(ArrayList<StUser> users) {

    }

    @Override
    public void confirmUser(boolean flag) {
        dialogProgres.closeProgresBar();
        Pref.removeValue(PrefKey.phone);
        Pref.removeValue(PrefKey.userFamily);
        Pref.removeValue(PrefKey.userName);
        Pref.removeValue(PrefKey.location);
        Pref.removeValue(PrefKey.cityId);
        Pref.removeValue(PrefKey.IsLogin);
        this.finish();
    }

    @Override
    protected void onResume() {
        G.activity = this;
        G.context = this;
        super.onResume();
    }

    private void checkUserType() {
        int typeMode = Pref.getIntegerValue(PrefKey.userTypeMode, 0);
        switch (typeMode) {
            case 0:
                txtUserType.setText("دانشجو");

                //setImageWithBackBlur(R.drawable.a, imgProfile, backImage);
                replaceContentWith(new FragmentProfileKarbar());
                break;
            case 1:
                txtUserType.setText("آموزشگاه");
                // imgLoader.DisplayImage(ApiClient.serverAddress+"/city_need/v1/uploads/"+Pref.getStringValue(PrefKey.phone,"")+".png", R.drawable.notebook, imgProfile);
                setImageWithBackBlur(Pref.getStringValue(PrefKey.phone, ""), imgProfile, backImage);
                replaceContentWith(new FragmentProfileAmozeshgah());//4
                break;
            case 2:
                txtUserType.setText("آموزش خصوصی");
                imgProfile.setImageResource(R.drawable.icon_college);
                replaceContentWith(new FragmentProfileAmozeshgah());
                break;
        }
    }

    private void setImageWithBackBlur(String name, ImageView image, ImageView backImage) {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
       Bitmap bit =  BlurBuilder.blur(this, getBitmapFromUrl(ApiClient.serverAddress + "/city_need/v1/uploads/" + name + ".png"));
        Glide.with(this)

                .load(bit)
                .asBitmap()
                .error(R.drawable.c)
                .centerCrop()

                .into(backImage);
        Glide.with(this)
                .load(ApiClient.serverAddress + "/city_need/v1/uploads/" + name + ".png")
                .centerCrop()

                .into(image);

    }

    protected Bitmap getBitmapFromUrl(final String src) {
        final Bitmap[] bit = new Bitmap[1];
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(src);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream input = connection.getInputStream();
                    Bitmap myBitmap = BitmapFactory.decodeStream(input);
                    bit[0] = myBitmap;
                } catch (Exception e) {

                    bit[0] = null;
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
        return bit[0];

    }

    /*public static void showDialog(DialogPrvince.OnDialogPrvinceListener onDialogPrvinceListener) {
        if (dialogPrvince == null) {
            dialogPrvince = new DialogPrvince(ActivityProfile.this, onDialogPrvinceListener);
            dialogPrvince.showDialog();
        }else{//4
            dialogPrvince.showDialog();
        }//5
    }
*/
}


