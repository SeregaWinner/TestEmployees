package pro.sky.service;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import pro.sky.exception.EmployeeAlreadyAddedException;
import pro.sky.exception.EmployeeNotFoundException;
import pro.sky.exception.EmployeeStorageIsFullException;
import pro.sky.exception.InvalidInputException;
import pro.sky.model.Employee;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.*;

@Service
public class EmployeeService {
    private final int MAX_EMPLOYEES = 10;
    private Map<String, Employee> employees = new HashMap<>();

    public EmployeeService(Map<String, Employee> employees) {
        this.employees = employees;

    }

    @PostConstruct
    private void init() {
        addEmployee("Вин", "Дизель");
        addEmployee("Пол", "Уокер");
        addEmployee("Джордана", "Брюстер");
        addEmployee("Дуэйн", "Джонсон");
        addEmployee("Тайриз", "Гибсон");
        addEmployee("Сон", "Ган");
        addEmployee("Галь", "Гадот");
    }

    public Employee addEmployee(String firstName, String lastName) throws EmployeeAlreadyAddedException,
            EmployeeStorageIsFullException {
        Employee employee = new Employee(firstName, lastName);
        validateInput(firstName, lastName);
        if (employees.size() == MAX_EMPLOYEES) {
            throw new EmployeeStorageIsFullException();
        }
        if (employees.containsKey(employee.combineFullName())) {
            throw new EmployeeAlreadyAddedException();
        }
        employees.put(employee.combineFullName(), employee);
        return employee;
    }

    public Employee removeEmployee(String firstName, String lastName) throws EmployeeNotFoundException {
        Employee employee = new Employee(firstName, lastName);
        validateInput(firstName, lastName);
        if (!employees.containsKey(employee.combineFullName())) {
            throw new EmployeeNotFoundException();
        }
        return employees.remove(employee.combineFullName());

    }


    public Employee findEmployee(String firstName, String lastName) throws EmployeeNotFoundException {
        Employee employee = new Employee(firstName, lastName);
        validateInput(firstName, lastName);
        if (!employees.containsKey(employee.combineFullName())) {
            throw new EmployeeNotFoundException();
        }
        return employees.get(employee.combineFullName());
    }

    public Collection<Employee> printAllEmployee() {
        return Collections.unmodifiableCollection(employees.values());
    }

    private void validateInput(String firstName, String lastName) {
        if (!(isAlpha(firstName) && isAlpha(lastName))) {
            throw new InvalidInputException();
        }
    }

}
