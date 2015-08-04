package proxyserver.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import proxyserver.protocol.ProtocolMessage;
import proxyserver.session.ClientSession;
import proxyserver.session.SessionManager;

public class ProxyServerHandler extends SimpleChannelInboundHandler<ProtocolMessage>{
	private final AttributeKey<Object> channelKey = AttributeKey.valueOf("channelKey");
	@Override
	protected void messageReceived(ChannelHandlerContext ctx,
			ProtocolMessage msg) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	@Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //System.err.printf("Factorial of %,d is: %,d%n", lastMultiplier, factorial);
    }

	@Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //System.err.printf("Factorial of %,d is: %,d%n", lastMultiplier, factorial);
		Attribute<Object> key =  ctx.channel().attr(channelKey);
		String account = (String)key.get();
		ClientSession session = SessionManager.instance().getClientSession(account);
		if(session != null){
			SessionManager.instance().removeSession(account);
			//TODO 通知后端清理 在线缓存
		}
		//a.set(value);
    }
}
