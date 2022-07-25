package ma.s2m.nxp.merchantauthenticatems.controller;

import ma.s2m.nxp.merchantauthenticatems.dto.MerchantAuthDTO;
import ma.s2m.nxp.merchantauthenticatems.dto.MerchantAuthResponse;
import ma.s2m.nxp.merchantauthenticatems.service.IAuthenticationService;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class AuthenticationController {

	final private IAuthenticationService authenticationService;

	public AuthenticationController(IAuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}
	@PostAuthorize("hasAuthority('MERCHANT')")
	@PostMapping("/authenticateMerchant")
	public MerchantAuthResponse authenticate(@Valid @RequestBody MerchantAuthDTO merchantDto) {
		return authenticationService.authenticateMerchant(merchantDto);
	}

}
