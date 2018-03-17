package com.kitchencreator.kitchencreator.Dashboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.kitchencreator.kitchencreator.Common.Common;
import com.kitchencreator.kitchencreator.R;
import com.kitchencreator.kitchencreator.ViewHolder.OrderDetailsAdapter;

public class Orderdetails extends AppCompatActivity {

    TextView orderid,orderphone,orderaddress,ordertotal,orderstatus;
    String ordervalueid ="";
    RecyclerView requestproduct;
    RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderdetails);

        orderid = findViewById(R.id.orderid1);
        orderphone = findViewById(R.id.orderphone1);
        orderaddress = findViewById(R.id.orderaddress1);
        ordertotal = findViewById(R.id.ordertotal1);
        orderstatus = findViewById(R.id.orderstatus1);

        requestproduct = findViewById(R.id.orderdet);
        layoutManager = new LinearLayoutManager(this);
        requestproduct.setLayoutManager(layoutManager);

        if (getIntent() != null)
        {
            ordervalueid = getIntent().getStringExtra("Orderid");
        }
        orderid.setText(ordervalueid);
        orderphone.setText(Common.currentrequest.getPhoneno());
        orderaddress.setText(Common.currentrequest.getAddress());
        ordertotal.setText(Common.currentrequest.getTotal());
        orderstatus.setText(Common.converttostatus(Common.currentrequest.getStatus()));

        OrderDetailsAdapter adapter = new OrderDetailsAdapter(Common.currentrequest.getProducts());
        adapter.notifyDataSetChanged();
        requestproduct.setAdapter(adapter);


    }
}
