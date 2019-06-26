package budget;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class QueryBudget {

    public QueryBudget(){

    }

    public double query(LocalDate startDate, LocalDate endDate){

        findAllBudgets().stream().filter(
                b -> b.getStartDate().isAfter(startDate) && b.getEndDate().isBefore(endDate)).collect(
                Collectors.toMap(b -> b.getStartDate().getMonthValue(), Function.identity()));

        return 0d;
    }

    public List<Budget> findAllBudgets(){
        Budget b1 = new Budget(LocalDate.of(2019, 2, 1), LocalDate.of(2019, 2, 28), 28000);
        Budget b2 = new Budget(LocalDate.of(2019, 3, 1), LocalDate.of(2019, 3, 31), 31000);
        Budget b3 = new Budget(LocalDate.of(2019, 4, 1), LocalDate.of(2019, 4, 30), 30000);
        Budget b4 = new Budget(LocalDate.of(2019, 6, 1), LocalDate.of(2019, 6, 30), 30000);

        return Arrays.asList(b1, b2, b3, b4);
    }

}
