package com.gcash.jabrigo.leave;

public interface Approvable {

    boolean approve(String approverName);
    boolean deny(String approverName, String reason);
}
