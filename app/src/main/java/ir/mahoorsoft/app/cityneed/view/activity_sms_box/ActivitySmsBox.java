package ir.mahoorsoft.app.cityneed.view.activity_sms_box;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioButton;

import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;

/**
 * Created by M-gh on 27-Feb-18.
 */

public class ActivitySmsBox extends AppCompatActivity implements View.OnClickListener {

    RadioButton rbInBox;
    RadioButton rbOutBox;
    Toolbar tb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_box);
        G.activity = this ;
        G.context = this;
        pointer();
    }

    private void pointer() {
        rbInBox = (RadioButton) findViewById(R.id.rbInBoxSmsBox);
        rbInBox.setChecked(true);
        rbOutBox = (RadioButton) findViewById(R.id.rbOutBoxSmsBox);
        tb = (Toolbar) findViewById(R.id.tbSmsBox);
        rbInBox.setOnClickListener(this);
        rbOutBox.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rbInBoxSmsBox:
                replaceContentWith(new FragmentSmsBoxIn());
                break;
            case R.id.rbOutBoxSmsBox:
//                replaceContentWith(new FS);
                break;
        }
    }


    public static void replaceContentWith(Fragment fragment) {

        G.activity.getSupportFragmentManager()
                .beginTransaction().replace(R.id.contentSmsBox, fragment)
                .commit();
    }

}
