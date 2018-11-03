
package com.ramapps.ramplaces.app.data.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "name",
        "location",
        "categories",
        "referralId"
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class Venue {

    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("location")
    private Location location;
    @JsonProperty("categories")
    private List<Category> categories = null;
    @JsonProperty("referralId")
    private String referralId;

    public Venue(){

    }

    public Venue(String id, String name, Location location, List<Category> categories, String referralId) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.categories = categories;
        this.referralId = referralId;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("location")
    public Location getLocation() {
        return location;
    }

    @JsonProperty("location")
    public void setLocation(Location location) {
        this.location = location;
    }

    @JsonProperty("categories")
    public List<Category> getCategories() {
        return categories;
    }

    @JsonProperty("categories")
    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    @JsonProperty("referralId")
    public String getReferralId() {
        return referralId;
    }

    @JsonProperty("referralId")
    public void setReferralId(String referralId) {
        this.referralId = referralId;
    }

}
