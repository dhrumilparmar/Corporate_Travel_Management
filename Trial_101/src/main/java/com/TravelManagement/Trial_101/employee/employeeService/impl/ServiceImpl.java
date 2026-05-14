package com.TravelManagement.Trial_101.employee.employeeService.impl;

import com.TravelManagement.Trial_101.employee.DTO.RequestDTO.EmployeeRequestDTO;
import com.TravelManagement.Trial_101.employee.DTO.ResponseDTO.DepartmentResponseDTO;
import com.TravelManagement.Trial_101.employee.DTO.ResponseDTO.DesignationResponseDTO;
import com.TravelManagement.Trial_101.employee.DTO.ResponseDTO.EmployeeResponseDTO;
import com.TravelManagement.Trial_101.employee.DTO.ResponseDTO.RoleResponseDTO;
import com.TravelManagement.Trial_101.employee.Entity.Department;
import com.TravelManagement.Trial_101.employee.Entity.Designation;
import com.TravelManagement.Trial_101.employee.Entity.Employee;
import com.TravelManagement.Trial_101.employee.Entity.Role;
import com.TravelManagement.Trial_101.employee.employeeRepository.DesignationRepo;
import com.TravelManagement.Trial_101.employee.employeeRepository.Rolerepo;
import com.TravelManagement.Trial_101.employee.employeeRepository.departmentrepo;
import com.TravelManagement.Trial_101.employee.employeeRepository.employeeRepository;
import com.TravelManagement.Trial_101.employee.employeeService.employeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ServiceImpl implements employeeService {

    @Autowired
    employeeRepository empRes;

    @Autowired
    departmentrepo departmentrepo;

    @Autowired
    DesignationRepo designationRepo;

    @Autowired
    Rolerepo rolerepo;

    @Override
    public EmployeeResponseDTO getEmployee(Integer id) {
        Optional<Employee> employee = empRes.findById(id);
        return mapToEmployeeResponseDTO(employee.get());
    }

    @Override
    public void deleteEmployeeById(Employee employee){
        empRes.delete(employee);
    }

    @Override
    public EmployeeResponseDTO updateEmployee(Employee employee){
        Employee updatedEmployee =empRes.save(employee);
        return mapToEmployeeResponseDTO(updatedEmployee);
    }






    @Override
    public EmployeeResponseDTO saveEmployee(EmployeeRequestDTO requestDTO) {

        // 1. Create a new Employee ENTITY
        Employee employee = new Employee();

        // 2. Map simple String/Enum fields
        employee.setFullName(requestDTO.getFullName());
        employee.setEmail(requestDTO.getEmail());
        employee.setPasswordHash(requestDTO.getPassword()); // ⚠️ Note: Use PasswordEncoder here later!

        if (requestDTO.getStatus() != null) {
            employee.setStatus(Employee.Status.valueOf(requestDTO.getStatus()));
        }

        // 3. Map Foreign Key IDs to Nested Entity Objects
        // (Hibernate needs Objects, not just raw Integer IDs)

        if (requestDTO.getDepartmentID() != null) {
            Optional<Department> responseOpt = departmentrepo.findById(requestDTO.getDepartmentID());
            if (responseOpt.isPresent()) {
                Department dto = responseOpt.get();
                Department department = new Department();
                department.setDepartmentID(dto.getDepartmentID());
                department.setDepartmentName(dto.getDepartmentName());
                // Set other properties
                employee.setDepartment(department);
            }
        }

        if (requestDTO.getDesignationID() != null) {
            Optional<Designation> responseOpt = designationRepo.findById(requestDTO.getDesignationID());
            if(responseOpt.isPresent()){
                Designation dto = responseOpt.get();
                Designation designation = new Designation();
                designation.setDesignationID(dto.getDesignationID());
                designation.setDesignationName(dto.getDesignationName());
                designation.setEmployeeLevel(dto.getEmployeeLevel());
                employee.setDesignation(designation);
            }
        }

        if (requestDTO.getRoleID() != null) {
            Optional<Role> responseOpt = rolerepo.findById(requestDTO.getRoleID());
            if (responseOpt.isPresent()) {
                Role dto = responseOpt.get();
                Role newRole = new Role();
                newRole.setRoleID(dto.getRoleID());
                newRole.setRoleName(dto.getRoleName());
                // Set other properties as needed
                employee.setRole(newRole);
            }
        }
        if (requestDTO.getManagerID() != null) {
            Employee manager = new Employee();
            manager.setEmployeeID(requestDTO.getManagerID());
            employee.setManager(manager);
        }

        // 4. Save the ENTITY to the database (NOT the DTO)
        Employee savedEmployee = empRes.save(employee);

        // 5. Map the saved Entity back to a Response DTO for the API response
        return mapToEmployeeResponseDTO(savedEmployee);
    }

    @Override
    public List<EmployeeResponseDTO> getAllEmployee() {
        // 1. Fetch List of Entities from DB
        List<Employee> employees = empRes.findAll();

        // 2. Convert List<Entity> to List<DTO> using Streams
        // ❌ DO NOT ADD ANY (EmployeeResponseDTO) CAST HERE!
        return employees.stream()
                .map(this::mapToEmployeeResponseDTO)
                .collect(Collectors.toList());
    }


    // ─── Department Mapper ─────────────────────────────────────────────
    private DepartmentResponseDTO mapToDepartmentResponseDTO(Department department) {
        if (department == null) return null;

        DepartmentResponseDTO dto = new DepartmentResponseDTO();
        dto.setDepartmentID(department.getDepartmentID());
        dto.setDepartmentName(department.getDepartmentName());
        dto.setStatus(department.getStatus() != null ? department.getStatus().name() : null);

        return dto;
    }

    // ─── Role Mapper ───────────────────────────────────────────────────
    private RoleResponseDTO mapToRoleResponseDTO(Role role) {
        if (role == null) return null;

        RoleResponseDTO dto = new RoleResponseDTO();
        dto.setRoleID(role.getRoleID());
        dto.setRoleName(role.getRoleName() != null ? role.getRoleName().name() : null);

        return dto;
    }

    // ─── Designation Mapper ────────────────────────────────────────────
    private DesignationResponseDTO mapToDesignationResponseDTO(Designation designation) {
        if (designation == null) return null;

        DesignationResponseDTO dto = new DesignationResponseDTO();
        dto.setDesignationID(designation.getDesignationID());
        dto.setDesignationName(designation.getDesignationName());
        dto.setEmployeeLevel(designation.getEmployeeLevel() != null ? designation.getEmployeeLevel().name() : null);
        dto.setStatus(designation.getStatus() != null ? designation.getStatus().name() : null);

        return dto;
    }

    private EmployeeResponseDTO mapToEmployeeResponseDTO(Employee employee) {
        if (employee == null) return null;

        EmployeeResponseDTO dto = new EmployeeResponseDTO();
        dto.setEmployeeID(employee.getEmployeeID());
        dto.setFullName(employee.getFullName());
        dto.setEmail(employee.getEmail());
        dto.setStatus(employee.getStatus() != null ? employee.getStatus().name() : null);
        dto.setCreatedAt(employee.getCreatedAt());

        // Map nested objects
        dto.setDepartment(mapToDepartmentResponseDTO(employee.getDepartment()));
        dto.setDesignation(mapToDesignationResponseDTO(employee.getDesignation()));
        dto.setRole(mapToRoleResponseDTO(employee.getRole()));
        dto.setCreatedAt(employee.getCreatedAt());

        // Map manager using Summary DTO to avoid infinite loop
//        if (employee.getManager() != null) {
//            dto.setManager(mapToEmployeeSummaryDTO(employee.getManager()));
//        }

        return dto;
    }

}
