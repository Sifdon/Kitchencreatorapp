package com.kitchencreator.kitchencreator.Dashboard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kitchencreator.kitchencreator.Interface.ItemClickListener;
import com.kitchencreator.kitchencreator.R;
import com.kitchencreator.kitchencreator.ViewHolder.ProductViewHolder;
import com.kitchencreator.kitchencreator.model.Product;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class productlist extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase proddb;
    DatabaseReference prod;

    String categoryId="";

    FirebaseRecyclerAdapter<Product,ProductViewHolder> adapter;
    FirebaseRecyclerAdapter<Product,ProductViewHolder> searchadapter;
    List<String> suggestlist = new ArrayList<>();
    MaterialSearchBar materialSearchBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productlist);

        proddb = FirebaseDatabase.getInstance();
        prod = proddb.getReference("product");

        recyclerView = findViewById(R.id.recylcerproduct);
       // recyclerView.setHasFixedSize(true);
        layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);;
        recyclerView.setLayoutManager(layoutManager);
try {
    if (getIntent() != null) {
        categoryId = getIntent().getStringExtra("Categoryid");
        if (!categoryId.isEmpty() && categoryId != null) {

            loadproduct(categoryId);
        }

    }
}catch (Exception e){

}
        materialSearchBar = findViewById(R.id.search_bar);
        materialSearchBar.setHint("Search the product");
        loadsuggest();
        materialSearchBar.setLastSuggestions(suggestlist);
        materialSearchBar.setCardViewElevation(10);
        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                List<String> suggest = new ArrayList<String>();
                for (String search:suggestlist){
                    if (search.toLowerCase().contains(materialSearchBar.getText().toString()))
                        suggest.add(search);
                }
                materialSearchBar.setLastSuggestions(suggest);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                //when search bar is closed
                //restore original adatper
                if (!enabled)
                    recyclerView.setAdapter(adapter);
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                //when searchn finish
                //show result of search adapter
                startSearch(text);

            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });

    }
    private void loadsuggest() {
        prod.orderByChild("id").equalTo(categoryId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                            Product item = postSnapshot.getValue(Product.class);
                            assert item != null;
                            suggestlist.add(item.getProductname());

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

    }
    private void loadproduct(String categoryId) {


        adapter = new FirebaseRecyclerAdapter<Product, ProductViewHolder>(Product.class,R.layout.productitem,ProductViewHolder.class,
                prod.orderByChild("id").equalTo(categoryId)) {



            @Override
            protected void populateViewHolder(ProductViewHolder viewHolder, Product model, int position) {


                viewHolder.txtprodname.setText(model.getProductname());
                viewHolder.prodprice.setText(model.getProductprice());
                Picasso.with(getBaseContext()).load(model.getProductimage()).into(viewHolder.prodimage);



                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent product = new Intent(productlist.this,productdetails.class);
                        product.putExtra("productid",adapter.getRef(position).getKey());
                        startActivity(product);
                    }
                });

            }
        };
        recyclerView.setAdapter(adapter);




    }

    private void startSearch(CharSequence text) {

        searchadapter = new FirebaseRecyclerAdapter<Product, ProductViewHolder>(
                Product.class,
                R.layout.productitem,
                ProductViewHolder.class,
                prod.orderByChild("productname").equalTo(text.toString())
        ) {
            @Override
            protected void populateViewHolder(ProductViewHolder viewHolder, Product model, int position) {
                viewHolder.txtprodname.setText(model.getProductname());
                viewHolder.prodprice.setText(model.getProductprice());
                Picasso.with(getBaseContext()).load(model.getProductimage()).into(viewHolder.prodimage);



                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent product = new Intent(productlist.this,productdetails.class);
                        product.putExtra("productid",searchadapter.getRef(position).getKey());
                        startActivity(product);
            }
        });
                recyclerView.setAdapter(searchadapter);
    }





};
    }
}

