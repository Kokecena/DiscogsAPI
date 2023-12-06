package com.github.kokecena.model.search;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Pagination {
    private Integer perPage;
    private Integer pages;
    private Integer page;
    private Urls urls;
    private Integer items;

}
