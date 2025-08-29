package com.gcash.jabrigo.leave;

import com.gcash.jabrigo.Employee;

public class MaternityLeaveRequest extends LeaveRequest{

    private String expectedDeliveryDate;

    public MaternityLeaveRequest(int requestId, Employee employee, String startDate, String endDate, String expectedDeliveryDate) {
        super(requestId, employee, startDate, endDate, "Maternity Leave");
        this.expectedDeliveryDate = expectedDeliveryDate;
    }

    public String getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }

    public void setExpectedDeliveryDate(String expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }

    @Override
    public int calculateLeaveDays() {
        return 90;
    }

    @Override
    public boolean processLeaveRequest() {
        System.out.println("Maternity Leave Request processed.");
        return true;
    }
}
