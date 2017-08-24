package com.example.rk.uremote;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by RK on 8/20/2017.
 */

public class BrandAdapter extends RecyclerView.Adapter<BrandAdapter.BrandViewHolder> {

    private int [] brandImages ;
    private String [] brandNames;
    private Context context;

    public BrandAdapter(Context context, int [] brandImages, String [] brandNames) {

        this.context = context;
        this.brandImages = brandImages;
        this.brandNames = brandNames;
    }

    @Override
    public BrandViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_brand_layout,parent,false);
        return new BrandViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BrandViewHolder holder, int position) {
        holder.brandName.setText(brandNames[position]);
        holder.brandImage.setImageResource(brandImages[position]);
    }

    @Override
    public int getItemCount() {
        return brandImages.length;
    }

    public class BrandViewHolder extends RecyclerView.ViewHolder {

        ImageView brandImage;
        TextView brandName;

        public BrandViewHolder(View itemView) {
            super(itemView);
            brandImage = (ImageView) itemView.findViewById(R.id.brand_image);
            brandName = (TextView) itemView.findViewById(R.id.brand_name);
        }
    }
}
