package com.github.kokecena.service;

import com.github.kokecena.model.database.Release;
import com.github.kokecena.model.database.ReleaseRatingByUser;
import com.github.kokecena.model.database.Search;
import com.github.kokecena.keys.SearchParameters;
import reactor.core.publisher.Mono;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;


import java.util.Map;

public interface DatabaseService {
    /**
     * Issue a search query to our database. This endpoint accepts pagination parameters
     * Authentication (as any user) is required.
     * @param query Your search query, see {@link SearchParameters SearchParameters} class
     * @return {@link Search Search} model
     */
    @GET("database/search")
    Mono<Search> search(@QueryMap Map<String, String> query);

    /**
     * The Release resource represents a particular physical or digital object released by one or more Artists.
     * @param releaseId The Release ID
     * @return {@link Release Release} model
     */
    @GET("releases/{release_id}")
    Mono<Release> release(@Path("release_id") String releaseId);

    /**
     * The Release resource represents a particular physical or digital object released by one or more Artists.
     * @param releaseId The Release ID
     * @param currAbbr Currency for marketplace data. Defaults to the authenticated users currency.
     *                 Must be one of the {@link com.github.kokecena.keys.CurrencyOptions CurrencyOption.class}
     * @return {@link Release Release} model
     */
    @GET("releases/{release_id}")
    Mono<Release> release(@Path("release_id") String releaseId, @Query("curr_abbr") String currAbbr);

    /**
     * The Release Rating endpoint retrieves the rating of a release for a given user.
     * @param releaseId The Release ID
     * @param username The username of the rating you are trying to request.
     * @return {@link ReleaseRatingByUser ReleaseRatingByUser} model
     */
    @GET("releases/{release_id}/rating/{username}")
    Mono<ReleaseRatingByUser> releaseRatingByUser(@Path("release_id") String releaseId, @Path("username") String username);
}
