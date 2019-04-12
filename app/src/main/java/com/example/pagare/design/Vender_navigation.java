package com.example.pagare.design;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;

public class Vender_navigation extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    EditText et_add,et_price;
    MultiAutoCompleteTextView mtv_basicIngredients;
    Button btn_add;

    String str_et_add,str_et_price,str_mtv_basicIngredients;

    TextView seller_name;
    TextView shop_name;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vender_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavigationView nv= (NavigationView) findViewById(R.id.nav_view);
        View wel=nv.getHeaderView(0);
        seller_name = (TextView) wel.findViewById(R.id.ownername);
        shop_name = (TextView) wel.findViewById(R.id.shopname);


        SharedPreferences sharedPreferences=Vender_navigation.this.getSharedPreferences("userid", Context.MODE_PRIVATE);
        String data1=sharedPreferences.getString("custname","");
        seller_name.setText(data1);

        SharedPreferences sharedPref = Vender_navigation.this.getSharedPreferences("userid1", Context.MODE_PRIVATE);
        String show=sharedPref.getString("shopname","");
        shop_name.setText(show);

        /*SharedPreferences sharedPreferencess=Vender_navigation.this.getSharedPreferences("userid1",Context.MODE_PRIVATE);
        String data=sharedPreferencess.getString("custname","");
        shop_name.setText(data);*/





        FragmentManager fragmentManager=getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame2,new vendor_home_fragment()).commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }



    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "Go to Navigation Drawer to Logout...", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.vender_navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager=getFragmentManager();

        if (id == R.id.nav_home) {
            fragmentManager.beginTransaction().replace(R.id.content_frame2,new vendor_home_fragment()).commit();
            // Handle the camera action
        } else if (id == R.id.nav_Profile) {
            fragmentManager.beginTransaction().replace(R.id.content_frame2,new vendor_profile_fragment()).commit();
        } else if (id == R.id.nav_add_item) {
            fragmentManager.beginTransaction().replace(R.id.content_frame2,new add_item_fragment()).commit();
        } else if (id == R.id.nav_update_item) {
            fragmentManager.beginTransaction().replace(R.id.content_frame2,new update_item_fragment()).commit();
        } else if (id == R.id.nav_delete_item) {
            fragmentManager.beginTransaction().replace(R.id.content_frame2,new delete_item_fragment()).commit();
        } else if (id == R.id.nav_view_menu) {
            fragmentManager.beginTransaction().replace(R.id.content_frame2,new view_menu_fragment()).commit();
        } else if (id == R.id.nav_logout) {
            fragmentManager.beginTransaction().replace(R.id.content_frame2,new vendor_logout_fragment()).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void yes(View view) {

        finish();
        Toast.makeText(Vender_navigation.this,"Logout Successful...",Toast.LENGTH_SHORT).show();

    }

    public void no(View view) {

        startActivity(new Intent(this,Vender_navigation.class));
        Toast.makeText(Vender_navigation.this,"Redirecting to Home...",Toast.LENGTH_SHORT).show();
    }


}
