package ir.mahoorsoft.app.cityneed.view.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Stack;

import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.struct.StUser;

/**
 * Created by RCC1 on 1/30/2018.
 */

public class AdapterSdudentNameList extends RecyclerView.Adapter<AdapterSdudentNameList.Holder> {

    public interface OnClickItemSdutentNameList {
        void sendSms(int position);

        void deleteSabtenam(int position);
    }

    public static Stack<Integer> checkedUser = new Stack<>();

    private OnClickItemSdutentNameList onClickItemSdutentNameList;
    private Context context;
    private ArrayList<StUser> surce = new ArrayList<>();
    private boolean showCheckBox;


    public AdapterSdudentNameList(Context context, ArrayList<StUser> surce, OnClickItemSdutentNameList onClickItemSdutentNameList, boolean showCheckBox) {
        checkedUser.clear();
        this.context = context;
        this.surce = surce;
        this.onClickItemSdutentNameList = onClickItemSdutentNameList;
        this.showCheckBox = showCheckBox;
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView txtName;
        Button btnSendSms;
        Button btnDeleteSabtenam;
        CheckBox cbx;

        public Holder(View itemView) {
            super(itemView);
            cbx = (CheckBox) itemView.findViewById(R.id.cbxMoreSelect);
            txtName = (TextView) itemView.findViewById(R.id.txtItemStudentName);
            btnSendSms = (Button) itemView.findViewById(R.id.btnSendSmsStudentListName);
            btnDeleteSabtenam = (Button) itemView.findViewById(R.id.btnDeleteSabtenamStudentListName);
        }
    }

    @Override
    public AdapterSdudentNameList.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_student_name, parent, false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(AdapterSdudentNameList.Holder holder, final int position) {
        final StUser items = surce.get(position);
        holder.txtName.setText(items.name);
        if (showCheckBox)
            holder.cbx.setVisibility(View.VISIBLE);
        holder.cbx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    checkedUser.push(position);
                else {
                    int index = checkedUser.indexOf(position);
                    checkedUser.remove(index);
                }
            }
        });
        holder.btnSendSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickItemSdutentNameList.sendSms(position);
            }
        });

        holder.btnDeleteSabtenam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickItemSdutentNameList.deleteSabtenam(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return surce.size();
    }


}
