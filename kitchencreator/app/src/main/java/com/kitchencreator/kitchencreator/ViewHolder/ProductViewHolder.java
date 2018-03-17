package com.kitchencreator.kitchencreator.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kitchencreator.kitchencreator.Interface.ItemClickListener;
import com.kitchencreator.kitchencreator.R;

/**
 * Created by hp on 07-02-2018.
 */

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtprodname,prodprice;
    public ImageView prodimage;

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public ProductViewHolder(View itemView) {
        super(itemView);


        txtprodname = (TextView)itemView.findViewById(R.id.productname);
        prodprice = (TextView)itemView.findViewById(R.id.productprice);
        prodimage = (ImageView)itemView.findViewById(R.id.productimage);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        itemClickListener.onClick(v,getAdapterPosition(),false);

    }
}
