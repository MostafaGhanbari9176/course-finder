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

public class AdapterSmsListOut extends RecyclerView.Adapter<AdapterSmsListOut.Holder> implements View.OnClickListener{

    int position;
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnDeleteItemIn:
                itemSmsList.deleteMessage(position);
                break;
            case R.id.btnSeenItemIn:
                itemSmsList.seenMessage(position);
                break;
            case R.id.btnSendSmsItemIn:
                itemSmsList.sendSms(position);
                break;
        }
    }

    public interface OnClickItemSmsList {
        void seenMessage(int position);
        void deleteMessage(int position);
        void sendSms(int position);
    }

    private OnClickItemSmsList itemSmsList;
    private Context context;
    private ArrayList<StSmsBox> surce = new ArrayList<>();


    public AdapterSmsListOut(Context context, ArrayList<StSmsBox> surce, OnClickItemSmsList itemSmsList) {
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
            txtCourseName = (TextView) itemView.findViewById(R.id.txtCourseNameItemOut);
            txtTsName = (TextView) itemView.findViewById(R.id.txtTsNameItemOut);
            txtDate = (TextView) itemView.findViewById(R.id.txtDateItemOut);
            btnDelete = (ImageView) itemView.findViewById(R.id.btnDeleteItemOut);
            btnSeen = (ImageView) itemView.findViewById(R.id.btnSeenItemOut);
            btnSendSms = (ImageView) itemView.findViewById(R.id.btnSendSmsItemOut);

        }
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_sms_out, parent, false);
        Holder holder = new Holder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, final int position) {
        final StSmsBox items = surce.get(position);
        this.position = position;
        holder.txtCourseName.setText(items.courseName);
        holder.txtTsName.setText(items.tsName );
        holder.txtDate.setText(items.date);
        holder.btnSendSms.setOnClickListener(this);
        holder.btnSeen.setOnClickListener(this);
        holder.btnDelete.setOnClickListener(this);

    }

    @Override
    public int getItemCount() {

        return surce.size();
    }


}
