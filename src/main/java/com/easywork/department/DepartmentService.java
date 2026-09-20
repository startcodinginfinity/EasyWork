package com.easywork.department;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class DepartmentService {
	
	public DepartmentService(DepartmentRepository departmentRepository) {
		this.departmentRepository = departmentRepository;
	}

	private final DepartmentRepository departmentRepository;

	public Department createDepartment(Department department) {
		return departmentRepository.save(department);
	}

	public List<Department> getAllDepartments() {
		return departmentRepository.findAll();
	}

	public Department getDepartment(Long id) {
		return departmentRepository.findById(id)
				.orElseThrow(()->
				new RuntimeException(
						"Department Not Found")
						);
	}

}

