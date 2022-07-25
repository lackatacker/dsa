package ma.s2m.nxp.merchantauthenticatems.service;

import ma.s2m.nxp.merchantauthenticatems.domain.MerchantAuthParams;
import ma.s2m.nxp.merchantauthenticatems.dto.MerchantAuthDTO;
import ma.s2m.nxp.merchantauthenticatems.dto.MerchantAuthResponse;
import ma.s2m.nxp.merchantauthenticatems.dto.MerchantAuthStatus;

public interface IAuthenticationService {

	MerchantAuthResponse authenticateMerchant(MerchantAuthDTO merchantAuthDTO);

	MerchantAuthStatus validateMerchant(MerchantAuthParams merchantAuthParams, String merchantId);

}
