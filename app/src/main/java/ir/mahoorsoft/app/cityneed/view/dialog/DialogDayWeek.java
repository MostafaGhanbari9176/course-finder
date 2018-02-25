package ir.mahoorsoft.app.cityneed.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Stack;

import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.struct.StGrouping;
import ir.mahoorsoft.app.cityneed.presenter.PresentGrouping;
import ir.mahoorsoft.app.cityneed.view.adapter.AdapterTabagheList;

/**
 * Created by M_gh on 12/10/2017.
 */

public class DialogDayWeek {

    private Context context;
    private View view;
    private Dialog dialog;
    private Button btn;
    private CheckBox cbx0;
    private CheckBox cbx1;
    private CheckBox cbx2;
    private CheckBox cbx3;
    private CheckBox cbx4;
    private CheckBox cbx5;
    private CheckBox cbx6;
    private ReturnDay returnDay;

    public DialogDayWeek(Context context, ReturnDay returnDay) {
        this.context = context;
        this.returnDay = returnDay;
        dialog = new Dialog(context);
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = li.inflate(R.layout.dialog_day_week, null, false);
    }

    public void Show() {
        btn = (Button) view.findViewById(R.id.btnConfirmDayDialog);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkDays();
            }
        });
        cbx0 = (CheckBox) view.findViewById(R.id.cbxDayDialog0);
        cbx1 = (CheckBox) view.findViewById(R.id.cbxDayDialog1);
        cbx2 = (CheckBox) view.findViewById(R.id.cbxDayDialog2);
        cbx3 = (CheckBox) view.findViewById(R.id.cbxDayDialog3);
        cbx4 = (CheckBox) view.findViewById(R.id.cbxDayDialog4);
        cbx5 = (CheckBox) view.findViewById(R.id.cbxDayDialog5);
        cbx6 = (CheckBox) view.findViewById(R.id.cbxDayDialog6);

        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();
    }

    private void checkDays() {
        if (getDays().length() != 0) {
            returnDay.days(getDays());
            dialog.cancel();
        } else
            Toast.makeText(context, "لطفا یک یا چند روز را انتخاب کنید.", Toast.LENGTH_SHORT).show();

    }

    private String getDays() {
        String day = "";
        if (cbx0.isChecked())
            day = day + '-' + "شنبه";
        if (cbx1.isChecked())
            day = day + '-' + "یکشنبه";
        if (cbx2.isChecked())
            day = day + '-' + "دوشنبه";
        if (cbx3.isChecked())
            day = day + '-' + "سه شنبه";
        if (cbx4.isChecked())
            day = day + '-' + "چهار شنبه";
        if (cbx5.isChecked())
            day = day + '-' + "پنجشنبه";
        if (cbx6.isChecked())
            day = day + '-' + "جمعه";

        return (G.myTrim(day,'-')).trim();
    }

    public interface ReturnDay {
        void days(String days);
    }
}
