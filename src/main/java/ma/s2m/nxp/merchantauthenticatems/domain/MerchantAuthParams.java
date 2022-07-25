package ma.s2m.nxp.merchantauthenticatems.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MerchantAuthParams {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String ecommerceServerID;

	private String mpiID;

	private String merchantID;

	private String merchantKitID;

	private String mcc;

	private String authenticationToken;

	private String ipAddress;

	private String currency;

	private String redirectionDateAndHour;

	private String operationAmount;

	private String mailAddress;

	private String browserLanguage;

	private String operationTypeIndicator;

	private String hashedValue;

	private String currencyISOCode;

}
