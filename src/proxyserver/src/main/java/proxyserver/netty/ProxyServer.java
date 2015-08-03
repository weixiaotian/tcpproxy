package proxyserver.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.AdaptiveRecvByteBufAllocator;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;


public class ProxyServer {
	private int port;
	
	/*默认线程数为系统CPU数量*2*/
	EventLoopGroup bossGroup = new NioEventLoopGroup();
    EventLoopGroup workerGroup = new NioEventLoopGroup();
	public ProxyServer(int port){
		this.port = port;
	}
	
	public void start() throws Exception{
		
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
             .channel(NioServerSocketChannel.class)
             .option(ChannelOption.TCP_NODELAY, true)
             .option(ChannelOption.SO_KEEPALIVE, false)
             .option(ChannelOption.RCVBUF_ALLOCATOR, AdaptiveRecvByteBufAllocator.DEFAULT)
             .handler(new ProxyServerInitializer());
            b.bind(port).sync().channel().closeFuture().sync();
        }catch(Exception ex){
        	throw ex;
        }finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
	}
	
	public void stop(){
		 workerGroup.shutdownGracefully();
         bossGroup.shutdownGracefully();
	}
}

