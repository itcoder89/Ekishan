package com.android.ekishan.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.ekishan.R;
import com.android.ekishan.model.BasketBannerList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

/**
 * Created by ${} on 5/25/2020.
 */
public class BasketBannerADapter extends PagerAdapter {
    private LayoutInflater layoutInflater;
    Activity activity;
    ArrayList<BasketBannerList> image_arraylist;

    public BasketBannerADapter(Activity activity, ArrayList<BasketBannerList> image_arraylist) {
        this.activity = activity;
        this.image_arraylist = image_arraylist;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(R.layout.tele_layout_slider, container, false);
        ImageView im_slider =   view.findViewById(R.id.im_slider);
//        im_slider.setBackground(image_arraylist.get(position));





        Picasso.with(activity.getApplicationContext())
                .load(image_arraylist.get(position).getImage())
//                .placeholder(R.drawable.banner2) // optional
//                .error(R.drawable.banner2)         // optional
                .into(im_slider);


        container.addView(view);

        return view;
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