package com.example.codingtest;

import android.os.Bundle;

import com.example.codingtest.data.CommitList;
import com.example.codingtest.data.CommitListAdapter;
import com.example.codingtest.data.Profile;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.DisplayMetrics;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codingtest.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    public static Profile profile;
    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    public static int width;
    public static int date_width1;
    public static int date_width2;
    public static int message_margin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // need to refactor (move to another file if get time)start
        DisplayMetrics displayMetrics = new DisplayMetrics();
        date_width1 = getResources().getDimensionPixelSize(R.dimen.date_width);
        date_width2 = getResources().getDimensionPixelSize(R.dimen.date_width2);
        message_margin = getResources().getDimensionPixelSize(R.dimen.message_margin);
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        width = displayMetrics.widthPixels;
        profile = new Profile();
        // need to refactor (move to another file if get time)end
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().hide();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public static Profile getProfile() {
        return profile;
    }
}