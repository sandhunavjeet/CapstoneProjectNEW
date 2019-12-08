package com.example.capstoneproject;

public class AppliedJob {
    String id;
    String jobid;
    String userid;
    String cmpid;
    String status;
    String jobid_userid_cmpid;
    public AppliedJob() {
    }
    public AppliedJob(String id, String jobid,String cmpid, String userid,String code, String status) {
        this.id = id;
        this.jobid = jobid;
        this.userid = userid;
        this.status = status;
        this.cmpid = cmpid;
        this.jobid_userid_cmpid = code;
    }

    public String getJobid_userid_cmpid() {
        return jobid_userid_cmpid;
    }

    public void setJobid_userid_cmpid(String jobid_userid_cmpid) {
        this.jobid_userid_cmpid = jobid_userid_cmpid;
    }

    public String getCmpid() {
        return cmpid;
    }

    public void setCmpid(String cmpid) {
        this.cmpid = cmpid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJobid() {
        return jobid;
    }

    public void setJobid(String jobid) {
        this.jobid = jobid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
