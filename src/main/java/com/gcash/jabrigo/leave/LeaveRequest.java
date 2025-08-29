package com.gcash.jabrigo.leave;

import com.gcash.jabrigo.Employee;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public abstract class LeaveRequest implements Approvable {

    protected int requestId;
    protected Employee employee;
    protected String startDate;
    protected String endDate;
    protected LeaveRequestStatus status;
    protected String leaveType;
    private final ArrayList<StatusChange> statusHistory;

    public LeaveRequest(int requestId, Employee employee, String startDate, String endDate, String leaveType) {
        this.requestId = requestId;
        this.employee = employee;
        this.startDate = startDate;
        this.endDate = endDate;
        this.leaveType = leaveType;
        this.status = LeaveRequestStatus.PENDING;
        statusHistory = new ArrayList<>();
        statusHistory.add(new StatusChange(LeaveRequestStatus.PENDING,"SYSTEM"));
    }

    public abstract int calculateLeaveDays();

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public LeaveRequestStatus getStatus() {
        return status;
    }

    public void setStatus(LeaveRequestStatus status) {
        this.status = status;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    public ArrayList<StatusChange> getStatusHistory() {
        return statusHistory;
    }

    @Override
    public boolean approve(String approverName) {
        switch (this.status) {
            case DENIED -> {
                System.out.println("Request has already been denied");
                return false;
            }
            case APPROVED -> {
                System.out.println("Request has already been approved");
                return false;
            }
            case PENDING -> {
                this.status = LeaveRequestStatus.APPROVED;
                System.out.println("Request has been approved");
                statusHistory.add(new StatusChange(LeaveRequestStatus.APPROVED, approverName));
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deny(String approverName, String reason) {
        switch (this.status) {
            case DENIED -> {
                System.out.println("Request has already been denied");
                return false;
            }
            case APPROVED -> {
                System.out.println("Request has already been approved");
                return false;
            }
            case PENDING -> {
                this.status = LeaveRequestStatus.DENIED;
                System.out.println("Request has been denied for reason: " + reason);
                statusHistory.add(new StatusChange(LeaveRequestStatus.DENIED, approverName));
                return true;
            }
        }
        return false;
    }

    public boolean processLeaveRequest() {
        System.out.println("Processing generic leave request...");
        return true;
    }

    public void printStatusHistory() {
        statusHistory.forEach( statusChange -> {
            System.out.println("status: " + statusChange.getNewStatus() + " date: " + statusChange.getChangeDate()
                    + " changed by: " + statusChange.getChangedBy());
        });
    }


    public class StatusChange {
        private final LeaveRequestStatus newStatus;
        private final String changeDate;
        private final String changedBy;

        public StatusChange(LeaveRequestStatus newStatus, String changedBy) {
            this.newStatus = newStatus;
            this.changedBy = changedBy;

            this.changeDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }

        public LeaveRequestStatus getNewStatus() {
            return newStatus;
        }

        public String getChangeDate() {
            return changeDate;
        }

        public String getChangedBy() {
            return changedBy;
        }
    }

    public enum LeaveRequestStatus {
        PENDING,
        APPROVED,
        DENIED
    }
}
