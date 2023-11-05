import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.style.Styler.LegendPosition;

public class Dashboard {
    JFrame frame;

    public Dashboard() {
        frame = new JFrame("Dashboard");
        createButtons();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(500, 500));
        frame.pack();
        frame.setVisible(true);
    }

    public void createButtons() {
        JPanel panel = new JPanel();
        BoxLayout layout = new BoxLayout(panel, BoxLayout.PAGE_AXIS);
        panel.setLayout(layout);

        panel.setAlignmentY(JPanel.BOTTOM_ALIGNMENT);
        panel.setAlignmentX(JPanel.CENTER_ALIGNMENT);

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> {
            System.exit(0);
        });
        JButton processFileButton = new JButton("View Data Trends");
        processFileButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser(new File(System.getProperty("user.dir")));
            int result = fileChooser.showOpenDialog(frame);

            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                List<HomeBuyer> homeBuyers = null;
                try {
                    homeBuyers = Main.readData(selectedFile);
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                }

                JFrame chartFrame = new JFrame("Chart Display");
                addCharts(chartFrame, homeBuyers);
                chartFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                //chartFrame.setSize(600, 400);
                chartFrame.setVisible(true);
            }
        });

        JButton customDataButton = new JButton("Personal Loan Estimator");
        customDataButton.addActionListener(e -> {
            PersonalLoanEstimator ple = new PersonalLoanEstimator();
        });
        
        panel.add(processFileButton);
        panel.add(customDataButton);
        panel.add(exitButton);

        frame.add(panel);
    }

    public void addCharts(JFrame chartFrame, List<HomeBuyer> homeBuyers) {
        PieChart pieChart = new PieChartBuilder().title("Status of Loan Applications (Primary Deciding Factor Only)").build();
        MetricTracking metrics = new MetricTracking();
        metrics.metricOutput(homeBuyers);
        pieChart.addSeries("Approved", metrics.approved);
        pieChart.addSeries("Low Credit Score", metrics.badCreditCount);
        pieChart.addSeries("High LTV (>95%)", metrics.highLTVCount);
        pieChart.addSeries("PMI Required (LTV > 80%)", metrics.medLTVCount);
        pieChart.addSeries("High DTI", metrics.highDTICount);
        pieChart.addSeries("High FEDTI", metrics.highFEDTICount);
        XChartPanel<PieChart> pieChartPanel = new XChartPanel<PieChart>(pieChart);
        chartFrame.add(pieChartPanel, BorderLayout.WEST);

        CategoryChart barChart = new CategoryChartBuilder().title("Totals for Each Factor").build();
        barChart.getStyler().setLegendPosition(LegendPosition.InsideNW);
        barChart.getStyler().setShowStackSum(true);
        List<String> barTitles = Arrays.asList(new String[] { "Approved", "Low Credit Score", "High LTV", "PMI Needed", "High DTI", "High FEDTI" });
        List<Integer> barValues = Arrays.asList(new Integer[] {metrics.totalApproved, metrics.totalBadCreditCount, metrics.totalHighLTVCount, metrics.totalMedLTVCount, metrics.totalHighDTICount, metrics.totalHighFEDTICount});
        barChart.addSeries("Number Applicants", barTitles, barValues);
        XChartPanel<CategoryChart> barChartPanel = new XChartPanel<CategoryChart>(barChart);
        chartFrame.add(barChartPanel, BorderLayout.EAST);
        // Display the window
        chartFrame.pack();
        chartFrame.setVisible(true);
    }
}
