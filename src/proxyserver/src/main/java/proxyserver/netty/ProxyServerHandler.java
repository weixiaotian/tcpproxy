package proxyserver.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import proxyserver.protocol.ProtocolMessage;
import proxyserver.session.ClientSession;
import proxyserver.session.SessionManager;
import proxyserver.uphandler.UpStreamHandler;
import proxyserver.uphandler.UpStreamHandlerFactory;

public class ProxyServerHandler extends SimpleChannelInboundHandler<ProtocolMessage>{
	private static Logger log = LoggerFactory.getLogger(ProxyServerHandler.class);
	private final AttributeKey<Object> channelKey = AttributeKey.valueOf("channelKey");
	
	@Override
	protected void messageReceived(ChannelHandlerContext ctx,
			ProtocolMessage msg) throws Exception {
		// TODO Auto-generated method stub
		if(msg == null){
			return;
		}
		int cmd = msg.getHeader().getCmd();
		try{
			UpStreamHandler handler = UpStreamHandlerFactory.getHandler(cmd);
			handler.handle(ctx, msg);
		}catch(Exception ex){
			log.error("process messageReceived error cmd " + cmd, ex);
		}
	}
	
	@Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
    }

	@Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		try{
			Attribute<Object> key =  ctx.channel().attr(channelKey);
			String account = (String)key.get();
			ClientSession session = SessionManager.instance().getClientSession(account);
			if(session != null){
				SessionManager.instance().removeSession(account);
				//TODO 通知后端清理 在线缓存
			}
		}catch(Exception ex){
			log.error("process channelInactive error", ex);
		}
    }
}
