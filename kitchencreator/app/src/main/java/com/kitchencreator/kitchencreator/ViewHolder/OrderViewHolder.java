package com.kitchencreator.kitchencreator.ViewHolder;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.kitchencreator.kitchencreator.Interface.ItemClickListener;
import com.kitchencreator.kitchencreator.R;

/**
 * Created by MOHIT on 07-03-2018.
 */

public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView ordername,orderid,orderaddress,orderstatus,orderphone,ordertotal;

    public ItemClickListener itemClickListener;

    public OrderViewHolder(View itemView) {
        super(itemView);

        orderid = itemView.findViewById(R.id.orderid);
        orderaddress = itemView.findViewById(R.id.orderaddress);
        ordername = itemView.findViewById(R.id.ordername);
        orderstatus = itemView.findViewById(R.id.orderstatus);
        orderphone = itemView.findViewById(R.id.orderphone);
        ordertotal = itemView.findViewById(R.id.ordertotal);

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
