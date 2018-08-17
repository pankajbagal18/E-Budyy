package com.example.shubham.myapplication;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;
import android.content.Intent;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Home()).commit();
        }
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Home()).commit();
                break;

            case R.id.nav_notes:
                Intent i1 = new Intent(MainActivity.this,Notes.class);
                startActivity(i1);
                break;
            case R.id.nav_signout:
                //Intent i2 = new Intent(MainActivity.this,MyCollectionActivity.class);
               // startActivity(i2);
                //add activities here
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getApplicationContext(),"Signed Out",Toast.LENGTH_SHORT).show();
                Intent i=new Intent(MainActivity.this,Login.class);
                startActivity(i);
                break;
            case R.id.nav_attend:
               // getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                   //     new ProfileFragment()).commit();
                //add fragments here
                Intent i2 = new Intent(MainActivity.this,Attendance.class);
                 startActivity(i2);

                break;
            case R.id.nav_budget:
                Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show();
                Intent i3 = new Intent(MainActivity.this,BudgetPlanner.class);
                startActivity(i3);
                break;



        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

}