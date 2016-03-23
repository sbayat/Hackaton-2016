package ing.hackaton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static org.apache.http.HttpHeaders.USER_AGENT;

@RestController
public class AccountingSoftController {

    private static final String AUTH_TOKEN = "auth_token";
    private static final String CURRENT_DIVISION = "CurrentDivision";

    @RequestMapping("/exact/catchtoken/{auth_token}")
    public StringResponse catchToken(@PathVariable(value=AUTH_TOKEN) String auth_token, HttpServletRequest httpRequest) {

        // save authorization token
        httpRequest.getSession().setAttribute(AUTH_TOKEN, auth_token);

        String currentDivision = null;
        StringBuffer result = null;
        try {
            String url = "https://start.exactonline.nl/api/v1/current/Me";

            currentDivision = determineDivision(auth_token);
            // save current division
            httpRequest.getSession().setAttribute(CURRENT_DIVISION, currentDivision);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        StringResponse returnval = new StringResponse(currentDivision);
        return returnval;
    }

    @RequestMapping(value = "/journaalpost/year/{year}", produces = "application/json")
    public String journaalpostenByYear(@PathVariable(value="year") String year) throws IOException {

        InputStream is = getClass().getClassLoader().getResourceAsStream("journaalposten" + year + ".json");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuffer sb = new StringBuffer();
        String sCurrentLine;
        while ((sCurrentLine = br.readLine()) != null) {
            sb.append(sCurrentLine);
            System.out.println(sCurrentLine);
        }
        JSONObject jso = new JSONObject(sb.toString());
        return jso.toString();
    }

    private String determineDivision(String auth_token) throws IOException {
        final String url = "https://start.exactonline.nl/api/v1/current/Me";

        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(url);

        // add request header
        request.addHeader("User-Agent", USER_AGENT);
        request.addHeader("Accept", "application/json");
        request.addHeader("Authorization",  "Bearer " + auth_token);

        org.apache.http.HttpResponse response = client.execute(request);

        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        System.out.println(result.toString());

        JSONObject jso = new JSONObject(result.toString());
        JSONObject d = jso.getJSONObject("d");
        JSONArray results = d.getJSONArray("results");
        JSONObject nul = results.getJSONObject(0);
        int cdv = jso.getJSONObject("d")
                .getJSONArray("results")
                .getJSONObject(0)
                .getInt(CURRENT_DIVISION);

        return String.valueOf(cdv);
    }
}
