package com.ramapps.ramplaces.app.data.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "code",
        "errorType",
        "errorDetail",
        "requestId"
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class Meta {

    @JsonProperty("code")
    private Integer code;
    @JsonProperty("errorType")
    private String errorType;
    @JsonProperty("errorDetail")
    private String errorDetail;
    @JsonProperty("requestId")
    private String requestId;

    @JsonProperty("code")
    public Integer getCode() {
        return code;
    }

    @JsonProperty("code")
    public void setCode(Integer code) {
        this.code = code;
    }

    @JsonProperty("errorType")
    public String getErrorType() {
        return errorType;
    }

    @JsonProperty("errorType")
    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    @JsonProperty("errorDetail")
    public String getErrorDetail() {
        return errorDetail;
    }

    @JsonProperty("errorDetail")
    public void setErrorDetail(String errorDetail) {
        this.errorDetail = errorDetail;
    }

    @JsonProperty("requestId")
    public String getRequestId() {
        return requestId;
    }

    @JsonProperty("requestId")
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

}
