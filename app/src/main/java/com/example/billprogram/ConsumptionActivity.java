package com.example.billprogram;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.litepaltest.Config;
import com.example.litepaltest.Record;

import org.litepal.crud.DataSupport;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ConsumptionActivity extends BaseActivity {
    Button mButton;
    private CirclePercentView mCirclePercentView;

    private float month_spend;
    private float today_spend;
    private float every_day_spend;

    private float this_month_rest;
    private float every_day_can_spend;
    private Date rest_date;

    private int record_number;

    private float pre_money;

    private int rest_day;

    Calendar cal = Calendar.getInstance(); // 当前日期


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumption);


        // 找到预算
        List<Config> configs = DataSupport.findAll(Config.class);
        for(Config cig : configs){
            pre_money = Float.parseFloat(cig.getPreMoney());
        }

        sum_record();


        mCirclePercentView = (CirclePercentView) findViewById(R.id.circleView);
        mButton = (Button) findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 本月消费
                TextView textView_month = (TextView)findViewById(R.id.comsumption_month);
                month_spend = Math.round(month_spend);
                textView_month.setText(String.valueOf(month_spend));

                // 今日消费
                TextView textView_today_spend = (TextView)findViewById(R.id.comsumption_today);
                today_spend = Math.round(today_spend);
                textView_today_spend.setText(String.valueOf(today_spend));

                // 日均消费
                every_day_spend = month_spend/record_number;
                TextView textView_every_day_spend = (TextView)findViewById(R.id.comsumption_every_day);
                every_day_spend= Math.round(every_day_spend);
                textView_every_day_spend.setText(String.valueOf(every_day_spend));

                // 本月剩余
                this_month_rest = pre_money-month_spend;
                TextView textView_rest_month = (TextView)findViewById(R.id.comsumption_rest_month);
                this_month_rest = Math.round(this_month_rest);
                textView_rest_month.setText(String.valueOf(this_month_rest));

                // 距离月末, 仅更新当前月份的剩余日期
                rest_day = cal.getActualMaximum(Calendar.DAY_OF_MONTH)- cal.get(Calendar.DAY_OF_MONTH); // 当前月份最后一天 - 当前日期
                TextView textView_rest_day = (TextView)findViewById(R.id.comsumption_rest_day);
                textView_rest_day.setText(String.valueOf(rest_day+"天"));

                // 日均可用
                every_day_can_spend = this_month_rest / rest_day;
                TextView textView_every_use = (TextView)findViewById(R.id.comsumption_every_use);
                every_day_can_spend = Math.round(every_day_can_spend); // 四舍五入取整
                textView_every_use.setText(String.valueOf(every_day_can_spend));

                int n = (int) (((pre_money-this_month_rest)/pre_money)*100);
                //System.out.println(n);
                mCirclePercentView.setPercent(n);       // 设置百分比

            }
        });
    }

    public void sum_record(){   // 统计总共消费
        List<Record> records = DataSupport.findAll(Record.class);   // 遍历 record 表单
        for(Record record: records){
            month_spend += record.getSpend();
            record_number++;

            /* 统计今日消费 */
            Log.d("DAY_OF_MONTH",String.valueOf(cal.get(Calendar.DAY_OF_MONTH)) );
            Log.d("record.getDay()", String.valueOf(record.getDay()));
            if(record.getDay() == (int)cal.get(Calendar.DAY_OF_MONTH) -1 ){ // 判断记录中的日期是否和今日日期相同
                today_spend += record.getSpend();
            }
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        mButton.performClick(); // 点击


    }






}
