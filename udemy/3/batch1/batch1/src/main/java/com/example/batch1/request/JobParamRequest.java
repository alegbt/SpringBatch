package com.example.batch1.request;

public class JobParamRequest {

    private  String paramKey;
    private  String paramValue;


    public JobParamRequest(String paramKey, String paramValue) {
        this.paramKey = paramKey;
        this.paramValue = paramValue;
    }

    public String getParamKey() {
        return paramKey;
    }

    public void setParamKey(String paramKey) {
        this.paramKey = paramKey;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }
}
