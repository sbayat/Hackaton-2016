package ing.hackaton.exact.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by hosinglee on 20/05/16.
 */
@Getter
@Setter
public class TokenETO {

    private String access_token;

    private String token_type;

    private String expires_in;

    private String refresh_token;
}
