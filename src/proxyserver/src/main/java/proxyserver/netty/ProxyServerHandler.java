package proxyserver.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import proxyserver.protocol.ProtocolMessage;

public class ProxyServerHandler extends SimpleChannelInboundHandler<ProtocolMessage>{

	@Override
	protected void messageReceived(ChannelHandlerContext ctx,
			ProtocolMessage msg) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
