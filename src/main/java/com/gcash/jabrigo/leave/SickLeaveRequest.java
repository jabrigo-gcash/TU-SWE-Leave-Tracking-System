package com.gcash.jabrigo.leave;

import com.gcash.jabrigo.Employee;

public class SickLeaveRequest extends LeaveRequest{

    private boolean medicalCertificateProvided;

    public SickLeaveRequest(int requestId, Employee employee, String startDate, String endDate, boolean medicalCertificateProvided) {
        super(requestId, employee, startDate, endDate, "Sick Leave");
        this.medicalCertificateProvided = medicalCertificateProvided;
    }

    public boolean isMedicalCertificateProvided() {
        return medicalCertificateProvided;
    }

    public void setMedicalCertificateProvided(boolean medicalCertificateProvided) {
        this.medicalCertificateProvided = medicalCertificateProvided;
    }

    @Override
    public int calculateLeaveDays() {
        return 5;
    }

    @Override
    public boolean processLeaveRequest() {
        if (calculateLeaveDays() > 2 && !medicalCertificateProvided) {
            System.out.println("Medical Certificate has not been provided.");
            return false;
        } else {
            System.out.println("Medical leave request processed.");
            return true;
        }
    }
}
