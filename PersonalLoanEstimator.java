import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class PersonalLoanEstimator {
    JFrame frame;
    JPanel panel;
    JPanel fieldsPanel;
    JFormattedTextField monthlyIncome;
    JFormattedTextField creditCardPayment;
    JFormattedTextField carPayment;
    JFormattedTextField studentLoanPayment;
    JFormattedTextField appraisedValue;
    JFormattedTextField downPayment;
    JFormattedTextField loanAmount;
    JFormattedTextField monthlyPayment;
    JFormattedTextField creditScore;

    JTextArea approvalStatus;

    HomeBuyer homeBuyer;

    public PersonalLoanEstimator() {
        frame = new JFrame("Personal Loan Estimator App");
        panel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(panel, BoxLayout.PAGE_AXIS);
        panel.setLayout(boxLayout);
        GridLayout gridLayout = new GridLayout(2, 11);
        gridLayout.setHgap(10);
        gridLayout.setVgap(10);
        fieldsPanel = new JPanel(gridLayout);
        panel.add(fieldsPanel);
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        fieldsPanel.add(new JLabel(""));
        fieldsPanel.add(new JLabel("Monthly Income", SwingConstants.CENTER));
        fieldsPanel.add(new JLabel("Credit Card Payment", SwingConstants.CENTER));
        fieldsPanel.add(new JLabel("Car Payment", SwingConstants.CENTER));
        fieldsPanel.add(new JLabel("Student Loan Payment", SwingConstants.CENTER));
        fieldsPanel.add(new JLabel("Appraised Value", SwingConstants.CENTER));
        fieldsPanel.add(new JLabel("Down Payment", SwingConstants.CENTER));
        fieldsPanel.add(new JLabel("Loan Amount", SwingConstants.CENTER));
        fieldsPanel.add(new JLabel("Monthly Mortgage Payment", SwingConstants.CENTER));
        fieldsPanel.add(new JLabel("Credit Score", SwingConstants.CENTER));
        fieldsPanel.add(new JLabel(""));
        // fieldsPanel.add(new JLabel(""));

        monthlyIncome = new JFormattedTextField(NumberFormat.getNumberInstance());
        monthlyIncome.setColumns(10);
        monthlyIncome.addPropertyChangeListener("value", new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent e) {
                if (e.getSource() != monthlyIncome || e.getNewValue() == null)
                    return;
                float newValue = ((Number) e.getNewValue()).floatValue();
                homeBuyer.setIncome(newValue);
                updateApprovalStatus();
            }
        });

        creditCardPayment = new JFormattedTextField(NumberFormat.getNumberInstance());
        creditCardPayment.setColumns(10);
        creditCardPayment.addPropertyChangeListener("value", new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent e) {
                if (e.getSource() != creditCardPayment || e.getNewValue() == null)
                    return;
                float newValue = ((Number) e.getNewValue()).floatValue();
                homeBuyer.setCreditCardPayment(newValue);
                updateApprovalStatus();
            }
        });

        carPayment = new JFormattedTextField(NumberFormat.getNumberInstance());
        carPayment.setColumns(10);
        carPayment.addPropertyChangeListener("value", new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent e) {
                if (e.getSource() != carPayment || e.getNewValue() == null)
                    return;
                float newValue = ((Number) e.getNewValue()).floatValue();
                homeBuyer.setCarPayment(newValue);
                updateApprovalStatus();
            }
        });

        studentLoanPayment = new JFormattedTextField(NumberFormat.getNumberInstance());
        studentLoanPayment.setColumns(10);
        studentLoanPayment.addPropertyChangeListener("value", new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent e) {
                if (e.getSource() != studentLoanPayment || e.getNewValue() == null)
                    return;
                float newValue = ((Number) e.getNewValue()).floatValue();
                homeBuyer.setStudentLoanPayment(newValue);
                updateApprovalStatus();
            }
        });

        appraisedValue = new JFormattedTextField(NumberFormat.getNumberInstance());
        appraisedValue.setColumns(10);
        appraisedValue.addPropertyChangeListener("value", new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent e) {
                if (e.getSource() != appraisedValue || e.getNewValue() == null)
                    return;
                float newValue = ((Number) e.getNewValue()).floatValue();
                homeBuyer.setAppraisedValue(newValue);
                updateApprovalStatus();
            }
        });

        downPayment = new JFormattedTextField(NumberFormat.getNumberInstance());
        downPayment.setColumns(10);
        downPayment.addPropertyChangeListener("value", new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent e) {
                if (e.getSource() != downPayment || e.getNewValue() == null)
                    return;
                float newValue = ((Number) e.getNewValue()).floatValue();
                homeBuyer.setDownPayment(newValue);
                updateApprovalStatus();
            }
        });

        loanAmount = new JFormattedTextField(NumberFormat.getNumberInstance());
        loanAmount.setColumns(10);
        loanAmount.addPropertyChangeListener("value", new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent e) {
                if (e.getSource() != loanAmount || e.getNewValue() == null)
                    return;
                float newValue = ((Number) e.getNewValue()).floatValue();
                homeBuyer.setLoanAmount(newValue);
                updateApprovalStatus();
            }
        });

        monthlyPayment = new JFormattedTextField(NumberFormat.getNumberInstance());
        monthlyPayment.setColumns(10);
        monthlyPayment.addPropertyChangeListener("value", new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent e) {
                if (e.getSource() != monthlyPayment || e.getNewValue() == null)
                    return;
                float newValue = ((Number) e.getNewValue()).floatValue();
                homeBuyer.setMortgagePayment(newValue);
                updateApprovalStatus();
            }
        });

        creditScore = new JFormattedTextField(NumberFormat.getIntegerInstance());
        creditScore.setColumns(10);
        creditScore.addPropertyChangeListener("value", new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent e) {
                if (e.getSource() != creditScore || e.getNewValue() == null)
                    return;
                int newValue = ((Number) e.getNewValue()).intValue();
                homeBuyer.setCreditScore(newValue);
                updateApprovalStatus();
            }
        });

        approvalStatus = new JTextArea("Please fill out all the boxes!");
        approvalStatus.setEditable(false);
        approvalStatus.setLineWrap(true);
        approvalStatus.setWrapStyleWord(true);

        fieldsPanel.add(new JLabel(""));
        fieldsPanel.add(monthlyIncome);
        fieldsPanel.add(creditCardPayment);
        fieldsPanel.add(carPayment);
        fieldsPanel.add(studentLoanPayment);
        fieldsPanel.add(appraisedValue);
        fieldsPanel.add(downPayment);
        fieldsPanel.add(loanAmount);
        fieldsPanel.add(monthlyPayment);
        fieldsPanel.add(creditScore);
        fieldsPanel.add(new JLabel(""));
        Dimension filler = new Dimension(0, 25);
        panel.add(new Box.Filler(filler, filler, filler));
        panel.add(approvalStatus, BorderLayout.SOUTH);

        frame.pack();
        frame.setMinimumSize(new Dimension(500, 500));
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        homeBuyer = new HomeBuyer();
    }

    public void updateApprovalStatus() {
        homeBuyer.setIssues(Main.getLoanStatus(homeBuyer));
        if (homeBuyer.isApproved()) {
            approvalStatus.setText("Approved!");
        } else {
            List<Main.Issue> issues = homeBuyer.getIssues();

            if (issues.size() == 1 && issues.get(0) == Main.Issue.MEDIUM_LTV) {
                // only issue is LTV ratio requiring PMI
                approvalStatus.setText(
                        "Conditionally Approved.\nDue to a high Loan to Value ratio (Value of the loan compared to the appraised value), private mortgage insurance required.\n");
            } else {
                String issuesString = "Unfortunately, you will not be likely to be accepted for a loan. See below for some next steps.\n\n";

                for (Main.Issue issue : issues) {
                    issuesString += getNextSteps(issue) + "\n\n";
                }

                approvalStatus.setText(issuesString);
            }
        }

        frame.pack();
    }

    public String getNextSteps(Main.Issue issue) {
        String string = "";
        switch (issue) {
            case BAD_CREDIT:
                string += "Your credit score is lower than the 640 score that is typically expected. Here are some ways to increase your credit score.\n"
                        + "https://money.usnews.com/credit-cards/articles/everything-you-need-to-know-about-credit-scores\n"
                        + "https://www.nerdwallet.com/article/finance/great-credit-powerful-tool\n";
                break;

            case HIGH_LTV:
                string += "Your loan to value ratio (loan value to appraised home value, LTV) is higher than 95% which is significantly over the expected 80%."
                        +
                        "This can be mitigated by either increasing your down payment or considering a different home.\n"
                        +
                        "Here are some articles that will help with that: \n\n " +

                        "What is LTV?\n" +
                        "https://www.consumerfinance.gov/ask-cfpb/what-is-a-debt-to-income-ratio-en-1791/\n" +
                        "https://www.investopedia.com/terms/d/dti.asp\n\n" +

                        "How do I lower LTV?\n" +
                        "https://pacifichomeloans.com/how-to-improve-your-debt-to-income-ratio-dti/\n" +
                        "https://www.incharge.org/blog/how-to-improve-debt-to-income-with-more-income/x\n";
                break;

            case MEDIUM_LTV:
                string += "Your loan to value ratio (loan value to appraised home value, LTV) is below 95% but still above the expected 80% so almost all lenders will require you to "
                        +
                        "additionally purchase private mortgage insurance (PMI). This will increase your yearly payment by 1% which is spread over each month's payment (this may cause issues with your debt to income ratio)."
                        +
                        "High LTV can be mitigated by either increasing your down payment or considering a different home.\n"
                        +
                        "What is LTV?\n" +
                        "https://www.consumerfinance.gov/ask-cfpb/what-is-a-debt-to-income-ratio-en-1791/\n" +
                        "https://www.investopedia.com/terms/d/dti.asp\n\n" +

                        "How do I lower LTV?\n" +
                        "https://pacifichomeloans.com/how-to-improve-your-debt-to-income-ratio-dti/\n" +
                        "https://www.incharge.org/blog/how-to-improve-debt-to-income-with-more-income/x\n" +

                        "What is PMI?\n" +
                        "https://www.consumerfinance.gov/ask-cfpb/what-is-private-mortgage-insurance-en-122/\n";
                break;

            case HIGH_DTI:
                string += "Your debt to income ratio (total monthly debt payments divided by total income, DTI) is higher than the typical maximum of 36%. "
                        +
                        "This can be difficult to solve because oftentimes lowering payments such as car loans or student loans is not possible. Lowering your monthly mortgage payments through either a higher down payment or a less expensive home can help reduce DTI.\n"
                        +
                        "Here are some resources to try and help you reduce your DTI ratio:\n" +
                        "https://www.consumerfinance.gov/ask-cfpb/what-is-a-debt-to-income-ratio-en-1791/\n" +
                        "https://www.investopedia.com/terms/d/dti.asp\n";
                break;
            case HIGH_FEDTI:
                string += "Your front end debt to income ratio (monthly mortgage payment divded by total income, FEDTI) is higher than the typical maximum of 28%. "
                        +
                        "This means your monthly mortgage payment is too much compared to your monthly income which is best solved by trying to reduce your monthly mortgage payment. Lowering your monthly mortgage payments through either a higher down payment or a less expensive home can help reduce FEDTI.\n"
                        +
                        "Here are some resources to try to reduce your FEDTI ratio:\n" +
                        "https://www.investopedia.com/terms/f/front-end-debt-to-income-ratio.asp\n" +
                        "https://pacifichomeloans.com/how-to-improve-your-debt-to-income-ratio-dti/\n";
                break;
        }

        return string;
    }
}
