package com.github.kokecena.model.release;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Label {
    private String catno;
    private String entityType;
    private String entityTypeName;
    private Integer id;
    private String name;
    private String resourceUrl;
}
