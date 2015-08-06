package proxyserver.uphandler;

import io.netty.channel.ChannelHandlerContext;
import proxyserver.protocol.ProtocolMessage;
import proxyserver.session.ClientSession;
import proxyserver.session.SessionManager;

public class SampleUpHandler extends UpStreamHandler{
	public static SampleUpHandler INSTANCE = new SampleUpHandler();
	
	private SampleUpHandler(){
		
	}
	@Override
	public void doHandle(ChannelHandlerContext ctx, ProtocolMessage msg) {
		// TODO Auto-generated method stub
		ClientSession session = SessionManager.instance().getClientSession(msg.getHeader().getUserAccount());
		if(session == null){
			ctx.channel().close();
		}
		// TODO 转给后端业务
	}

}
