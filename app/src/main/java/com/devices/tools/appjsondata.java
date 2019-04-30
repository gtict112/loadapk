package com.devices.tools;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class appjsondata {

    private List<JSONArray> jsonlist = new ArrayList<>();
    private JSONObject gammejosn ;
    private  String jobpid = "";
    private  String returntype = "";
    private  String jobname ="";
    private boolean isshow =true;
    public void AddjsonItem(JSONArray itemInfo) {
        jsonlist.add( itemInfo );
    }

    public void setGammejosn(JSONObject gammejosn) {
        this.gammejosn = gammejosn;
    }

    public JSONObject getGammejosn() {
        return gammejosn;
    }

    public  String getJobpid() {
        return jobpid;
    }
    public  void setJobpid(String jobpid)
    {
        this.jobpid = jobpid;
    }

    public void setReturntype(String returntype) {
        this.returntype = returntype;
    }
    public String getReturntype()
    {
        return returntype;
    }

    public void setJobname(String jobname) {
        this.jobname = jobname;
    }

    public String getJobname() {
        return jobname;
    }

    public boolean isIsshow() {
        return isshow;
    }
    public void setIsshow(boolean isshow)
    {
        this.isshow =isshow;
    }

    public List<JSONArray> getItemList() {
        return jsonlist;
    }
}
