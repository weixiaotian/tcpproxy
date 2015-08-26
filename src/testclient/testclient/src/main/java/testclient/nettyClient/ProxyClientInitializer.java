package testclient.nettyClient;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class ProxyClientInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		// TODO Auto-generated method stub
		ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(new ProtocolDecoder());
        pipeline.addLast(new ProtocolEncoder());

        // and then business logic.
        pipeline.addLast(new ProxyClientHandler());
	}
	
}
