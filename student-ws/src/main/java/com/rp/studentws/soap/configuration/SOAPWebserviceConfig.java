package com.rp.studentws.soap.configuration;

import java.util.Collections;
import java.util.List;

import javax.security.auth.callback.CallbackHandler;
import javax.servlet.Servlet;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.soap.security.xwss.XwsSecurityInterceptor;
import org.springframework.ws.soap.security.xwss.callback.SimplePasswordValidationCallbackHandler;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

/**
 * A class for configuring the Spring message dispatcher servlet to receive the request
 * @author rajendra
 *
 */
@Configuration
// indicates spring configuration class etc.
@EnableWs
//@EnableWs enables SOAP Web Service features in this Spring Boot application.


// WsConfigurerAdapter is mechanism to add interceptor if we want to add.
// In case of Xws security we need to add our intercepter to make it secure.

public class SOAPWebserviceConfig extends WsConfigurerAdapter {
	
	public static final String WEBSERVICE_NAMESPACE = "http://rptech.com/course";
	//MessageDispatcherServlet :  Servlet for simplified dispatching of Web service messages.
	// we need to mapped to our URL and provide application context
	
	
	@Bean
	ServletRegistrationBean registerMessageDispatcherServlet(ApplicationContext applicationContext)
	{
		MessageDispatcherServlet messageDispatcherServlet = new MessageDispatcherServlet();
		
		messageDispatcherServlet.setApplicationContext(applicationContext);
		messageDispatcherServlet.setTransformWsdlLocations(true); // This is needed when we expose WSDL of this web service.
		
		
		ServletRegistrationBean  servletRegistrationBean = new ServletRegistrationBean(messageDispatcherServlet, "/webservices/*");
		
		return servletRegistrationBean;
		
	}
	
	@Bean(name="courses")
	public DefaultWsdl11Definition getWSDLDefination( XsdSchema xsdSchema)
	{
		
		DefaultWsdl11Definition defaultWsdl11Definition = new DefaultWsdl11Definition();
		
		defaultWsdl11Definition.setPortTypeName("CoursePortType");
		
		defaultWsdl11Definition.setSchema(xsdSchema);
		
		defaultWsdl11Definition.setLocationUri("/webservices");
		
		defaultWsdl11Definition.setTargetNamespace(WEBSERVICE_NAMESPACE);
		
		
		return defaultWsdl11Definition;
		
		
	}
	
	
	
	@Bean
	public XsdSchema getXSDSchema()
	{
		XsdSchema xsdSchema = new SimpleXsdSchema(new ClassPathResource("Course-details.xsd"));
		return xsdSchema;
	}
	
	
	@Bean
	public XwsSecurityInterceptor getSecurityIntercepter()
	{
		XwsSecurityInterceptor securityInterceptor =  new XwsSecurityInterceptor();
		securityInterceptor.setCallbackHandler(getSecurityCallbackHandler());
		securityInterceptor.setPolicyConfiguration(new ClassPathResource("SecurityPolicy.xml"));
		return securityInterceptor;
	}
	
	@Bean
	public SimplePasswordValidationCallbackHandler getSecurityCallbackHandler() {
		SimplePasswordValidationCallbackHandler passwordValidationCallbackHandler = new SimplePasswordValidationCallbackHandler();
		passwordValidationCallbackHandler.setUsersMap(Collections.singletonMap("USER1", "PASSWORD"));
		return passwordValidationCallbackHandler;
	}

	/**
	
	 * <p>This implementation is overrided from  WsConfigurerAdapter to add our security interceptor.
	 */
	@Override
	public void addInterceptors(List<EndpointInterceptor> interceptors) {
		System.out.println(" Inside addInterceptors ");
		interceptors.add(getSecurityIntercepter());
	}

}
