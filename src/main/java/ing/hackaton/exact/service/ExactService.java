package ing.hackaton.exact.service;

import ing.hackaton.exact.api.Response;
import ing.hackaton.exact.api.Token;
import ing.hackaton.exact.api.models.ProfitLossOverview;
import ing.hackaton.exact.api.system.Division;
import ing.hackaton.exact.api.system.Me;
import org.apache.http.client.methods.HttpGet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
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

        return ExactRestClient.getInstance().requestAccessToken(tokenURL, REDIRECT_URI, GRANT_TYPE, CLIENT_ID, CLIENT_SECRET, code);
    }

    public List<Division> getDevisions(String token, Me division) throws IOException {
        String url = EXACT_ONLINE_URL + "/v1/" + division.getCurrentDivision() + "/system/Divisions";

        Response response = ExactRestClient.getInstance().execute(new HttpGet(url), token, Division.class);

        return response.getResults();
    }

    public Me getCurrentDivision(String token) throws IOException {
        final String url = EXACT_ONLINE_URL + "/v1/current/Me";

        Response response = ExactRestClient.getInstance().execute(new HttpGet(url), token, Me.class);

        return (Me) response.getResults().get(0);
    }

    public ProfitLossOverview getProfitLostOverview(String token) throws IOException {
        final String url = EXACT_ONLINE_URL + "/v1/308801/read/financial/ProfitLossOverview?$select=CurrentYear,CostsCurrentPeriod,CostsCurrentYear,CostsPreviousYear,CostsPreviousYearPeriod,CurrencyCode,CurrentPeriod,PreviousYear,PreviousYearPeriod,ResultCurrentPeriod,ResultCurrentYear,ResultPreviousYear,ResultPreviousYearPeriod,RevenueCurrentPeriod,RevenueCurrentYear,RevenuePreviousYear,RevenuePreviousYearPeriod";

        Response response = ExactRestClient.getInstance().execute(new HttpGet(url), token, ProfitLossOverview.class);

        return (ProfitLossOverview) response.getResults().get(0);
    }
}
