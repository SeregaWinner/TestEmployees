package pro.sky.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.exception.EmployeeNotFoundException;
import pro.sky.model.Employee;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTest {
    @Mock
    private EmployeeServiceImpl employeeService;
    @InjectMocks
    private DepartmentServiceImpl departmentService;

    private final List<Employee> employees = List.of(
            new Employee("Вин", "Дизель", 1, 150_000),
            new Employee("Пол", "Уокер", 1, 100_000),
            new Employee("Джордана", "Брюстер", 2, 120_000),
            new Employee("Дуэйн", "Джонсон", 2, 130_000),
            new Employee("Тайриз", "Гибсон", 3, 110_000),
            new Employee("Сон", "Ган", 3, 50_000)
    );

    @BeforeEach
    void beforeEach() {
        when(employeeService.printAllEmployee()).thenReturn(employees);
    }

    @Test
    void findEmployeesByDepartmentTest() {
        assertThat(departmentService.findEmployeesByDepartment(1))
                .containsExactlyInAnyOrder(
                        new Employee("Вин", "Дизель", 1, 150_000),
                        new Employee("Пол", "Уокер", 1, 100_000)
                );
    }

    @Test
    void calculationFindEmployeeWithSumSalaryTest() {
        assertThat(departmentService.calculationFindEmployeeWithSumSalary(3))
                .isEqualTo(160_000);
    }

    @Test
    void calculationFindEmployeeWithSumSalaryNegativeTest() {
        assertThat(departmentService.calculationFindEmployeeWithSumSalary(5))
                .isEqualTo(0);
    }

    @Test
    void findEmployeeWithMaxSalaryTest() {
        assertThat(departmentService.findEmployeeWithMaxSalary(3))
                .isEqualTo(110_000);
    }

    @Test
    void when_departmentService_not_found_department_max_salary_EmployeeNotFoundException_is_throw() {
        assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(() -> departmentService.findEmployeeWithMaxSalary(5));
    }

    @Test
    void findEmployeeWithMinSalaryTest() {
        assertThat(departmentService.findEmployeeWithMinSalary(3))
                .isEqualTo(50_000);
    }

    @Test
    void when_departmentService_not_found_department_min_salary_EmployeeNotFoundException_is_throw() {
        assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(() -> departmentService.findEmployeeWithMinSalary(5));
    }

    @Test
    void findEmployeeForAllDepartmentsTest() {
        assertThat(departmentService.findEmployeeForAllDepartments())
                .containsExactlyInAnyOrderEntriesOf(
                        Map.of(1, List.of(
                                        new Employee("Вин", "Дизель", 1, 150_000),
                                        new Employee("Пол", "Уокер", 1, 100_000)),
                                2, List.of(
                                        new Employee("Джордана", "Брюстер", 2, 120_000),
                                        new Employee("Дуэйн", "Джонсон", 2, 130_000)),
                                3, List.of(
                                        new Employee("Тайриз", "Гибсон", 3, 110_000),
                                        new Employee("Сон", "Ган", 3, 50_000))

                        ));

    }


}
