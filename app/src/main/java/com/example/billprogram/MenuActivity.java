package com.example.billprogram;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // 消费一览按钮跳转响应
        Button menuConsumption = (Button) findViewById(R.id.menu_consumption);
        menuConsumption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentConsumption = new Intent(MenuActivity.this, ConsumptionActivity.class);
                startActivity(intentConsumption);
            }
        });

        // 记一笔按钮跳转响应
        Button menuNote = (Button) findViewById(R.id.menu_note);
        menuNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentNote = new Intent(MenuActivity.this, noteConsumptionActivity.class);
                startActivity(intentNote);
            }
        });

        // 分类按钮跳转响应
        Button menuClassify = (Button) findViewById(R.id.menu_classify);
        menuClassify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentClassify = new Intent(MenuActivity.this, ClassifyActivity.class);
                startActivity(intentClassify);
            }
        });

        // 报表按钮响应
        Button menuReport = (Button) findViewById(R.id.menu_report);
        menuReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentReport = new Intent(MenuActivity.this, ReportTableActivity.class);
                startActivity(intentReport);
            }
        });
    }


}
