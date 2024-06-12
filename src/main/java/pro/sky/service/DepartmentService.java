package pro.sky.service;

import org.springframework.stereotype.Service;
import pro.sky.exception.EmployeeNotFoundException;
import pro.sky.model.Employee;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Comparator.comparingDouble;
import static java.util.stream.Collectors.groupingBy;

@Service
public class DepartmentService {
    private final  EmployeeService employeeService;

    public DepartmentService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    public Employee findEmployeeWithMaxSalary(int department){
        return employeeService.printAllEmployee().stream()
                .filter(e -> e.getDepartment() == department)
                .max(comparingDouble(Employee::getSalary))
                .orElseThrow(EmployeeNotFoundException::new);
    }
    public Employee findEmployeeWithMinSalary(int department){
        return employeeService.printAllEmployee().stream()
                .filter(e -> e.getDepartment() == department)
                .min(comparingDouble(Employee::getSalary))
                .orElseThrow(EmployeeNotFoundException::new);
    }
    public Collection<Employee> findEmployeesByDepartment (int department){
        return employeeService.printAllEmployee().stream()
                .filter(e -> e.getDepartment() == department)
                .collect(Collectors.toList());
    }

    public Map<Integer, List<Employee>> findEmployeesByDepartment(){
        return employeeService.printAllEmployee().stream()
                .collect(groupingBy(Employee:: getDepartment));
    }
}
