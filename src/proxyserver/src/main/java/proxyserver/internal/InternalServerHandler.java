package proxyserver.internal;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import proxyserver.protocol.ProtocolMessage;
import proxyserver.session.ClientSession;
import proxyserver.session.SessionManager;
import proxyserver.utils.ProxyConfig;

public class InternalServerHandler extends SimpleChannelInboundHandler<ProtocolMessage>{

	@Override
	protected void messageReceived(ChannelHandlerContext ctx,
			ProtocolMessage msg) throws Exception {
		// TODO Auto-generated method stub
		String account = msg.getHeader().getUserAccount();
		ClientSession session = SessionManager.instance().getClientSession(account);
		if(session == null){
			// TODO 通知后端连接已经断开
			return;
		}
		if(msg.getHeader().getCmd() == ProxyConfig.getInstance().getAuthCmd() &&
			!msg.isRequest()){
			if(msg.getHeader().getStatusCode()!=200){
				SessionManager.instance().removeSession(account);
			}
		}
		session.write(msg);
	}

}
