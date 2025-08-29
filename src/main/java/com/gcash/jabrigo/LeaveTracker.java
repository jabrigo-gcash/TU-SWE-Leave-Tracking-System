package com.gcash.jabrigo;

public class LeaveTracker {

    public static void main(String[] args) {

        Employee employee1 = new Employee(1, "Emp1", "Dep1");
        Employee employee2 = new Employee(2, "Emp2", "Dep2");

        LeaveRequest request = new LeaveRequest(1, employee1, "2025/09/01", "2025/09/03");

        System.out.println("Employee Name: " + employee2.getName());
        System.out.println("Leave Balance: " + employee2.getLeaveBalance());

        employee2.requestLeave(3);

        System.out.println("New Leave Balance: " + employee2.getLeaveBalance());

        System.out.println("Leave Requester: " + request.getEmployee().getName());
        System.out.println("Start Date: " + request.getStartDate());
        System.out.println("Status: " + request.getStatus());
    }
}