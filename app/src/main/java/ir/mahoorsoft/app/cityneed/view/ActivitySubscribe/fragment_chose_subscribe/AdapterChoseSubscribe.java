package ir.mahoorsoft.app.cityneed.view.ActivitySubscribe.fragment_chose_subscribe;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.struct.StSubscribe;

/**
 * Created by MAHNAZ on 10/28/2017.
 */

public class AdapterChoseSubscribe extends RecyclerView.Adapter<AdapterChoseSubscribe.Holder> {

    public interface SubscribeClick {

        void subscribeItemClick(int position);

    }


    SubscribeClick subscribeClick;
    Context context;
    ArrayList<StSubscribe> source;

    public AdapterChoseSubscribe(Context context, ArrayList<StSubscribe> source, SubscribeClick subscribeClick) {
        this.context = context;
        this.source = source;
        this.subscribeClick = subscribeClick;
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView txtSubject;
        TextView txtPrice;
        TextView txtDescription;
        LinearLayout item;
        ImageView img;
        ImageView imgDropDown;
        Button btnBuy;
        CardView showMoreFeature;
        public Holder(View itemView) {
            super(itemView);
            txtSubject = (TextView) itemView.findViewById(R.id.txtSubscribeSubject);
            txtPrice = (TextView) itemView.findViewById(R.id.txtPriceSubscribe);
            txtDescription = (TextView) itemView.findViewById(R.id.txtDescriptionSubscribe);
            item = (LinearLayout) itemView.findViewById(R.id.itemChoseSubscribe);
            img = (ImageView) itemView.findViewById(R.id.imgSubscribe);
            imgDropDown = (ImageView) itemView.findViewById(R.id.imgDropDownItemSubscribe);
            btnBuy = (Button) itemView.findViewById(R.id.btnBuySubscribe);
            showMoreFeature = (CardView) itemView.findViewById(R.id.CVShowMoreFetureSubscribe);
        }
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).
                inflate(R.layout.item_chose_subscribe, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(final Holder holder, int position) {

        StSubscribe subscribeItem = source.get(position);
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.showMoreFeature.getVisibility() == View.GONE) {
                    holder.showMoreFeature.setVisibility(View.VISIBLE);
                    holder.imgDropDown.setImageResource(R.drawable.icon_drop_up);
                }
                else {
                    holder.showMoreFeature.setVisibility(View.GONE);
                    holder.imgDropDown.setImageResource(R.drawable.icon_drop_down);
                }
            }
        });
        holder.txtDescription.setText(subscribeItem.description);
        holder.txtPrice.setText(subscribeItem.price+"تومان");
        holder.txtSubject.setText(subscribeItem.subject);

    }

    @Override
    public int getItemCount() {
        return source.size();
    }


}
