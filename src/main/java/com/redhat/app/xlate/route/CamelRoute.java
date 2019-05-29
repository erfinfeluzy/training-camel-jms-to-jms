package com.redhat.app.xlate.route;

import org.apache.camel.TypeConversionException;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class CamelRoute extends RouteBuilder {
	

	@Override
	public void configure() throws Exception {
		
		//enable auto-scan typeConverter
		getContext().setTypeConverterStatisticsEnabled(true);
		
		onException(TypeConversionException.class)
			.redeliveryDelay(0)
			.maximumRedeliveries(3)
			.to("jms:queue:q.empi.transform.dlq");
		
		from("jms:queue:q.input")
			.log("received message on: jms:queue:q.email")
			.convertBodyTo(String.class)
			.log("received message on: jms:queue:q.output")
			.to("jms:queue:q.output");
		
		
	}

}
