package com.github.kokecena.model.release;

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
public class Community {
    private List<Contribuitor> contributors;
    private String dataQuality;
    private Integer have;
    private Rating rating;
    private String status;
    private Submitter submitter;
    private Integer want;
}
