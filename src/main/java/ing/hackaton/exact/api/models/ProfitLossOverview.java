package ing.hackaton.exact.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Created by hosinglee on 23/05/16.
 */
@Getter
public class ProfitLossOverview {


    @JsonProperty("CurrentYear")
    private Integer currentYear;

    @JsonProperty("PreviousYear")
    private Integer previousYear;

    @JsonProperty("CurrentPeriod")
    private Integer currentPeriod;

    @JsonProperty("PreviousYearPeriod")
    private Integer previousYearPeriod;

    @JsonProperty("CurrencyCode")
    private String currencyCode;

    @JsonProperty("ResultCurrentYear")
    private BigDecimal resultCurrentYear;

    @JsonProperty("ResultPreviousYear")
    private BigDecimal resultPreviousYear;

    @JsonProperty("RevenueCurrentYear")
    private BigDecimal revenueCurrentYear;

    @JsonProperty("RevenuePreviousYear")
    private BigDecimal revenuePreviousYear;

    @JsonProperty("CostCurrentYear")
    private BigDecimal costCurrentYear;

    @JsonProperty("CostPreviousYear")
    private BigDecimal costPreviousYear;

    @JsonProperty("ResultCurrentPeriod")
    private BigDecimal resultCurrentPeriod;

    @JsonProperty("ResultPreviousYearPeriod")
    private BigDecimal resultPreviousYearPeriod;

    @JsonProperty("RevenueCurrentPeriod")
    private BigDecimal revenueCurrentPeriod;

    @JsonProperty("RevenuePreviousYearPeriod")
    private BigDecimal revenuePreviousYearPeriod;

    @JsonProperty("CostsCurrentPeriod")
    private BigDecimal costsCurrentPeriod;

    @JsonProperty("CostsPreviousYearPeriod")
    private BigDecimal costsPreviousYearPeriod;

}
