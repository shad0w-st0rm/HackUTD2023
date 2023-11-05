import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public enum Issue
    {
        BAD_CREDIT,
        HIGH_LTV,
        MEDIUM_LTV,
        HIGH_DTI,
        HIGH_FEDTI
    }

    public static void main (String [] args)
    {
        Main main = new Main();
        main.runIt();
    }

    public void runIt()
    {
        List<HomeBuyer> homeBuyers = null;
        try
        {
            homeBuyers = readData(new File("HackUTD-2023-HomeBuyerInfo.csv"));
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Could not find csv file!");
            return;
        }

        Dashboard dashboard = new Dashboard();

        /*
        try {
            processBatchFile(homeBuyers, "output.txt");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        */
    }

    public static List<HomeBuyer> readData(File file) throws FileNotFoundException
    {
        List<HomeBuyer> homeBuyers = new ArrayList<HomeBuyer>();
        Scanner scanner = new Scanner(file);
        scanner.nextLine();
        while (scanner.hasNextLine())
        {
            String line = scanner.nextLine();
            HomeBuyer homeBuyer = createHomeBuyer(line);
            List<Issue> issues = getLoanStatus(homeBuyer);
            homeBuyer.setIssues(issues);
            homeBuyers.add(homeBuyer);
        }
        scanner.close();
        return homeBuyers;
    }

    public static HomeBuyer createHomeBuyer(String data)
    {
        String [] fields = data.split(",");
        if (fields.length < 10)
        {
            System.out.println("Data read error: not enough fields on this line: " + data);
            return null;
        }
        return new HomeBuyer(fields);
    }

    public static void processBatchFile(List<HomeBuyer> homeBuyersList, File outputFile) throws IOException
    {
        outputFile.createNewFile();
        BufferedWriter outputWriter = new BufferedWriter(new FileWriter(outputFile));

        for (HomeBuyer homeBuyer : homeBuyersList)
        {
            outputWriter.append(homeBuyer.getId() + ",");
            String status = (homeBuyer.isApproved() ? "Y" : "N");
            outputWriter.append(status + "\n");
        }
        outputWriter.close();
    }

    public static float getLTV(HomeBuyer homeBuyer) 
    {
        return (homeBuyer.getLoanAmount() / homeBuyer.getAppraisedValue() * 100);
    }

    public static float getDTI(HomeBuyer homeBuyer) 
    {
        float totalDebts = homeBuyer.getCreditCardPayment() + homeBuyer.getCarPayment() + homeBuyer.getStudentLoanPayment() + homeBuyer.getMortgagePayment();
        float dti = (totalDebts / homeBuyer.getIncome());

        return dti;
    }

    public static float getFEDTI(HomeBuyer homeBuyer) 
    {
        return ((homeBuyer.getMortgagePayment() / homeBuyer.getIncome())*100);
    }

    public static List<Issue> getLoanStatus(HomeBuyer homeBuyer)
    {
        List<Issue> issues = new ArrayList<Issue>();
        if (homeBuyer.getCreditScore() < 640)
        {
            issues.add(Issue.BAD_CREDIT);
        }

        float ltv = getLTV(homeBuyer);
        if (ltv >= 80)
        {
            homeBuyer.addPMICost();
            if (ltv > 95)
                issues.add(Issue.HIGH_LTV);
            else
                issues.add(Issue.MEDIUM_LTV);
        }

        if (getDTI(homeBuyer) > 36)
        {
            issues.add(Issue.HIGH_DTI);
        }

        if (getFEDTI(homeBuyer) > 28)
        {
            issues.add(Issue.HIGH_FEDTI);
        }

        return issues;
    }

}
