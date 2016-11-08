package com.exxonmobil.mobapp.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.exxonmobil.mobapp.R;
import com.exxonmobil.mobapp.app.MarkerDataSource;
import com.exxonmobil.mobapp.app.MarkerObj;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.List;

/**
 * Created by Nakeeb PC on 8/1/2016.
 */
public class StationInfo extends AppCompatActivity {

    GridLayout fuels, services;
    RelativeLayout fuelsBar, servicesBar;
    MarkerDataSource stationInfo;
    TextView StationName;
    TextView StationAddress;
    TextView StationRegion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.station_info);

        Toolbar toolbar= (Toolbar) findViewById(R.id.mytoolBar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Station Information");

        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withIcon(R.mipmap.mobil_tm).withBadge("Home").withName("Menu");

        SecondaryDrawerItem item2= (SecondaryDrawerItem) new SecondaryDrawerItem().withIdentifier(2).withIcon(R.mipmap.stations).withName("Stations");
        SecondaryDrawerItem item3= (SecondaryDrawerItem) new SecondaryDrawerItem().withIdentifier(2).withIcon(R.mipmap.products).withName("Products");
        SecondaryDrawerItem item4= (SecondaryDrawerItem) new SecondaryDrawerItem().withIdentifier(2).withIcon(R.mipmap.promotions).withName("Promotions");
        SecondaryDrawerItem item5= (SecondaryDrawerItem) new SecondaryDrawerItem().withIdentifier(2).withIcon(R.mipmap.profile).withName("Profile");
        SecondaryDrawerItem item6= (SecondaryDrawerItem) new SecondaryDrawerItem().withIdentifier(2).withIcon(R.mipmap.history).withName("History");
        SecondaryDrawerItem item7= (SecondaryDrawerItem) new SecondaryDrawerItem().withIdentifier(2).withIcon(R.mipmap.settings).withName("Settings");


        Drawer result = new DrawerBuilder().withActivity(this).withToolbar(toolbar).addDrawerItems(item1,item2,item3,item4,item5, item6, item7).build();

        result.setSelection(item3);
        item1.mOnDrawerItemClickListener = new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                Intent intent = new Intent(getApplicationContext(), Landing.class);
                startActivity(intent);
                finish();
                return true;
            }
        };
        item2.mOnDrawerItemClickListener = new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                Intent intent = new Intent(getApplicationContext(), Stations.class);
                startActivity(intent);
                finish();
                return true;
            }
        };
        item3.mOnDrawerItemClickListener = new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                Intent intent = new Intent(getApplicationContext(), Products.class);
                startActivity(intent);
                finish();
                return true;
            }
        };
        item4.mOnDrawerItemClickListener = new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                Intent intent = new Intent(getApplicationContext(), Promotions.class);
                startActivity(intent);
                finish();
                return true;
            }
        };
        item5.mOnDrawerItemClickListener = new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                Intent intent = new Intent(getApplicationContext(), Profile.class);
                startActivity(intent);
                finish();
                return true;
            }
        };

        result.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
  //      getSupportActionBar().setHomeButtonEnabled(true);
/*
        toolbar.setNavigationIcon(R.drawable.back_btn);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StationInfo.this, Stations.class);
                startActivity(i);
            }
        });
*/

        StationName = (TextView) findViewById(R.id.station_name);
        StationAddress = (TextView)findViewById(R.id.station_address);
        StationRegion = (TextView) findViewById(R.id.station_region);
        ImageView stationCall = (ImageView) findViewById(R.id.station_call);
        ImageView stationRoute = (ImageView) findViewById(R.id.station_route);

        fuels = (GridLayout) findViewById(R.id.fuels);
        services = (GridLayout)findViewById(R.id.services);
        fuelsBar = (RelativeLayout) findViewById(R.id.fuels_bar);
        servicesBar = (RelativeLayout) findViewById(R.id.services_bar);



        fuels.setPadding(0, 0, 5, 0);

        Bundle marker = getIntent().getExtras();

        stationInfo = new MarkerDataSource(this);
        try {
            stationInfo.open();

        } catch (Exception e) {
            Log.i("hello", "hello");
        }

        List<MarkerObj> m = stationInfo.getStationsDetails("name="+'"'+marker.getString("marker")+'"');

        StationName.setText(m.get(0).getName());
        StationAddress.setText(m.get(0).getAddress());
        StationRegion.setText(m.get(0).getRegion() + ", " + m.get(0).getCity());
        final String phone = m.get(0).getPhone();
        final String latitude = m.get(0).getLatitude();
        final String longitude = m.get(0).getLongitude();

        int sflag = 0;
        int fflag = 0;

        if(m.get(0).getLubricants().equals("Mobil 1 Center")) {
            ImageView Mobil1Center = new ImageView(this);
            Mobil1Center.setImageResource(R.drawable.mobil1_center);
            Mobil1Center.setLayoutParams(new LayoutParams(448, 50));
            services.addView(Mobil1Center);
            sflag = 1;
        }
        else if(m.get(0).getLubricants().equals("Autocare")){
            ImageView AutoCare = new ImageView(this);
            AutoCare.setImageResource(R.drawable.products);
            AutoCare.setLayoutParams(new LayoutParams(75, 75));
            services.addView(AutoCare);
            sflag = 1;
        }
        if(m.get(0).getMOG80().equals("YES")){
            ImageView mog80 = new ImageView(this);
            mog80.setImageResource(R.drawable.mog80);
            mog80.setLayoutParams(new LayoutParams(75, 75));
            fuels.addView(mog80);
            fflag = 1;
        }
        if(m.get(0).getMOG92().equals("YES")){
            ImageView mog92 = new ImageView(this);
            mog92.setImageResource(R.drawable.mog92);
            mog92.setLayoutParams(new LayoutParams(75, 75));
            fuels.addView(mog92);
            fflag = 1;
        }
        if(m.get(0).getMOG95().equals("YES")){
            ImageView mog95 = new ImageView(this);
            mog95.setImageResource(R.drawable.mog95);
            mog95.setLayoutParams(new LayoutParams(75, 75));
            fuels.addView(mog95);
            fflag = 1;
        }
        if(m.get(0).getDiesel().equals("YES")) {
            ImageView diesel = new ImageView(this);
            diesel.setImageResource(R.drawable.diesel);
            diesel.setLayoutParams(new LayoutParams(75, 75));
            fuels.addView(diesel);
            fflag = 1;
        }
        if(m.get(0).getOnTheRun().equals("YES")){
            ImageView OnTheRun = new ImageView(this);
            OnTheRun.setImageResource(R.drawable.on_the_run);
            OnTheRun.setLayoutParams(new LayoutParams(179, 75));
            services.addView(OnTheRun);
            sflag =1;
        }
        if(m.get(0).getMobilMart().equals("YES")){
            ImageView MobilMart = new ImageView(this);
            MobilMart.setImageResource(R.drawable.mobil1_logo);
            MobilMart.setLayoutParams(new LayoutParams(75, 75));
            services.addView(MobilMart);
            sflag = 1;
        }
        if(m.get(0).getTheWayToGo().equals("YES")){

            ImageView TheWayToGo = new ImageView(this);
            TheWayToGo.setImageResource(R.drawable.stations);
            TheWayToGo.setLayoutParams(new LayoutParams(75, 75));
            services.addView(TheWayToGo);
            sflag = 1;
        }
        if(m.get(0).getCarWash().equals("YES")){
            ImageView CarWash = new ImageView(this);
            CarWash.setImageResource(R.drawable.car_wash);
            CarWash.setLayoutParams(new LayoutParams(75, 75));
            services.addView(CarWash);
            sflag = 1;
        }

        if (sflag ==0){
            servicesBar.setVisibility(View.GONE);
            services.setVisibility(View.GONE);
        }
        if (fflag == 0){
            fuelsBar.setVisibility(View.GONE);
            fuels.setVisibility(View.GONE);
        }

        stationCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
                startActivity(i);
            }
        });

        stationRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("google.navigation:q=" + latitude + "," + longitude);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);

            }
        });


    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.finish();
        System.exit(0);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
        System.exit(0);
    }
}