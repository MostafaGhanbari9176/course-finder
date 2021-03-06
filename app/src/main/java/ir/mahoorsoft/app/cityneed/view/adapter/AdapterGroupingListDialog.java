package ir.mahoorsoft.app.cityneed.view.adapter;

import android.content.Context;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.api.ApiClient;
import ir.mahoorsoft.app.cityneed.model.struct.StGrouping;

/**
 * Created by RCC1 on 1/30/2018.
 */

public class AdapterGroupingListDialog extends RecyclerView.Adapter<AdapterGroupingListDialog.Holder> {

    private int lastPosition;

    public interface OnClickItemTabagheList {
        void tabagheListItemClick(int position);
    }

    private OnClickItemTabagheList onClickItemTabagheList;
    private Context context;
    private ArrayList<StGrouping> source = new ArrayList<>();
    private Holder selectedItem;

    public AdapterGroupingListDialog(Context context, ArrayList<StGrouping> source, OnClickItemTabagheList onClickItemTabagheList) {
        this.context = context;
        this.source = source;
        this.onClickItemTabagheList = onClickItemTabagheList;
    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView imgItem;
        TextView txtTabagheName;
        LinearLayout item;
        CardView cardView;

        public Holder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cvGroupingItem);
            imgItem = (ImageView) itemView.findViewById(R.id.imgItemTabaghe);
            txtTabagheName = (TextView) itemView.findViewById(R.id.txtTabagheNameItem);
            item = (LinearLayout) itemView.findViewById(R.id.itemTabaghe);
        }
    }

    @Override
    public AdapterGroupingListDialog.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_grouping_list_dialog, parent, false);
        Holder holder = new Holder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final AdapterGroupingListDialog.Holder holder, final int position) {
        final StGrouping items = source.get(position);
        holder.txtTabagheName.setText(items.subject);
        Glide.with(context)
                .load(ApiClient.serverAddress + "/city-need/v1/uploads/tabaghe/" + items.id + ".png")
                .centerCrop()
                .error(R.drawable.grouping)
                .into(holder.imgItem);
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickItemTabagheList.tabagheListItemClick(position);
            }
        });

        lastPosition = G.setListItemsAnimation(new View[]{holder.cardView}, new View[]{holder.txtTabagheName}, position, lastPosition);
    }

    @Override
    public void onViewDetachedFromWindow(Holder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.item.clearAnimation();
        holder.itemView.clearAnimation();
    }

    @Override
    public int getItemCount() {

        return source.size();
    }


}
