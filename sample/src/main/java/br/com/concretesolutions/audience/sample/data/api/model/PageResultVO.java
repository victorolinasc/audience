package br.com.concretesolutions.audience.sample.data.api.model;


import com.squareup.moshi.Json;

import java.util.List;

public class PageResultVO<T> {

    @Json(name = "total_count")
    private Integer totalCount;
    @Json(name = "incomplete+results")
    private Boolean incompleteResults;
    @Json(name = "items")
    private List<T> items;

    // GETTERs & SETTERs -------------------
    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Boolean getIncompleteResults() {
        return incompleteResults;
    }

    public void setIncompleteResults(Boolean incompleteResults) {
        this.incompleteResults = incompleteResults;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}
