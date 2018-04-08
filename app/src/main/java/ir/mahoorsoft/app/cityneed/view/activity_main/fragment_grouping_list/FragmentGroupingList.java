package ir.mahoorsoft.app.cityneed.view.activity_main.fragment_grouping_list;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;

/**
 * Created by M-gh on 08-Apr-18.
 */

public class FragmentGroupingList extends Fragment {

    View view;
    Spinner spin;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_grouping_list, container, false);
        inite();
        return view;
    }
    private void inite(){
        spin = (Spinner) view.findViewById(R.id.spin);
        String[] arraySpinner = new String[] {
                "1", "2", "3", "4", "5"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(G.context,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(R.layout.fragment_grouping_list);
        spin.setAdapter(adapter);
    }
}
