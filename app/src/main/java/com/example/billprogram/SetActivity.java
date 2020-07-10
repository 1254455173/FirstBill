package com.example.billprogram;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.litepaltest.Config;

public class SetActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);

        final EditText et = (EditText) findViewById(R.id.set_update_premoney);  // 更新
        Button btn_premoney = (Button) findViewById(R.id.set_update);
        btn_premoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 更新预算
                Config cig = new Config();
                cig.setPreMoney(et.getText().toString());
                cig.setId(1);
                cig.save();
                cig.updateAll("id = ?", "1"); // 输入更新值

                Toast.makeText(getApplicationContext(), " 更新成功为 "+ cig.getPreMoney(), Toast.LENGTH_LONG).show();
            }
        });

    }
}
