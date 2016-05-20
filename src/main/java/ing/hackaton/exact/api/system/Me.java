package ing.hackaton.exact.api.system;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by hosinglee on 20/05/16.
 */
@Getter
public class Me {

    private Integer currentDivision;

    public Me(Integer currentDivision) {
        this.currentDivision = currentDivision;
    }
}
