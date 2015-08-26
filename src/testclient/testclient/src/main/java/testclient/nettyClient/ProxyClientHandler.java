package testclient.nettyClient;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import testclient.protocol.ProtocolMessage;

public class ProxyClientHandler extends SimpleChannelInboundHandler<ProtocolMessage> {
	
	private ChannelHandlerContext ctx = null;
	
	@Override
	protected void messageReceived(ChannelHandlerContext ctx,
			ProtocolMessage msg) throws Exception {
		// TODO Auto-generated method stub
		if(msg.getHeader().getMark() == 0x80){
			
		}else if(msg.getHeader().getMark() == 0x81){
			
		}else{
			
		}
	}
	
	@Override
    public void channelActive(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }
	
	
	public void write(ProtocolMessage msg){
		if(ctx == null){
			throw new RuntimeException("please connect before write msg");
		}else{
			ctx.write(msg);
			ctx.flush();
		}
	}

}
