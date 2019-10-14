package com.example.pizzastore;


/**
 *
 * Homework 2
 * MainActivity.java
 *Group 19
 *Mayuri Jain, Narendra Pahuja
 */

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    final static int REQ_CODE = 100;
    Button btnAddToppings;
    Button btnClearPizza;
    Button btnCheckout;
    ProgressBar toppingsProgressBar;
    CheckBox deliveryCheckBox;
    GridLayout toppingsGridView;
    ArrayList<String> selectedtopping = new ArrayList<>();
    ImageView toppingImage;
    String deliveryflag = "false";
    static String PIZZA_KEY = "PIZZA_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAddToppings = findViewById(R.id.btnAddTopings);
        btnClearPizza = findViewById(R.id.btnClearPizza);
        btnCheckout = findViewById(R.id.btnCheckout);
        toppingsProgressBar = findViewById(R.id.toppingsProgressBar);
        deliveryCheckBox = findViewById(R.id.delivery);
        toppingsGridView = findViewById(R.id.gridLayout);
        toppingsGridView.setColumnCount(5);
        toppingsGridView.setRowCount(2);
        toppingsGridView.setForegroundGravity(Gravity.CENTER);
        toppingsProgressBar.setProgress(0);
        toppingsProgressBar.setMax(10);
        final String[] toppings = getResources().getStringArray(R.array.toppings);


        AlertDialog.Builder topinglist = new AlertDialog.Builder(this);
        topinglist.setTitle(getString(R.string.chooseTopping)).setCancelable(true)
                .setItems(toppings, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                            selectedtopping.add(toppings[i]);
                            refreshTopings(toppings[i]);
                            toppingsProgressBar.setProgress(selectedtopping.size());
                    }
                });

        final AlertDialog listdialog = topinglist.create();
        btnClearPizza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedtopping.clear();
                toppingsGridView.removeAllViews();
                toppingsProgressBar.setProgress(0);


            }
        });

        btnAddToppings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (selectedtopping.size() < 10) {
                    listdialog.show();
                }else{
                    Toast.makeText(getApplicationContext(), R.string.maximumcapacity, Toast.LENGTH_SHORT).show();
                }

            }
        });
        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                if(selectedtopping.size() > 0){
                    Intent intent = new Intent(MainActivity.this, OrderActivity.class);
                    deliveryflag = deliveryCheckBox.isChecked() == true ? "true" : "false";
                    Pizza p = new Pizza(selectedtopping, deliveryflag);
                    intent.putExtra(PIZZA_KEY, new Pizza(selectedtopping, deliveryflag));
                    startActivityForResult(intent, REQ_CODE);  
                }else{
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.minimumTopping), Toast.LENGTH_SHORT).show();
                }
               
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQ_CODE){
            if(resultCode == RESULT_OK){
                Log.d("demo in result", "clearing data");
                selectedtopping.clear();
                toppingsGridView.removeAllViews();
                toppingsProgressBar.setProgress(0);
                deliveryCheckBox.setChecked(false);

            }
        }
    }

    public void refreshTopings(String topping) {
        toppingImage = new ImageView(this);

        switch (topping) {
            case "Bacon":
                toppingImage.setImageResource(R.drawable.bacon);
                toppingImage.setTag("Bacon");
                break;
            case "Cheese":
                toppingImage.setImageResource(R.drawable.cheese);
                toppingImage.setTag("Cheese");
                break;
            case "Garlic":
                toppingImage.setImageResource(R.drawable.garlic);
                toppingImage.setTag("Garlic");
                break;
            case "Green Pepper":
                toppingImage.setImageResource(R.drawable.green_pepper);
                toppingImage.setTag("Green Pepper");
                break;
            case "Onions":
                toppingImage.setImageResource(R.drawable.onion);
                toppingImage.setTag("Onions");
                break;
            case "Olives":
                toppingImage.setImageResource(R.drawable.olive);
                toppingImage.setTag("Olives");
                break;
            case "Red Pepper":
                toppingImage.setImageResource(R.drawable.red_pepper);
                toppingImage.setTag("Red Pepper");
                break;
            case "Mushroom":
                toppingImage.setImageResource(R.drawable.mashroom);
                toppingImage.setTag("Mushroom");
                break;


        }

        AbsListView.LayoutParams lay = new AbsListView.LayoutParams(150, 150);
        toppingImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toppingsGridView.removeView(view);
                selectedtopping.remove(view.getTag());
                toppingsProgressBar.setProgress(selectedtopping.size());
            }
        });
        toppingsGridView.addView(toppingImage, lay);

    }
}
