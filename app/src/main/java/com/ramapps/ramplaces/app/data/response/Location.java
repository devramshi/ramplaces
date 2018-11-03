
package com.ramapps.ramplaces.app.data.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "address",
    "crossStreet",
    "lat",
    "lng",
    "distance",
    "cc",
    "city",
    "state",
    "country",
    "formattedAddress"
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class Location {

    @JsonProperty("address")
    private String address;
    @JsonProperty("crossStreet")
    private String crossStreet;
    @JsonProperty("lat")
    private Double lat;
    @JsonProperty("lng")
    private Double lng;
    @JsonProperty("distance")
    private Integer distance;
    @JsonProperty("cc")
    private String cc;
    @JsonProperty("city")
    private String city;
    @JsonProperty("state")
    private String state;
    @JsonProperty("country")
    private String country;
    @JsonProperty("formattedAddress")
    private List<String> formattedAddress = null;

    @JsonProperty("address")
    public String getAddress() {
        return address;
    }

    @JsonProperty("address")
    public void setAddress(String address) {
        this.address = address;
    }

    @JsonProperty("crossStreet")
    public String getCrossStreet() {
        return crossStreet;
    }

    @JsonProperty("crossStreet")
    public void setCrossStreet(String crossStreet) {
        this.crossStreet = crossStreet;
    }

    @JsonProperty("lat")
    public Double getLat() {
        return lat;
    }

    @JsonProperty("lat")
    public void setLat(Double lat) {
        this.lat = lat;
    }

    @JsonProperty("lng")
    public Double getLng() {
        return lng;
    }

    @JsonProperty("lng")
    public void setLng(Double lng) {
        this.lng = lng;
    }

    @JsonProperty("distance")
    public Integer getDistance() {
        return distance;
    }

    @JsonProperty("distance")
    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    @JsonProperty("cc")
    public String getCc() {
        return cc;
    }

    @JsonProperty("cc")
    public void setCc(String cc) {
        this.cc = cc;
    }

    @JsonProperty("city")
    public String getCity() {
        return city;
    }

    @JsonProperty("city")
    public void setCity(String city) {
        this.city = city;
    }

    @JsonProperty("state")
    public String getState() {
        return state;
    }

    @JsonProperty("state")
    public void setState(String state) {
        this.state = state;
    }

    @JsonProperty("country")
    public String getCountry() {
        return country;
    }

    @JsonProperty("country")
    public void setCountry(String country) {
        this.country = country;
    }

    @JsonProperty("formattedAddress")
    public List<String> getFormattedAddress() {
        return formattedAddress;
    }

    @JsonProperty("formattedAddress")
    public void setFormattedAddress(List<String> formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

}
