package com.example.likhitha.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuInflater;
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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Newsmain extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static int navItemIndex = 0;
    private Button sendEmail;

    private EditText oldEmail, password;
    private ProgressBar progressBar;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    ActionBarDrawerToggle toggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsmain);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Firebase.setAndroidContext(this);

      /*  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
       /* ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);*/
        //toggle.syncState();
        displaySelectedScreen(R.id.nav_home);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
     /*   toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                    drawer.closeDrawer(Gravity.RIGHT);
                } else {
                    drawer.openDrawer(Gravity.RIGHT);
                }
            }
        });*/

       toggle = new ActionBarDrawerToggle(this,  drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            @Override
            public boolean onOptionsItemSelected(android.view.MenuItem item) {
                if (item != null && item.getItemId() == R.id.btnMyMenu) {
                    if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                        drawer.closeDrawer(Gravity.RIGHT);
                    } else {
                        drawer.openDrawer(Gravity.RIGHT);
                    }
                    return true;
                }
                return false;
            }
        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
    }


   /* @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }*/


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        displaySelectedScreen(item.getItemId());
        return true;
    }
    private void displaySelectedScreen(int itemId) {

        //creating fragment object
        Fragment fragment = null;

        //initializing the fragment object which is selected
        switch (itemId) {
            case R.id.nav_home:
                navItemIndex = 0;
                //setToolbarTitle();
                Log.e("hi","hi");
                fragment = new NewsFragment();
                break;
            case R.id.nav_play:
                navItemIndex = 0;
                //setToolbarTitle();
                Log.e("hi","hi");
                fragment = new Play();
                break;
            default:
                fragment = new NewsFragment();
                break;
        }

        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.END);
      }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        int menuToUse = R.menu.menu_right_side;

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(menuToUse, menu);

        return super.onCreateOptionsMenu(menu);
    }

  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // automatically handle clicks on the Home/Up button, so long
        MenuInflater menuInflater = getMenuInflater();
        //menuInflater.inflate(R.menu.Profile_customer, menu);
        menuInflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }
zz
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {


            case R.id.logout: {

                try {
                    auth.signOut();

                    //startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    // SharedPreferences settings = getSharedPreferences(Session.PREFS_NAME, 0);
                    //SharedPreferences.Editor editor = settings.edit();
                    // editor.remove("logged");
                    //editor.clear();
                    //ditor.commit();
                } catch (Exception e) {
                    Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(this, "Logout Successfully!", Toast.LENGTH_SHORT).show();
                return true;
            }
            case R.id.changepwd: {

                try {
                    oldEmail.setVisibility(View.VISIBLE);
                    sendEmail.setVisibility(View.VISIBLE);

                } catch (Exception e) {
                    Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
                }
                return true;
            }

        }

        return false;
    }*/

}
