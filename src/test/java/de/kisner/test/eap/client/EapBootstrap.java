package de.kisner.test.eap.client;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EapBootstrap
{
	final static Logger logger = LoggerFactory.getLogger(EapBootstrap.class);



	public static void init()
	{
		
	}

	@SuppressWarnings("unchecked")
	public static <F> F lookup(Class<F> facade) throws NamingException
	{
		Context context = buildContext();
		
		StringBuilder sb = new StringBuilder();
		sb.append("ejb:");
		sb.append("/eap");
		sb.append("/").append(facade.getSimpleName()).append("Bean");
		sb.append("!").append(facade.getName());	
		logger.info("Looking up: "+sb.toString());
		
		return (F)context.lookup(sb.toString());
	}
	
	private static Context buildContext() throws NamingException
	{
		Properties properties = new Properties();
		properties.put(Context.INITIAL_CONTEXT_FACTORY,  "org.wildfly.naming.client.WildFlyInitialContextFactory");
		properties.put(Context.PROVIDER_URL, String.format("%s://%s:%d", "remote+http", "localhost", 8080));
		properties.put("jboss.naming.client.ejb.context", true);
		
		return new InitialContext(properties);
	}
}