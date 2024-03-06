package com.example.companyemployee.controller;

import com.example.companyemployee.entity.Employee;
import com.example.companyemployee.service.CompanyService;
import com.example.companyemployee.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class EmployeeController {

    private final EmployeeService employeeService;
    private final CompanyService companyService;

    @GetMapping("/employees")
    public String employeesPage(ModelMap modelMap) {
        List<Employee> employees = employeeService.findAll();
        modelMap.put("employees", employees);
        return "employees";
    }

    @GetMapping("/addEmployee")
    public String employeesAdd(ModelMap modelMap) {
        modelMap.put("companies", companyService.findAll());
        return "addEmployee";
    }

    @PostMapping("/addEmployee")
    public String addEmployee(@ModelAttribute Employee employee,
                              @RequestParam("picture") MultipartFile multipartFile) throws IOException {

        employeeService.save(employee, multipartFile);
        return "redirect:/employees";
    }

    @PostMapping("/employees/update")
    public String updateEmployee(@ModelAttribute Employee employee,
                                 @RequestParam("picture") MultipartFile multipartFile) throws IOException {

        employeeService.update(employee, multipartFile);
        return "redirect:/employees";
    }

    @GetMapping("/employees/update/{id}")
    public String updateEmployeePage(@PathVariable("id") int id, ModelMap modelMap) {
        Optional<Employee> byId = employeeService.findById(id);
        if (byId.isPresent()) {
            modelMap.put("companies", companyService.findAll());
            modelMap.addAttribute("employee", byId.get());
        } else {
            return "redirect:/employees";
        }
        return "updateEmployees";
    }

    @GetMapping("/employees/image/delete")
    public String deleteEmployeeImage(@RequestParam("id") int id) {
        Optional<Employee> byId = employeeService.findById(id);
        if (byId.isEmpty()) {
            return "redirect:/employees";
        } else {
            employeeService.deleteEmployeeImage(id);
            return "redirect:/employees/update/" + id;
        }
    }
}

