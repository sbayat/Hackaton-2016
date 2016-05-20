package ing.hackaton.exact.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import ing.hackaton.exact.api.system.Division;
import ing.hackaton.exact.api.system.Me;
import ing.hackaton.exact.api.system.Response;
import ing.hackaton.exact.model.DivisionETO;
import ing.hackaton.exact.model.TokenETO;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
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

    private static final String EXACT_ONLINE_URL = "https://start.exactonline.nl/api/";

    public TokenETO getAccessToken(String code) throws IOException {
        String tokenURL = EXACT_ONLINE_URL + "oauth2/token";

        String redirect_uri = "http://localhost:8080/static/requestToken.html";
        String grant_type = "authorization_code";
        String client_id = "{27a8cdb0-7a05-44fc-bb6a-10a6efdf4a7a}";
        String client_secret = "VdtyYEN1RBSb";

        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(tokenURL);

        // add header
        post.setHeader("Content-Type", "application/x-www-form-urlencoded");

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("redirect_uri", redirect_uri));
        urlParameters.add(new BasicNameValuePair("grant_type", grant_type));
        urlParameters.add(new BasicNameValuePair("client_id", client_id));
        urlParameters.add(new BasicNameValuePair("client_secret", client_secret));
        urlParameters.add(new BasicNameValuePair("code", code));

        UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(urlParameters);
        post.setEntity(urlEncodedFormEntity);

        HttpResponse response = client.execute(post);

        TokenETO tokenETO = null;
        if (response.getStatusLine().getStatusCode() == HttpStatus.OK.value()) {
            String json = EntityUtils.toString(response.getEntity());

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            tokenETO = objectMapper.readValue(json, TokenETO.class);
        }

        return tokenETO;
    }

    public List<Division> getDevisions(String token, Me division) throws IOException {
        String url = EXACT_ONLINE_URL + "/v1/" + division.getCurrentDivision() + "/system/Divisions";

        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url);

        // add request header
        request.addHeader("Accept", "application/json");
        request.addHeader("Authorization", "Bearer " + token);

        HttpResponse response = client.execute(request);

        List<Division> results = null;
        if (response.getStatusLine().getStatusCode() == HttpStatus.OK.value()) {
            String json = EntityUtils.toString(response.getEntity());

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            //TODO find a better way to map to Entity
            Response ro = objectMapper.readValue(json, Response.class);
            results = new ArrayList<Division>();
            for (LinkedHashMap lh : ro.getD().getResults()) {
                results.add(new Division((Integer) lh.get("Code"), (String) lh.get("Description")));
            }
        }

        return results;
    }

    public Me getCurrentDivision(String token) throws IOException {

        final String url = EXACT_ONLINE_URL + "/v1/current/Me";

        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url);

        // add request header
        request.addHeader("Accept", "application/json");
        request.addHeader("Authorization", "Bearer " + token);

        HttpResponse response = client.execute(request);

        Me division = null;
        if (response.getStatusLine().getStatusCode() == HttpStatus.OK.value()) {
            String json = EntityUtils.toString(response.getEntity());

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            //TODO find a better way to map to Entity
            Response ro = objectMapper.readValue(json, Response.class);
            division = new Me((Integer) ro.getD().getResults().get(0).get("CurrentDivision"));
        }

        return division;
    }
}
