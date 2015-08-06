package proxyserver.uphandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;
import proxyserver.protocol.ProtocolMessage;
import proxyserver.session.ClientSession;
import proxyserver.session.SessionManager;

public class AuthCmdHandler extends UpStreamHandler {
	private final AttributeKey<Object> channelKey = AttributeKey.valueOf("channelKey");
	
	public static AuthCmdHandler INSTANCE = new AuthCmdHandler();
	
	private AuthCmdHandler(){
		
	}
	@Override
	public void doHandle(ChannelHandlerContext ctx, ProtocolMessage msg) {
		// TODO Auto-generated method stub
		String userAccound = msg.getHeader().getUserAccount();
		ClientSession session = new ClientSession(ctx.channel());
		ctx.channel().attr(channelKey).set(msg.getHeader().getUserAccount());
		SessionManager.instance().setClientSession(userAccound, session);
	}
	
}
