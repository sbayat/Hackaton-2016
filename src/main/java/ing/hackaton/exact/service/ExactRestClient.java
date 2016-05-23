package ing.hackaton.exact.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import ing.hackaton.exact.api.Response;
import ing.hackaton.exact.api.Token;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hosinglee on 23/05/16.
 */
public class ExactRestClient {

    private HttpClient client;

    private ObjectMapper objectMapper;

    public static ExactRestClient getInstance() {
        return new ExactRestClient();
    }

    private ExactRestClient() {
        client = HttpClientBuilder.create().build();

        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public Response execute(HttpRequestBase httpRequest, String token, Class type) throws IOException {
        // add request header
        httpRequest.addHeader("Accept", "application/json");
        httpRequest.addHeader("Authorization", "Bearer " + token);

        HttpResponse response = client.execute(httpRequest);

        Response result = new Response();
        result.setHttpStatus(HttpStatus.valueOf(response.getStatusLine().getStatusCode()));

        if (response.getStatusLine().getStatusCode() == HttpStatus.OK.value()) {
            String json = EntityUtils.toString(response.getEntity());
            result.setResults(mapExactOnlineResultToEntity(json, type));
        }

        return result;
    }

    public Token requestAccessToken(String tokenURL, String redirect_uri, String grant_type, String client_id, String client_secret, String code) throws IOException {
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

        String json = EntityUtils.toString(response.getEntity());

        return objectMapper.readValue(json, Token.class);
    }


    private List mapExactOnlineResultToEntity(String json, Class type) throws IOException {
        JSONObject jso = new JSONObject(json);
        JSONObject d = jso.getJSONObject("d");
        JSONArray results = d.getJSONArray("results");

        return objectMapper.readValue(results.toString(), objectMapper.getTypeFactory().constructCollectionType(List.class, type));
    }
}
