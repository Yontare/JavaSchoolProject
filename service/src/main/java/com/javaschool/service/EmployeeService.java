package com.javaschool.service;

import com.javaschool.dto.CreateEmployeeRequest;
import dao.DepartmentDirectoryDao;
import dao.EmployeeDao;
import dao.PositionDao;
import model.EmployeeCard;

import java.util.ArrayList;
import java.util.List;

public class EmployeeService {
    private DepartmentDirectoryDao departmentDao = new DepartmentDirectoryDao();
    private PositionDao positionDao = new PositionDao();
    private EmployeeDao employeeDao = new EmployeeDao();

    public CreateEmployeeRequest get(long id) {
        CreateEmployeeRequest employeeRequest = null;
        EmployeeCard employeeCard = employeeDao.get(id);

        if (employeeCard != null) {
            employeeRequest = employeeToRequest(employeeCard);
        }

        return employeeRequest;
    }

    public void save(CreateEmployeeRequest employeeRequest) {
        employeeDao.save(requestToEmployee(employeeRequest));
    }

    public void delete(CreateEmployeeRequest employeeRequest) {
        employeeDao.delete(requestToEmployee(employeeRequest));
    }

    public List<CreateEmployeeRequest> getCreateEmployeeRequestList(long departmentId) {
        List<CreateEmployeeRequest> createEmployeeRequestList = null;
        List<EmployeeCard> employeeCardList = employeeDao.getEmployeeList(departmentId);

        if (employeeCardList != null) {
            createEmployeeRequestList = new ArrayList<>();

            for (EmployeeCard employeeCard: employeeCardList) {
                createEmployeeRequestList.add(employeeToRequest(employeeCard));
            }
        }

        return createEmployeeRequestList;
    }

    private CreateEmployeeRequest employeeToRequest(EmployeeCard employeeCard) {
        CreateEmployeeRequest employeeRequest = new CreateEmployeeRequest();

        employeeRequest.setPersonalData(employeeCard.getPersonalData());
        employeeRequest.setPosition(positionDao.get(employeeCard.getPosition_id()));
        employeeRequest.setPosition_id(employeeCard.getPosition_id());
        employeeRequest.setDepartment(departmentDao.getDepartmentName(employeeCard.getDepartment_id()));
        employeeRequest.setDepartment_id(employeeCard.getDepartment_id());

        return employeeRequest;
    }

    private EmployeeCard requestToEmployee(CreateEmployeeRequest employeeRequest) {
        EmployeeCard employeeCard = new EmployeeCard();

        employeeCard.setPersonalData(employeeRequest.getPersonalData());
        employeeCard.setPosition_id(employeeRequest.getPosition_id());
        employeeCard.setDepartment_id(employeeRequest.getDepartment_id());

        return employeeCard;
    }
}
