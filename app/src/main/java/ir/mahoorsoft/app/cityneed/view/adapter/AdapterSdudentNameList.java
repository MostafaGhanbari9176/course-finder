package ir.mahoorsoft.app.cityneed.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Stack;

import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.struct.StUser;

/**
 * Created by RCC1 on 1/30/2018.
 */

public class AdapterSdudentNameList extends RecyclerView.Adapter<AdapterSdudentNameList.Holder> {

    private int lastposition;

    public interface OnClickItemSdutentNameList {
        void sendSms(int position);

        void deleteSabtenam(int position);

        void confirmStudent(int position, CardView cardView);
    }

    public static Stack<Integer> checkedPositions = new Stack<>();
    private OnClickItemSdutentNameList onClickItemSdutentNameList;
    private Context context;
    private ArrayList<StUser> surce = new ArrayList<>();
    private boolean showCheckBox;


    public AdapterSdudentNameList(Context context, ArrayList<StUser> surce, OnClickItemSdutentNameList onClickItemSdutentNameList, boolean showCheckBox) {
        checkedPositions.clear();
        this.context = context;
        this.surce = surce;
        this.onClickItemSdutentNameList = onClickItemSdutentNameList;
        this.showCheckBox = showCheckBox;
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView txtName;
        TextView txtCellPhone;
        TextView btnSendSms;
        TextView btnDeleteSabtenam;
        TextView btnConfirm;
        CheckBox cbx;
        CardView cardView;
        LinearLayout llButton;
        RelativeLayout RLDelete;

        public Holder(View itemView) {
            super(itemView);
            llButton = (LinearLayout) itemView.findViewById(R.id.llItemStudentName);
            cardView = (CardView) itemView.findViewById(R.id.cvItemStudentName);
            cbx = (CheckBox) itemView.findViewById(R.id.cbxMoreSelect);
            txtName = (TextView) itemView.findViewById(R.id.txtItemStudentName);
            txtCellPhone = (TextView) itemView.findViewById(R.id.txtCellPhoneStudentName);
            btnSendSms = (TextView) itemView.findViewById(R.id.btnSendSmsStudentListName);
            btnDeleteSabtenam = (TextView) itemView.findViewById(R.id.btnDeleteSabtenamStudentListName);
            btnConfirm = (TextView) itemView.findViewById(R.id.btnConfirmSabtenamStudent);
            RLDelete = (RelativeLayout) itemView.findViewById(R.id.RLDeletedStudent);
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
        if (items.isCanceled != 0)
            holder.RLDelete.setVisibility(View.VISIBLE);
        else
            holder.RLDelete.setVisibility(View.GONE);
        if (items.status == 0) {
            holder.cardView.setCardBackgroundColor(Color.argb(100, 255, 118, 144));
            holder.btnConfirm.setVisibility(View.VISIBLE);
        } else {
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.light));
            holder.btnConfirm.setVisibility(View.GONE);
        }
        holder.txtName.setText(items.name);
        holder.txtCellPhone.setText(items.cellPhone);
        if (checkedPositions.indexOf(position) != -1)
            holder.cbx.setChecked(true);
        else
            holder.cbx.setChecked(false);
        if (showCheckBox) {
            holder.cbx.setVisibility(View.VISIBLE);
            holder.llButton.setVisibility(View.GONE);
        } else {
            holder.cbx.setVisibility(View.GONE);
            holder.llButton.setVisibility(View.VISIBLE);
        }
        holder.cbx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked())
                    checkedPositions.push(position);
                else
                    checkedPositions.remove(checkedPositions.indexOf(position));
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

        lastposition = G.setListItemsAnimation(new View[]{holder.cardView, holder.txtName}, new View[]{holder.btnConfirm, holder.btnDeleteSabtenam, holder.btnSendSms}, position, lastposition);
    }

    @Override
    public int getItemCount() {
        return surce.size();
    }


}
