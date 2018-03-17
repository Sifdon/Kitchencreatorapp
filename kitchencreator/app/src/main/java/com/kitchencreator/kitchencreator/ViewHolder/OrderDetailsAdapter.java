package com.kitchencreator.kitchencreator.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kitchencreator.kitchencreator.R;
import com.kitchencreator.kitchencreator.model.Order;

import java.util.List;

/**
 * Created by MOHIT on 12-03-2018.
 */

class MyViewHolder extends RecyclerView.ViewHolder{

    public TextView name,quantity,price,discount;
    public MyViewHolder(View itemView) {
        super(itemView);

        name = itemView.findViewById(R.id.Productname2);
        quantity = itemView.findViewById(R.id.Productquantity);
        price = itemView.findViewById(R.id.price);
        discount = itemView.findViewById(R.id.Discount);
    }
}
public class OrderDetailsAdapter extends RecyclerView.Adapter<MyViewHolder>{


    List<Order> myorders;

    public OrderDetailsAdapter(List<Order> myorders) {
        this.myorders = myorders;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemview = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.orderdetailslayout,parent,false);
        return new MyViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Order order = myorders.get(position);
        holder.name.setText(String.format("Name : %s",order.getProductname()));
        holder.quantity.setText(String.format("Quantity : %s",order.getQuantity()));
        holder.price.setText(String.format("Price : %s",order.getPrice()));
        holder.discount.setText(String.format("Discount : %s",order.getDiscount()));

    }

    @Override
    public int getItemCount() {
        return myorders.size();
    }
}
