package com.example.billprogram;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.litepaltest.Catetalog;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.ArrayList;
import java.util.List;


public class ClassifyActivity extends BaseActivity implements View.OnClickListener {
    private ArrayList<String>data=new ArrayList<>();    // 设置两个全局变量为视图内容，当点击的时候进行更改视图

    private ArrayList<String> dataTimes = new ArrayList<>();

    private ListView listView;

    private List<Catetalog> catetalogs;

    private ArrayList<String> dataLine = new ArrayList<>();

    private ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // 实现 CRUD
    @Override
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.classify_plus:    // 点击了添加按钮，确定则添加一个分类表单在数据库中
                final EditText et = new EditText(this); // 分类表单
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setTitle("设置添加");
                dialog.setIcon(R.drawable.plus2);
                dialog.setView(et);
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Connector.getDatabase(); // 创建数据库,并创建表 Catetalog
                        // System.out.println(et.getText().toString());
                        Catetalog catetalog= new Catetalog();
                        catetalog.setName(et.getText().toString()); // 分类名字,作为唯一标识
                        catetalog.save();
                        refresh();
                        Toast.makeText(getApplicationContext(),"添加成功", Toast.LENGTH_LONG).show();
                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),"取消添加", Toast.LENGTH_LONG).show();
                    }
                });
                dialog.show();
                break;
            case R.id.classify_delete: // 删除分类
                final EditText et1 = new EditText(this);
                AlertDialog.Builder dialog1 = new AlertDialog.Builder(this);
                dialog1.setTitle("设置删除");
                dialog1.setIcon(R.drawable.delete2);
                dialog1.setView(et1);
                dialog1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DataSupport.deleteAll(Catetalog.class, "name = ?", et1.getText().toString());
                        refresh();
                        Toast.makeText(getApplicationContext(),"删除成功", Toast.LENGTH_LONG).show();
                    }
                });
                dialog1.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),"取消删除", Toast.LENGTH_LONG).show();

                    }
                });
                dialog1.show();
                break;
            case R.id.classify_edit: // 更改分类名字 // 有点儿复杂，涉及两个输入框
                LayoutInflater factory= LayoutInflater.from(this);
                final View textEntryView = factory.inflate(R.layout.update, null);

                final EditText et22 = (EditText)textEntryView.findViewById(R.id.editTextNum);  // 更新值
                final EditText et2 = (EditText) textEntryView.findViewById(R.id.editTextName); // 更新对象


                AlertDialog.Builder dialog2 = new AlertDialog.Builder(this);
                dialog2.setTitle("设置更新");
                dialog2.setIcon(R.drawable.edit2);
                dialog2.setView(textEntryView);
                dialog2.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Catetalog catetalog2 = new Catetalog();
                        catetalog2.setName(et2.getText().toString()); // 找到更新的对像
                        catetalog2.updateAll("name = ?", et22.getText().toString()); // 输入更新值

                        refresh();
                        Toast.makeText(getApplicationContext(), "编辑成功", Toast.LENGTH_LONG).show();
                    }
                });
                dialog2.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "取消编辑", Toast.LENGTH_LONG).show();
                    }
                });
                dialog2.show();
                break;

            default:
                break;
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        onDraw(); // 初始化绘制图像
    }


    public void refresh(){      // 动态刷新             // 实现热更新
        data.clear();
        catetalogs.clear();
        dataLine.clear(); // 先清空原有数据
        dataTimes.clear();

        onDraw(); // 清空数据列表后重新绘制


        //adapter.notifyDataSetChanged();

    }

    public void onStop(){   // bug
        super.onStop();
        this.finish();  // 这个也很关键
    }

    public void onDraw(){

        /* 查询 catetalog 表单, 获得名字和次数，这里比较笨，因为每次都将所有 item 绘制了一遍*/
        catetalogs = DataSupport.findAll(Catetalog.class); // 查询
        for(Catetalog c : catetalogs){  // 初始化视图
            data.add(c.getName());
            dataTimes.add(String.valueOf(c.getRecordsLen()));
        }

        /* 居中对齐效果 */
        String s1;
        String s2;
        for(int i = 0; i< data.size(); i++){  // 添加元素
            s1 = data.get(i);
            s2 = dataTimes.get(i);
            String s;
            s = "             "+s1+"                                                "+s2;  // 对齐方式比较简陋
            dataLine.add(s);

        }

        /* 通过适配器加入到 listView 中*/
        setContentView(R.layout.activity_classify);
        adapter = new ArrayAdapter<String>(
                ClassifyActivity.this, android.R.layout.simple_list_item_1, dataLine);
        listView= (ListView) findViewById(R.id.list_view);
        //listView.setItemsCanFocus(false);   // 让 item 无法获取焦点
        listView.setAdapter(adapter);
    }



}
