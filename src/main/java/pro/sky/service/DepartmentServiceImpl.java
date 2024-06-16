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
public class DepartmentServiceImpl  {
    private final EmployeeServiceImpl employeeService;

    public DepartmentServiceImpl(EmployeeServiceImpl employeeService) {
        this.employeeService = employeeService;
    }


    public List<Employee> findEmployeesByDepartment(int department) {
        return employeeService.printAllEmployee().stream()
                .filter(e -> e.getDepartment() == department)
                .collect(Collectors.toList());
    }


    public Integer calculationFindEmployeeWithSumSalary(int department) {
        return employeeService.printAllEmployee().stream()
                .filter(employee -> employee.getDepartment() == department)
                .mapToInt(Employee::getSalary)
                .sum();
    }


    public Employee findEmployeeWithMaxSalary(int department) {
        return employeeService.printAllEmployee().stream()
                .filter(e -> e.getDepartment() == department)
                .max(Comparator.comparingInt(Employee::getSalary))
                .orElseThrow(EmployeeNotFoundException::new);
    }


    public Employee findEmployeeWithMinSalary(int department) {
        return employeeService.printAllEmployee().stream()
                .filter(e -> e.getDepartment() == department)
                .min(Comparator.comparingInt(Employee::getSalary))
                .orElseThrow(EmployeeNotFoundException::new);
    }



    public Map<Integer, List<Employee>> findEmployeeForAllDepartments() {
        return employeeService.printAllEmployee().stream()
                .collect(groupingBy(Employee::getDepartment));
    }
}
