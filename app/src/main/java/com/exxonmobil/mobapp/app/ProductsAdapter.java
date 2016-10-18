package com.exxonmobil.mobapp.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.exxonmobil.mobapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by El Nakeeb on 8/8/2016.
 */
public class ProductsAdapter extends BaseAdapter {

    private final LayoutInflater inflater;
    ArrayList<ProductObj> list;
    Context context;

    public ProductsAdapter(Context context, ArrayList<ProductObj> list) {
        this.context = context;
        this.list = list;
        inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = inflater.inflate(R.layout.product_item, null);
        ProductObj product = list.get(position);

        TextView name= (TextView) v.findViewById(R.id.productName);
        TextView description= (TextView) v.findViewById(R.id.productDescription);
        ImageView img= (ImageView) v.findViewById(R.id.productImage);
        Picasso.with(context).load(AppConfig.URL_SERVER + "products/" + product.getImage()).into(img);

        name.setText(product.getName());
        description.setText(product.getDescription());

        return v;
    }
}
