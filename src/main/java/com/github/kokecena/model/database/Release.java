package com.github.kokecena.model.database;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.github.kokecena.model.release.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Release {
    private String title;
    private Integer id;
    private List<Artist> artists;
    private String artistsSort;
    private String dataQuality;
    private String thumb;
    private Community community;
    private List<Company> companies;
    private String country;
    private String dateAdded;
    private String dateChanged;
    private String estimatedWeight;
    private List<ExtraArtist> extraartists;
    private Boolean blockedFromSale;
    private Integer formatQuantity;
    private List<Format> formats;
    private List<String> genres;
    private List<Identifier> identifiers;
    private List<Image> images;
    private List<Label> labels;
    private Double lowestPrice;
    private Integer masterId;
    private String masterUrl;
    private String notes;
    private Integer numForSale;
    private String released;
    private String releasedFormatted;
    private String resourceUrl;
    private List<String> series;
    private String status;
    private List<String> styles;
    private List<Track> tracklist;
    private String uri;
    private List<Video> videos;
    private Integer year;
}
