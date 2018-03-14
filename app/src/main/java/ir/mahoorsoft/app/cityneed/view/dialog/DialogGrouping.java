package ir.mahoorsoft.app.cityneed.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Stack;

import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.struct.StGrouping;
import ir.mahoorsoft.app.cityneed.presenter.PresentGrouping;
import ir.mahoorsoft.app.cityneed.view.adapter.AdapterGroupingListDialog;

/**
 * Created by M_gh on 12/10/2017.
 */

public class DialogGrouping implements PresentGrouping.OnPresentTabagheListener, AdapterGroupingListDialog.OnClickItemTabagheList, View.OnClickListener {
    private Stack<StGrouping> idSaver = new Stack<>();
    private Context context;
    private OnTabagheItemClick onTabagheItemClick;
    private View view;
    private Dialog dialog;
    private RecyclerView list;
    private RecyclerView list2;
    private ArrayList<StGrouping> source = new ArrayList<>();
    private ArrayList<StGrouping> source2 = new ArrayList<>();
    private AdapterGroupingListDialog adapter = new AdapterGroupingListDialog(context, source, this);
    private AdapterGroupingListDialog adapter2 = new AdapterGroupingListDialog(context, source2, this);
    private Button btnBack;
    private Button btnSelectHere;
    private Button btnEnter;
    private ProgressBar pb;
    private TextView txtToolBar;
    private int position;
    private int sourceNumber;

    public DialogGrouping(Context context, OnTabagheItemClick onTabagheItemClick) {
        this.context = context;
        this.onTabagheItemClick = onTabagheItemClick;
        dialog = new Dialog(context);
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = li.inflate(R.layout.dialog_tabaghe_list, null, false);
    }

    public void Show() {

        setingUpList();
        setingUpList2();
        btnBack = (Button) view.findViewById(R.id.btnBackDialogTabaghe);
        btnEnter = (Button) view.findViewById(R.id.btnEnter);
        btnSelectHere = (Button) view.findViewById(R.id.btnChoseHere);
        btnEnter.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btnSelectHere.setOnClickListener(this);
        pb = (ProgressBar) view.findViewById(R.id.pbTabaghe);
        txtToolBar = (TextView) view.findViewById(R.id.txtToolbarTabaghe);
        pb.setVisibility(View.GONE);
        txtToolBar.setVisibility(View.VISIBLE);
        setSource(-1);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

    }

    private void backHandle() {
        btnEnter.setVisibility(View.GONE);
        if (idSaver.size() == 0)
            dialog.cancel();
        else if (idSaver.size() == 1) {
            idSaver.pop();
            setSource(-1);
            btnSelectHere.setVisibility(View.GONE);
        } else {
            idSaver.pop();
            StGrouping t = idSaver.peek();
            btnSelectHere.setText("انتخاب : " + t.subject);
            setSource(t.id);
        }
    }

    private void setSource(int id) {
        txtToolBar.setText("دسته بندی خود را انتخاب کنید");
        showProgresBar();
        PresentGrouping presentGrouping = new PresentGrouping(this);
        presentGrouping.getTabaghe(id);
    }

    private void setingUpList() {
        list = (RecyclerView) view.findViewById(R.id.RVDialogTanaghe);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(context);
        list.setLayoutManager(manager);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void setingUpList2() {
        list2 = (RecyclerView) view.findViewById(R.id.RVDialogTanaghe2);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(context);
        list2.setLayoutManager(manager);
        list2.setAdapter(adapter2);
        adapter2.notifyDataSetChanged();
    }

    @Override
    public void onResiveTabaghe(ArrayList<StGrouping> data) {
        closeProgresBar();
        source.clear();
        source2.clear();
        for (int i = 0; i < data.size() / 2; i++) {
            StGrouping t = new StGrouping(2, data.get(i).id, data.get(i).uperId, data.get(i).subject);
            source2.add(t);
        }

        for (int i = data.size() / 2; i < data.size(); i++) {
            StGrouping t = new StGrouping(1, data.get(i).id, data.get(i).uperId, data.get(i).subject);
            source.add(t);
        }
        adapter = new AdapterGroupingListDialog(context, source, this);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        adapter2 = new AdapterGroupingListDialog(context, source2, this);
        list2.setAdapter(adapter2);
        adapter2.notifyDataSetChanged();

        dialog.show();
    }

    @Override
    public void tabagheNahaei() {
        closeProgresBar();
        idSaver.pop();
        txtToolBar.setText("زیر دسته ایی موجود نیست");
    }

    @Override
    public void sendMessageFTabagheT(String message) {
        dialog.cancel();
        closeProgresBar();
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void tabagheListItemClick(int position, int sourceNumber, int groupId) {
        btnEnter.setVisibility(View.VISIBLE);
        btnSelectHere.setVisibility(View.VISIBLE);
        this.position = position;
        this.sourceNumber = sourceNumber;
        if (sourceNumber == 1) {
            adapter2.setSelectedCardView(null);
            btnSelectHere.setText("انتخاب : " + source.get(position).subject);
            btnEnter.setText("ورود به : " + source.get(position).subject);
        } else {
            adapter.setSelectedCardView(null);
            btnSelectHere.setText("انتخاب : " + source2.get(position).subject);
            btnEnter.setText("ورود به : " + source2.get(position).subject);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnEnter:
                enterHandle();
                break;
            case R.id.btnChoseHere:
                choseHandle();
                break;
            case R.id.btnBackDialogTabaghe:
                backHandle();
                break;
        }
    }

    private void enterHandle() {
        btnEnter.setVisibility(View.GONE);
        StGrouping t;
        if (sourceNumber == 1) {
            btnSelectHere.setText("انتخاب : " + source.get(position).subject);
            t = new StGrouping(0, source.get(position).id, source.get(position).uperId, source.get(position).subject);
        } else {
            btnSelectHere.setText("انتخاب : " + source2.get(position).subject);
            t = new StGrouping(0, source2.get(position).id, source2.get(position).uperId, source2.get(position).subject);
        }
        idSaver.add(t);
        setSource(sourceNumber == 1 ? source.get(position).id : source2.get(position).id);
    }

    private void choseHandle() {
        if (sourceNumber == 1) {
            onTabagheItemClick.tabagheInf(source.get(position).subject, source.get(position).id);
        } else {
            onTabagheItemClick.tabagheInf(source2.get(position).subject, source2.get(position).id);
        }
        dialog.cancel();
    }

    public interface OnTabagheItemClick {
        void tabagheInf(String name, int id);
    }

    private void showProgresBar() {
        pb.setVisibility(View.VISIBLE);
        txtToolBar.setVisibility(View.GONE);
    }

    private void closeProgresBar() {
        pb.setVisibility(View.GONE);
        txtToolBar.setVisibility(View.VISIBLE);
    }
}
