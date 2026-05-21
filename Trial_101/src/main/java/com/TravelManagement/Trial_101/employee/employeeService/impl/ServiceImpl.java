package com.TravelManagement.Trial_101.employee.employeeService.impl;

import com.TravelManagement.Trial_101.employee.DTO.RequestDTO.EmployeeRequestDTO;
import com.TravelManagement.Trial_101.employee.DTO.ResponseDTO.DepartmentResponseDTO;
//import com.TravelManagement.Trial_101.employee.DTO.ResponseDTO.DesignationResponseDTO;
import com.TravelManagement.Trial_101.employee.DTO.ResponseDTO.EmployeeResponseDTO;
import com.TravelManagement.Trial_101.employee.DTO.ResponseDTO.ManagerResponseDTO;
import com.TravelManagement.Trial_101.employee.DTO.ResponseDTO.RoleResponseDTO;
import com.TravelManagement.Trial_101.employee.Entity.Department;
//import com.TravelManagement.Trial_101.employee.Entity.Designation;
import com.TravelManagement.Trial_101.employee.Entity.Employee;
import com.TravelManagement.Trial_101.employee.Entity.Role;
//import com.TravelManagement.Trial_101.employee.employeeRepository.DesignationRepo;
import com.TravelManagement.Trial_101.employee.employeeRepository.Rolerepo;
import com.TravelManagement.Trial_101.employee.employeeRepository.departmentrepo;
import com.TravelManagement.Trial_101.employee.employeeRepository.employeeRepository;
import com.TravelManagement.Trial_101.employee.employeeService.employeeService;
import jakarta.persistence.EntityNotFoundException;
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

//    @Autowired
//    DesignationRepo designationRepo;

    @Autowired
    Rolerepo rolerepo;

    @Override
    public List<ManagerResponseDTO> getAllManagers() {
        // Get all employees who are managers
        List<Employee> managerEntities = empRes.getAllManager();

        // Convert to ManagerResponseDTO list
        return managerEntities.stream()
                .map(this::mapToManagerResponseDTO)
                .collect(Collectors.toList());
    }

//

//    @Override
//    public List<EmployeeResponseDTO> getAllEmployee() {
//        // 1. Fetch List of Entities from DB
//        List<Employee> employees = empRes.findAll();
//
//        // 2. Convert List<Entity> to List<DTO> using Streams
//        // ❌ DO NOT ADD ANY (EmployeeResponseDTO) CAST HERE!
//        return employees.stream()
//                .map(this::mapToEmployeeResponseDTO)
//                .collect(Collectors.toList());
//    }


//    @Override
//    public List<ManagerResponseDTO> getAllManager(){
//        List<ManagerResponseDTO> employees =  empRes.getAllManager();
//
//        return ManagerResponseDTO(employees);
//    }

//    @Override
//    public List<ManagerResponseDTO> getAllManager(){
//        ManagerResponseDTO employee = (ManagerResponseDTO) empRes.getAllManager();
//        return Collections.singletonList(mapToManagerResponseDTO(employee));
//    }



    @Override
    public EmployeeResponseDTO getEmployee(Integer id) {
        Optional<Employee> employee = empRes.findById(id);
        return mapToEmployeeResponseDTO(employee.get());
    }

    @Override
    public void deleteEmployeeById(Integer employeeID){
        if (!empRes.existsById(employeeID)) {
            // Or just let deleteById throw the error, which is also fine
            throw new EntityNotFoundException("Employee not found with id: " + employeeID);
        }

        empRes.deleteById(employeeID);
    }

    @Override
    public EmployeeResponseDTO updateEmployee(Integer id, EmployeeRequestDTO requestDTO) {
        // Get existing employee
        Employee employee = empRes.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + id));

        // Update fields
        if (requestDTO.getFullName() != null) {
            employee.setFullName(requestDTO.getFullName());
        }
        if (requestDTO.getEmail() != null) {
            employee.setEmail(requestDTO.getEmail());
        }
        if (requestDTO.getDepartmentID() != null) {
            Department department = departmentrepo.findById(requestDTO.getDepartmentID())
                    .orElseThrow(() -> new RuntimeException("Department not found with ID: " + requestDTO.getDepartmentID()));
            employee.setDepartment(department);
        }
        if (requestDTO.getRoleID() != null) {
            Role role = rolerepo.findById(requestDTO.getRoleID())
                    .orElseThrow(() -> new RuntimeException("Role not found with ID: " + requestDTO.getRoleID()));
            employee.setRole(role);
        }
        if (requestDTO.getStatus() != null) {
            employee.setStatus(Employee.Status.valueOf(requestDTO.getStatus()));
        }

        // Update password
        if (requestDTO.getPassword() != null && !requestDTO.getPassword().trim().isEmpty()) {
            employee.setPasswordHash(requestDTO.getPassword());
        }

        // Update manager
        if (requestDTO.getManagerID() != null) {
            Employee manager = empRes.findById(requestDTO.getManagerID())
                    .orElseThrow(() -> new RuntimeException("Manager not found with ID: " + requestDTO.getManagerID()));
            employee.setManager(manager);
        } else {
            employee.setManager(null);
        }

        // Save updated entity
        Employee savedEmployee = empRes.save(employee);

        // Convert to response DTO
        return convertToResponseDTO(savedEmployee);
    }

    // Helper method to convert Entity to Response DTO
    private EmployeeResponseDTO convertToResponseDTO(Employee employee) {
        EmployeeResponseDTO dto = new EmployeeResponseDTO();
        dto.setEmployeeID(employee.getEmployeeID());
        dto.setFullName(employee.getFullName());
        dto.setEmail(employee.getEmail());
        dto.setStatus(String.valueOf(employee.getStatus()));
        dto.setCreatedAt(employee.getCreatedAt());
        dto.setPasswordHash(employee.getPasswordHash());

        // Map department
        if (employee.getDepartment() != null) {
            dto.setDepartment(mapToDepartmentResponseDTO(employee.getDepartment()));
        }

        // Map role
        if (employee.getRole() != null) {
            dto.setRole(mapToRoleResponseDTO(employee.getRole()));
        }

        // Map manager info
        if (employee.getManager() != null) {
            dto.setManagerID(employee.getManager().getEmployeeID());

            // GET MANAGER NAME FROM EMPLOYEE ID - This is what you asked for!
            String managerName = findManagerNameFromEmployeeId(employee.getManager().getEmployeeID());
            dto.setManagerName(managerName);
        }

        return dto;
    }





    @Override
    public EmployeeResponseDTO saveEmployee(EmployeeRequestDTO requestDTO) {
        // 1. Create a new Employee ENTITY
        Employee employee = new Employee();

        // 2. Map simple String/Enum fields
        employee.setFullName(requestDTO.getFullName());
        employee.setEmail(requestDTO.getEmail());
        employee.setPasswordHash(requestDTO.getPassword());

        if (requestDTO.getStatus() != null) {
            employee.setStatus(Employee.Status.valueOf(requestDTO.getStatus().toUpperCase()));
        }

        // 3. Map Foreign Key IDs to Nested Entity Objects

        // Department
        if (requestDTO.getDepartmentID() != null) {
            Department department = departmentrepo.findById(requestDTO.getDepartmentID())
                    .orElseThrow(() -> new RuntimeException("Department not found with ID: " + requestDTO.getDepartmentID()));
            employee.setDepartment(department);
        }

        // Role
        if (requestDTO.getRoleID() != null) {
            Role role = rolerepo.findById(requestDTO.getRoleID())
                    .orElseThrow(() -> new RuntimeException("Role not found with ID: " + requestDTO.getRoleID()));
            employee.setRole(role);
        }

        // Manager - FETCH THE ACTUAL MANAGER ENTITY FROM DATABASE!
        if (requestDTO.getManagerID() != null) {
            Employee manager = empRes.findById(requestDTO.getManagerID())
                    .orElseThrow(() -> new RuntimeException("Manager not found with ID: " + requestDTO.getManagerID()));
            employee.setManager(manager); // Set the complete manager entity
        }

        // 4. Save the ENTITY to the database
        Employee savedEmployee = empRes.save(employee);

        // 5. Map to Response DTO
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


    private ManagerResponseDTO mapToManagerResponseDTO(Employee employee) {
        if (employee == null) return null;

        ManagerResponseDTO dto = new ManagerResponseDTO();
        dto.setEmployeeID(employee.getEmployeeID());

        // Check if fullName is null or empty, and handle appropriately
        if (employee.getFullName() != null && !employee.getFullName().trim().isEmpty()) {
            dto.setFullName(employee.getFullName());
        } else {
            // Try to fetch the complete employee record
            String managerName = findManagerNameFromEmployeeId(employee.getEmployeeID());
            dto.setFullName(managerName);
        }

        return dto;
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
//    private DesignationResponseDTO mapToDesignationResponseDTO(Designation designation) {
//        if (designation == null) return null;
//
//        DesignationResponseDTO dto = new DesignationResponseDTO();
//        dto.setDesignationID(designation.getDesignationID());
//        dto.setDesignationName(designation.getDesignationName());
//        dto.setEmployeeLevel(designation.getEmployeeLevel() != null ? designation.getEmployeeLevel().name() : null);
//        dto.setStatus(designation.getStatus() != null ? designation.getStatus().name() : null);
//
//        return dto;
//    }
    private EmployeeResponseDTO mapToEmployeeResponseDTO(Employee employee) {
        if (employee == null) return null;

        EmployeeResponseDTO dto = new EmployeeResponseDTO();
        dto.setEmployeeID(employee.getEmployeeID());
        dto.setFullName(employee.getFullName());
        dto.setEmail(employee.getEmail());
        dto.setStatus(employee.getStatus() != null ? employee.getStatus().name() : null);
        dto.setCreatedAt(employee.getCreatedAt());
        dto.setPasswordHash(employee.getPasswordHash());

        // Map nested objects
        if (employee.getDepartment() != null) {
            dto.setDepartment(mapToDepartmentResponseDTO(employee.getDepartment()));
        }

        if (employee.getRole() != null) {
            dto.setRole(mapToRoleResponseDTO(employee.getRole()));
        }

        // Map manager info - GET MANAGER NAME FROM EMPLOYEE ID
        if (employee.getManager() != null) {
            dto.setManagerID(employee.getManager().getEmployeeID());

            // GET MANAGER NAME FROM EMPLOYEE ID - This is what you asked for!
            String managerName = findManagerNameFromEmployeeId(employee.getManager().getEmployeeID());
            dto.setManagerName(managerName);
        }

        return dto;
    }

    // NEW METHOD: Find manager name from employee ID
    private String findManagerNameFromEmployeeId(Integer managerId) {
        if (managerId == null) {
            return null;
        }

        try {
            Optional<Employee> managerOpt = empRes.findById(managerId);
            if (managerOpt.isPresent()) {
                Employee manager = managerOpt.get();
                if (manager.getFullName() != null && !manager.getFullName().trim().isEmpty()) {
                    return manager.getFullName();
                } else {
                    // Fallback if fullName is null or empty
                    return "Manager " + managerId;
                }
            } else {
                return "Unknown Manager (" + managerId + ")";
            }
        } catch (Exception e) {
            System.err.println("Error finding manager with ID " + managerId + ": " + e.getMessage());
            return "Manager " + managerId;
        }
    }
}
