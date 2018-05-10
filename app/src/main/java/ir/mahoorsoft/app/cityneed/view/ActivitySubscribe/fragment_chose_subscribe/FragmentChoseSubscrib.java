package ir.mahoorsoft.app.cityneed.view.ActivitySubscribe.fragment_chose_subscribe;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.struct.StBuy;
import ir.mahoorsoft.app.cityneed.model.struct.StSubscribe;
import ir.mahoorsoft.app.cityneed.presenter.PresentSubscribe;

/**
 * Created by RCC1 on 5/7/2018.
 */

public class FragmentChoseSubscrib extends Fragment implements PresentSubscribe.OnPresentSubscribeListener, AdapterChoseSubscribe.SubscribeClick{

    View view;
    ArrayList<StSubscribe> source = new ArrayList<>();
    RecyclerView list;
    AdapterChoseSubscribe adapter;
    RelativeLayout pbar;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_chose_subscribe, container, false);
        init();
        return view;
    }

    private void init(){
        pointers();
        queryForSubscribes();
    }

    private void pointers(){
        pbar = (RelativeLayout) view.findViewById(R.id.RLPbarSubscribeList);
        list = (RecyclerView) view.findViewById(R.id.RVSubscribeList);
    }

    private void queryForSubscribes(){
        (new PresentSubscribe(this)).getSubscribeList();
    }

    @Override
    public void onResiveSubscribeList(ArrayList<StSubscribe> data) {
        pbar.setVisibility(View.GONE);
        source.clear();
        source.addAll(data);
        adapter = new AdapterChoseSubscribe(G.context, source, this);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(G.context, LinearLayoutManager.VERTICAL, false);
        list.setLayoutManager(manager);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void sendMessageFromSubscribe(String message) {
        pbar.setVisibility(View.GONE);
        Toast.makeText(G.context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onReceiveUserBuy(ArrayList<StBuy> data) {

    }

    @Override
    public void subscribeItemClick(int position) {

    }
}
