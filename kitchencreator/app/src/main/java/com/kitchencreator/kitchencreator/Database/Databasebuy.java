package com.kitchencreator.kitchencreator.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.kitchencreator.kitchencreator.model.Order;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MOHIT on 08-03-2018.
 */

public class Databasebuy extends SQLiteAssetHelper {

    private static final String DB_NAME = "Buynow.db";
    private static final int DB_VER = 1;

    public Databasebuy(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    public List<Order> gettingcarts() {
        SQLiteDatabase sb = getReadableDatabase();
        SQLiteQueryBuilder fb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"Productname", "ProductId", "Quantity", "Price", "Discount"};
        String sql = "Buynow";

        fb.setTables(sql);
        Cursor c = fb.query(sb, sqlSelect, null, null, null, null, null);

        final List<Order> result = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                result.add(new Order(c.getString(c.getColumnIndex("ProductId")),
                        c.getString(c.getColumnIndex("Productname")),
                        c.getString(c.getColumnIndex("Quantity")),
                        c.getString(c.getColumnIndex("Price")),
                        c.getString(c.getColumnIndex("Discount"))));
            } while (c.moveToNext());
        }
        return result;
    }
    public void buynowcart(Order order)
    {
        SQLiteDatabase sb = getReadableDatabase();
        String query = String.format("INSERT INTO Buynow(ProductId,Productname,Quantity,Price,Discount) VALUES('%s','%s','%s','%s','%s');",
                order.getProductId(),
                order.getProductname(),
                order.getQuantity(),
                order.getPrice(),
                order.getDiscount()
        );
        sb.execSQL(query);

    }
    public void cleanbuycart() {

        SQLiteDatabase sb = getReadableDatabase();
        String query = "DELETE FROM Buynow";
        sb.execSQL(query);

    }
}
