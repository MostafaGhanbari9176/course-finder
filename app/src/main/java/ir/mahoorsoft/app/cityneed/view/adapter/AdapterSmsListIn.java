package ir.mahoorsoft.app.cityneed.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.struct.StSmsBox;

/**
 * Created by RCC1 on 1/22/2018.
 */

public class AdapterSmsListIn extends RecyclerView.Adapter<AdapterSmsListIn.Holder>{


    public interface OnClickItemSmsList {
        void seenMessage(int position);
        void deleteMessage(int position);
        void sendSms(int position);
    }

    private OnClickItemSmsList itemSmsList;
    private Context context;
    private ArrayList<StSmsBox> surce = new ArrayList<>();


    public AdapterSmsListIn(Context context, ArrayList<StSmsBox> surce, OnClickItemSmsList itemSmsList) {
        this.context = context;
        this.surce = surce;
        this.itemSmsList = itemSmsList;

    }

    class Holder extends RecyclerView.ViewHolder {
        TextView txtTsName;
        TextView txtCourseName;
        TextView txtDate;
        ImageView btnDelete;
        ImageView btnSendSms;
        ImageView btnSeen;

        public Holder(View itemView) {
            super(itemView);
            txtCourseName = (TextView) itemView.findViewById(R.id.txtCourseNameItemIn);
            txtTsName = (TextView) itemView.findViewById(R.id.txtTsNameItemIn);
            txtDate = (TextView) itemView.findViewById(R.id.txtDateItemIn);
            btnDelete = (ImageView) itemView.findViewById(R.id.btnDeleteItemIn);
            btnSeen = (ImageView) itemView.findViewById(R.id.btnSeenItemIn);
            btnSendSms = (ImageView) itemView.findViewById(R.id.btnSendSmsItemIn);

        }
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_sms_in, parent, false);
        Holder holder = new Holder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, final int position) {
        final StSmsBox items = surce.get(position);

        holder.txtCourseName.setText(items.courseName);
        holder.txtTsName.setText(items.tsName );
        holder.txtDate.setText(items.date);
        holder.btnSendSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemSmsList.sendSms(position);
            }
        });
        holder.btnSeen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemSmsList.seenMessage(position);
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemSmsList.deleteMessage(position);
            }
        });

    }

    @Override
    public int getItemCount() {

        return surce.size();
    }


}
