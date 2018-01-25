package ir.mahoorsoft.app.cityneed.view.activity_account.activity_registering;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;

import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.preferences.Pref;
import ir.mahoorsoft.app.cityneed.model.struct.PrefKey;
import ir.mahoorsoft.app.cityneed.model.uploadFile.AndroidMultiPartEntity;
import ir.mahoorsoft.app.cityneed.model.uploadFile.Config;
import ir.mahoorsoft.app.cityneed.view.activityFiles.ActivityFiles;
import ir.mahoorsoft.app.cityneed.view.activity_account.activity_profile.ActivityProfile;
import ir.mahoorsoft.app.cityneed.view.dialog.DialogPrvince;

/**
 * Created by MAHNAZ on 10/16/2017.
 */

public class ActivityRegistering extends AppCompatActivity implements View.OnClickListener, DialogPrvince.OnDialogPrvinceListener {

    Button btnBack;
    Button btnSave;
    Button btnUploadImag;
    Button btnLocation;
    DialogPrvince dialogPrvince;
    TextView txtPhone;
    TextView txtSubject;
    TextView txtAddress;
    TextView txtTozihat;
    ProgressBar pb;
    int cityId;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registering);
        pointers();
        btnLocation.setText(Pref.getStringValue(PrefKey.location, "انتخاب استان وشهر"));
        dialogPrvince = new DialogPrvince(this, this);
    }

    @Override
    public void onBackPressed() {
        this.finish();
        super.onBackPressed();
    }

    private void pointers() {
        txtTozihat = (TextView) findViewById(R.id.txtTozihat);
        txtPhone = (TextView) findViewById(R.id.txtPhoneRegistery);
        txtAddress = (TextView) findViewById(R.id.txtAddress);
        txtSubject = (TextView) findViewById(R.id.txtSubject);
        btnBack = (Button) findViewById(R.id.btnBack_SumbitInformation);
        btnUploadImag = (Button) findViewById(R.id.btnUploadImg);
        btnSave = (Button) findViewById(R.id.btnSaveRegistery);
        btnLocation = (Button) findViewById(R.id.btnLocation);
        pb = (ProgressBar) findViewById(R.id.pgbUploadImg);
        btnSave.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btnLocation.setOnClickListener(this);
        btnUploadImag.setOnClickListener(this);
    }

    private void starterActivitry(Class aClass) {

        Intent intent = new Intent(this, aClass);
        startActivity(intent);
        finish();

    }

    private void reqForData() {
        dialogPrvince.showDialog();
    }

    public static void showMessage(String message) {

        Toast.makeText(G.context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnBack_SumbitInformation:
                starterActivitry(ActivityProfile.class);
                break;

            case R.id.btnLocation:
                reqForData();
                break;

            case R.id.btnSaveRegistery:
                getData();
                break;

            case R.id.btnUploadImg:
                choseFile();
                break;

        }
    }

    private void getData() {

        if (txtPhone.getText().toString().trim().length() != 11 &&
                txtSubject.getText().toString().trim().length() != 0 &&
                txtAddress.getText().toString().trim().length() != 0 &&
                btnLocation.getText().length() != 0) {


        } else {
            Toast.makeText(this, "لطفا اطلاعات را کامل وصحیح وارد کنید...", Toast.LENGTH_SHORT).show();
        }
    }///barresi

    private void choseFile() {
        Intent intent = new Intent(this, ActivityFiles.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);



            /*if (requestCode == 1 && resultCode == RESULT_OK) {
                UploadFileToServer upFile = new UploadFileToServer(data.getStringExtra("path"), pb, this);
                upFile.execute();*/
            if (requestCode == 1 && resultCode == RESULT_OK) {
                uploadFile(data.getStringExtra("path"));
            }
        } catch (Exception ex) {
            Toast.makeText(ActivityRegistering.this, ex.toString(),
                    Toast.LENGTH_SHORT).show();
        }
    }


    long totalSize = 0;

    private void uploadFile(final String filePath) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                String responseString;

                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(Config.FILE_UPLOAD_URL);

                try {
                    AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
                            new AndroidMultiPartEntity.ProgressListener() {

                                @Override
                                public void transferred(final long num) {
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            int percent = (int) ((num / (float) totalSize) * 100);
                                            pb.setProgress(percent);

                                        }
                                    });
                                }
                            });

                    File sourceFile = new File(filePath);

                    // Adding file data to http body
                    entity.addPart("fileToUpload", new FileBody(sourceFile));

                    // Extra parameters if you want to pass to server
                   /* entity.addPart("website",
                            new StringBody("www.androidhive.info"));
                    entity.addPart("email", new StringBody("abc@gmail.com"));
*/

                    totalSize = entity.getContentLength();
                    httppost.setEntity(entity);

                    // Making server call
                    HttpResponse response = httpclient.execute(httppost);
                    HttpEntity r_entity = response.getEntity();

                    int statusCode = response.getStatusLine().getStatusCode();
                    if (statusCode == 200) {
                        // Server response
                        responseString = EntityUtils.toString(r_entity);
                    } else {
                        responseString = "Error occurred! Http Status Code: "
                                + statusCode;
                    }

                } catch (ClientProtocolException e) {
                    responseString = e.toString();
                } catch (IOException e) {
                    responseString = e.toString();
                }
            }
        }).start();
    }

    @Override
    public void locationInformation(String location, int cityId) {
        btnLocation.setText(location);
        this.cityId = cityId;
    }
}
