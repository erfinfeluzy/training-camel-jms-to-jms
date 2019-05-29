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
		
//		from("amqp:queue:q.output")
//			.log("received message on: amqp:queue:q.output \n ${body}");
//			.convertBodyTo(String.class)
//			.log("send message to: amqp:queue:q.output")
//			.to("jms:queue:q.output");
//			.to("amqp:queue:q.output");
		
		from("timer:foo?fixedRate=true&period=1")
			.log("timer on ${header.firedTime}")
			.setBody(simple("Hello from timer at ${header.firedTime}"))
			.to("amqp:queue:q.output");
		
		
		
	}

}
