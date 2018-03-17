package com.kitchencreator.kitchencreator.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kitchencreator.kitchencreator.Interface.ItemClickListener;
import com.kitchencreator.kitchencreator.R;

/**
 * Created by hp on 06-02-2018.
 */

public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtmenuname;//menudescription;
    public ImageView menuimage;

    private ItemClickListener itemClickListener;

    public MenuViewHolder(View itemView) {
        super(itemView);


        txtmenuname = (TextView)itemView.findViewById(R.id.menu_name);
      //  menudescription = (TextView)itemView.findViewById(R.id.menu_description);
        menuimage = (ImageView)itemView.findViewById(R.id.menu_image);

        itemView.setOnClickListener(this);

    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
itemClickListener.onClick(v,getAdapterPosition(),false);
    }
}
