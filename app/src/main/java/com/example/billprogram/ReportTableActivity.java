package com.example.billprogram;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.example.litepaltest.Record;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ReportTableActivity extends BaseActivity {
    private LineChart line;    // 控件对象 不能在这里初始化对象！！！！！！！！！！！！！！
    private ArrayList<Entry> list = new ArrayList<>(); // 数据集合
    private LineDataSet set; // 设置数据

    Calendar calendar = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_table);


        /*String[] mMonths = new String[]{
                "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Okt", "Nov", "Dec"};*/


        line = (LineChart) findViewById(R.id.lineChart1);
        /*for (int i = 0; i < 30; i++) {
            list.add(new Entry(i, (float) (Math.random() * 1000)));
        }*/
        all_records();  // 设置 list 的 record 数据

        setLenged();
        setData(list);


        //设置绘制折线的动画时间
        line.animateX(2500);
        line.animateY(2500);
        //设置折线为圆滑折线(加在上面的setLine方法里)
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        //设置数值选择监听
        //line.setOnChartValueSelectedListener(this);
        //后台绘制
        line.setDrawGridBackground(false);
        //设置支持触控手势
        line.setTouchEnabled(true);

        //设置缩放
        line.setDragEnabled(true);
        //设置推动
        line.setScaleEnabled(true);
        //如果禁用,扩展可以在x轴和y轴分别完成
        line.setPinchZoom(true);

        // 重新绘制
        // line.invalidate();

        setXY();

        /*设置覆盖物*/
        CustomMPLineChartMarkerView mv = new CustomMPLineChartMarkerView(this);
        mv.setChartView(line);
        line.setMarker(mv);


    }
    private void setXY(){
        //设置样式
        YAxis yAxis=line.getAxisLeft(); // 左边颜色
        yAxis.setTextColor(Color.parseColor("#333333"));

        YAxis rightAxis = line.getAxisRight();
        //设置图表右边的y轴禁用
        rightAxis.setEnabled(false);

       //设置x轴
        //设置x轴
        XAxis xAxis = line.getXAxis();
        xAxis.setTextColor(Color.parseColor("#333333"));
        xAxis.setTextSize(11f);
        xAxis.setAxisMinimum(0f);
        xAxis.setDrawAxisLine(false);//是否绘制轴线
        xAxis.setDrawGridLines(false);//设置x轴上每个点对应的线
        xAxis.setDrawLabels(true);//绘制标签  指x轴上的对应数值
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置x轴的显示位置
        xAxis.setGranularity(1f);//禁止放大后x轴标签重绘

        List<String> listAxis = new ArrayList<>();      // 自定义横坐标
        for (int i = 0; i < 30; i++) {
            listAxis.add(String.valueOf(i+1).concat("日"));
        }
        xAxis = line.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(listAxis));


    }

    private void setData(ArrayList<Entry> list) {
        set = new LineDataSet(list, "Label");
        LineData data = new LineData(set);
        //设置数据
        line.setData(data);
        setLine(set);

        // 如果为零，不设置小圆点


    }
    private void setLine(LineDataSet set) {
        //设置线条的颜色
        set.setColor(Color.BLUE);
        //虚线模式下绘制直线
        set.enableDashedLine(20f, 5f, 0f);
        //点击后高亮线的显示颜色
        set.enableDashedHighlightLine(1f, 1f, 1f);
        set.setHighlightLineWidth(1);
        set.setHighLightColor(Color.GRAY);

        //设置数据小圆点的颜色
        set.setCircleColor(Color.BLUE);
        //设置圆点是否有空心
        set.setDrawCircles(true);
        //设置线条的宽度，最大10f,最小0.2f
        set.setLineWidth(1f);
        //设置小圆点的半径，最小1f，默认4f
        set.setCircleRadius(2f);
        //设置是否显示小圆点
        set.setDrawCircles(false);
        //设置字体颜色
        set.setValueTextColor(Color.GRAY);
        //设置字体大小
        set.setValueTextSize(5f);
        //设置是否填充
        set.setDrawFilled(true);
    }
    private void setLenged(){
        Legend legend=line.getLegend();
        legend.setTextColor(Color.RED);
        legend.setTextSize(20f);
        //设置图例垂直对齐
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        //设置图例居中
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        //设置图例方向
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        //设置图例是在图内还是图外绘制
        legend.setDrawInside(false);
        //图例条目之间距离
        legend.setXEntrySpace(4f);
        //设置图例可否换行
        legend.setWordWrapEnabled(true);
        //设置图例现状为线.默认为方形
        // legend.setForm(Legend.LegendForm.LINE);
        //是否隐藏图例/true_否，false_是
        legend.setEnabled(false);

        //透明化图例
        legend = line.getLegend();
        legend.setForm(Legend.LegendForm.NONE);
        legend.setTextColor(Color.WHITE);

        //隐藏x轴描述
        Description description = new Description();
        description.setEnabled(false);
        line.setDescription(description);

    }

    public void all_records(){   // 统计总共消费
        List<Record> records = DataSupport.findAll(Record.class);   // 遍历 record 表单
        List<Float> spends = new ArrayList<>(31);
        for(int j=0; j< 30; j++){  // 初始化
            spends.add((float)(0)); // 每一天消费记录初始化为零
        }
        Log.d("thisssssssssssss", String.valueOf(spends.size()));

        int temp;
        for(Record record: records){
            // 如果某一天有数据，那么将该天的所有消费记录加起来
            temp = record.getDay();
            for(int k = 0; k< spends.size(); k++){
                if(k == temp){
                    spends.set(k, spends.get(k) + record.getSpend());
                }
            }
        }

        for (int i = 0; i < 30; i++) {
            list.add(new Entry(i, spends.get(i)));
        }
       // Log.d("thisssssssssssss", String.valueOf(list.size()));
    }
}
