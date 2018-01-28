package ir.mahoorsoft.app.cityneed.view.activity_account.activity_profile;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.StringSignature;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandlerFactory;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

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
        Pref.removeValue(PrefKey.subject);
        Pref.removeValue(PrefKey.address);
        Pref.removeValue(PrefKey.userTypeMode);
        Pref.removeValue(PrefKey.landPhone);
        Pref.removeValue(PrefKey.madrak);
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
                setImageWithBackBlur(R.drawable.user, imgProfile, backImage);
                replaceContentWith(new FragmentProfileKarbar());
                break;
            case 1:
                txtUserType.setText("آموزشگاه");
                setUrlWithBackBlur(Pref.getStringValue(PrefKey.phone,""), imgProfile, backImage);
                replaceContentWith(new FragmentProfileAmozeshgah());//4
                break;
            case 2:
                txtUserType.setText("آموزش خصوصی");
                imgProfile.setImageResource(R.drawable.icon_college);
                replaceContentWith(new FragmentProfileAmozeshgah());
                break;
        }
    }

    private void setUrlWithBackBlur(String name, ImageView image, ImageView backImage) {
        Bitmap theBitmap = null;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            theBitmap =
                    Glide.with(this)
                            .load(ApiClient.serverAddress + "/city_need/v1/uploads/" + name + ".png")
                            .asBitmap()
                            .into(100, 100) // Width and height
                            .get();

        } catch (Exception e) {
            e.printStackTrace();
        }

        //BlurBuilder.blur(this, DownloadImage(ApiClient.serverAddress + "/city_need/v1/uploads/" + name + ".png")).compress(Bitmap.CompressFormat.PNG, 100, stream);
        Glide.with(this)
                .load(stream.toByteArray())
                .asBitmap()
                .centerCrop()
                .into(backImage);
        backImage.setImageBitmap(DownloadImage(ApiClient.serverAddress + "/city_need/v1/uploads/" + name + ".png"));
        Glide.with(this)
                .load(ApiClient.serverAddress + "/city_need/v1/uploads/" + name + ".png")
                .fitCenter()
                .signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                .into(image);


    }

    private void setImageWithBackBlur(int img, ImageView image, ImageView backImage) {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        BlurBuilder.blur(this, BitmapFactory.decodeResource(getResources(), img)).compress(Bitmap.CompressFormat.PNG, 100, stream);
        Glide.with(this)
                .load(stream.toByteArray())
                .asBitmap()
                .centerCrop()
                .into(backImage);

        Glide.with(this)
                .load(img)
                .fitCenter()
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

    private Bitmap DownloadImage(String URL)
    {
//      System.out.println("image inside="+URL);
        Bitmap bitmap = null;
        InputStream in = null;
        try {
            in = OpenHttpConnection(URL.substring(0,URL.lastIndexOf('.')));
            bitmap = BitmapFactory.decodeStream(in);
            in.close();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
//        System.out.println("image last");
        return bitmap;
    }
    private InputStream OpenHttpConnection(String urlString)
            throws IOException
    {
        InputStream in = null;
        int response = -1;

        URL url = new URL(urlString);
        URLConnection conn = url.openConnection();

        if (!(conn instanceof HttpURLConnection))
            throw new IOException("Not an HTTP connection");

        try{
            HttpURLConnection httpConn = (HttpURLConnection) conn;
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.connect();

            response = httpConn.getResponseCode();
            if (response == HttpURLConnection.HTTP_OK)
            {
                in = httpConn.getInputStream();
            }
        }
        catch (Exception ex)
        {
            throw new IOException("Error connecting");
        }
        return in;
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


