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
public class ExtraArtist {
    private String anv;
    private Integer id;
    private String join;
    private String name;
    private String resourceUrl;
    private String role;
    private String tracks;
}
