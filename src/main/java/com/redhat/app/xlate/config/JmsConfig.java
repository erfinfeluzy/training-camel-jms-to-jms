package com.redhat.app.xlate.config;

import org.apache.camel.component.amqp.AMQPComponent;
import org.apache.camel.component.amqp.AMQPConnectionDetails;
import org.apache.camel.component.jms.JmsConfiguration;
import org.apache.qpid.jms.JmsConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.connection.CachingConnectionFactory;

@Configuration
//@EnableJms
public class JmsConfig {

	
	/** AMQP config **/
//	@Bean
//	AMQPConnectionDetails amqpConnection() {
//	  return new AMQPConnectionDetails("amqp://10.7.48.66:32164"); 
//	}
//	 
//	@Bean
//	AMQPConnectionDetails securedAmqpConnection() {
//	  return new AMQPConnectionDetails("amqp://10.7.48.66:32164", "amquser", "amqpass"); 
//	}
	
	@Bean("jmsConnectionFactory")
	JmsConnectionFactory jmsConnectionFactory() {
		 return new JmsConnectionFactory("amquser", "amqpass", "amqp://broker-amqp-amq:5672");
	}
	
	@Bean("jmsCachingConnectionFactory")
	@Primary
	CachingConnectionFactory jmsCachingConnectionFactory() {
		return new CachingConnectionFactory(jmsConnectionFactory());
	}
	
	@Bean
	JmsConfiguration jmsConfiguration() {
		
		JmsConfiguration result = new JmsConfiguration(jmsCachingConnectionFactory());
		result.setCacheLevelName("CACHE_CONSUMER");
		return result;
				
	}
	
	@Bean
	AMQPComponent amqp() {
		return new AMQPComponent(jmsConfiguration());
	}

	


}
