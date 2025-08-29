package com.gcash.jabrigo.leave;

import com.gcash.jabrigo.Employee;

public class VacationLeaveRequest extends LeaveRequest{

    private boolean isPaidTimeOff;

    public VacationLeaveRequest(int requestId, Employee employee, String startDate, String endDate, boolean isPaidTimeOff) {
        super(requestId, employee, startDate, endDate, "Vacation Leave");
        this.isPaidTimeOff = isPaidTimeOff;
    }

    public boolean isPaidTimeOff() {
        return isPaidTimeOff;
    }

    public void setPaidTimeOff(boolean paidTimeOff) {
        isPaidTimeOff = paidTimeOff;
    }

    @Override
    public int calculateLeaveDays() {
        return 10;
    }

    @Override
    public boolean processLeaveRequest() {
        System.out.println("Vacation Leave Request processed.");
        return true;
    }
}
