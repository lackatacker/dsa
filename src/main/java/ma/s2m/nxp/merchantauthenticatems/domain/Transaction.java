package ma.s2m.nxp.merchantauthenticatems.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Transaction implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private static final long serialVersionUID = 1L;

	private Date trxDate;

	private String trxCode;

	private String tempToken;

	private String trxCurrency;

	private String trxCurrencyName;

	private String trxReference;

	private String trxAmount;

	private String trxEcmId;

	private String trxMcc;

	private String trxCardHolderPhoneNumber;

	private String trxCardHolderMailAddress;

	private String trxCardHolderIPAddress;

	private String trxMerchantKitId;

	private String trxAcquirerId;

	private String trxLanguage;

	private String trxCountryCode;

	private String trxPan;

	private String trxExpiryDate;

	private String trxLocalDate;

	private String trxMpiId;

	private String trxIsCardHolderEligibleToRecieveMail;

	private String merchantId;

	private String trxCardBrand;

	private String trxProcessStatus;

	private String trxCvv;

	private String trxType;

	private String rememberMe;

	private String trxTotalAmount;

	private String trxInstallmentsCount;

	private String trxPeriodicity;

	private String trxOriginalMsgType;

	private String trxOriginalStan;

	private String trxOriginalDate;

	private String trxOriginalMerId;

	private String trxMerchantURL;

	private String trxCavv;

	private String callBackURL;

	private String redirectBackUrl;

	private String errorMessage;

}
