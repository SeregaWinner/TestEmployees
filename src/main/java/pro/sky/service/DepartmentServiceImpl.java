package pro.sky.service;

import org.springframework.stereotype.Service;
import pro.sky.exception.EmployeeNotFoundException;
import pro.sky.model.Employee;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Service
public class DepartmentServiceImpl {
    private final EmployeeServiceImpl employeeService;

    public DepartmentServiceImpl(EmployeeServiceImpl employeeService) {
        this.employeeService = employeeService;
    }


    public List<Employee> findEmployeesByDepartment(int department) {
        return employeeService.takeOutAllEmployee().stream()
                .filter(e -> e.getDepartment() == department)
                .collect(Collectors.toList());
    }


    public Integer calculationFindEmployeeWithSumSalary(int department) {
        return employeeService.takeOutAllEmployee().stream()
                .filter(employee -> employee.getDepartment() == department)
                .mapToInt(Employee::getSalary)
                .sum();
    }


    public Integer findEmployeeWithMaxSalary(int department) {
        return employeeService.takeOutAllEmployee().stream()
                .filter(e -> e.getDepartment() == department)
                .max(Comparator.comparingInt(Employee::getSalary))
                .map(Employee::getSalary)
                .orElseThrow(EmployeeNotFoundException::new);
    }


    public Integer findEmployeeWithMinSalary(int department) {
        return employeeService.takeOutAllEmployee().stream()
                .filter(e -> e.getDepartment() == department)
                .min(Comparator.comparingInt(Employee::getSalary))
                .map(Employee::getSalary)
                .orElseThrow(EmployeeNotFoundException::new);
    }


    public Map<Integer, List<Employee>> findEmployeeForAllDepartments() {
        return employeeService.takeOutAllEmployee().stream()
                .collect(groupingBy(Employee::getDepartment));
    }
}
