package ir.mahoorsoft.app.cityneed.view.activity_subscribe.fragment_chose_subscribe;

import android.content.Context;
import androidx.core.content.ContextCompat;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.api.ApiClient;
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
        CardView heder;
        CardView confirm;

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
            heder = (CardView) itemView.findViewById(R.id.CVHederItemChoseSubscribe);
            confirm = (CardView) itemView.findViewById(R.id.CVConfirmItemChoseSubscribe);
        }
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).
                inflate(R.layout.item_chose_subscribe, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {

        StSubscribe subscribeItem = source.get(position);

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.showMoreFeature.getVisibility() == View.GONE) {
                    G.animatingForVisible(holder.showMoreFeature, 0f, 1f, 0f);
                    holder.imgDropDown.animate().rotation(180).start();
                } else {
                    G.animatingForGone(holder.showMoreFeature, 1f, 0f, -holder.showMoreFeature.getHeight());
                    holder.imgDropDown.animate().rotation(0).start();
                }
            }
        });
        holder.btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subscribeClick.subscribeItemClick(position);
            }
        });

        holder.txtDescription.setText(subscribeItem.description);
        holder.txtPrice.setText(subscribeItem.price + "تومان");
        holder.txtSubject.setText(subscribeItem.subject);
        setColor(holder, position);
        setImage(holder.img, position);
    }

    private void setColor(Holder holder, int position) {
        if (source.get(position).subject.equals("برونزی")) {
            holder.heder.setCardBackgroundColor(ContextCompat.getColor(G.context, R.color.dark_brown_sub));
            holder.confirm.setCardBackgroundColor(ContextCompat.getColor(G.context, R.color.dark_brown_sub));
            holder.txtDescription.setTextColor(ContextCompat.getColor(G.context, R.color.dark_brown_sub));
            holder.showMoreFeature.setCardBackgroundColor(ContextCompat.getColor(G.context, R.color.light_brown_sub));

        } else if (source.get(position).subject.equals("نقره ایی")) {
            holder.heder.setCardBackgroundColor(ContextCompat.getColor(G.context, R.color.dark_silver_sub));
            holder.confirm.setCardBackgroundColor(ContextCompat.getColor(G.context, R.color.dark_silver_sub));
            holder.txtDescription.setTextColor(ContextCompat.getColor(G.context, R.color.dark_silver_sub));
            holder.showMoreFeature.setCardBackgroundColor(ContextCompat.getColor(G.context, R.color.light_silver_sub));

        } else if (!source.get(position).subject.equals("طلایی")) {
            holder.heder.setCardBackgroundColor(ContextCompat.getColor(G.context, R.color.light_simple_sub));
            holder.confirm.setCardBackgroundColor(ContextCompat.getColor(G.context, R.color.light_simple_sub));
            holder.txtDescription.setTextColor(ContextCompat.getColor(G.context, R.color.light_simple_sub));
            holder.showMoreFeature.setCardBackgroundColor(ContextCompat.getColor(G.context, R.color.dark_simple_sub));

        }
    }


    private void setImage(ImageView img, int position) {
        Glide.with(G.context)
                .load(ApiClient.serverAddress + "/city-need/v1/uploads/subscribe/" + source.get(position).id + ".png")
                .error(R.drawable.subscribe_pressure)
                .fitCenter()
                .into(img);
    }

    @Override
    public int getItemCount() {
        return source.size();
    }


}
