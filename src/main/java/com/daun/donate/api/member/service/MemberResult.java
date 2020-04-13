package com.daun.donate.api.member.service;

public class MemberResult {
    private String code;
    private String codeMessage;
    private String status;
    private String statusMessage;

    public MemberResult() {
        code="1";
        codeMessage="ok";
        status="200";
        statusMessage="success";
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodeMessage() {
        return codeMessage;
    }

    public void setCodeMessage(String codeMessage) {
        this.codeMessage = codeMessage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }
}
