package proxyserver.uphandler;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import io.netty.channel.ChannelHandlerContext;
import proxyserver.protocol.ProtocolMessage;

public abstract class UpStreamHandler {
	private Executor executor = Executors.newFixedThreadPool(20, new ThreadFactory() {
		private int count = 0;
		public Thread newThread(Runnable r) {
			// TODO Auto-generated method stub
			Thread t = new Thread(r);
			t.setDaemon(false);
			t.setName("p-upwork" + "-" + count);
			count++;
			return t;
		}
	});
	
	public abstract void doHandle(ChannelHandlerContext ctx,
			ProtocolMessage msg);
	
	
	public void handle(final ChannelHandlerContext ctx,
			final ProtocolMessage msg){//线程切换，不占用IO线程
		executor.execute(new Runnable() {
			
			public void run() {
				// TODO Auto-generated method stub
				doHandle(ctx, msg);
			}
		});
	}
}
