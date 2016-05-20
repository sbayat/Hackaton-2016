package ing.hackaton;

import ing.hackaton.exact.api.system.Division;
import ing.hackaton.exact.model.TokenETO;
import ing.hackaton.exact.service.ExactService;
import ing.hackaton.model.DivisionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hosinglee on 17/05/16.
 */
@RestController
public class ExactController {

    private static final String EXACT_ONLINE_URL = "https://start.exactonline.nl/api/";

    @Autowired
    private ExactService exactService;

    @RequestMapping(method = RequestMethod.GET, path = "/exact/token/{code}")
    public ResponseEntity<?> getAccessToken(@PathVariable String code) throws IOException {
        TokenETO accessToken = exactService.getAccessToken(code);

        if (accessToken != null) {
            //TODO transform accessToken to DTO
            return new ResponseEntity<>(accessToken, HttpStatus.ACCEPTED);
        } else {
            // TODO is this the correct http status?
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/exact/divisions")
    public List<DivisionDTO> getDivisions(@RequestHeader(value="token") String token) throws IOException {
        Division division = exactService.getCurrentDivision(token);

        List<Division> devisions = exactService.getDevisions(token, division);

        List<DivisionDTO> divisionDTOs = new ArrayList<>();

        for (Division devision : devisions) {
            DivisionDTO divisionDTO = new DivisionDTO(devision.getDescription(), devision.getCode());
            divisionDTOs.add(divisionDTO);
        }

        return divisionDTOs;
    }
}
