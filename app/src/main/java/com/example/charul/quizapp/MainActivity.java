package com.example.charul.quizapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);

        drawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView=(NavigationView)findViewById(R.id.Navigation);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
            return super.onOptionsItemSelected(item);
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();

        if(id==R.id.dashboard){
            Toast.makeText(this,"Dashboard",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this,MainActivity.class));
        }
        if(id==R.id.Categories){
            Toast.makeText(this,"Categories",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this,CategoriesActivity.class));
        }
        if(id==R.id.profile){
            Toast.makeText(this,"Profile",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this,ProfileActivity.class));
        }
        if(id==R.id.Settings){
            Toast.makeText(this,"Settings",Toast.LENGTH_SHORT).show();
        }
        if(id==R.id.About){
            Toast.makeText(this,"About",Toast.LENGTH_SHORT).show();
        }
        if(id==R.id.Logout){
            Toast.makeText(this,"Logout",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
        }

        return false;
    }
}