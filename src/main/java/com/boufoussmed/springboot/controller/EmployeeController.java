package com.boufoussmed.springboot.controller;

import com.boufoussmed.springboot.exception.ResourceNotFoundException;
import com.boufoussmed.springboot.model.Employee;
import com.boufoussmed.springboot.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/")
@CrossOrigin(origins = "http://localhost:4200")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/employees")
    //@CrossOrigin(exposedHeaders = "Access-Control-Allow-Origin")
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @PostMapping("/employees")
    public Employee createEmployee(@RequestBody Employee employee) {
        return this.employeeRepository.save(employee);
    }

  /*  @GetMapping("/employees/{id}")
    public Employee getEmployeeById(@PathVariable Long id) {

        Optional<Employee> employee = employeeRepository.findById(id);

        if(!employee.isPresent())
            throw new ResourceNotFoundException("Employee not found with id : " + id);

        return employee.get();
    }*/

    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee " +
                "with id : " + id + " is not found"));

        return ResponseEntity.ok(employee);
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable("id") Long id, @RequestBody Employee employeeDetails) {

        Employee employee = this.getEmployee(id);

        employee.setEmailId(employeeDetails.getEmailId());
        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());

        return ResponseEntity.ok(employeeRepository.save(employee));
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable("id") Long id) {
        Map<String, Boolean> deleted = new HashMap<>();
        if (id == null) {
            throw new ResourceNotFoundException("This employee id is null");
        }
        if (null != getEmployee(id)) {
            this.employeeRepository.deleteById(id);
            deleted.put("deleted", true);
        } else {
            deleted.put("deleted", false);
        }

        return ResponseEntity.ok(deleted);
    }

    public Employee getEmployee(Long id) {

        Optional<Employee> employee = employeeRepository.findById(id);

        if (!employee.isPresent())
            throw new ResourceNotFoundException("Employee " +
                    "with id : " + id + " is not found");
        else
            return employee.get();
    }
}

