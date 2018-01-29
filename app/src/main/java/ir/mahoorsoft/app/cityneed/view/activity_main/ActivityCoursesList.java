package ir.mahoorsoft.app.cityneed.view.activity_main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Stack;

import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.Items;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.presenter.PresentTabaghe;
import ir.mahoorsoft.app.cityneed.view.adapter.AdapterCourseList;
import ir.mahoorsoft.app.cityneed.view.dialog.DialogProgres;

/**
 * Created by RCC1 on 1/22/2018.
 */

public class ActivityCoursesList extends AppCompatActivity implements AdapterCourseList.OnClickItemCourseList, PresentTabaghe.OnPresentTabagheListener {
    ArrayList<Items> surce;
    AdapterCourseList adapter;
    RecyclerView list;
    String directoryName = "course";
    DialogProgres dialogProgres;
    Stack<Integer> id = new Stack<>();
    int oldId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses_list);

        dialogProgres = new DialogProgres(this);
        if (getIntent().getExtras() != null) {
            directoryName = getIntent().getStringExtra("dirName");
        }
        oldId = -1;
        setSource(-1);
    }

    private void setSource(int uperId) {
        dialogProgres.showProgresBar();
        if (directoryName.equalsIgnoreCase("course")) {
        } else {
            PresentTabaghe presentTabaghe = new PresentTabaghe(this);
            presentTabaghe.getTabaghe(uperId);
        }

    }

    @Override
    public void courseListItemClick(int position) {
        id.push(oldId);
        oldId = surce.get(position).id;
        setSource(surce.get(position).id);
    }

    @Override
    public void onResiveTabaghe(ArrayList<Items> data) {
        surce = data;
        dialogProgres.closeProgresBar();
        adapter = new AdapterCourseList(this, data, this, directoryName);
        list = (RecyclerView) findViewById(R.id.RVCourseList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(G.context
                , LinearLayoutManager.VERTICAL, false);
        list.setLayoutManager(layoutManager);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void tabagheNahaei() {
        if(id.size()!=0)
        id.pop();
        sendMessageFTabagheT(oldId + "");
    }

    @Override
    public void sendMessageFTabagheT(String message) {
        dialogProgres.closeProgresBar();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {

        if (id.size() == 0) {
            super.onBackPressed();
            this.finish();
        } else{
            setSource(oldId = id.pop());
        }
    }
}
