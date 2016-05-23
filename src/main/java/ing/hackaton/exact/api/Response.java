package ing.hackaton.exact.api;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by hosinglee on 20/05/16.
 */
@Getter
@Setter
public class Response<T> {

    private HttpStatus httpStatus;

    private List<T> results;

}
