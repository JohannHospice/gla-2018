package gla2018.grp4;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

public class ResourceApplication extends ResourceConfig {
	public ResourceApplication() {
		registerClasses(MyResource.class);
		registerClasses(JacksonFeature.class);
	}
}
