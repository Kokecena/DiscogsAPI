package com.github.kokecena.model.database;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.github.kokecena.model.search.Pagination;
import com.github.kokecena.model.search.Result;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
@Getter
@Setter
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Search {
  private Pagination pagination;
  private List<Result> results;
}
