package ma.s2m.nxp.merchantauthenticatems.utils;

public class JWTutils {

	public static int TIME_OUT_REFRESH_TOKEN = 864000000; // 3600 * 24 * 1000 * 10 --- 10
															// days

	public static int TIME_OUT_ACCESS_TOKEN = 900000; // 60 * 1000 * 15 --- 15 minutes

	public static String TOKEN_PREFIX = "Bearer ";

	public static String AUTH_HEADER = "Authorization";

	public static String SECRET_KEY = "S2M-jUl1@n455ange-S2M";

}
