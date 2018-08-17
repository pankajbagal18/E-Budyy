package com.example.shubham.myapplication;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class DashboardActivity extends AppCompatActivity {
    private static final String TAG = "DashboardActivity";



    private ViewPager mViewPager;
    private int menuId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);



        // Set up the ViewPager with the sections adapter.




      ;
       // mBottomNavigationView.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
        final BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_bar);
      //  BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
      //  bottomNavigationView.getMenu().findItem(1).setChecked(true);
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                menuId=item.getItemId();

               // mMenuId = item.getItemId();
                for (int i = 0; i < bottomNavigationView.getMenu().size(); i++) {
                    MenuItem menuItem = bottomNavigationView.getMenu().getItem(i);
                    boolean isChecked = menuItem.getItemId() == item.getItemId();
                    menuItem.setChecked(isChecked);
                }

                switch (item.getItemId()){
                    //case R.id.ic_arrow:

                       // break;

                    case R.id.nav_home:
                        Intent intent1 = new Intent(DashboardActivity.this, MainActivity.class);
                        startActivity(intent1);
                        break;

                    case R.id.nav_mycollection:
                      //  Intent intent2 = new Intent(DashboardActivity.this, MyCollectionActivity.class);
                      //  startActivity(intent2);
                        break;

                  //  case R.id.nav:
                  //      Intent intent3 = new Intent(DashboardActivity.this, ActivityThree.class);
                  //      startActivity(intent3);
                   //     break;

                   // case R.id.ic_backup:
                   //     Intent intent4 = new Intent(MainActivity.this, ActivityFour.class);
                   //     startActivity(intent4);
                    //    break;
                }


                return false;
            }
        });

    }



}