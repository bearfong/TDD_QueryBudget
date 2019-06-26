package budget;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;

import org.junit.Test;

public class QueryBudgetTest {

    private QueryBudget qeryBudget = new QueryBudget();

    @Test
    public void test_same_date() throws Exception{
        LocalDate sameDate = LocalDate.of(2019,2,1);
        double amount = qeryBudget.query(sameDate, sameDate);
        assertThat(amount).isEqualTo(1000d);

    }

    @Test
    public void test_two_day_in_same_month() throws Exception{
        LocalDate startDate = LocalDate.of(2019,2,1);
        LocalDate endDate = LocalDate.of(2019,2,2);
        double amount = qeryBudget.query(startDate, endDate);
        assertThat(amount).isEqualTo(2000d);

    }

    @Test
    public void test_cross_month() throws Exception{
        LocalDate startDate = LocalDate.of(2019,2,1);
        LocalDate endDate = LocalDate.of(2019,3,1);
        double amount = qeryBudget.query(startDate, endDate);
        assertThat(amount).isEqualTo(29000);
    }

    @Test
    public void test_startDate_after_endDate() {
        LocalDate startDate = LocalDate.of(2019,4,1);
        LocalDate endDate = LocalDate.of(2019,3,1);
        assertThatThrownBy(() -> qeryBudget.query(startDate, endDate)).isInstanceOf(Exception.class).hasMessageContaining("Startdate should be valid");
    }

    @Test
    public void test_cross_empty_month() throws Exception{
        LocalDate startDate = LocalDate.of(2019,4,15);
        LocalDate endDate = LocalDate.of(2019,6,15);
        double amount = qeryBudget.query(startDate, endDate);
        assertThat(amount).isEqualTo(31000);
    }

    @Test
    public void test_query_no_budget_date()  throws Exception{
        LocalDate startDate = LocalDate.of(2019,1,1);
        LocalDate endDate = LocalDate.of(2019,1,1);
        double amount = qeryBudget.query(startDate, endDate);
        assertThat(amount).isEqualTo(0);
    }

    @Test
    public void test_query_include_no_budget_date_and_has_budget_date()  throws Exception{
        LocalDate startDate = LocalDate.of(2019,1,1);
        LocalDate endDate = LocalDate.of(2019,2,1);
        double amount = qeryBudget.query(startDate, endDate);
        assertThat(amount).isEqualTo(1000d);
    }

    @Test
    public void test_query_cross_has_budget_date_range() throws Exception{
        LocalDate startDate = LocalDate.of(2019,1,1);
        LocalDate endDate = LocalDate.of(2019,7,1);
        double amount = qeryBudget.query(startDate, endDate);
        assertThat(amount).isEqualTo(119000d);
    }
}
