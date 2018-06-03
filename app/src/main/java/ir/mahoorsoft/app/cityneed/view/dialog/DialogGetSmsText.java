package ir.mahoorsoft.app.cityneed.view.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.ArrayList;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.localDatabase.LocalDatabase;
import ir.mahoorsoft.app.cityneed.view.CharCheck;
import ir.mahoorsoft.app.cityneed.view.activity_sms_box.AdapterReadySmsList;


/**
 * Created by RCC1 on 4/29/2018.
 */

public class DialogGetSmsText implements AdapterReadySmsList.OnClickItemReadySmsListListener, View.OnClickListener {

    private Context context;
    private RecyclerView list;
    private AdapterReadySmsList adapter;
    private ArrayList<String> source = new ArrayList<>();
    private Dialog dialog;
    private TextView btnAdd;
    private TextView txtMessage;
    private Button btnConfirm;
    private Button btnCancel;
    private boolean isUserChanged = false;
    private String confirmText = "ارسال";

    public interface DialogGetSmsTextListener {
        void sendindSms(String smsText);
    }

    DialogGetSmsTextListener dialogGetSmsTextListener;

    public DialogGetSmsText(Context context, DialogGetSmsTextListener dialogGetSmsTextListener) {
        this.context = context;
        this.dialogGetSmsTextListener = dialogGetSmsTextListener;
        dialog = new Dialog(context);

    }

    public DialogGetSmsText(Context context, DialogGetSmsTextListener dialogGetSmsTextListener, String confirmText) {
        this.confirmText = confirmText;
        this.context = context;
        this.dialogGetSmsTextListener = dialogGetSmsTextListener;
        dialog = new Dialog(context);
    }

    public void showDialog() {
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = li.inflate(R.layout.dialog_get_sms_text, null, true);
        list = (RecyclerView) view.findViewById(R.id.RVReadySmsList);
        btnAdd = (TextView) view.findViewById(R.id.btnAddReadySmsText);
        txtMessage = (TextView) view.findViewById(R.id.txtDialogGetSmsText);
        btnConfirm = (Button) view.findViewById(R.id.btnConfirmDialogGetSmsText);
        btnConfirm.setText(confirmText);
        btnCancel = (Button) view.findViewById(R.id.btnCancelDialogGetSmsText);
        btnCancel.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        btnConfirm.setOnClickListener(this);
        settingUpList();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(view);
        dialog.show();
    }

    private void settingUpList() {
        getData();
        adapter = new AdapterReadySmsList(context, source, this);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        list.setLayoutManager(manager);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void getData() {
        source.clear();
        source.addAll(LocalDatabase.getSmsText(context));
    }

    public void closeDialog() {
        dialog.cancel();
    }

    @Override
    public void readySmsListClick(String text, int position) {
        txtMessage.setText(text);
    }

    @Override
    public void readySmsListRemoveClick(String text, int position) {
        LocalDatabase.removeSmsText(context, text);
        source.remove(position);
        adapter.notifyItemRemoved(position);
        adapter.notifyItemRangeChanged(position, adapter.getItemCount());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddReadySmsText:
                getTextMessage();
                break;

            case R.id.btnConfirmDialogGetSmsText:
                if (TextUtils.isEmpty(txtMessage.getText().toString().trim()))
                    txtMessage.setError("لطفا متن پیام خود را وارد کنید.");
                else {
                    dialogGetSmsTextListener.sendindSms(txtMessage.getText().toString());
                    closeDialog();
                }
                break;

            case R.id.btnCancelDialogGetSmsText:
                closeDialog();
                break;
        }
    }

    private void getTextMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("افزودن پیام آماده");
        final EditText editText = new EditText(context);
        editText.setPadding(60, 60, 60, 60);
        editText.setGravity(Gravity.RIGHT);
        editText.setHint("متن پیام خود را وارد کنید");
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isUserChanged) {
                    isUserChanged = false;
                    // txtSharayet.setTextKeepState();
                    editText.setTextKeepState(CharCheck.faCheck(editText.getText().toString()));

                } else
                    isUserChanged = true;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        builder.setView(editText);
        builder.setPositiveButton("انصراف", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });
        builder.setNegativeButton("ثبت", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                addSmsText(editText.getText().toString());
                dialog.cancel();
            }
        });
        builder.show();
    }

    private void addSmsText(String smsText) {
        LocalDatabase.addSmsText(context, smsText);
        source.add(smsText);
        adapter.notifyDataSetChanged();
    }
}
