package ma.s2m.nxp.merchantauthenticatems.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MerchantAuthStatus {

	private String authenticationIndicator;

	private String currencyCode;

	private boolean isRequestProcessed;

	private String eligibilityIndicator;

	// Not included in the answer of the web service
	private String ecommerceServerURL;

}
