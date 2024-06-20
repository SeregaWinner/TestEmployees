package pro.sky.service;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import pro.sky.exception.EmployeeAlreadyAddedException;
import pro.sky.exception.EmployeeNotFoundException;
import pro.sky.exception.EmployeeStorageIsFullException;
import pro.sky.exception.InvalidInputException;
import pro.sky.model.Employee;

import java.util.*;

import static org.apache.commons.lang3.StringUtils.*;

@Service
public class EmployeeServiceImpl {
    private final int MAX_EMPLOYEES = 6;
    private final Map<String, Employee> employees = new HashMap<>();



    @PostConstruct
    private void init() {
        addEmployee("Вин", "Дизель", 1, 150000);
        addEmployee("Пол", "Уокер", 1, 100000);
        addEmployee("Джордана", "Брюстер", 2, 150000);
        addEmployee("Дуэйн", "Джонсон", 2, 150000);
        addEmployee("Тайриз", "Гибсон", 3, 120000);
        addEmployee("Сон", "Ган", 3, 50000);
        addEmployee("Галь", "Гадот", 4, 130000);
    }


    public Employee addEmployee(String firstName, String lastName, int department, int salary)
            throws EmployeeAlreadyAddedException, EmployeeStorageIsFullException {
        Employee employee = new Employee(firstName, lastName, department, salary);
        validateInput(firstName, lastName);
        if (employees.size() == MAX_EMPLOYEES) {
            throw new EmployeeStorageIsFullException();
        }
        String key = combineKey(firstName, lastName);
        if (employees.containsKey(key)) {
            throw new EmployeeAlreadyAddedException();
        }
        employees.put(employee.combineFullName(), employee);
        return employee;
    }

    public Employee removeEmployee(String firstName, String lastName)
            throws EmployeeNotFoundException {
        validateInput(firstName, lastName);
        String key = combineKey(firstName, lastName);
        if (!employees.containsKey(key)) {
            throw new EmployeeNotFoundException();
        }
        return employees.remove(key);

    }


    public Employee findEmployee(String firstName, String lastName)
            throws EmployeeNotFoundException {
        validateInput(firstName, lastName);
        String key = combineKey(firstName, lastName);
        if (!employees.containsKey(key)) {
            throw new EmployeeNotFoundException();
        }
        return employees.get(key);
    }

    public Collection<Employee> takeOutAllEmployee() {
        return new ArrayList<>(employees.values());
    }

    private void validateInput(String firstName, String lastName) {
        if (!(isAlpha(firstName) && isAlpha(lastName))) {
            throw new InvalidInputException();
        }
    }

    public String combineKey(String firstName, String lastName) {
        return firstName + " " + lastName;
    }

}


