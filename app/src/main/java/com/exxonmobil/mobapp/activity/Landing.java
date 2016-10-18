package com.exxonmobil.mobapp.activity;

/**
 * Created by El Nakeeb on 7/14/2016.
 */

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import com.exxonmobil.mobapp.R;
import com.exxonmobil.mobapp.helper.SessionManager;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;


public class Landing extends AppCompatActivity{

    Button Stations;
    Button Products;
    Button Payment;
    Button Profile;
    GridView grid;
    String[] web = {
            "Stations",
            "Products",
            "Promotions",
            "Profile"
    } ;
    int[] imageId = {
            R.drawable.stations,
            R.drawable.products,
            R.drawable.payment,
            R.drawable.profile

    };

    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing);
        Toolbar toolbar= (Toolbar) findViewById(R.id.mytoolBar);
        setSupportActionBar(toolbar);

        PrimaryDrawerItem item1=new PrimaryDrawerItem().withIdentifier(1).withIcon(R.mipmap.mobil_tm).withBadge("Home").withName("Menu");

        SecondaryDrawerItem item2= (SecondaryDrawerItem) new SecondaryDrawerItem().withIdentifier(2).withIcon(R.mipmap.stations).withName("Stations");
        SecondaryDrawerItem item3= (SecondaryDrawerItem) new SecondaryDrawerItem().withIdentifier(2).withIcon(R.mipmap.products).withName("Products");
        SecondaryDrawerItem item4= (SecondaryDrawerItem) new SecondaryDrawerItem().withIdentifier(2).withIcon(R.mipmap.promotions).withName("Promotions");
        SecondaryDrawerItem item5= (SecondaryDrawerItem) new SecondaryDrawerItem().withIdentifier(2).withIcon(R.mipmap.profile).withName("Profile");
        SecondaryDrawerItem item6= (SecondaryDrawerItem) new SecondaryDrawerItem().withIdentifier(2).withIcon(R.mipmap.history).withName("History");
        SecondaryDrawerItem item7= (SecondaryDrawerItem) new SecondaryDrawerItem().withIdentifier(2).withIcon(R.mipmap.settings).withName("Settings");


        SharedPreferences prefs = getSharedPreferences("UserDetails", MODE_PRIVATE);
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.station_bg)
                .addProfiles(
                        new ProfileDrawerItem().withName(prefs.getString("name", "")).withEmail(prefs.getString("email", "")).withIcon(getResources().getDrawable(R.drawable.profile))
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();

        Drawer result = new DrawerBuilder().withActivity(this).withToolbar(toolbar).addDrawerItems(item1,item2,item3,item4,item5, item6, item7).withAccountHeader(headerResult).build();

        item2.mOnDrawerItemClickListener = new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                Intent intent = new Intent(getApplicationContext(), Stations.class);
                startActivity(intent);
                finish();
                return false;
            }
        };
        item3.mOnDrawerItemClickListener = new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                Intent intent = new Intent(getApplicationContext(), Products.class);
                startActivity(intent);
                finish();
                return false;
            }
        };
        item4.mOnDrawerItemClickListener = new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                Intent intent = new Intent(getApplicationContext(), Promotions.class);
                startActivity(intent);
                finish();
                return false;
            }
        };
        item5.mOnDrawerItemClickListener = new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                Intent intent = new Intent(getApplicationContext(), Profile.class);
                startActivity(intent);
                finish();
                return false;
            }
        };


        Stations = (Button) findViewById(R.id.stations_icon);
        Products = (Button) findViewById(R.id.products_icon);
        Payment = (Button) findViewById(R.id.promotions_icon);
        Profile = (Button) findViewById(R.id.profile_icon);

        // Session manager
        session = new SessionManager(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn().equals("0")) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(Landing.this, Login.class);
            startActivity(intent);
            finish();
        }

        Stations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Stations.class);
                startActivity(i);
                finish();
            }
        });

        Products.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Products.class);
                startActivity(i);
                finish();
            }
        });

        Payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Promotions.class);
                startActivity(i);
                finish();
            }
        });

        Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Profile.class);
                startActivity(i);
                finish();
            }
        });

    }

}