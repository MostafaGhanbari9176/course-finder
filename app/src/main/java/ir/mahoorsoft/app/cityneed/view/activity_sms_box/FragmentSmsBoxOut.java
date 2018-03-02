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
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.preferences.Pref;
import ir.mahoorsoft.app.cityneed.model.struct.PrefKey;
import ir.mahoorsoft.app.cityneed.model.struct.StSmsBox;
import ir.mahoorsoft.app.cityneed.presenter.PresenterSmsBox;
import ir.mahoorsoft.app.cityneed.view.adapter.AdapterSmsListIn;
import ir.mahoorsoft.app.cityneed.view.adapter.AdapterSmsListOut;
import ir.mahoorsoft.app.cityneed.view.dialog.DialogProgres;

/**
 * Created by M-gh on 27-Feb-18.
 */

public class FragmentSmsBoxOut extends Fragment implements PresenterSmsBox.OnPresentSmsBoxListener, AdapterSmsListOut.OnClickItemSmsList {
    int deletedMessagePotision;
    View view;
    DialogProgres dialogProgres;
    RecyclerView list;
    AdapterSmsListOut adapter;
    ArrayList<StSmsBox> source = new ArrayList<>();
    LinearLayout llEmpty;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_out_sms_box, container, false);
        init();
        return view;
    }

    private void init() {
        dialogProgres = new DialogProgres(G.context);
        list = (RecyclerView) view.findViewById(R.id.RVSmsOut);
        llEmpty = (LinearLayout) view.findViewById(R.id.llTxtEmptyListSmsOut);
        getData();
    }

    private void getData() {
        dialogProgres.showProgresBar();
        PresenterSmsBox presenterSmsBox = new PresenterSmsBox(this);
        presenterSmsBox.getTsSms(Pref.getStringValue(PrefKey.apiCode, ""));
    }

    @Override
    public void onResiveSms(ArrayList<StSmsBox> sms) {
        dialogProgres.closeProgresBar();
        if(sms.get(0).empty == 1){
            llEmpty.setVisibility(View.VISIBLE);
            return;
        }

        source.clear();
        source.addAll(sms);
        adapter = new AdapterSmsListOut(G.context, source, this);
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
        if (!flag)
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
        showMessage(position);
    }

    @Override
    public void deleteMessage(int position) {
        deletedMessagePotision = position;
        answerForDelete();
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
        builder.show();
    }



}
