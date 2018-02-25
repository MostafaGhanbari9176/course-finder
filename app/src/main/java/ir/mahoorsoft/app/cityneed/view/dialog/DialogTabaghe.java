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
import ir.mahoorsoft.app.cityneed.view.adapter.AdapterTabagheList;

/**
 * Created by M_gh on 12/10/2017.
 */

public class DialogTabaghe implements PresentGrouping.OnPresentTabagheListener, AdapterTabagheList.OnClickItemTabagheList {
    private Stack<StGrouping> idSaver = new Stack<>();
    private Context context;
    private OnTabagheItemClick onTabagheItemClick;
    private View view;
    private Dialog dialog;
    private RecyclerView list;
    private RecyclerView list2;
    private ArrayList<StGrouping> source = new ArrayList<>();
    private ArrayList<StGrouping> source2 = new ArrayList<>();
    private AdapterTabagheList adapter = new AdapterTabagheList(context, source, this);
    private AdapterTabagheList adapter2 = new AdapterTabagheList(context, source2, this);
    private Button btn;
    private ProgressBar pb;
    private TextView txt;

    public DialogTabaghe(Context context, OnTabagheItemClick onTabagheItemClick) {
        this.context = context;
        this.onTabagheItemClick = onTabagheItemClick;
        dialog = new Dialog(context);
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = li.inflate(R.layout.dialog_tabaghe_list, null, false);
    }

    public void Show() {

        setingUpList();
        setingUpList2();
        btn = (Button) view.findViewById(R.id.btnBackDialogTabaghe);
        pb = (ProgressBar) view.findViewById(R.id.pbTabaghe);
        txt = (TextView) view.findViewById(R.id.txtToolbarTabaghe);
        pb.setVisibility(View.GONE);
        txt.setVisibility(View.VISIBLE);
        setSource(-1);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backHandle();
            }
        });
        dialog.setContentView(view);
//        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

    }

    private void backHandle() {
        if (idSaver.size() == 0)
            dialog.cancel();
        else if (idSaver.size() == 1) {
            idSaver.pop();
            setSource(-1);
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

        adapter = new AdapterTabagheList(context, source, this);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        adapter2 = new AdapterTabagheList(context, source2, this);
        list2.setAdapter(adapter2);
        adapter2.notifyDataSetChanged();

        dialog.show();
    }

    @Override
    public void tabagheNahaei() {
        closeProgresBar();
        dialog.cancel();
        StGrouping t = idSaver.pop();
        onTabagheItemClick.tabagheInf(t.subject, t.id);
    }

    @Override
    public void sendMessageFTabagheT(String message) {
        dialog.cancel();
        closeProgresBar();
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void tabagheListItemClick(int position, int sourceNumber, int groupId) {
        if (sourceNumber == 1) {
            StGrouping t = new StGrouping(0, source.get(position).id, source.get(position).uperId, source.get(position).subject);
            idSaver.add(t);
        } else {
            StGrouping t = new StGrouping(0, source2.get(position).id, source2.get(position).uperId, source2.get(position).subject);
            idSaver.add(t);
        }
        setSource(sourceNumber == 1 ? source.get(position).id : source2.get(position).id);
    }

    public interface OnTabagheItemClick {
        void tabagheInf(String name, int id);
    }

    private void showProgresBar() {
        pb.setVisibility(View.VISIBLE);
        txt.setVisibility(View.GONE);
    }

    private void closeProgresBar() {
        pb.setVisibility(View.GONE);
        txt.setVisibility(View.VISIBLE);
    }
}
