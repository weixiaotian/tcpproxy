package testclient.nettyClient;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import testclient.protocol.ProtocolMessage;

public class NettyClient {
	private static Logger log = LoggerFactory.getLogger(NettyClient.class);
	private ProxyClientHandler handler;
	EventLoopGroup group = null;
	public void connect(String host,int port) throws Exception{
		group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
             .channel(NioSocketChannel.class)
             .handler(new ProxyClientInitializer());

            // Make a new connection.
            ChannelFuture f = b.connect(host, port).sync();
            
            handler = (ProxyClientHandler) f.channel().pipeline().last();
        }catch(Exception ex){
        	log.error("connect error", ex);
        	throw ex;
        }
	}
	
	public void write(ProtocolMessage msg){
		if(handler == null){
			throw new RuntimeException("please connect before write msg");
		}else{
			handler.write(msg);
		}
	}
	
	public void disconnect(){
		group.shutdownGracefully();
	}
}
