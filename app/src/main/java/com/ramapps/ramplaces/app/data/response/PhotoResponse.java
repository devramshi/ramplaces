
package com.ramapps.ramplaces.app.data.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "meta",
    "response"
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class PhotoResponse {

    @JsonProperty("meta")
    private Meta meta;
    @JsonProperty("response")
    private Photo response;

    @JsonProperty("meta")
    public Meta getMeta() {
        return meta;
    }

    @JsonProperty("meta")
    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    @JsonProperty("response")
    public Photo getResponse() {
        return response;
    }

    @JsonProperty("response")
    public void setResponse(Photo response) {
        this.response = response;
    }

}
