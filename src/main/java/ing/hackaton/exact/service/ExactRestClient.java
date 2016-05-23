package ing.hackaton.exact.service;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hosinglee on 23/05/16.
 */
public class ExactRestClient {

    private HttpClient client;

    public static ExactRestClient getInstance() {
        return new ExactRestClient();
    }

    private ExactRestClient() {
        client = HttpClientBuilder.create().build();
    }

    public HttpResponse execute(HttpRequestBase httpRequest, String token) throws IOException {
        // add request header
        httpRequest.addHeader("Accept", "application/json");
        httpRequest.addHeader("Authorization", "Bearer " + token);

        return client.execute(httpRequest);
    }

    public HttpResponse requestAccessToken(String tokenURL, String redirect_uri, String grant_type, String client_id, String client_secret, String code) throws IOException {
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

        return client.execute(post);
    }


}
