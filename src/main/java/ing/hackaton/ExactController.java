package ing.hackaton;

import ing.hackaton.exact.api.system.Division;
import ing.hackaton.exact.api.system.Me;
import ing.hackaton.exact.api.Token;
import ing.hackaton.exact.service.ExactService;
import ing.hackaton.exception.BadRequestException;
import ing.hackaton.model.DivisionDTO;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Token getAccessToken(@PathVariable String code) throws IOException {
        Token accessToken = exactService.getAccessToken(code);

        if (accessToken == null) {
            throw new BadRequestException();
        }

        return accessToken;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/exact/divisions")
    public List<DivisionDTO> getDivisions(@RequestHeader(value="token") String token) throws IOException {
        Me me = exactService.getCurrentDivision(token);

        //if ()

        List<Division> divisions = exactService.getDevisions(token, me);

        List<DivisionDTO> divisionDTOs = new ArrayList<>();

        for (Division division : divisions) {
            DivisionDTO divisionDTO = new DivisionDTO(division.getCode(), division.getDescription());
            divisionDTOs.add(divisionDTO);
        }

        return divisionDTOs;
    }
}
