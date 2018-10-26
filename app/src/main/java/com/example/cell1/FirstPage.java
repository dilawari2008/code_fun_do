package com.example.cell1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ShareActionProvider;
import android.widget.Toast;

public class    FirstPage extends Activity {

    public static String a,b;
    public EditText editText1,editText2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);

            editText1=(EditText)findViewById(R.id.tv1);
            editText2=(EditText)findViewById(R.id.tv2);

        Button button=(Button)findViewById(R.id.btn1);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                SharedPreferences sharedPreferences=getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("Name",editText1.getText().toString());
                editor.putString("Phone",editText2.getText().toString());
                sharedPreferences.getBoolean("KEY_CHECKED",true);



                editor.apply();

              //  Toast.makeText(FirstPage.this,sharedPreferences.getString("Name","")+" "+sharedPreferences.getString("Phone",""),Toast.LENGTH_SHORT).show();


                Intent intent=new Intent(FirstPage.this,ToDoActivity.class);
                startActivity(intent);
                finish();




            }
        });



/*
        Button button=(Button)findViewById(R.id.btn1);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toast(v);
              //  ToDoActivity.phonephone=b;
               // ToDoActivity.namename=a;

                ToDoActivity.namename=a;
                ToDoActivity.phonephone=b;

                Intent intent=new Intent(FirstPage.this,ToDoActivity.class);
                startActivity(intent);
                finish();


            }
        });

*/

    }

/*
    public void toast(View view){
        editText1=(EditText)findViewById(R.id.tv1);
        a=editText1.getText().toString();
        editText2=(EditText)findViewById(R.id.tv2);
        b=editText2.getText().toString();
    }

*/

}
