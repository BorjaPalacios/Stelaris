package com.example.stelaris.clases.carousel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.stelaris.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CarouselAdapter extends PagerAdapter {

    private Context context;
    private List<CarouselItem> carouselItemList;

    public CarouselAdapter(Context context, List<CarouselItem> carouselItemList) {
        this.context = context;
        this.carouselItemList = carouselItemList;
    }

    @NonNull
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        try {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View sliderLayout = inflater.inflate(R.layout.carousel_layout, null);

            ImageView featured_image = sliderLayout.findViewById(R.id.my_featured_image);

            Picasso.get().load(carouselItemList.get(position).getUrl()).into(featured_image);

            container.addView(sliderLayout);
            return sliderLayout;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return carouselItemList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }
}
