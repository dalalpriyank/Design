package com.example.pagare.design;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class cust_vender_register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_vender_register);

        Button customer,vendor;

        customer= (Button) findViewById(R.id.btn_cust);
        vendor= (Button) findViewById(R.id.btn_vendor);

        customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(cust_vender_register.this,MainActivity.class);
                startActivity(i);
            }
        });


        vendor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(cust_vender_register.this,Vender_login.class);
                startActivity(i);
            }
        });

    }

}
