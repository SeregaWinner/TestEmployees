package pro.sky.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pro.sky.exception.EmployeeAlreadyAddedException;
import pro.sky.exception.EmployeeNotFoundException;
import pro.sky.exception.EmployeeStorageIsFullException;
import pro.sky.exception.InvalidInputException;
import pro.sky.model.Employee;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class EmployeeServiceTest {
    private final EmployeeServiceImpl employeeService = new EmployeeServiceImpl();

    private final List<Employee> employees = List.of(
            new Employee("Вин", "Дизель", 1, 150_000),
            new Employee("Пол", "Уокер", 1, 100_000),
            new Employee("Джордана", "Брюстер", 2, 150_000),
            new Employee("Дуэйн", "Джонсон", 2, 150_000),
            new Employee("Тайриз", "Гибсон", 3, 120_000)
    );

    @BeforeEach
    public void beforeEach() {
        employees.forEach(employee -> employeeService
                .addEmployee(employee.getFirstName(),
                        employee.getLastName(),
                        employee.getDepartment(),
                        employee.getSalary()));
    }

    @AfterEach
    public void afterEach() {
        employeeService.printAllEmployee()
                .forEach(employee -> employeeService.removeEmployee(employee.getFirstName(), employee.getLastName()));
    }


    @Test
    void addEmployeeTest() {
        Employee expected = new Employee("Сон", "Ган", 3, 50_000);

        Employee actual = employeeService.addEmployee("Сон", "Ган", 3, 50_000);

        assertThat(actual).isEqualTo(expected);
        assertThat(actual).isEqualTo(employeeService.findEmployee("Сон", "Ган"));
        assertThat(actual).isIn(employeeService.printAllEmployee());

    }

    @Test
    void when_employeeService_is_full_then_employees_EmployeeStorageIsFullException_is_thrown() {
        employeeService.addEmployee("Сон", "Ган", 3, 50_000);

        assertThatExceptionOfType(EmployeeStorageIsFullException.class)
                .isThrownBy(() -> employeeService.addEmployee("Галь", "Гадот", 4, 130_000));
    }

    @Test
    void when_employeeService_contains_employees_EmployeeAlreadyAddedException_is_thrown() {
        assertThatExceptionOfType(EmployeeAlreadyAddedException.class)
                .isThrownBy(() -> employeeService.addEmployee("Вин", "Дизель", 1, 150_000));

    }

    @Test
    void findEmployee() {
        Employee expected = new Employee("Вин", "Дизель", 1, 150_000);
        assertThat(employeeService.printAllEmployee()).contains(expected);

        Employee actual = employeeService.findEmployee("Вин", "Дизель");
        assertThat(actual).isEqualTo(expected);

    }

    @Test
    void when_employeeService_employee_not_found_EmployeeNotFoundException_is_thrown() {
        assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(() -> employeeService.findEmployee("Галь", "Гадот"));
    }

    @Test
    void removeEmployee() {
        Employee expected = new Employee("Вин", "Дизель", 1, 150_000);
        assertThat(employeeService.printAllEmployee()).contains(expected);

        Employee actual = employeeService.removeEmployee("Вин", "Дизель");
        assertThat(actual).isEqualTo(expected);
        assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(() -> employeeService.findEmployee("Вин", "Дизель"));
        assertThat(actual).isNotIn(employeeService.printAllEmployee());

    }

    @Test
    void when_employeeService_employee_not_found_EmployeeNotFoundException_is_thrown_by_remove() {
        assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(() -> employeeService.removeEmployee("Галь", "Гадот"));
    }


    @Test
    void getAllEmployeesTest() {
       assertThat(employeeService.printAllEmployee())
               .containsExactlyInAnyOrderElementsOf(employees);
    }

    @Test
    void validateInputTest(){
        assertThatExceptionOfType(InvalidInputException.class)
                .isThrownBy(() -> employeeService.addEmployee("", "Гадот",4, 130_000));
    }
}