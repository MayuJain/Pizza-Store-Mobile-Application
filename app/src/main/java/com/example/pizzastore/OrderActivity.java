package com.example.pizzastore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class OrderActivity extends AppCompatActivity {
    TextView toppingsListtextview;
    Button btnFInish;
    TextView basepricevalue;
    TextView topingscost;
    TextView deliverycost;
    TextView totalcost;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        basepricevalue = findViewById(R.id.basepricevalue);
        topingscost = findViewById(R.id.topingscost);
        btnFInish = findViewById(R.id.btnFInish);
        toppingsListtextview = findViewById(R.id.toppingsListtextview);
        totalcost = findViewById(R.id.totalcost);
        deliverycost = findViewById(R.id.deliverycost);
        Pizza p = getIntent().getExtras().getParcelable(MainActivity.PIZZA_KEY);

        float deliverycharges = 4;
        double topingsCharges = p.Toppings.size() * 1.5;
        double BaseCharges = 6.5;
        double totalcharges = 0;
        String topingslist = "";

        for(int k = 0; k < p.Toppings.size();k++){
            if(k == p.Toppings.size()-1){
                topingslist = topingslist + p.Toppings.get(k);
            }else{
                topingslist = topingslist + p.Toppings.get(k) + ",";
            }
        }
        String delivery = p.delivery;
        totalcharges = BaseCharges + p.Toppings.size() * 1.5;
        deliverycost.setText("0.0" + "$");

        if (delivery.contains("true")) {
            deliverycost.setText(Double.toString(deliverycharges) + "$");
            totalcharges += deliverycharges;
        }

        basepricevalue.setText(Double.toString(BaseCharges) + "$");
        topingscost.setText(Double.toString(topingsCharges) + "$");
        totalcost.setText(Double.toString(totalcharges) + "$");
        toppingsListtextview.setText(topingslist);
        btnFInish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }
}
