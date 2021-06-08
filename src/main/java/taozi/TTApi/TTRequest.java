package taozi.TTApi;

import java.util.List;


public class TTRequest {

    private List<String> to_user_list;

    private String msg;

    private String bizAlarm;

    public List<String> getTo_user_list() {
        return to_user_list;
    }

    public void setTo_user_list(List<String> to_user_list) {
        this.to_user_list = to_user_list;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getBizAlarm() {
        return bizAlarm;
    }

    public void setBizAlarm(String bizAlarm) {
        this.bizAlarm = bizAlarm;
    }
}
