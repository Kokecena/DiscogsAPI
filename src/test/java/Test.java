import com.github.kokecena.Discogs;
import com.github.kokecena.model.database.Search;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.*;

import static com.github.kokecena.keys.SearchParameters.SEARCH_QUERY;

public class Test {
    private static final String CONSUMER_KEY = "rGjOUCZxesqlfIARxGWM";
    private static final String CONSUMER_SECRET = "lYfmWnyRtDIdtoDOfzktXHMUdaIQVMyo";

    public static void main(String... args) {
        //Discogs API interface
        Discogs discogs = new Discogs("JovisPlayer", CONSUMER_KEY, CONSUMER_SECRET);
        //Setting search options

        Map<String, String> querys = new HashMap<>();
        querys.put(SEARCH_QUERY, "Night & Day");
        // Create a call instance for looking up Retrofit database.
        Mono<Search> monoSearch = discogs.databaseService().search(querys);
        monoSearch.publishOn(Schedulers.single())
                .map(Search::getResults)
                .filter(result -> !result.isEmpty())
                .subscribe(System.out::println);
    }
}
