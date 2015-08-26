package testclient.utils;

import testclient.protocol.ProtocolMessage;

public class Transaction {
	private ProtocolMessage request;
	private RequestFuture future;
	private Stopwatch sw;
	
	public Transaction(ProtocolMessage request,RequestFuture future){
		this.request = request;
		this.future = future;
		sw = new Stopwatch();
	}
	
	public ProtocolMessage getRequest() {
		return request;
	}
	
	public RequestFuture getFuture() {
		return future;
	}

	public Stopwatch getSw() {
		return sw;
	}
}
