package ing.hackaton.exact.api;

import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by hosinglee on 20/05/16.
 */
@Getter
public class Response {

    private Result d;

    public List<LinkedHashMap> getResults() {
        return d.getResults();
    }

    public class Result {
        private List<LinkedHashMap> results;

        public List<LinkedHashMap> getResults() {
            return results;
        }
    }
}
