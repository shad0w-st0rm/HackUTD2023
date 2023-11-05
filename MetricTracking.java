import java.util.List;

public class MetricTracking {
    int approved;
    int fails;
    int badCreditCount;
    int highLTVCount;
    int medLTVCount;
    int highDTICount;
    int highFEDTICount;

    int totalApproved;
    int totalBadCreditCount;
    int totalHighLTVCount;
    int totalMedLTVCount;
    int totalHighDTICount;
    int totalHighFEDTICount;

    public void metricOutput(List<HomeBuyer> homeBuyers)
    {
        for (HomeBuyer homeBuyer : homeBuyers)
        {
            List<Main.Issue> issues = homeBuyer.getIssues();
            if (issues.size() > 0) {
            
                fails++;
                
                Main.Issue failReason = issues.get(0);
                if (failReason == Main.Issue.BAD_CREDIT) {
                    badCreditCount++;
                }
                else if (failReason == Main.Issue.HIGH_LTV) {
                    highLTVCount++;
                }
                else if (failReason == Main.Issue.MEDIUM_LTV) {
                    medLTVCount++;
                }
                else if (failReason == Main.Issue.HIGH_DTI) {
                    highDTICount++;
                }
                else {
                    highFEDTICount++;
                }

                for (int i = 0; i < issues.size(); i++) 
                {
                    Main.Issue failReason2 = issues.get(i);
                    if (failReason2 == Main.Issue.BAD_CREDIT) {
                        totalBadCreditCount++;
                    }
                    else if (failReason2 == Main.Issue.HIGH_LTV) {
                        totalHighLTVCount++;
                    }
                    else if (failReason2 == Main.Issue.MEDIUM_LTV) {
                        totalMedLTVCount++;
                    }
                    else if (failReason2 == Main.Issue.HIGH_DTI) {
                        totalHighDTICount++;
                    }
                    else {
                        totalHighFEDTICount++;
                    }
                }
            }
            else {
                approved++;
                totalApproved++;
            }   
        }       
    }



}




























