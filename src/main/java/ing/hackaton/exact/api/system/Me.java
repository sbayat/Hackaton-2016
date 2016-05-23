package ing.hackaton.exact.api.system;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by hosinglee on 20/05/16.
 */
@Getter
public class Me {

    @JsonProperty("CurrentDivision")
    private Integer currentDivision;
}
