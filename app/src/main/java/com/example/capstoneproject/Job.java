package com.example.capstoneproject;

public class Job {

    String jobid;
    String cmpid;
    String cmpname;
    String title;
    String pay;
    String appdate;
    String apptime;
    String desc;

    public Job() {
    }

    public Job(String jobid, String cmpid, String cmpname, String title, String pay,
               String appdate, String apptime, String desc) {
        this.jobid = jobid;
        this.cmpid = cmpid;
        this.cmpname = cmpname;
        this.title = title;
        this.pay = pay;
        this.appdate = appdate;
        this.apptime = apptime;
        this.desc = desc;
    }

    public String getJobid() {
        return jobid;
    }

    public void setJobid(String jobid) {
        this.jobid = jobid;
    }

    public String getCmpid() {
        return cmpid;
    }

    public void setCmpid(String cmpid) {
        this.cmpid = cmpid;
    }

    public String getCmpname() {
        return cmpname;
    }

    public void setCmpname(String cmpname) {
        this.cmpname = cmpname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPay() {
        return pay;
    }

    public void setPay(String pay) {
        this.pay = pay;
    }

    public String getAppdate() {
        return appdate;
    }

    public void setAppdate(String appdate) {
        this.appdate = appdate;
    }

    public String getApptime() {
        return apptime;
    }

    public void setApptime(String apptime) {
        this.apptime = apptime;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
