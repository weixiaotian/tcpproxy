package testclient;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import testclient.nettyClient.NettyClient;
import testclient.protocol.ProtocolHeader;
import testclient.protocol.ProtocolMessage;
import testclient.utils.ClientConfig;
import testclient.utils.ProtocolHelper;
import testclient.utils.RequestFuture;
import testclient.utils.RequestFuture.EventHandler;
import testclient.utils.RequestFuture.EventResult;
import testclient.utils.TransactionManager;

public class ProxyClient {
	private NettyClient client = new NettyClient();
	private ScheduledExecutorService scheduledExecutor = Executors.newScheduledThreadPool(1);
	private String account;
	
	public ProxyClient(String account){
		this.account = account;
	}
	
	public void connect() throws Exception{
		client.connect(ClientConfig.getInstance().getServerIp(), ClientConfig.getInstance().getServerPort());
		auth();
		keepAlive();
	}
	
	private void keepAlive(){
		scheduledExecutor.scheduleWithFixedDelay(new Runnable() {
			
			public void run() {
				// TODO Auto-generated method stub
				sendRequest(null, 100002);
			}
		}, 240, 240, TimeUnit.SECONDS);
	}
	
	private void auth(){
		RequestFuture future = sendRequest(null, 100001);
		future.addHandler(new EventHandler() {
			
			public void run(Object sender, EventResult result) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	public RequestFuture sendRequest(byte[] body,int cmd){
		ProtocolMessage req = ProtocolHelper.createRequest(this.account, cmd, body);
		RequestFuture future = new RequestFuture();
		short seq =(short) TransactionManager.Instance.addTransaction(req, future);
		req.getHeader().setSeq(seq);
		client.write(req);
		return future;
	}
	
	public void disconnect(){
		scheduledExecutor.shutdown();
		
	}
}
