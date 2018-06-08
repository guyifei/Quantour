package anyeight.serviceImpl.neuralLearning;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;

import javax.swing.*;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

class Graph extends ApplicationFrame{
    ChartPanel frame1;
    private static final long serialVersionUID = 1L;

    public Graph(String s , List<ArrayList<Double>> excel) {
        super(s);
        setContentPane(createDemoLine(excel));
    }

    public static DefaultCategoryDataset createDataset(List<ArrayList<Double>> excel) {
        DefaultCategoryDataset linedataset = new DefaultCategoryDataset();
        for (int i=0; i <excel.size(); i++) {
            linedataset.addValue(excel.get(i).get(0), "expect", Integer.toString(i+1));
            linedataset.addValue(excel.get(i).get(1), "target", Integer.toString(i+1));
        }

        return linedataset;
    }

    public static JPanel createDemoLine(List<ArrayList<Double>> excel) {
        JFreeChart jfreechart = createChart(createDataset(excel));
        return new ChartPanel(jfreechart);
    }

    // 生成图表主对象JFreeChart
    public static JFreeChart createChart(DefaultCategoryDataset linedataset) {
        // 定义图表对象
        JFreeChart chart = ChartFactory.createLineChart("Cumulative rate of return", //折线图名称
                "time", // 横坐标名称
                "Value", // 纵坐标名称
                linedataset, // 数据
                PlotOrientation.VERTICAL, // 水平显示图像
                true, // include legend
                false, // tooltips
                false // urls
        );
        // chart.setBackgroundPaint(Color.red);

        CategoryPlot plot = chart.getCategoryPlot();
        // plot.setDomainGridlinePaint(Color.red);
        plot.setDomainGridlinesVisible(true);
        // 5,设置水平网格线颜色
        // plot.setRangeGridlinePaint(Color.blue);
        // 6,设置是否显示水平网格线
        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinesVisible(true); //是否显示格子线
        //plot.setBackgroundAlpha(f); //设置背景透明度

        NumberAxis rangeAxis = (NumberAxis)plot.getRangeAxis();

        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setAutoRangeIncludesZero(true);
        rangeAxis.setUpperMargin(0.20);
        rangeAxis.setLabelAngle(Math.PI / 2.0);
        rangeAxis.setAutoRange(false);
        FileOutputStream fos_jpg=null;
        try{
            fos_jpg=new FileOutputStream("C:\\Users\\T5-SK\\Desktop\\QQ截图20170614223323.png");
        /*
         * 第二个参数如果为100，会报异常：
         * java.lang.IllegalArgumentException: The 'quality' must be in the range 0.0f to 1.0f
         * 限制quality必须小于等于1,把100改成 0.1f。
         */
            // ChartUtilities.writeChartAsJPEG(fos_jpg, 0.99f, chart, 600, 300, null);
            ChartUtilities.writeChartAsJPEG(fos_jpg, chart, 900, 400);

        }catch(Exception e){
            System.out.println("[e]"+e);
        }finally{
            try{
                fos_jpg.close();
            }catch(Exception e){

            }
        }
        return chart;
    }
}
