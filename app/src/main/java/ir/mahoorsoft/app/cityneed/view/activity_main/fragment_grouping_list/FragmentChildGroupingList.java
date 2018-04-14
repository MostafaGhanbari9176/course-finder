package ir.mahoorsoft.app.cityneed.view.activity_main.fragment_grouping_list;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.struct.StGrouping;
import ir.mahoorsoft.app.cityneed.model.tables.grouping.Grouping;
import ir.mahoorsoft.app.cityneed.presenter.PresentGrouping;
import ir.mahoorsoft.app.cityneed.view.activity_sms_box.FragmentSmsBoxIn;
import ir.mahoorsoft.app.cityneed.view.activity_sms_box.FragmentSmsBoxOut;
import ir.mahoorsoft.app.cityneed.view.adapter.AdapterGroupingListName;
import ir.mahoorsoft.app.cityneed.view.adapter.AdapterViewPager;
import ir.mahoorsoft.app.cityneed.view.dialog.DialogProgres;

/**
 * Created by M-gh on 08-Apr-18.
 */

public class FragmentChildGroupingList extends Fragment implements AdapterGroupingListName.OnClickItemTabagheList, PresentGrouping.OnPresentTabagheListener {
    public interface EditePages {
        void addPage(int id, String name);

        void updatePage();
    }

    EditePages editePages;
    View view;
    RecyclerView list;
    AdapterGroupingListName adapter;
    ArrayList<StGrouping> source = new ArrayList<>();
    DialogProgres dialogProgres;
    int pageId;
    int pageGroupId;
    String pageName = "";
    static boolean update = false;

/*    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            editePages = (EditePages) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement MyInterface ");
        }
    }*/

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_child_grouping_list, container, false);
        pointers();
        return view;
    }

    private void pointers() {

        list = (RecyclerView) view.findViewById(R.id.RVFragmentGroupingList);
    }

    public void queryForGroupList(int pageId, String pageName, int pageGroupId) {
        this.pageId = pageId;
        this.pageName = pageName;
        this.pageGroupId = pageGroupId;
        if (dialogProgres == null)
            dialogProgres = new DialogProgres(G.context);
        dialogProgres.showProgresBar();
        PresentGrouping presentGrouping = new PresentGrouping(this);
        presentGrouping.getTabaghe(pageGroupId);
    }

    @Override
    public void tabagheListItemClick(int position, int groupId) {

        int currentPage = FragmentGroupingList.viewPager.getCurrentItem();
        for (int i = 0; i < FragmentGroupingList.pages.size(); i++) {

            if (FragmentGroupingList.pages.get(i).pageId > currentPage) {
                FragmentGroupingList.pages.remove(i);
                i--;
                FragmentGroupingList.itemCounter--;
                update = true;
            }
        }
        editePages.addPage(groupId, source.get(position).subject);
    }

    public void setSource(ArrayList<StGrouping> source) {
        this.source.addAll(source);
        adapter = new AdapterGroupingListName(G.context, this.source, this);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(G.context, LinearLayoutManager.VERTICAL, false);
        if (list == null)
            return;
        list.setLayoutManager(manager);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onResiveTabaghe(ArrayList<StGrouping> data) {
        if (dialogProgres != null)
            dialogProgres.closeProgresBar();
        source.clear();
        source.addAll(data);
        GroupingListPages page = new GroupingListPages(data, pageId, pageGroupId, pageName);
        FragmentGroupingList.pages.add(page);
        adapter = new AdapterGroupingListName(G.context, source, this);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(G.context, LinearLayoutManager.VERTICAL, false);
        if (list == null)
            return;
        list.setLayoutManager(manager);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        if (update) {
            editePages.updatePage();
            update = false;
        }
    }

    @Override
    public void tabagheNahaei() {
        dialogProgres.closeProgresBar();
        Toast.makeText(G.context, "ok", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void sendMessageFTabagheT(String message) {
        dialogProgres.closeProgresBar();
        Toast.makeText(G.context, message, Toast.LENGTH_SHORT).show();
    }


}
