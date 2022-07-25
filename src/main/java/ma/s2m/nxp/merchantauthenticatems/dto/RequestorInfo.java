package ma.s2m.nxp.merchantauthenticatems.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.s2m.nxp.merchantauthenticatems.domain.MccCode;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestorInfo {

	private String threeDsRequestorCode;

	private String identifier;

	private String cprporationName;

	private String marqueName;

	private String country;

	private String contThreeDrtName;

	private String contThreeDrtAddr;

	private String contThreeDrPhone;

	private String contThreeDrFax;

	private String contThreeDrEmail;

	private String tHreeDsRequestUrl;

	private String threeDsRequestorApps;

	private String institution;

	private String currency;

	private String status;

	private MccCode mccCode;

}
