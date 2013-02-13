package pirate.mostycity.utils;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.MissingResourceException;

import org.apache.wicket.Component;
import org.apache.wicket.model.StringResourceModel;

public class ResourceHelper implements Serializable{

	private static final long serialVersionUID = 1L;

	public static String getString(String key, String defaultValue, Component component) {

		try {
			return new StringResourceModel(key, component, null).getString();
		} catch (MissingResourceException ex) {
			if (defaultValue != null) {
				return defaultValue;
			} else {
				return "[" + key + "]";
			}
		}
		
	}
	
	public static String getString(String key, Component component) {

		return getString(key, null, component);
	}
	
	public static String getString(String key, String defaultValue, Component component,
			Object... args) {

		return MessageFormat.format(getString(key, defaultValue, component), args);
	}
	
	public static String getString(String key, Component component, Object... args) {

		return MessageFormat.format(getString(key, component), args);
	}
}
