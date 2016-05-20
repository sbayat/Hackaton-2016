package ing.hackaton.exact.api.system;

import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by hosinglee on 20/05/16.
 */
@Getter
public class Response {

    private Result d;

    public class Result {
        private List<LinkedHashMap> results;

        public List<LinkedHashMap> getResults() {
            return results;
        }
    }
}
