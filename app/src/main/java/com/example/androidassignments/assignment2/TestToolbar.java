package com.example.androidassignments.assignment2;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.example.androidassignments.ListItemsActivity;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.androidassignments.databinding.ActivityTestToolbarBinding;

import com.example.androidassignments.R;

public class TestToolbar extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityTestToolbarBinding binding;
    Snackbar snackbar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTestToolbarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_test_toolbar);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, R.string.floating_button_text, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_test_toolbar);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public boolean onCreateOptionsMenu(Menu m){
        getMenuInflater().inflate(R.menu.toolbar_menu, m);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem mi){
        int id = mi.getItemId();
        View view = (View) findViewById(R.id.content);
        if (snackbar == null){
            snackbar = Snackbar.make(view, R.string.base_snackbar_text, Snackbar.LENGTH_LONG)
                    .setAction("Action", null);
        }

        switch(id){
            case R.id.action_one:
                Log.d("Toolbar", "Option 1 selected");
                snackbar.show();
                break;
            case R.id.action_two:
                Log.d("Toolbar", "Option 2 selected");
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.toolbar_dialog_title);
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                break;
            case R.id.action_three:
                Log.d("Toolbar", "Option 3 selected");
                AlertDialog.Builder customDialog =
                        new AlertDialog.Builder(this);
                // Get the layout inflater
                LayoutInflater inflater = this.getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.custom_dialog, null);
                customDialog.setView(dialogView)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                EditText edit = dialogView.findViewById(R.id.dialog_message_box);
                                String message = edit.getText().toString();
                                snackbar.setText(message);
                            }
                        })
                        .setNegativeButton(R.string.cancel,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                    }
                                });
                Dialog custom_dialog = customDialog.create();
                custom_dialog.show();
                break;
            case R.id.about:
                Log.d("Toolbar", "About selected");
                Toast toast = Toast.makeText(TestToolbar.this, R.string.about_toast_text, Toast.LENGTH_SHORT);
                toast.show();
        }
        return true;
    }
}