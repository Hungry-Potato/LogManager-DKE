package com.logLogin.Model;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class parseMsg {
    @JsonProperty("ip")
    private List<String> ip;
    @JsonProperty("source_ip")
    private String sourceIp;
    @JsonProperty("login_id")
    private String loginId;
    @JsonProperty("result")
    private String result;
    @JsonProperty("hostname")
    private String hostname;
    @JsonProperty("@timestamp")
    private String timestamp;

    @Override
    public String toString() {
        if(result.equals("success"))
            return "[SUCCESS] User '" + loginId + "' connected from " + sourceIp + " to " + hostname + "(" + ip + ").";
        else
            return "[FAIL] User '" + loginId + "' failed to connect from " + sourceIp + " to " + hostname + "(" + ip + ").";
    }
}
