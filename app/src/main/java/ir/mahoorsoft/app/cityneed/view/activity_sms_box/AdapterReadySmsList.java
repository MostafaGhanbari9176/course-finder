package ir.mahoorsoft.app.cityneed.view.activity_sms_box;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import ir.mahoorsoft.app.cityneed.R;


/**
 * Created by RCC1 on 1/30/2018.
 */

public class AdapterReadySmsList extends RecyclerView.Adapter<AdapterReadySmsList.Holder> {

    public interface OnClickItemReadySmsListListener {
        void readySmsListClick(String text, int position);
        void readySmsListRemoveClick(String text, int position);
    }

    private OnClickItemReadySmsListListener onClickItemReadySmsListListener;
    private Context context;
    private ArrayList<String> surce = new ArrayList<>();

    public AdapterReadySmsList(Context context, ArrayList<String> surce, OnClickItemReadySmsListListener onClickItemReadySmsListListener) {
        this.context = context;
        this.surce = surce;
        this.onClickItemReadySmsListListener = onClickItemReadySmsListListener;

    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView txtTabagheName;
        LinearLayout item;
        ImageView btnRemove;
        public Holder(View itemView) {
            super(itemView);
            txtTabagheName = (TextView) itemView.findViewById(R.id.txtItemReadySmsList);
            item = (LinearLayout) itemView.findViewById(R.id.itemReadySmsList);
            btnRemove = (ImageView) itemView.findViewById(R.id.btnRemoveItemReadysmsText);
        }
    }

    @Override
    public AdapterReadySmsList.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_ready_sms_text, parent, false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final AdapterReadySmsList.Holder holder, final int position) {
        final String item = surce.get(position);
        holder.txtTabagheName.setText(item);
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickItemReadySmsListListener.readySmsListClick(item, position);
            }
        });
        holder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickItemReadySmsListListener.readySmsListRemoveClick(item, position);
            }
        });
    }
    @Override
    public int getItemCount() {
        return surce.size();
    }


}
