package pro.sky.controller;

import org.springframework.web.bind.annotation.*;
import pro.sky.model.Employee;
import pro.sky.service.DepartmentServiceImpl;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    private final DepartmentServiceImpl departmentService;

    public DepartmentController(DepartmentServiceImpl departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("*/{id}/salary/sum")
    public Integer findSumSalaryFromDepartment(@RequestParam int department) {
        return departmentService.calculationFindEmployeeWithSumSalary(department);
    }

    @GetMapping("*/{id}/salary/max")
    public Integer findEmployeeWithMaxSalary(@RequestParam int department) {
        return departmentService.findEmployeeWithMaxSalary(department);

    }

    @GetMapping("*/{id}/salary/min")
    public Integer findEmployeeWithMinSalary(@RequestParam int department) {
        return departmentService.findEmployeeWithMinSalary(department);

    }

    @GetMapping("*/{id}/employees")
    public List<Employee> findEmployeesByDepartment(@PathVariable("id") int department) {
        return departmentService.findEmployeesByDepartment(department);
    }

    @GetMapping("/employees")
    public Map<Integer, List<Employee>> findEmployeeForAllDepartments() {
        return departmentService.findEmployeeForAllDepartments();

    }


}
