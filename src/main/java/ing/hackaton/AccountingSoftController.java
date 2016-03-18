package ing.hackaton;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountingSoftController {

    @RequestMapping("/exact")
    public StringResponse greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new StringResponse("Een test");
    }
}
