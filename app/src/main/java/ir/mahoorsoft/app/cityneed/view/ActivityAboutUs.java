package ir.mahoorsoft.app.cityneed.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.struct.StMahoorAppData;
import ir.mahoorsoft.app.cityneed.presenter.PresentMahoorAppData;
import ir.mahoorsoft.app.cityneed.view.adapter.AdapterMahoorAppDataItem;


/**
 * Created by M-gh on 16-Mar-18.
 */

public class ActivityAboutUs extends AppCompatActivity implements PresentMahoorAppData.OnPresentMahoorAppDataListener, AdapterMahoorAppDataItem.OnClickItemTabagheList {
    ProgressBar bar;
    TextView txtEmpty;
    private RecyclerView list;
    private RecyclerView list2;
    private ArrayList<StMahoorAppData> source = new ArrayList<>();
    private ArrayList<StMahoorAppData> source2 = new ArrayList<>();
    private AdapterMahoorAppDataItem adapter = new AdapterMahoorAppDataItem(this, source, this);
    private AdapterMahoorAppDataItem adapter2 = new AdapterMahoorAppDataItem(this, source2, this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        bar = (ProgressBar) findViewById(R.id.pbAboutUs);
        txtEmpty = (TextView) findViewById(R.id.txtEmptyAboutUs);
        getMahoorAppData();
    }

    private void getMahoorAppData() {
        PresentMahoorAppData presentMahoorAppData = new PresentMahoorAppData(this);
        presentMahoorAppData.getMahoorAppData();
        setingUpList();
        setingUpList2();
    }

    private void setingUpList() {
        list = (RecyclerView) findViewById(R.id.RVoneAboutUs);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        list.setLayoutManager(manager);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void setingUpList2() {
        list2 = (RecyclerView) findViewById(R.id.RVtwoAboutUs);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        list2.setLayoutManager(manager);
        list2.setAdapter(adapter2);
        adapter2.notifyDataSetChanged();
    }

    @Override
    public void resieveAppData(ArrayList<StMahoorAppData> data) {
        if (data.get(0).empty == 1) {
            bar.setVisibility(View.GONE);
            txtEmpty.setVisibility(View.VISIBLE);
        } else {
            bar.setVisibility(View.GONE);
            txtEmpty.setVisibility(View.GONE);
            source.clear();
            source2.clear();
            for (int i = 0; i < data.size() / 2; i++) {
                StMahoorAppData t = new StMahoorAppData(data.get(i).appName, data.get(i).url, data.get(i).pictureId, 2);
                source2.add(t);
            }

            for (int i = data.size() / 2; i < data.size(); i++) {
                StMahoorAppData t = new StMahoorAppData(data.get(i).appName, data.get(i).url, data.get(i).pictureId, 1);
                source.add(t);
            }
            adapter = new AdapterMahoorAppDataItem(this, source, this);
            list.setAdapter(adapter);
            adapter.notifyDataSetChanged();

            adapter2 = new AdapterMahoorAppDataItem(this, source2, this);
            list2.setAdapter(adapter2);
            adapter2.notifyDataSetChanged();
        }
    }

    @Override
    public void messageFromAppData(String message) {
        bar.setVisibility(View.GONE);
        txtEmpty.setVisibility(View.VISIBLE);
    }

    @Override
    public void tabagheListItemClick(int position, int sourceNumber, String url) {
        String targetUrl;
        if (sourceNumber == 1)
            targetUrl = source.get(position).url;
        else
            targetUrl = source2.get(position).url;
        try {

            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(targetUrl));
            startActivity(i);
        } catch (Exception e) {
            Toast.makeText(ActivityAboutUs.this, "خطا!", Toast.LENGTH_SHORT).show();

        }
    }
}
