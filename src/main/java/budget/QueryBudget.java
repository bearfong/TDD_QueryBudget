package budget;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class QueryBudget {

    private final List<Budget> budgets = findAllBudgets();
    private final List<String> budgetsKeys = findAllBudgets().stream().map(Budget::getDate).collect(Collectors.toList());

    public double query(LocalDate startDate, LocalDate endDate) throws Exception{

        double sum = 0;
        String queryStartDate = getQueryStartDate(startDate);
        String queryEndDate = getQueryStartDate(endDate);

        if (startDate.isBefore(endDate) && !queryStartDate.equals(queryEndDate)) {

            int startDays = startDate.getDayOfMonth();
            int endDays = endDate.getDayOfMonth();

            double firstMonthDays = (double)(getLengthOfMonth(startDate) - startDays +1) / getLengthOfMonth(startDate);

            double lastMonthDays = (double)endDays / getLengthOfMonth(endDate);

            sum += getBudgetByDate(queryStartDate).getAmount() * firstMonthDays;
            sum += getBudgetByDate(queryEndDate).getAmount() * lastMonthDays;

            Integer startIndex = (getExistStartBudgetIndex(startDate).equals(getBudgetIndex(queryStartDate))) ? getBudgetIndex(queryStartDate) + 1: getExistStartBudgetIndex(startDate);
            Integer endIndex = (getExistEndBudgetIndex(endDate).equals(getBudgetIndex(queryEndDate))) ? getBudgetIndex(queryEndDate) : getExistEndBudgetIndex(endDate) +1 ;
            for (int i=startIndex; i < endIndex ; i++ ){
                sum += budgets.get(i).getAmount();
            }

        }else if(startDate.isEqual(endDate)){
            double firstMonthDays = (double)1 / getLengthOfMonth(startDate);
            sum += getBudgetByDate(queryStartDate).getAmount() * firstMonthDays;
        }
        else if(startDate.isBefore(endDate) && queryStartDate.equals(queryEndDate)){

            int startDays = startDate.getDayOfMonth();
            int endDays = endDate.getDayOfMonth();
            double monthDays = (double)(endDays - startDays + 1) / getLengthOfMonth(startDate);

            sum += getBudgetByDate(queryStartDate).getAmount() * monthDays;
        }
        else{
            throw new Exception("Startdate should be valid.");
        }

        return sum;
    }

    private String getQueryStartDate(LocalDate startDate) {
        return startDate.getYear() + "/" + startDate.getMonth().getValue();
    }

    public Integer getLengthOfMonth(LocalDate date) {
        return YearMonth.of(date.getYear(), date.getMonth()).lengthOfMonth();
    }

    public Integer getExistEndBudgetIndex(LocalDate date) {
        String queryDate = getQueryStartDate(date);
        Integer idx = getBudgetIndex(queryDate);
        if(idx == -1){
            return getExistEndBudgetIndex(date.plusMonths(-1));
        }else{
            return idx;
        }
    }

    public Integer getExistStartBudgetIndex(LocalDate date) {
        String queryDate = getQueryStartDate(date);
        Integer idx = getBudgetIndex(queryDate);
        if(idx == -1){
            return getExistStartBudgetIndex(date.plusMonths(1));
        }else{
            return idx;
        }
    }

    public Integer getBudgetIndex(String yearMonth) {
        return budgetsKeys.indexOf(yearMonth);
    }

    public Budget getBudgetByDate(String yearMonth) {
        Integer index = getBudgetIndex(yearMonth);
        if(index == -1){
            return new Budget(yearMonth, 0);
        }
        return budgets.get(index);
    }

    public List<Budget> findAllBudgets(){
        Budget b1 = new Budget("2019/2", 28000);
        Budget b2 = new Budget("2019/3", 31000);
        Budget b3 = new Budget("2019/4", 30000);
        Budget b4 = new Budget("2019/6", 30000);

        return Arrays.asList(b1, b2, b3, b4);
    }

}
