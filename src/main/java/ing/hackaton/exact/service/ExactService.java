package ing.hackaton.exact.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import ing.hackaton.exact.api.Response;
import ing.hackaton.exact.api.Token;
import ing.hackaton.exact.api.system.Division;
import ing.hackaton.exact.api.system.Me;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;


/**
 * Created by hosinglee on 20/05/16.
 */
@Service
public class ExactService {

    @Value("${exact.api_uri}")
    private String EXACT_ONLINE_URL;

    @Value("${exact.redirect_uri}")
    private String REDIRECT_URI;

    @Value("${exact.grant_type}")
    private String GRANT_TYPE;

    @Value("${exact.client_id}")
    private String CLIENT_ID;

    @Value("${exact.client_secret}")
    private String CLIENT_SECRET;



    public Token getAccessToken(String code) throws IOException {
        String tokenURL = EXACT_ONLINE_URL + "oauth2/token";

        HttpResponse response = ExactRestClient.getInstance().requestAccessToken(tokenURL, REDIRECT_URI, GRANT_TYPE, CLIENT_ID, CLIENT_SECRET, code);

        Token token = null;
        if (response.getStatusLine().getStatusCode() == HttpStatus.OK.value()) {
            String json = EntityUtils.toString(response.getEntity());

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            token = objectMapper.readValue(json, Token.class);
        }

        return token;
    }

    public List<Division> getDevisions(String token, Me division) throws IOException {
        String url = EXACT_ONLINE_URL + "/v1/" + division.getCurrentDivision() + "/system/Divisions";

        HttpResponse response = ExactRestClient.getInstance().execute(new HttpGet(url), token);

        List<Division> results = null;
        if (response.getStatusLine().getStatusCode() == HttpStatus.OK.value()) {
            String json = EntityUtils.toString(response.getEntity());

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            //TODO find a better way to map to Entity
            Response ro = objectMapper.readValue(json, Response.class);
            results = new ArrayList();
            for (LinkedHashMap lh : ro.getResults()) {
                results.add(new Division((Integer) lh.get("Code"), (String) lh.get("Description")));
            }
        }

        return results;
    }

    public Me getCurrentDivision(String token) throws IOException {
        final String url = EXACT_ONLINE_URL + "/v1/current/Me";

        HttpResponse response = ExactRestClient.getInstance().execute(new HttpGet(url), token);

        Me division = null;
        if (response.getStatusLine().getStatusCode() == HttpStatus.OK.value()) {
            String json = EntityUtils.toString(response.getEntity());

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            //TODO find a better way to map to Entity
            Response ro = objectMapper.readValue(json, Response.class);
            division = new Me((Integer) ro.getResults().get(0).get("CurrentDivision"));
        }

        return division;
    }
}
