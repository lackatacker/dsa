package ma.s2m.nxp.merchantauthenticatems.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MerchantAuthResponse {

	private String result;

	private String sessionId;

	private String successIndicator;

	private String error;

}
