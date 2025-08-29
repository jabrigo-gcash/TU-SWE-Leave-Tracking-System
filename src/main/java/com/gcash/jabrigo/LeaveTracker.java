package com.gcash.jabrigo;

import com.gcash.jabrigo.leave.LeaveRequest;
import com.gcash.jabrigo.leave.MaternityLeaveRequest;
import com.gcash.jabrigo.leave.SickLeaveRequest;
import com.gcash.jabrigo.leave.VacationLeaveRequest;

import java.util.*;

public class LeaveTracker {

    private static void printMainMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("1. Create New Leave Request");
        System.out.println("2. Process a Pending Request");
        System.out.println("3. View All Request Histories");
        System.out.println("4. Exit");
        System.out.print("Choose an option: ");
    }

    private static void printNewLeaveRequestMenu() {
        System.out.println("\n--- Create New Leave Request ---");
        System.out.println("Select an employee:");
        System.out.println("1. Alice");
        System.out.println("2. Bob");
        System.out.print("Enter employee number: ");
    }

    private static void printSelectLeaveTypeMenu() {
        System.out.println("\nSelect leave type:");
        System.out.println("Select an employee");
        System.out.println("1. Sick Leave");
        System.out.println("2. Vacation Leave");
        System.out.println("3. Maternity Leave");
        System.out.println("Enter leave type number: ");
    }

    private static void printProcessNextPendingRequest(LeaveRequest pendingRequest) {
        System.out.println("\n--- Processing Next Pending Request ---");
        System.out.println("Request ID: " + pendingRequest.getRequestId());
        System.out.println("Employee: " + pendingRequest.getEmployee().getName());
        System.out.println("Leave Type: " + pendingRequest.getLeaveType());
        System.out.println("Dates: " + pendingRequest.getStartDate() + " to " + pendingRequest.getEndDate());
        System.out.println("Status: " + pendingRequest.getStatus());
    }

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        Employee employee1 = new Employee(1, "Alice", "Dep1");
        Employee employee2 = new Employee(2, "Bob", "Dep2");
        ArrayList<LeaveRequest> allLeaveRequests = new ArrayList<>();

        System.out.println("Welcome to the HR Leave Management System!");

        boolean canExit = false;

        while(!canExit) {
            printMainMenu();
            try {
                int option = s.nextInt();
                switch (option) {
                    case 1 -> {
                        printNewLeaveRequestMenu();
                        int employeeOption = s.nextInt();
                        Employee requestingEmployee;
                        switch (employeeOption) {
                            case 1 -> requestingEmployee = employee1;
                            case 2 -> requestingEmployee = employee2;
                            default -> throw new InvalidInputException("Invalid employee");
                        }

                        printSelectLeaveTypeMenu();
                        int leaveTypeOption = s.nextInt();
                        if (leaveTypeOption < 0 || leaveTypeOption > 3) {
                            throw new InvalidInputException("Invalid leave type");
                        }
                        LeaveRequest leaveRequest;

                        System.out.print("Enter Start Date (YYYY-MM-DD): ");
                        String startDate = s.next();
                        System.out.print("Enter End Date (YYYY-MM-DD): ");
                        String endDate = s.next();

                        switch (leaveTypeOption) {
                            case 1 -> {
                                System.out.print("Is a medical certificate provided? (true/false): ");
                                boolean medCertFlag = Boolean.parseBoolean(s.next());
                                leaveRequest = new SickLeaveRequest(allLeaveRequests.size()+1, requestingEmployee, startDate, endDate, medCertFlag);
                            }
                            case 2 -> {
                                System.out.print("Is it paid time off? (true/false): ");
                                boolean paidTimeOffFlag = Boolean.parseBoolean(s.next());
                                leaveRequest = new VacationLeaveRequest(allLeaveRequests.size()+1, requestingEmployee, startDate, endDate, paidTimeOffFlag);
                            }
                            case 3 -> {
                                System.out.print("Enter Expected Delivery Date (YYYY-MM-DD): ");
                                String deliveryDate = s.next();
                                leaveRequest = new MaternityLeaveRequest(allLeaveRequests.size()+1, requestingEmployee, startDate, endDate, deliveryDate);
                            }
                            default -> leaveRequest = null;
                        }
                        if (leaveRequest != null) {
                            allLeaveRequests.add(leaveRequest);
                            System.out.println("\nSuccessfully created Sick Leave request for " + requestingEmployee.getName() + ".");
                        }
                    }
                    case 2 -> {
                        LeaveRequest pendingRequest = allLeaveRequests.stream()
                                .filter(leaveRequest -> leaveRequest.getStatus() == LeaveRequest.LeaveRequestStatus.PENDING)
                                .sorted(Comparator.comparing(LeaveRequest::getRequestId))
                                .toList().getFirst();

                        printProcessNextPendingRequest(pendingRequest);
                        boolean success = pendingRequest.processLeaveRequest();
                        if (success) {
                            pendingRequest.approve("System");
                        } else {
                            pendingRequest.deny("System", "System validation failed.");
                        }

                        pendingRequest.printStatusHistory();
                    }
                    case 3 -> allLeaveRequests.forEach(LeaveRequest::printStatusHistory);
                    case 4 -> {
                        canExit = true;
                        System.out.println("\nExiting system. Goodbye!");
                    }
                }
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
            } catch (InputMismatchException e) {
                System.out.println("Invalid input");
            }
        }
        s.close();
    }
}