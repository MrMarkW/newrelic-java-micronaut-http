package com.newrelic.instrumentation.micronaut.http.client;

import com.newrelic.api.agent.Transaction;
import com.newrelic.api.agent.TransportType;

import io.micronaut.http.HttpResponse;
import io.reactivex.functions.Consumer;

public class ResponseConsumer implements Consumer<HttpResponse<?>> {
	
	private Transaction txn = null;
	
	public ResponseConsumer(Transaction t) {
		txn = t;
	}

	@Override
	public void accept(HttpResponse<?> response) {
		if(txn != null && response != null) {
			MicronautHeaders headers = new MicronautHeaders(response);
			txn.acceptDistributedTraceHeaders(TransportType.HTTP, headers);
		}
	}

}
