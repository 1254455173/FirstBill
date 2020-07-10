package com.example.billprogram;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * Created by 陈 on 2020/5/11.
 */

public class BaseActivity extends AppCompatActivity {
   //private ForceOffLineReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
    }


    @Override   // 该类中引入菜单
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override  // 定义菜单事件
    public boolean onOptionsItemSelected(MenuItem item) { // 定义菜单响应事件
        switch (item.getItemId()) {
            case R.id.menu_set:
                Intent intentSetMenu = new Intent(this, SetActivity.class);
                startActivity(intentSetMenu);
                //Toast.makeText(this,"set_click", Toast.LENGTH_SHORT).show();
                  break;
            case R.id.menu_mainMenu:
                Intent intentMainMenu = new Intent(this, MenuActivity.class);
                startActivity(intentMainMenu);
                //Toast.makeText(this,"set_click", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(this, "faild click", Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}
