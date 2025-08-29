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
        System.out.println("Processing sick leave request for " + employee.getName() + "...");
        if (calculateLeaveDays() > 2 && !medicalCertificateProvided) {
            System.out.println("-> VALIDATION FAILED: Sick leave longer than 2 days requires a medical certificate.");
            return false;
        } else {
            System.out.println("-> VALIDATION SUCCESSFUL: Sick leave is valid.");
            return true;
        }
    }
}
