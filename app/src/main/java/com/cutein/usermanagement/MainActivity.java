    package com.cutein.usermanagement;

import android.content.Intent;
import androidx.annotation.NonNull;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.view.GravityCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;

    public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

        private FirebaseAuth mAuth;
        private Toolbar mToolbar;

        private ViewPager mViewPager;
      //  private SectionsPagerAdapter mSectionsPagerAdapter;

        private DatabaseReference mUserRef;

        private TabLayout mTabLayout;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            mAuth = FirebaseAuth.getInstance();

           mToolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(mToolbar);
            getSupportActionBar().setTitle("Cute Inn");



            if (mAuth.getCurrentUser() != null) {


                mUserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid());

            }
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            NavigationView navigationView = findViewById(R.id.nav_view);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();
            navigationView.setNavigationItemSelectedListener(this);


//            //Tabs
//            mViewPager = (ViewPager) findViewById(R.id.main_tabPager);
//            //mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
//
//           // mViewPager.setAdapter(mSectionsPagerAdapter);
//
//            mTabLayout = (TabLayout) findViewById(R.id.main_tabs);
//            mTabLayout.setupWithViewPager(mViewPager);

        }



        @Override
        public void onBackPressed() {
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        }

        @Override
        public void onStart() {
            super.onStart();
            // Check if user is signed in (non-null) and update UI accordingly.
            FirebaseUser currentUser = mAuth.getCurrentUser();

            if(currentUser == null){

                sendToStart();

            } else {

                mUserRef.child("online").setValue("true");

            }

        }


        @Override
        protected void onStop() {
            super.onStop();

            FirebaseUser currentUser = mAuth.getCurrentUser();

            if(currentUser != null) {

                mUserRef.child("online").setValue(ServerValue.TIMESTAMP);

            }

        }

        private void sendToStart() {

            Intent startIntent = new Intent(MainActivity.this, StartActivity.class);
            startActivity(startIntent);
            finish();

        }


        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            super.onCreateOptionsMenu(menu);

            getMenuInflater().inflate(R.menu.main_menu, menu);


            return true;
        }


        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            super.onOptionsItemSelected(item);

            if(item.getItemId() == R.id.main_logout_btn){

                mUserRef.child("online").setValue(ServerValue.TIMESTAMP);

                FirebaseAuth.getInstance().signOut();
                sendToStart();

            }

            if(item.getItemId() == R.id.main_settings_btn){

               // Intent settingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
               // startActivity(settingsIntent);

            }

            if(item.getItemId() == R.id.main_all_btn){

               // Intent settingsIntent = new Intent(MainActivity.this, UsersActivity.class);
                //startActivity(settingsIntent);

            }

            return true;
        }

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            displaySelectedScreen(item.getItemId());


            return false;
        }

        private void displaySelectedScreen(int itemId) {

            //creating fragment object
            Fragment fragment = null;

            //initializing the fragment object which is selected
            switch (itemId) {
                case R.id.nav_home:
                    fragment = new menu1();
                    break;
                case R.id.nav_gallery:




            }

            //replacing the fragment
            if (fragment != null) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.commit();
            }

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);


        }
    }
