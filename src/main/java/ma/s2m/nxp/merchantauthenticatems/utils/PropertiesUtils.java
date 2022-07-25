package ma.s2m.nxp.merchantauthenticatems.utils;

import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtils {

	private static final PropertiesUtils INSTANCE = new PropertiesUtils();

	private static final Properties properties;

	private PropertiesUtils() {
	}

	static {
		properties = new Properties();
		try {
			// read from parameters.properties
			ClassLoader classLoader = PropertiesUtils.class.getClassLoader();
			InputStream applicationPropertiesStream = classLoader.getResourceAsStream("parameters.properties");
			properties.load(applicationPropertiesStream);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static PropertiesUtils getInstance() {
		return INSTANCE;
	}

	public String getProperty(String name) {
		return properties.getProperty(name);
	}

}
