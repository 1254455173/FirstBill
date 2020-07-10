package com.example.billprogram;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.litepaltest.Catetalog;
import com.example.litepaltest.Config;
import com.example.litepaltest.Record;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class noteConsumptionActivity extends BaseActivity implements View.OnClickListener{
    private ArrayList<String> array_classify_name = new ArrayList<>();   // 分类集合
    private String str;

    private ArrayAdapter<String> adapter;


    Calendar calendar = Calendar.getInstance();
    private int record_year;
    private int record_month;
    private int record_day=0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_consumption);

        final EditText ed_consumption = (EditText) findViewById(R.id.note_consumption); // 消费
        final EditText ed_comment = (EditText) findViewById(R.id.note_comment); // 评论

        final Button dateButton = (Button) findViewById(R.id.date_dialog_button); // 按钮点击后，出现日历,设置 record 的日期
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_date_dialog(dateButton);
            }
        });

        catetalog_bind();   // 绑定分类名字到控件

        // 知道当前数据选择了哪一个分类
        find_item();



        Button btn_note = (Button) findViewById(R.id.note_btn);
        btn_note.setOnClickListener(new View.OnClickListener() {    // 响应数据更新
            @Override
            public void onClick(View v) {
                Connector.getDatabase(); // 创建数据库,并创建表 book
                Record record = new Record();   // 保存数据
                final Date date = new Date();
                record.setDate(date);

                /* 设置日期 */
                record.setDay(record_day);

                //System.out.println(ed_consumption.getText().toString());
                record.setSpend(Float.parseFloat(ed_consumption.getText().toString()));
                record.setComment(ed_comment.getText().toString());

                // 同时这里要更新分类的 record 记录
                List<Catetalog> catetalogs = DataSupport.where("name = ?", str).find(Catetalog.class);  // 先找到
                System.out.println(catetalogs.size());
                System.out.println(str);

                for(Catetalog catetalog: catetalogs){   // 全部更新，保存最后一个类到记录中
                    // catetalog.setName(str);
                    catetalog.setRecords(record);
                    catetalog.setRecordsLen(catetalog.getRecordsLen()+1);
                    catetalog.updateAll("name = ?", str);
                    record.setCatetalgo(catetalog);
                    catetalog.save();
                }
                record.save();

                // 这里还要更新预算 Config 表
                Config cig = DataSupport.findFirst(Config.class);   // 先要找到，不妨以第一个

                Float pre = new Float(cig.getPreMoney());
                Float spendMoney = new Float(record.getSpend());
                String nowMoney = String.valueOf(pre-spendMoney);
                cig.setId(1);
                cig.setPreMoney(nowMoney);
                cig.updateAll("id = ?", "1");

                Toast.makeText(getApplicationContext(), " 记录成功", Toast.LENGTH_LONG).show();
            }
        });


    }


    @Override
    public void onClick(View arg0){

    }

    public void catetalog_bind(){   // 绑定分类名字到控件
        // 获得所有分类
        List<Catetalog> catetalogs = DataSupport.findAll(Catetalog.class);
        // 将所有分类的名字绑定在控件上
        for(Catetalog catetalog: catetalogs){
            array_classify_name.add(catetalog.getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,array_classify_name);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        Spinner spinner = (Spinner) findViewById(R.id.note_type);
        spinner.setAdapter(adapter);

    }

    public void find_item(){    // 判断 spinner 选中了哪个元素，将元素的字符串返回
        final Spinner sp;
        sp = (Spinner) findViewById(R.id.note_type);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                //拿到被选择项的值
                str = (String) sp.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });
    }

    public void show_date_dialog(final Button dateButton) { // 显示日期对话框，设置 record 日期
            DatePickerDialog date_dialog=new DatePickerDialog(noteConsumptionActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    /*record_day = dayOfMonth-1;
                    record_year = year;
                    record_month = monthOfYear;*/

                    dateButton.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
                }
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            date_dialog.show();
    }

}
