package ir.mahoorsoft.app.cityneed.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
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
    private ArrayList<StGrouping> source = new ArrayList<>();
    private AdapterGroupingListDialog adapter = new AdapterGroupingListDialog(context, source, this);
    private Button btnBack;
    private Button btnSelectHere;
    private ProgressBar pb;
    private TextView txtToolBar;
    private int position;

    public DialogGrouping(Context context, OnTabagheItemClick onTabagheItemClick) {
        this.context = context;
        this.onTabagheItemClick = onTabagheItemClick;
        dialog = new Dialog(context);
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = li.inflate(R.layout.dialog_tabaghe_list, null, false);
    }

    public void Show() {
        btnBack = (Button) view.findViewById(R.id.btnBackDialogTabaghe);
        btnSelectHere = (Button) view.findViewById(R.id.btnChoseHere);
        list = (RecyclerView) view.findViewById(R.id.RVDialogGrouping);
        btnBack.setOnClickListener(this);
        btnSelectHere.setOnClickListener(this);
        pb = (ProgressBar) view.findViewById(R.id.pbTabaghe);
        txtToolBar = (TextView) view.findViewById(R.id.txtToolbarTabaghe);
        txtToolBar.setText("دسته بندی خود را انتخاب کنید");
        setSource(-1);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();
    }

    private void backHandle() {
        btnSelectHere.setVisibility(View.GONE);
        txtToolBar.setText("دسته بندی خود را انتخاب کنید");
        if (idSaver.size() == 0)
            dialog.cancel();
        else if (idSaver.size() == 1) {
            idSaver.pop();
            setSource(-1);
            btnSelectHere.setVisibility(View.GONE);
        } else {
            idSaver.pop();
            StGrouping t = idSaver.peek();
            setSource(t.id);
        }
    }

    private void setSource(int id) {
        showProgresBar();
        PresentGrouping presentGrouping = new PresentGrouping(this);
        presentGrouping.getTabaghe(id);
    }

    @Override
    public void onResiveTabaghe(ArrayList<StGrouping> data) {
        closeProgresBar();
        source.clear();
        source.addAll(data);
        adapter = new AdapterGroupingListDialog(context, source, this);
        RecyclerView.LayoutManager manager = new GridLayoutManager(context, 2, GridLayout.VERTICAL, false);
        list.setLayoutManager(manager);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void sendMessageFTabagheT(String message) {
        idSaver.pop();
        closeProgresBar();
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void tabagheListItemClick(int position) {
        if (source.get(position).isFinaly == 1) {
            txtToolBar.setText("آخرین زیر شاخه");
            btnSelectHere.setVisibility(View.VISIBLE);
            btnSelectHere.setText("انتخاب : " + source.get(position).subject);
        } else {
            setSource(source.get(position).id);
            idSaver.push(source.get(position));
        }
        this.position = position;
        adapter.setSelectedItem(null);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnChoseHere:
                choseHandle();
                break;
            case R.id.btnBackDialogTabaghe:
                backHandle();
                break;
        }
    }

    private void choseHandle() {
        onTabagheItemClick.tabagheInf(source.get(position).subject, source.get(position).id);
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
