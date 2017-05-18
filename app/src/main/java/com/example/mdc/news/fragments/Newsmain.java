package com.example.mdc.news.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
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
import android.widget.RelativeLayout;

import com.example.mdc.news.LoginActivity;
import com.example.mdc.news.Match_recycler;
import com.example.mdc.news.NewsFragment;
import com.example.mdc.news.R;
import com.example.mdc.news.SimpleGestureFilter;
import com.example.mdc.news.fragments.Profile;
import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.io.File;
import java.io.FileOutputStream;

public class Newsmain extends Fragment
        implements NavigationView.OnNavigationItemSelectedListener, Animation.AnimationListener {
    private SimpleGestureFilter detector;
    private GestureDetectorCompat gestureDetectorCompat;
    public static int navItemIndex = 0;
    private Button sendEmail;
    DrawerLayout drawer;
    private EditText oldEmail, password;
    private ProgressBar progressBar;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    private LinearLayout linear;
    ActionBarDrawerToggle toggle;
    RelativeLayout rlayout;
    View view;
    private ViewGroup hiddenPanel;
    private boolean isPanelShown;
    private boolean isHidden;
    private FirebaseAuth firebaseAuth;
    LinearLayout llHeader, llFooter, llMain;
    int click = 0;
    Animation slideup, slidedown;
    Fragment fragment;
    ActionBar actionBar;
    File file;
    FileOutputStream fileoutputstream;
    private Menu menu;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_newsmain, container, false);
        init(view);
        return view;
    }


    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);*/


    private void init(View view){
        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        //setContentView(R.layout.activity_newsmain);

        try {
            Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
            //setSupportActionBar(toolbar);
            Firebase.setAndroidContext(getActivity());
            linear = (LinearLayout) view.findViewById(R.id.linear);
            CardView cardView = (CardView) view.findViewById(R.id.card_view);
          //  llHeader = (LinearLayout) findViewById(R.id.llHeader);
          //  llFooter = (LinearLayout) findViewById(R.id.llFooter);
            firebaseAuth = FirebaseAuth.getInstance();
          //  llMain = (LinearLayout) findViewById(R.id.llMain);
            hiddenPanel = (ViewGroup) view.findViewById(R.id.content_main);
            FrameLayout frame = (FrameLayout) view.findViewById(R.id.content_frame);
            drawer = (DrawerLayout) view.findViewById(R.id.drawer_layout);
            final BottomBar bottomBar = (BottomBar) view.findViewById(R.id.bottomBar);

             /*   linear.setOnClickListener(new View.OnClickListener() {
                         @Override
                      public void onClick(View v) {
                      getSupportActionBar().show();
                    //  boolean shouldGoInvisible = false;
                   //     boolean drawerOpen = shouldGoInvisible;
                       //getSupportActionBar().hide();
                  //    hideMenuItems(menu, !drawerOpen);
                   }
                  });*/

           bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
                @Override
                public void onTabSelected(@IdRes int tabId) {
                    if (tabId == R.id.tab_save) {

                       /* View v1 = getWindow().getDecorView().getRootView();
                        v1.setDrawingCacheEnabled(true);
                        Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
                        v1.setDrawingCacheEnabled(false);
                       ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 60, bytearrayoutputstream);
                   file = new File( Environment.getExternalStorageDirectory() + "/screenshot.png");
                        try
                        {
                            file.createNewFile();
                            fileoutputstream = new FileOutputStream(file);
                            fileoutputstream.write(bytearrayoutputstream.toByteArray());
                            fileoutputstream.close();
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }*/
                    }
                    if(tabId == R.id.tab_share)
                    {
                        Intent i = new Intent(Intent.ACTION_SEND);
                        i.setType("text/plain");
                        i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        i.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                        View v1 = getActivity().getWindow().getDecorView().getRootView();
                        v1.setDrawingCacheEnabled(true);
                        Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
                        v1.setDrawingCacheEnabled(false);
                        try {
                            File file = new File(getActivity().getApplication().getExternalCacheDir(),"logicchip.png");
                            FileOutputStream fOut = new FileOutputStream(file);
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                            fOut.flush();
                            fOut.close();
                            file.setReadable(true, false);
                            final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
                            intent.setType("image/png");
                            startActivity(Intent.createChooser(intent, "Share image via"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

            displaySelectedScreen(R.id.nav_home);
            NavigationView navigationView = (NavigationView) view.findViewById(R.id.nav_view);
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

            toggle = new ActionBarDrawerToggle(getActivity(), drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

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

            /*BS getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);

          

           slideup = AnimationUtils.loadAnimation(getApplicationContext(),
                    R.anim.slide_up);
            slideup.setAnimationListener(this);


            slidedown = AnimationUtils.loadAnimation(getApplicationContext(),
                    R.anim.slide_down);
            slidedown.setAnimationListener(this);*/


         /*rlayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (click == 0) {
                        llHeader.startAnimation(slidedown);
                        bottomBar.startAnimation(slideup);

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


 /* @Override

    public boolean onPrepareOptionsMenu(Menu menu) {

        // If the nav drawer is open, hide action items related to the content view
        boolean shouldGoInvisible = true;
        boolean drawerOpen = shouldGoInvisible;
        getSupportActionBar().hide();
        hideMenuItems(menu, !drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    private void hideMenuItems(Menu menu, boolean visible)
    {

        for(int i = 0; i < menu.size(); i++){

            menu.getItem(i).setVisible(visible);

        }
    }*/

    /*BS @Override
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
            case R.id.nav_logout:
                navItemIndex = 0;
                //setToolbarTitle();
                Log.e("hi","play");
                firebaseAuth.signOut();
                getActivity().finish();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
            //default:
               // fragment = new NewsFragment();
               // break;
        }

        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }



        DrawerLayout drawer = (DrawerLayout) view.findViewById(R.id.drawer_layout);
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
