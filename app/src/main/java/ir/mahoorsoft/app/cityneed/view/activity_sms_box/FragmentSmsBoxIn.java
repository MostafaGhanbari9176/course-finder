package ir.mahoorsoft.app.cityneed.view.activity_sms_box;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.preferences.Pref;
import ir.mahoorsoft.app.cityneed.model.struct.PrefKey;
import ir.mahoorsoft.app.cityneed.model.struct.StSmsBox;
import ir.mahoorsoft.app.cityneed.presenter.PresentReport;
import ir.mahoorsoft.app.cityneed.presenter.PresenterSmsBox;
import ir.mahoorsoft.app.cityneed.view.CharCheck;
import ir.mahoorsoft.app.cityneed.view.adapter.AdapterSmsListIn;
import ir.mahoorsoft.app.cityneed.view.dialog.DialogProgres;

/**
 * Created by M-gh on 27-Feb-18.
 */

public class FragmentSmsBoxIn extends Fragment implements PresenterSmsBox.OnPresentSmsBoxListener, AdapterSmsListIn.OnClickItemSmsList, PresentReport.OnPresentReportListener {
    boolean isUserChanged = true;
    int deletedMessagePotision;
    View view;
    DialogProgres dialogProgres;
    RecyclerView list;
    AdapterSmsListIn adapter;
    ArrayList<StSmsBox> source = new ArrayList<>();
    TextView txtEmpty;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_in_sms_box, container, false);
        init();
        return view;
    }

    private void init() {
        dialogProgres = new DialogProgres(G.context);
        txtEmpty = (TextView) view.findViewById(R.id.txtEmptyInSms);
        list = (RecyclerView) view.findViewById(R.id.RVSmsIn);

        getData();
    }

    private void getData() {

        dialogProgres.showProgresBar();
        PresenterSmsBox presenterSmsBox = new PresenterSmsBox(this);
        presenterSmsBox.getRsSms(Pref.getStringValue(PrefKey.apiCode, ""));
    }

    @Override
    public void onResiveSms(ArrayList<StSmsBox> sms) {
        dialogProgres.closeProgresBar();
        if (sms.get(0).empty == 1) {
            txtEmpty.setVisibility(View.VISIBLE);
            return;
        }


        source.clear();
        source.addAll(sms);
        adapter = new AdapterSmsListIn(G.context, source, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(G.context
                , LinearLayoutManager.VERTICAL, false);
        list.setLayoutManager(layoutManager);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onResiveFlagFromSmsBox(boolean flag) {

        dialogProgres.closeProgresBar();
        if (!flag)
            messageFromSmsBox("خطا!!");
    }

    @Override
    public void smsDeleteFlag(boolean flag) {
        dialogProgres.closeProgresBar();
        if (!flag)
            return;
        source.remove(deletedMessagePotision);
        adapter.notifyItemRemoved(deletedMessagePotision);
        adapter.notifyItemRangeChanged(deletedMessagePotision, adapter.getItemCount());
    }

    @Override
    public void sendingMessageFlag(boolean flag) {
        dialogProgres.closeProgresBar();
        if (flag)
            messageFromSmsBox("پیام ارسال شد");
        else
            messageFromSmsBox("پیام ارسال نشد");
    }

    @Override
    public void messageFromSmsBox(String message) {
        dialogProgres.closeProgresBar();
        Toast.makeText(G.context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void seenMessage(int position) {
        upDateSeen(position);
        showMessage(position);
    }

    @Override
    public void deleteMessage(int position) {
        deletedMessagePotision = position;
        answerForDelete();
    }

    @Override
    public void reportSms(int position) {
        getReportData(position);
    }

    private void getReportData(final int position) {
        final Dialog dialog = new Dialog(G.context);
        LayoutInflater li = (LayoutInflater) G.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = li.inflate(R.layout.dialog_report, null, false);
        final TextView textView = (TextView) view.findViewById(R.id.txtDialogReport);
        Button btnConfirm = (Button) view.findViewById(R.id.btnConfirmDialogReport);
        Button btnCancel = (Button) view.findViewById(R.id.btnCancelDialogReport);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkReportData(textView)) {
                    sendReport("sms", textView.getText().toString(), source.get(position).id, source.get(position).tsId, Pref.getStringValue(PrefKey.phone, ""));
                    dialog.cancel();
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();
    }

    private boolean checkReportData(TextView txt) {
        if (TextUtils.isEmpty(txt.getText().toString().trim()))
            txt.setError("یه دلیل ثبت کنید");
        else
            return true;
        return false;
    }

    private void sendReport(String signText, String reportText, int spamId, String spamerId, String reporterId) {
        dialogProgres.showProgresBar();
        PresentReport presentReport = new PresentReport(this);
        presentReport.report(signText, reportText, spamId, spamerId, reporterId);
    }

    @Override
    public void flagFromReport(boolean flag) {
        dialogProgres.closeProgresBar();
        if (flag)
            messageFromReport("ارسال شد,بابت فیدبک شما متشکریم");
        else
            messageFromReport("خطا");
    }

    @Override
    public void messageFromReport(String message) {
        dialogProgres.closeProgresBar();
        Toast.makeText(G.context, message, Toast.LENGTH_SHORT).show();
    }

    private void upDateSeen(int position) {
        if (source.get(position).seen == 0) {
            PresenterSmsBox presenterSmsBox = new PresenterSmsBox(this);
            presenterSmsBox.upDateSeen(source.get(position).id);
        }
    }

    private void answerForDelete() {
        AlertDialog.Builder builder = new AlertDialog.Builder(G.context);
        builder.setTitle("حذف پیام");
        builder.setMessage("مطمعن؟؟");
        builder.setPositiveButton("خیر", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setNegativeButton("بله", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                queryForDelete();
                dialog.cancel();
            }
        });
        builder.show();
    }

    private void queryForDelete() {
        dialogProgres.showProgresBar();
        PresenterSmsBox presenterSmsBox = new PresenterSmsBox(this);
        presenterSmsBox.deleteMessage(source.get(deletedMessagePotision).id);
    }

    private void showMessage(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(G.context);
        builder.setTitle("متن پیام");
        builder.setMessage(source.get(position).text);
        builder.setPositiveButton("خب", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setNegativeButton("پاسخ دادن", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getTextMessage(position);
                dialog.cancel();
            }
        });
        builder.show();
    }

    private void getTextMessage(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(G.context);
        builder.setTitle("ارسالی به " + source.get(position).tsName);
        final EditText editText = new EditText(G.context);
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
        builder.setNegativeButton("ارسال", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sendMessage(position, editText.getText().toString());
                dialog.cancel();
            }
        });
        builder.show();
    }

    private void sendMessage(int position, String text) {
        dialogProgres.showProgresBar();
        int type;
        if (Pref.getIntegerValue(PrefKey.userTypeMode, 0) == 1 || Pref.getIntegerValue(PrefKey.userTypeMode, 0) == 2)
            type = 1;
        else
            type = 0;
        PresenterSmsBox presenterSmsBox = new PresenterSmsBox(this);
        presenterSmsBox.saveSms(text, Pref.getStringValue(PrefKey.apiCode, ""), source.get(position).tsId, source.get(position).courseId, type);
    }
}
