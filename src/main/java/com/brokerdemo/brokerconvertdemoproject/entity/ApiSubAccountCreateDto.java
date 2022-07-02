package com.brokerdemo.brokerconvertdemoproject.entity;

public class ApiSubAccountCreateDto {
      String acctLv;
      String label;
      String subAcct;
      String ts;

    public String getAcctLv() {
        return acctLv;
    }

    public void setAcctLv(String acctLv) {
        this.acctLv = acctLv;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getSubAcct() {
        return subAcct;
    }

    public void setSubAcct(String subAcct) {
        this.subAcct = subAcct;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }
}
