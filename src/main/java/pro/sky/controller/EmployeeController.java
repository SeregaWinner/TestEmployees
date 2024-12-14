package pro.sky.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.model.Employee;
import pro.sky.service.EmployeeServiceImpl;

import java.util.Collection;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private final EmployeeServiceImpl employeeService;

    public EmployeeController(EmployeeServiceImpl employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/add")
    public Employee add(@RequestParam(value = "firstName") String firstName,
                        @RequestParam(value = "lastName") String lastName,
                        @RequestParam(value = "id") int department,
                        @RequestParam(value = "salary") int salary) {
        return employeeService.addEmployee(firstName, lastName, department, salary);
    }

    @GetMapping("/find")
    public Employee find(@RequestParam(value = "firstName") String firstName,
                         @RequestParam(value = "lastName") String lastName) {
        return employeeService.findEmployee(firstName, lastName);
    }

    @GetMapping("/remove")
    public Employee remove(@RequestParam(value = "firstName") String firstName,
                           @RequestParam(value = "lastName") String lastName) {
        return employeeService.removeEmployee(firstName, lastName);
    }

    @GetMapping()
    public Collection<Employee> findAll() {
        return employeeService.takeOutAllEmployee();
    }
}
