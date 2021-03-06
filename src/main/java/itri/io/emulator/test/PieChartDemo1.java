package itri.io.emulator.test;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class PieChartDemo1 extends ApplicationFrame {

  private static final long serialVersionUID = 1L;

  {
      // set a theme using the new shadow generator feature available in
      // 1.0.14 - for backwards compatibility it is not enabled by default
      ChartFactory.setChartTheme(new StandardChartTheme("JFree/Shadow", true));
  }

  /**
   * Default constructor.
   *
   * @param title  the frame title.
   */
  public PieChartDemo1(String title) {
      super(title);
      setContentPane(createDemoPanel());
  }

  /**
   * Creates a sample dataset.
   *
   * @return A sample dataset.
   */
  private static PieDataset createDataset() {
      DefaultPieDataset dataset = new DefaultPieDataset();
      dataset.setValue("One", new Double(43.2));
      dataset.setValue("Two", new Double(10.0));
      dataset.setValue("Three", new Double(27.5));
      dataset.setValue("Four", new Double(17.5));
      dataset.setValue("Five", new Double(11.0));
      dataset.setValue("Six", new Double(19.4));
      return dataset;
  }

  /**
   * Creates a chart.
   *
   * @param dataset  the dataset.
   *
   * @return A chart.
   */
  private static JFreeChart createChart(PieDataset dataset) {

      JFreeChart chart = ChartFactory.createPieChart(
          "Pie Chart Demo 1",  // chart title
          dataset,             // data
          true,                // include legend
          true,
          false
      );

      PiePlot plot = (PiePlot) chart.getPlot();
      plot.setSectionOutlinesVisible(false);
      plot.setNoDataMessage("No data available");
      return chart;

  }

  /**
   * Creates a panel for the demo (used by SuperDemo.java).
   *
   * @return A panel.
   */
  public static JPanel createDemoPanel() {
      JFreeChart chart = createChart(createDataset());
      ChartPanel panel = new ChartPanel(chart);
      panel.setMouseWheelEnabled(true);
      return panel;
  }

  /**
   * Starting point for the demonstration application.
   *
   * @param args  ignored.
   */
  public static void main(String[] args) {

      // ******************************************************************
      //  More than 150 demo applications are included with the JFreeChart
      //  Developer Guide...for more information, see:
      //
      //  >   http://www.object-refinery.com/jfreechart/guide.html
      //
      // ******************************************************************

      PieChartDemo1 demo = new PieChartDemo1("Pie Chart Demo 1");
      demo.pack();
      RefineryUtilities.centerFrameOnScreen(demo);
      demo.setVisible(true);
  }

}
