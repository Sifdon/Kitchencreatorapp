package com.kitchencreator.kitchencreator.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.kitchencreator.kitchencreator.Interface.ItemClickListener;
import com.kitchencreator.kitchencreator.R;

/**
 * Created by MOHIT on 06-03-2018.
 */

public class ShowCartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtname,txtprice;
    public ImageView imgcount;
    public ImageButton delete;

    private ItemClickListener itemClickListener,deleteitem;

    public void setDeleteitem(ItemClickListener deleteitem) {
        this.deleteitem = deleteitem;
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public ShowCartViewHolder(View itemView) {
        super(itemView);

        txtname = itemView.findViewById(R.id.cartitemname);
        txtprice = itemView.findViewById(R.id.cartitemprice);
        imgcount = itemView.findViewById(R.id.imgcount);
        delete = itemView.findViewById(R.id.cart_delete);

        delete.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);
        deleteitem.onClick(v,getAdapterPosition(),false);


    }



}
