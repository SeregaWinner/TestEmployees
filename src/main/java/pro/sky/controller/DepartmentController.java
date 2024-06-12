package pro.sky.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.model.Employee;
import pro.sky.service.DepartmentService;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("max-salary")
    public Employee findEmployeeWithMaxSalary(@RequestParam int department) {
        return departmentService.findEmployeeWithMaxSalary(department);

    }

    @GetMapping("min-salary")
    public Employee findEmployeeWithMinSalary(@RequestParam int department) {
        return departmentService.findEmployeeWithMinSalary(department);

    }

    @GetMapping(value = "all", params = "department")
    public Collection<Employee> findEmployeeByDepartment(@RequestParam int department) {
        return departmentService.findEmployeesByDepartment(department);

    }

    @GetMapping("all")
    public Map<Integer, List<Employee>> findEmployeesByDepartment() {
        return departmentService.findEmployeesByDepartment();
    }
}
