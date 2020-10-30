package com.android.ekishan.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;


import com.android.ekishan.R;
import com.android.ekishan.fragment.ProductSingleFragment;
import com.android.ekishan.model.HomeBanners;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by a on 5/30/2018.
 */

public class SliderPagerAdapter extends PagerAdapter {
    private LayoutInflater layoutInflater;
    Activity activity;
    ArrayList<HomeBanners> image_arraylist;
    Context context;

    public SliderPagerAdapter(Activity activity, ArrayList<HomeBanners> image_arraylist,Context context) {
        this.activity = activity;
        this.image_arraylist = image_arraylist;
        this.context = context;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(R.layout.tele_layout_slider, container, false);
        ImageView im_slider =   view.findViewById(R.id.im_slider);
        RelativeLayout rlslider =   view.findViewById(R.id.rlslider);
        Picasso.with(activity)
                .load(image_arraylist.get(position).getImage())
//                .placeholder(R.drawable.banners) // optional
//                .error(R.drawable.banners)         // optional
                .into(im_slider);



        rlslider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!image_arraylist.get(position).getDeal_product_id().equalsIgnoreCase("0")) {
                    loadFragment(new ProductSingleFragment(image_arraylist.get(position).getDeal_product_id()));
                }
            }
        });
//        Picasso.with(activity.getApplicationContext())
//                .load(image_arraylist.get(position))
//                .placeholder(R.mipmap.ic_launcher) // optional
//                .error(R.mipmap.ic_launcher)         // optional
//                .into(im_slider);


        container.addView(view);

        return view;
    }
    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        ((AppCompatActivity)context).getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragment_container, fragment)
                .commit();
        if (fragment != null) {
            return true;
        }
        return false;
    }

    @Override
    public int getCount() {
        return image_arraylist.size();
    }


    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }
}