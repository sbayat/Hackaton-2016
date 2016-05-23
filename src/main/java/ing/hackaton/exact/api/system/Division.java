package ing.hackaton.exact.api.system;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by hosinglee on 20/05/16.
 */
@Getter
public class Division {

    @JsonProperty("Description")
    private String description;

    @JsonProperty("Code")
    private Integer code;

}
