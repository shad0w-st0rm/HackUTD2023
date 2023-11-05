import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;

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
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    public void createButtons() {
        ImagePanel panel = new ImagePanel(new ImageIcon("background.png").getImage());
        BoxLayout layout = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(layout);

        // panel.setAlignmentY(JPanel.BOTTOM_ALIGNMENT);
        // panel.setAlignmentX(JPanel.CENTER_ALIGNMENT);

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> {
            System.exit(0);
        });

        JButton processFileButton = new JButton("View Data Trends");
        processFileButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser(new File(System.getProperty("user.dir")));
            fileChooser.setFileFilter(new CSVFilter());
            fileChooser.setAcceptAllFileFilterUsed(false);
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
            }
        });

        JButton csvOutputButton = new JButton("Save Approvals to .csv");
        csvOutputButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser(new File(System.getProperty("user.dir")));
            fileChooser.setFileFilter(new CSVFilter());
            fileChooser.setAcceptAllFileFilterUsed(false);
            int result = fileChooser.showDialog(frame, "Select Data File");
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                List<HomeBuyer> homeBuyers = null;
                try {
                    homeBuyers = Main.readData(selectedFile);
                    int saveResult = fileChooser.showDialog(frame, "Select Output File");
                    if (saveResult == JFileChooser.APPROVE_OPTION) {
                        File outputFile = fileChooser.getSelectedFile();
                        Main.processBatchFile(homeBuyers, outputFile);
                        JOptionPane.showMessageDialog(frame, "File successfully saved!");
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        JButton customDataButton = new JButton("Personal Loan Estimator");
        customDataButton.addActionListener(e -> {
            PersonalLoanEstimator ple = new PersonalLoanEstimator();
        });
        Dimension d2 = new Dimension(0, 75);
        Dimension buttonSize = new Dimension(175, 50);

        panel.add(new Box.Filler(d2, d2, d2));
        processFileButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        processFileButton.setMaximumSize(buttonSize);
        processFileButton.setMinimumSize(buttonSize);
        panel.add(processFileButton);

        panel.add(new Box.Filler(d2, d2, d2));
        csvOutputButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        csvOutputButton.setMaximumSize(buttonSize);
        csvOutputButton.setMinimumSize(buttonSize);
        panel.add(csvOutputButton);

        panel.add(new Box.Filler(d2, d2, d2));
        customDataButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        customDataButton.setMaximumSize(buttonSize);
        customDataButton.setMinimumSize(buttonSize);
        panel.add(customDataButton);

        Dimension d1 = new Dimension(0, 200);
        panel.add(new Box.Filler(d1, d1, d1));
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setMaximumSize(buttonSize);
        exitButton.setMinimumSize(buttonSize);
        panel.add(exitButton);

        frame.add(panel);
    }

    public void addCharts(JFrame chartFrame, List<HomeBuyer> homeBuyers) {
        PieChart pieChart = new PieChartBuilder().title("Status of Loan Applications (Primary Deciding Factor Only)")
                .build();
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
        List<String> barTitles = Arrays.asList(
                new String[] { "Approved", "Low Credit Score", "High LTV", "PMI Needed", "High DTI", "High FEDTI" });
        List<Integer> barValues = Arrays
                .asList(new Integer[] { metrics.totalApproved, metrics.totalBadCreditCount, metrics.totalHighLTVCount,
                        metrics.totalMedLTVCount, metrics.totalHighDTICount, metrics.totalHighFEDTICount });
        barChart.addSeries("Number Applicants", barTitles, barValues);
        XChartPanel<CategoryChart> barChartPanel = new XChartPanel<CategoryChart>(barChart);
        chartFrame.add(barChartPanel, BorderLayout.EAST);
        // Display the window
        chartFrame.pack();
        chartFrame.setVisible(true);
        chartFrame.setLocationRelativeTo(null);
    }

    class ImagePanel extends JPanel // used to set backgrounds
    {

        Image img;

        public ImagePanel(String img) {
            this(new ImageIcon(img).getImage());
        }

        public ImagePanel(Image img) // sets the image size
        {
            this.img = img;
            Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
            setPreferredSize(size);
            setMinimumSize(size);
            setMaximumSize(size);
            setSize(size);
        }

        public void paintComponent(Graphics g) // paints the background image
        {
            g.drawImage(img, 0, 0, null);
        }
    }

    class CSVFilter extends FileFilter {
        public boolean accept(File f) {
            if (f.isDirectory()) return true;

            return f.isFile() && f.getName().toLowerCase().endsWith(".csv");
        }

        public String getDescription() { return "*.csv"; }
    }
}
