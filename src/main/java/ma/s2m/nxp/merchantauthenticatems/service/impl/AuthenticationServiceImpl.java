package ma.s2m.nxp.merchantauthenticatems.service.impl;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.uuid.Generators;

import ma.s2m.nxp.merchantauthenticatems.utils.Constants;
import ma.s2m.nxp.merchantauthenticatems.domain.MerchantAuthParams;
import ma.s2m.nxp.merchantauthenticatems.domain.Transaction;
import ma.s2m.nxp.merchantauthenticatems.dto.MerchantAuthDTO;
import ma.s2m.nxp.merchantauthenticatems.dto.MerchantAuthResponse;
import ma.s2m.nxp.merchantauthenticatems.dto.MerchantAuthStatus;
import ma.s2m.nxp.merchantauthenticatems.dto.RequestorInfo;
import ma.s2m.nxp.merchantauthenticatems.exception.InvalidMerchantException;
import ma.s2m.nxp.merchantauthenticatems.repository.TransactionRepository;
import ma.s2m.nxp.merchantauthenticatems.service.IAuthenticationService;
import ma.s2m.nxp.merchantauthenticatems.utils.PropertiesUtils;

@PropertySource(value = "classpath:/application.yml", ignoreResourceNotFound = true)
@Service
public class AuthenticationServiceImpl implements IAuthenticationService {

	private static final Logger logger = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

	private TransactionRepository transactionRepository;

	private RestTemplate restTemplate;

	@Value("${CURRENCY_ISO_CODE}")
	private String currencyISOCode;

	public AuthenticationServiceImpl(RestTemplate restTemplate, TransactionRepository transactionRepository) {
		this.restTemplate = restTemplate;
		this.transactionRepository = transactionRepository;
	}

	@Override
	public MerchantAuthResponse authenticateMerchant(MerchantAuthDTO merchantAuthDTO) {

		MerchantAuthResponse merchantAuthResp = new MerchantAuthResponse();

		MerchantAuthParams authParams = new MerchantAuthParams();
		authParams.setEcommerceServerID(merchantAuthDTO.getPspId());
		authParams.setMpiID(merchantAuthDTO.getMpiId());
		authParams.setMerchantID(merchantAuthDTO.getMerchantId());
		authParams.setMerchantKitID(merchantAuthDTO.getMerchantKitId());
		authParams.setMcc(merchantAuthDTO.getMcc());
		authParams.setAuthenticationToken("");
		authParams.setIpAddress(merchantAuthDTO.getCardHolderIPAddress());
		authParams.setRedirectionDateAndHour(merchantAuthDTO.getDateTimeSIC());
		authParams.setOperationAmount(merchantAuthDTO.getOrder().getAmount());
		authParams.setMailAddress(merchantAuthDTO.getCustomer().getEmail());
		authParams.setBrowserLanguage(merchantAuthDTO.getLanguage());
		authParams.setOperationTypeIndicator(merchantAuthDTO.getTransactionTypeIndicator());
		authParams.setCurrency(merchantAuthDTO.getOrder().getCurrency());
		authParams.setCurrencyISOCode(currencyISOCode);

		logger.info("Validating Merchant with ID: " + merchantAuthDTO.getMerchantId());
		MerchantAuthStatus merchantAuthStatus = validateMerchant(authParams, merchantAuthDTO.getMerchantId());
		if (merchantAuthStatus != null
				&& merchantAuthStatus.getAuthenticationIndicator().equals(Constants.SUCCESS_RESPONSE_CODE)) {
			logger.info("Merchant was authenticated successfully!");
			logger.info("Creating transaction...");
			String token = StringUtils.remove(Generators.timeBasedGenerator().generate().toString(), "-").toUpperCase();
			Transaction transaction = new Transaction();
			transaction.setTrxCurrency(currencyISOCode);
			transaction.setTrxReference(merchantAuthDTO.getOrder().getReference());
			transaction.setTrxMcc(merchantAuthDTO.getMcc());
			transaction.setTrxAmount(merchantAuthDTO.getOrder().getAmount());
			transaction.setTrxCardHolderPhoneNumber(merchantAuthDTO.getCustomer().getMobilePhone());
			transaction.setTrxCardHolderMailAddress(merchantAuthDTO.getCustomer().getEmail());
			transaction.setTrxMerchantKitId(merchantAuthDTO.getMerchantKitId());
			transaction.setTrxCardHolderIPAddress(merchantAuthDTO.getCardHolderIPAddress());
			transaction.setTrxAcquirerId(merchantAuthDTO.getAcquirerId());
			transaction.setTrxCountryCode(merchantAuthDTO.getCountryCode());
			transaction.setTrxLanguage(merchantAuthDTO.getLanguage());
			transaction.setMerchantId(merchantAuthDTO.getMerchantId());
			transaction.setCallBackURL(merchantAuthDTO.getCallBackUrl());
			transaction.setTrxEcmId(merchantAuthDTO.getPspId());
			transaction.setRememberMe(null);
			transaction.setTrxLocalDate(merchantAuthDTO.getDateTimeSIC());
			transaction.setTrxMpiId(merchantAuthDTO.getMpiId());
			transaction.setTrxIsCardHolderEligibleToRecieveMail(merchantAuthStatus.getEligibilityIndicator());
			transaction.setTrxType(merchantAuthDTO.getTransactionTypeIndicator());
			transaction.setTrxCurrencyName(merchantAuthDTO.getOrder().getCurrency());
			transaction.setTrxMerchantURL(merchantAuthDTO.getRedirectBackUrl());
			transaction.setCallBackURL(merchantAuthDTO.getCallBackUrl());
			transaction.setRedirectBackUrl(merchantAuthDTO.getRedirectBackUrl());
			transaction.setTempToken(token);

			logger.info("Saving transaction...");
			merchantAuthResp.setResult("SUCCESS");
			merchantAuthResp.setSessionId(token);
			merchantAuthResp.setSuccessIndicator(RandomStringUtils.randomNumeric(10));
			transaction.setTrxProcessStatus(merchantAuthResp.getSuccessIndicator());
			transactionRepository.save(transaction);

			logger.info("SUCCESS - SessionId : {}", token);
			return merchantAuthResp;
		}
		else {
			merchantAuthResp.setResult("ERROR");
			merchantAuthResp.setError("No merchant found with id " + merchantAuthDTO.getMerchantId());
			logger.error("ERROR - Failed to authenticate merchant {}", merchantAuthDTO.getMerchantId());
			return merchantAuthResp;
		}
	}

	public MerchantAuthStatus validateMerchant(MerchantAuthParams merchantAuthParams, String merchantId) {
		PropertiesUtils propertiesUtils = PropertiesUtils.getInstance();
		// String merchantAuthUrl =
		// propertiesUtils.getProperty("V6_MERCHANT_AUTHENTICATION_WS_LOCATION_psp_001");
		String merchantAuthUrl = propertiesUtils.getProperty("MPI_MERCHANT_INFOS_WS_LOCATION_mpi_test");

		// JSONObject requestObj = new JSONObject();
		// String ecmServerId = merchantAuthParams.getEcommerceServerID();
		// String ecmMpiId = merchantAuthParams.getMpiID();
		// String ecmMerchantId = merchantAuthParams.getMerchantID();
		// String ecmMerchantKitId = merchantAuthParams.getMerchantKitID();
		// String ecmMcc = merchantAuthParams.getMcc();
		// String ecmToken = merchantAuthParams.getAuthenticationToken();
		// String ecmCurrency = merchantAuthParams.getCurrency();
		//
		// if (ecmServerId == null || ecmMpiId == null || ecmMerchantId == null ||
		// ecmMerchantKitId == null || ecmMcc == null
		// || ecmToken == null || ecmCurrency == null) {
		//
		// logger.error("ECM_SERV_ID, ECM_MPI_ID or ECM_MERCHANT_KIT_ID is/are left
		// empty");
		// return null;
		// } else {
		// HttpHeaders headers = new HttpHeaders();
		// headers.setContentType(MediaType.APPLICATION_JSON);
		// HttpEntity<String> request = new HttpEntity<String>(requestObj.toString(),
		// headers);

		// cast 3ds server result to jsonobject and populate merchant status then return
		// it
		// String result =
		// restTemplate.postForObject(merchantAuthUrl+merchantAuthParams.getMerchantID() ,
		// request, String.class);
		// logger.info("result: " + result);
		try {
			RequestorInfo requestor = restTemplate.getForObject(merchantAuthUrl + merchantAuthParams.getMerchantID(),
					RequestorInfo.class);
			MerchantAuthStatus merchantAuthStatus = new MerchantAuthStatus();
			merchantAuthStatus.setAuthenticationIndicator("000"); // success
			merchantAuthStatus.setCurrencyCode(merchantAuthParams.getCurrency());
			merchantAuthStatus.setEcommerceServerURL(merchantAuthUrl);

			return merchantAuthStatus;
		}
		catch (HttpClientErrorException e) {
			throw new InvalidMerchantException("No merchant found with id " + merchantAuthParams.getMerchantID());
		}
	}

}