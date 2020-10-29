package ir.mahoorsoft.app.cityneed.view;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import cn.lightsky.infiniteindicator.ImageLoader;

/**
 * Created by usb-web on 12/12/2017.
 */

public class GlideLoader implements ImageLoader {

    public void initLoader(Context context) {

    }

    @Override
    public void load(Context context, ImageView targetView, Object res) {

        if (res instanceof String){
            Glide.with(context)
                    .load( res)
                    .centerCrop()
                    .into(targetView);
        }
    }
}