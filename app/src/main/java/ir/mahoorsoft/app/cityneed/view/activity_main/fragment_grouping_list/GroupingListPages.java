package ir.mahoorsoft.app.cityneed.view.activity_main.fragment_grouping_list;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.model.struct.StGrouping;

/**
 * Created by M-gh on 12-Apr-18.
 */

public class GroupingListPages {


    public GroupingListPages(ArrayList<StGrouping> page, int pageId, int pageGroupId, String pageName) {
        this.page = page;
        this.pageId = pageId;
        this.pageGroupId = pageGroupId;
        this.pageName = pageName;
    }
    public ArrayList<StGrouping> page;
    public int pageId;
    public int pageGroupId;
    public String pageName;
}
