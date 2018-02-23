package ir.mahoorsoft.app.cityneed.view.activity_main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Stack;

import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.struct.StCourse;
import ir.mahoorsoft.app.cityneed.model.struct.StTabaghe;
import ir.mahoorsoft.app.cityneed.presenter.PresentCourse;
import ir.mahoorsoft.app.cityneed.presenter.PresentTabaghe;
import ir.mahoorsoft.app.cityneed.view.adapter.AdapterCourseList;
import ir.mahoorsoft.app.cityneed.view.adapter.AdapterTabagheList;
import ir.mahoorsoft.app.cityneed.view.dialog.DialogProgres;

/**
 * Created by RCC1 on 1/30/2018.
 */

public class ActivityTabagheList extends AppCompatActivity implements AdapterTabagheList.OnClickItemTabagheList, PresentTabaghe.OnPresentTabagheListener {
    Stack<Integer> id = new Stack<>();
    int oldId = -1;
    int newId = -1;
    AdapterTabagheList adapter;
    RecyclerView list;
    ArrayList<StTabaghe> surce;
    DialogProgres dialogProgres;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        dialogProgres = new DialogProgres(this);
        surce = new ArrayList<>();
        list = (RecyclerView) findViewById(R.id.RVList);
        adapter = new AdapterTabagheList(this, surce, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this
                , LinearLayoutManager.VERTICAL, false);
        list.setLayoutManager(layoutManager);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        setSource(-1);
    }

    private void setSource(int id) {

        newId = id;
        dialogProgres.showProgresBar();
        PresentTabaghe presentTabaghe = new PresentTabaghe(this);
        presentTabaghe.getTabaghe(id);
    }



    @Override
    public void tabagheNahaei() {
        dialogProgres.closeProgresBar();
        Intent intent = getIntent();
        intent.putExtra("id", newId);

       for (int i = 0;i<surce.size();i++){
           if(surce.get(i).id == newId)
               intent.putExtra("name",surce.get(i).subject);

       }
        setResult(RESULT_OK, intent);
        this.finish();
        //sendMessageFTabagheT(newId + "");
    }

    @Override
    public void sendMessageFTabagheT(String message) {
        dialogProgres.closeProgresBar();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {


        if (id.size() > 1)

        {
            int x = id.pop();
            oldId = id.pop();
            setSource(x);
        } else

        {
            super.onBackPressed();
            this.finish();
        }
    }

    @Override
    public void onResiveTabaghe(ArrayList<StTabaghe> data) {
        this.id.push(oldId);
        oldId = newId;
        surce = data;
        dialogProgres.closeProgresBar();
        adapter = new AdapterTabagheList(this, data, this);
        list = (RecyclerView) findViewById(R.id.RVList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this
                , LinearLayoutManager.VERTICAL, false);
        list.setLayoutManager(layoutManager);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void tabagheListItemClick(int position, int sourceNumber) {
        setSource(surce.get(position).id);
    }
}
/*
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
*/

