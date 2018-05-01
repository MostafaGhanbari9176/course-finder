package ir.mahoorsoft.app.cityneed.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

        void confirmStudent(int position, CardView cardView);
    }

    public static Stack<Integer> checkedUser = new Stack<>();
    public static Stack<CardView> selectedItems = new Stack<>();

    private OnClickItemSdutentNameList onClickItemSdutentNameList;
    private Context context;
    private ArrayList<StUser> surce = new ArrayList<>();
    private boolean showCheckBox;


    public AdapterSdudentNameList(Context context, ArrayList<StUser> surce, OnClickItemSdutentNameList onClickItemSdutentNameList, boolean showCheckBox) {
        checkedUser.clear();
        selectedItems.clear();
        this.context = context;
        this.surce = surce;
        this.onClickItemSdutentNameList = onClickItemSdutentNameList;
        this.showCheckBox = showCheckBox;
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView txtName;
        Button btnSendSms;
        Button btnDeleteSabtenam;
        Button btnConfirm;
        CheckBox cbx;
        CardView cardView;
        LinearLayout llButton;

        public Holder(View itemView) {
            super(itemView);
            llButton = (LinearLayout) itemView.findViewById(R.id.llItemStudentName);
            cardView = (CardView) itemView.findViewById(R.id.cvItemStudentName);
            cbx = (CheckBox) itemView.findViewById(R.id.cbxMoreSelect);
            txtName = (TextView) itemView.findViewById(R.id.txtItemStudentName);
            btnSendSms = (Button) itemView.findViewById(R.id.btnSendSmsStudentListName);
            btnDeleteSabtenam = (Button) itemView.findViewById(R.id.btnDeleteSabtenamStudentListName);
            btnConfirm = (Button) itemView.findViewById(R.id.btnConfirmSabtenamStudent);
        }
    }

    @Override
    public AdapterSdudentNameList.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_student_name, parent, false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final AdapterSdudentNameList.Holder holder, final int position) {
        final StUser items = surce.get(position);
        if (items.status == 0) {
            holder.cardView.setCardBackgroundColor(Color.argb(100, 255, 118, 144));
        }
        else{
            holder.btnConfirm.setVisibility(View.GONE);
        }
        holder.txtName.setText(items.name);
        if (showCheckBox) {
            holder.cbx.setVisibility(View.VISIBLE);
            holder.llButton.setVisibility(View.GONE);
        }
        holder.cbx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkedUser.push(position);
                    selectedItems.push(holder.cardView);
                } else {
                    int index = checkedUser.indexOf(position);
                    checkedUser.remove(index);
                    selectedItems.remove(index);
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

        holder.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (items.status == 0)
                    onClickItemSdutentNameList.confirmStudent(position, holder.cardView);
                else
                    Toast.makeText(context, "تایید شده!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return surce.size();
    }


}
