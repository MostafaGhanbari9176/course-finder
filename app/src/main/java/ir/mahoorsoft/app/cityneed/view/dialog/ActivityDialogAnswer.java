package ir.mahoorsoft.app.cityneed.view.dialog;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ir.mahoorsoft.app.cityneed.R;

/**
 * Created by M-gh on 28-Jan-18.
 */

public class ActivityDialogAnswer extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_answer);
        TextView txt = (TextView) findViewById(R.id.txtDialogAnswer);
        (findViewById(R.id.btnYesDialogAnswer)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                intent.putExtra("answer",true);
                setResult(RESULT_OK,intent);
                ActivityDialogAnswer.this.finish();
            }
        });
        (findViewById(R.id.btnNoDialogAnswer)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =getIntent();
                intent.putExtra("answer",false);
                setResult(RESULT_OK,intent);
                ActivityDialogAnswer.this.finish();
            }
        });

        if(getIntent().getExtras() !=null)
            txt.setText(getIntent().getExtras().get("txt").toString());
        else
            txt.setText("مطمعن");
    }
}
