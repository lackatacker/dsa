package ma.s2m.nxp.merchantauthenticatems.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.s2m.nxp.merchantauthenticatems.domain.Customer;
import ma.s2m.nxp.merchantauthenticatems.domain.Order;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MerchantAuthDTO {

	@NotBlank
	private String pspId;

	@NotBlank
	private String mpiId;

	@NotBlank
	private String merchantId;

	@NotBlank
	private String merchantKitId;

	@NotBlank
	private String mcc;

	@NotBlank
	private String authenticationToken;

	@Valid
	private Customer customer;

	@NotBlank
	private String cardHolderIPAddress;

	@NotBlank
	private String dateTimeSIC;

	@Valid
	private Order order;

	private String dateTimeBuyer;

	@NotBlank
	private String language;

	@NotBlank
	private String transactionTypeIndicator;

	@NotBlank
	private String countryCode;

	private String acquirerId;

	private Double recInsTransactionTotalAmount;

	private String installmentsNbr;

	private String periodicity;

	@NotBlank
	private String callBackUrl;

	@NotBlank
	private String redirectBackUrl;

}
