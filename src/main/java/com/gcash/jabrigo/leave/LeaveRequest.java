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
        statusHistory.add(new StatusChange(LeaveRequestStatus.PENDING, "System"));
    }

    public abstract int calculateLeaveDays();

    public int getRequestId() {
        return requestId;
    }

    public Employee getEmployee() {
        return employee;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public LeaveRequestStatus getStatus() {
        return status;
    }

    public String getLeaveType() {
        return leaveType;
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
                System.out.println("Request #" + requestId + " approved by " + approverName + ".");
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
                System.out.println("Request #" + requestId + " denied by " + approverName + ". Reason: " + reason);
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
        System.out.println("---- Status History for Request #" + requestId + "----");
        for (StatusChange statusChange : statusHistory) {
            System.out.println("Status set to '" + statusChange.getNewStatus() + "' by " + statusChange.getChangedBy()
                    + " on " + statusChange.getChangeDate());
        }
        System.out.println("---------------------------------");
    }


    public static class StatusChange {
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
        PENDING ("Pending"),
        APPROVED ("Approved"),
        DENIED ("Denied");

        private final String displayName;

        LeaveRequestStatus(String displayName) {
            this.displayName = displayName;
        }

        @Override
        public String toString() {
            return displayName;
        }
    }
}
