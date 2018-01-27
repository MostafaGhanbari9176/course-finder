package ir.mahoorsoft.app.cityneed.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ir.mahoorsoft.app.cityneed.R;

/**
 * Created by RCC1 on 1/27/2018.
 */

public class DialogAnswer {
    private Context context;
    private String text = "مطمعن؟";
    private Dialog dialog;

    public interface OnClickDialogAnswerListener {
        void answer(boolean answer);
    }

    OnClickDialogAnswerListener onClickDialogAnswerListener;

    public DialogAnswer(Context context, OnClickDialogAnswerListener onClickDialogAnswerListener) {
        this.context = context;
        dialog = new Dialog(context);
        this.onClickDialogAnswerListener = onClickDialogAnswerListener;
    }

    public DialogAnswer(Context context, String text, OnClickDialogAnswerListener onClickDialogAnswerListener) {
        this(context, onClickDialogAnswerListener);
        this.text = text;
    }

    public void showDialog() {
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = li.inflate(R.layout.dialog_answer, null, false);
        ((TextView) view.findViewById(R.id.txtDialogAnswer)).setText(text);
        ((Button) view.findViewById(R.id.btnYesDialogAnswer)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                onClickDialogAnswerListener.answer(true);
            }
        });
        ((Button) view.findViewById(R.id.btnNoDialogAnswer)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                onClickDialogAnswerListener.answer(false);
            }
        });
        dialog.setContentView(view);
       // dialog.getWindow().set
        dialog.show();
        dialog.setCancelable(false);
    }
}
