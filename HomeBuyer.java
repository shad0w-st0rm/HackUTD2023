import java.util.List;

public class HomeBuyer
{
    private int id;
    private float income;
    private float creditCardPayment; 
    private float carPayment;
    private float studentLoanPayment;
    private float appraisedValue;
    private float downPayment;
    private float loanAmount;
    private float mortgagePayment;
    private int creditScore;

    private List<Main.Issue> issues;
    
    public HomeBuyer()
    {
        
    }

    public HomeBuyer(String [] data)
    {
        id = Integer.parseInt(data[0]);
        income = Float.parseFloat(data[1]);
        creditCardPayment = Float.parseFloat(data[2]);
        carPayment = Float.parseFloat(data[3]);
        studentLoanPayment = Float.parseFloat(data[4]);
        appraisedValue = Float.parseFloat(data[5]);
        downPayment = Float.parseFloat(data[6]);
        loanAmount = Float.parseFloat(data[7]);
        mortgagePayment = Float.parseFloat(data[8]);
        creditScore = Integer.parseInt(data[9]);
    }

    public int getId() { return id; }
    public float getIncome() { return income;}
    public float getCreditCardPayment() { return creditCardPayment; }
    public float getCarPayment() { return carPayment; }
    public float getStudentLoanPayment() { return studentLoanPayment; }
    public float getAppraisedValue() { return appraisedValue; }
    public float getDownPayment() { return downPayment; }
    public float getLoanAmount() { return loanAmount; }
    public float getMortgagePayment() {return mortgagePayment;}
    public int getCreditScore() { return creditScore; }

    public void setId(int id) { this.id = id;}
    public void setIncome(float income) { this.income = income; }
    public void setCreditCardPayment(float cardPayment) { creditCardPayment = cardPayment; }
    public void setCarPayment(float carPayment) { this.carPayment = carPayment; }
    public void setStudentLoanPayment(float payment) { studentLoanPayment = payment; }
    public void setAppraisedValue(float value) {appraisedValue = value; }
    public void setDownPayment(float downPayment) { this.downPayment = downPayment; }
    public void setLoanAmount(float loanAmount) { this.loanAmount = loanAmount; }
    public void setMortgagePayment(float mortgagePayment) { this.mortgagePayment = mortgagePayment; }
    public void setCreditScore (int creditScore) { this.creditScore = creditScore; }

    public List<Main.Issue> getIssues() { return issues; }
    public void setIssues(List<Main.Issue> issuesList) { issues = issuesList; }

    public boolean isApproved() { return issues.size() == 0; }

    public void addPMICost()
    {
        mortgagePayment *= 1.01f;
    }

    public String toString()
    {
        String homeBuyerData = "";
        homeBuyerData += "ID: " + id + "\n";
        homeBuyerData += "Monthly Income: " + income + "\n";
        homeBuyerData += "Credit Card Payment: " + creditCardPayment + "\n";
        homeBuyerData += "Car Payment: " + carPayment + "\n";
        homeBuyerData += "Student Loan Payment: " + studentLoanPayment + "\n";
        homeBuyerData += "Appraised Value: " + appraisedValue + "\n";
        homeBuyerData += "Down Payment: " + downPayment + "\n";
        homeBuyerData += "Loan Amount: " + loanAmount + "\n";
        homeBuyerData += "Mortgage Payment: " + mortgagePayment + "\n";
        homeBuyerData += "Credit Score: " + creditScore;
        return homeBuyerData;
    }
}