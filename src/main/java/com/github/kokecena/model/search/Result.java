package com.github.kokecena.model.search;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
@Getter
@Setter
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Result {
    private Integer id;
    private List<String> style;
    private String thumb;
    private String title;
    private String country;
    private List<String> format;
    private String uri;
    private String coverImage;
    private Community community;
    private List<String> label;
    private String catno;
    private String year;
    private List<String> genre;
    private String resourceUrl;
    private String type;
    private List<String> barcode;
}
