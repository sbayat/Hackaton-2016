package ing.hackaton.exact.model;

import ing.hackaton.exact.api.system.Division;
import lombok.Getter;

import java.util.List;

/**
 * Created by hosinglee on 20/05/16.
 */
@Getter
public class DivisionETO {

    private Result d;

    public class Result {
        private List<Division> results;

        public List<Division> getResults() {
            return results;
        }
    }
}
