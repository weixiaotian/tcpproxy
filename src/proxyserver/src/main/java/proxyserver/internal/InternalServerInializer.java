package proxyserver.internal;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import proxyserver.netty.ProtocolDecoder;
import proxyserver.netty.ProtocolEncoder;

public class InternalServerInializer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		// TODO Auto-generated method stub
		ChannelPipeline pipeline = ch.pipeline();
		 // Add the number codec first,
       pipeline.addLast(new ProtocolDecoder());
       pipeline.addLast(new ProtocolEncoder());

       // and then business logic.
       // Please note we create a handler for every new channel
       // because it has stateful properties.
       pipeline.addLast(new InternalServerHandler());
	}

}
