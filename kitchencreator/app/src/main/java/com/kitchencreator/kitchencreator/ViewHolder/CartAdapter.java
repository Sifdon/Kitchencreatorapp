package com.kitchencreator.kitchencreator.ViewHolder;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.kitchencreator.kitchencreator.Common.Common;
import com.kitchencreator.kitchencreator.Interface.ItemClickListener;
import com.kitchencreator.kitchencreator.R;
import com.kitchencreator.kitchencreator.model.Order;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by hp on 11-02-2018.
 */

class  CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
,View.OnCreateContextMenuListener{

    public TextView txtname,txtprice;
    public ImageView imgcount;

    private ItemClickListener itemClickListener;

    public void setTxtname(TextView txtname) {
        this.txtname = txtname;
    }

    public CartViewHolder(View itemView) {
        super(itemView);
        txtname = itemView.findViewById(R.id.cartitemname);
        txtprice = itemView.findViewById(R.id.cartitemprice);
        imgcount = itemView.findViewById(R.id.imgcount);

        itemView.setOnCreateContextMenuListener(this);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Select action");
        menu.add(0,0,getAdapterPosition(), Common.DELETE);

    }
}

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {

    private List<Order> listData = new ArrayList<>();
    private Context context;

    public CartAdapter(List<Order> listData, Context context) {
        this.listData = listData;
        this.context = context;
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemview = inflater.inflate(R.layout.cartlayout,parent,false );
        return new CartViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {
        TextDrawable drawable = TextDrawable.builder()
                .buildRound(""+listData.get(position).getQuantity(), Color.BLUE);
            holder.imgcount.setImageDrawable(drawable);

        Locale locale = new Locale("en","in");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
        int price =(Integer.parseInt(listData.get(position).getPrice())*(Integer.parseInt(listData.get(position).getQuantity())));
        holder.txtprice.setText(fmt.format(price));

        holder.txtname.setText(listData.get(position).getProductname());

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
}
