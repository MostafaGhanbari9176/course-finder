package ir.mahoorsoft.app.cityneed.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import ir.mahoorsoft.app.cityneed.R;

/**
 * Created by MAHNAZ on 11/30/2017.
 */

public class DialogProgres {
    private Context context;
    private String message;
    private Dialog dialog;

    public DialogProgres(Context context, String messqge) {
        //Context appContext = context.getApplicationContext();
        this.context = context;
        this.message = messqge;
        dialog = new Dialog(context);

    }

    public DialogProgres(Context context) {

        this.context = context;
        this.message = "لطفا منتظر بمانید.....";
        dialog = new Dialog(context);

    }

    public void showProgresBar() {

        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = li.inflate(R.layout.dialog_progresbar, null, true);
        TextView txt = (TextView) view.findViewById(R.id.txtDialoProgresBar);
        txt.setText(message);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();
        dialog.setCancelable(false);
    }

    public void closeProgresBar() {
        dialog.cancel();
    }

}
