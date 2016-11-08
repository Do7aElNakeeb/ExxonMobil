package com.exxonmobil.mobapp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.exxonmobil.mobapp.R;
import com.exxonmobil.mobapp.app.AppConfig;
import com.exxonmobil.mobapp.app.AppController;
import com.exxonmobil.mobapp.app.PromoAdapter;
import com.exxonmobil.mobapp.app.PromoObj;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nakeeb PC on 7/24/2016.
 */
public class Promotions extends AppCompatActivity {

    private static final String TAG = Promotions.class.getSimpleName();
    ListView lv;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.promotions);
        lv = (ListView) findViewById(R.id.listView);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        Toolbar toolbar= (Toolbar) findViewById(R.id.mytoolBar);
        setSupportActionBar(toolbar);

        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withIcon(R.mipmap.mobil_tm).withBadge("Home").withName("Menu");

        SecondaryDrawerItem item2= (SecondaryDrawerItem) new SecondaryDrawerItem().withIdentifier(2).withIcon(R.mipmap.stations).withName("Stations");
        SecondaryDrawerItem item3= (SecondaryDrawerItem) new SecondaryDrawerItem().withIdentifier(2).withIcon(R.mipmap.products).withName("Products");
        SecondaryDrawerItem item4= (SecondaryDrawerItem) new SecondaryDrawerItem().withIdentifier(2).withIcon(R.mipmap.promotions).withName("Promotions");
        SecondaryDrawerItem item5= (SecondaryDrawerItem) new SecondaryDrawerItem().withIdentifier(2).withIcon(R.mipmap.profile).withName("Profile");
        SecondaryDrawerItem item6= (SecondaryDrawerItem) new SecondaryDrawerItem().withIdentifier(2).withIcon(R.mipmap.history).withName("History");
        SecondaryDrawerItem item7= (SecondaryDrawerItem) new SecondaryDrawerItem().withIdentifier(2).withIcon(R.mipmap.settings).withName("Settings");


        Drawer result = new DrawerBuilder().withActivity(this).withToolbar(toolbar).addDrawerItems(item1,item2,item3,item4,item5, item6, item7).build();

        result.setSelection(item4);
        item1.mOnDrawerItemClickListener = new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                Intent intent = new Intent(getApplicationContext(), Landing.class);
                startActivity(intent);
                return true;
            }
        };
        item2.mOnDrawerItemClickListener = new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                Intent intent = new Intent(getApplicationContext(), Stations.class);
                startActivity(intent);
                return true;
            }
        };
        item3.mOnDrawerItemClickListener = new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                Intent intent = new Intent(getApplicationContext(), Products.class);
                startActivity(intent);
                return true;
            }
        };
        item4.mOnDrawerItemClickListener = new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                Intent intent = new Intent(getApplicationContext(), Promotions.class);
                startActivity(intent);
                return true;
            }
        };
        item5.mOnDrawerItemClickListener = new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                Intent intent = new Intent(getApplicationContext(), Profile.class);
                startActivity(intent);
                return true;
            }
        };

        syncPromotions();
    }

    private void syncPromotions(){

        // Tag used to cancel the request
        String tag_string_req = "req_register";


        StringRequest strReq = new StringRequest(com.android.volley.Request.Method.POST, AppConfig.URL_PROMOTIONS , new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Stations Response: " + response);

                ArrayList<PromoObj> list=new ArrayList<>();

                try {
                    // Extract JSON array from the response
                    JSONArray arr = new JSONArray(response);
                    System.out.println(arr.length());



                    // If no of array elements is not zero
                    if(arr.length() != 0){

                        pDialog.setMessage("Loading Promotions ...");
             //           showDialog();
                        // Loop through each array element, get JSON object
                        for (int i = 0; i < arr.length(); i++) {
                            // Get JSON object
                            JSONObject obj = (JSONObject) arr.get(i);

                            // DB QueryValues Object to insert into SQLite
                            String message = obj.get("message").toString();
                            String image = obj.get("image").toString();
/*
                            ImageView img= (ImageView) findViewById(R.id.promoImg);
                            Picasso.with(Promotions.this).load(AppConfig.URL_PROMO + image).into(img);
                            BitmapDrawable bitimg = (BitmapDrawable) img.getDrawable();
                            Bitmap bitmapImg = bitimg.getBitmap();
                            */
                            list.add(new PromoObj(message, image));

                        }

                        PromoAdapter ma = new PromoAdapter(Promotions.this, list);
                        lv.setAdapter(ma);
          //              hideDialog();
                    }

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(Promotions.this, error.getMessage(), Toast.LENGTH_LONG).show();
//                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }


    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

}
