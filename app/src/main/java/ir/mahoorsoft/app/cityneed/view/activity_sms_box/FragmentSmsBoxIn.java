package ir.mahoorsoft.app.cityneed.view.activity_sms_box;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.preferences.Pref;
import ir.mahoorsoft.app.cityneed.model.struct.PrefKey;
import ir.mahoorsoft.app.cityneed.model.struct.ResponseOfServer;
import ir.mahoorsoft.app.cityneed.model.struct.StSmsBox;
import ir.mahoorsoft.app.cityneed.presenter.PresenterSmsBox;
import ir.mahoorsoft.app.cityneed.view.adapter.AdapterSmsListIn;
import ir.mahoorsoft.app.cityneed.view.adapter.AdapterTabagheList;
import ir.mahoorsoft.app.cityneed.view.dialog.DialogProgres;

/**
 * Created by M-gh on 27-Feb-18.
 */

public class FragmentSmsBoxIn extends Fragment implements PresenterSmsBox.OnPresentSmsBoxListener, AdapterSmsListIn.OnClickItemSmsList {
    int deletedMessagePotision;
    View view;
    DialogProgres dialogProgres;
    RecyclerView list;
    AdapterSmsListIn adapter;
    ArrayList<StSmsBox> source = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_in_sms_box, container, false);
        init();
        return view;
    }

    private void init() {
        dialogProgres = new DialogProgres(G.context);
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
        if(!flag)
            messageFromSmsBox("خطا!!");
    }

    @Override
    public void smsDeleteFlag(boolean flag) {
        dialogProgres.closeProgresBar();
        if(!flag)
            return;
        source.remove(deletedMessagePotision);
        adapter.notifyItemRemoved(deletedMessagePotision);
        adapter.notifyItemRangeChanged(deletedMessagePotision, adapter.getItemCount());
    }

    @Override
    public void smsUpdateSeen(boolean flag) {
        if (flag)
            messageFromSmsBox("عملیات موفق");
        else
            messageFromSmsBox("عملیات نا موفق");
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
    public void sendSms(int position) {

        getTextMessage(position);
    }

    private void upDateSeen(int position) {
        dialogProgres.showProgresBar();
        PresenterSmsBox presenterSmsBox = new PresenterSmsBox(this);
        presenterSmsBox.upDateSeen(position);
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

    private void queryForDelete( ) {
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
        editText.setHint("متن پیام خود را وارد کنید");
        builder.setView(editText);
        builder.setPositiveButton("خب", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });
        builder.setNegativeButton("پاسخ دادن", new DialogInterface.OnClickListener() {
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
        presenterSmsBox.saveSms(text, source.get(position).tsId, source.get(position).rsId, source.get(position).courseId, type);
    }
}
