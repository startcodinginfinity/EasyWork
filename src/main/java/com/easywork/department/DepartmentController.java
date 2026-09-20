package com.easywork.department;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {
	
	public DepartmentController(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	private final DepartmentService departmentService;
	
	@PostMapping
	public DepartmentResponse createDepartment(@RequestBody Department department) {
		
		Department savedDepartment = departmentService.createDepartment(department);
		
		return convertToResponse(savedDepartment);
	}
	
	@GetMapping
	public List<DepartmentResponse> getDepartments(){
		return departmentService.getAllDepartments()
				.stream()
				.map(this::convertToResponse)
				.toList();
	}
	
	@GetMapping("/{id}")
	public DepartmentResponse getDepartment(@PathVariable Long id) {
		Department department = departmentService.getDepartment(id);
		return convertToResponse(department);
	}
	
	private DepartmentResponse convertToResponse(Department department) {
		return new DepartmentResponse(
				department.getId(),
				department.getName(),
				department.getDescription(),
				department.getCompany().getId());
	}

}
