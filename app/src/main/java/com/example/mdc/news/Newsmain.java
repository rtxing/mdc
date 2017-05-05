package com.example.mdc.news;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.cocosw.bottomsheet.BottomSheet;
import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Newsmain extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Animation.AnimationListener {
    public static int navItemIndex = 0;
    private Button sendEmail;
    DrawerLayout drawer;
    private EditText oldEmail, password;
    private ProgressBar progressBar;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    ActionBarDrawerToggle toggle;
    View view;
    private ViewGroup hiddenPanel;
    private boolean isPanelShown;
    private boolean isHidden;

    LinearLayout llHeader, llFooter, llMain;
    int click = 0;
    Animation slideup, slidedown;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsmain);
        try {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            Firebase.setAndroidContext(this);
            //  actionBar.setDisplayHomeAsUpEnabled(true);
            CardView cardView = (CardView) findViewById(R.id.card_view);
            llHeader = (LinearLayout) findViewById(R.id.llHeader);
            llFooter = (LinearLayout) findViewById(R.id.llFooter);

            llMain = (LinearLayout) findViewById(R.id.llMain);
            hiddenPanel = (ViewGroup) findViewById(R.id.content_main);
            FrameLayout frame = (FrameLayout) findViewById(R.id.content_frame);
            drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
       /* ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);*/
            //toggle.syncState();
            displaySelectedScreen(R.id.nav_home);
            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                        drawer.closeDrawer(Gravity.RIGHT);
                    } else {
                        drawer.openDrawer(Gravity.RIGHT);
                    }
                }
            });

            toggle = new ActionBarDrawerToggle(this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

                //   @Override
                public boolean onOptionsItemSelected(MenuItem item) {

                    if (item.getItemId() == R.id.btnMyMenu) {
                        if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                            drawer.closeDrawer(Gravity.RIGHT);
                        } else {
                            drawer.openDrawer(Gravity.RIGHT);
                        }
                        return true;
                    }
                    return true;
                }
            };
            drawer.setDrawerListener(toggle);
            toggle.syncState();
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
            frame.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                  //  openAndroidBottomMenu(view);

                    return true;
                }
            });

           /* cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isHidden) showSystemUi();
                    else hideSystemUi();
                }
            });

           /* slideup = AnimationUtils.loadAnimation(getApplicationContext(),
                    R.anim.slide_up);
            slideup.setAnimationListener(this);


            slidedown = AnimationUtils.loadAnimation(getApplicationContext(),
                    R.anim.slide_down);
            slidedown.setAnimationListener(this);


            llMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (click == 0) {
                        llHeader.startAnimation(slidedown);
                        llFooter.startAnimation(slideup);

                        llHeader.setVisibility(View.VISIBLE);
                        llFooter.setVisibility(View.VISIBLE);
                        click++;
                    } else {

                        llHeader.setVisibility(View.GONE);
                        llFooter.setVisibility(View.GONE);
                        click = 0;

                    }

                }
            });*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
  /*  @Override
    protected void onResume() {
        super.onResume();

        hideSystemUi();
    }

    private void hideSystemUi() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_IMMERSIVE
        );
        getSupportActionBar().hide();
        isHidden = true;
       // toggle.setText("Hide");
    }

    private void showSystemUi() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_VISIBLE
        );

        getSupportActionBar().show();
        isHidden = false;
    }
       // toggle.setText("Show");
  /*  public void slideUpDown(final View view) {
        if (!isPanelShown()) {
            // Show the panel
            Animation bottomUp = AnimationUtils.loadAnimation(this,
                    R.anim.slide_up);

            hiddenPanel.startAnimation(bottomUp);
            hiddenPanel.setVisibility(View.VISIBLE);
        }
        else {
            // Hide the Panel
            Animation bottomDown = AnimationUtils.loadAnimation(this,
                    R.anim.slide_down);

            hiddenPanel.startAnimation(bottomDown);
            hiddenPanel.setVisibility(View.GONE);
        }
    }

    private boolean isPanelShown() {
        return hiddenPanel.getVisibility() == View.VISIBLE;
    }

    public void openAndroidBottomMenu(View view) {

        new BottomSheet.Builder(this).sheet(R.menu.bottom_menu_item).listener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case R.id.menu_save:
                        break;

                    case R.id.menu_share:
                        break;
                }
            }
        }).show();
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
   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
       int menuToUse = R.menu.menu_right_side;

       MenuInflater inflater = getMenuInflater();

       inflater.inflate(menuToUse, menu);
       MenuItem item = menu.findItem(R.id.btnMyMenu);
       item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {

           @Override
           public boolean onMenuItemClick(MenuItem item) {
               if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                   drawer.closeDrawer(Gravity.RIGHT);
               } else {
                   drawer.openDrawer(Gravity.RIGHT);
               }
               return true;
           }
       });
       return true;
      // return super.onCreateOptionsMenu(menu);
   }

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
                Log.e("hi","play");
                fragment = new Match_recycler();
                break;
            case R.id.nav_profile:
                navItemIndex = 0;
                //setToolbarTitle();
                Log.e("hi","play");
                fragment = new Profile();
                break;
            //default:
               // fragment = new NewsFragment();
               // break;
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
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

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
