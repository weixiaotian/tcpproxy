package proxyserver.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.AdaptiveRecvByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ProxyServer {
	private static Logger log = LoggerFactory.getLogger(ProxyServer.class); 
	
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
             .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
             .childHandler(new ProxyServerInitializer());
              ChannelFuture cf = b.bind(port).sync();
              cf.addListener(new ChannelFutureListener() {
				
				public void operationComplete(ChannelFuture future) throws Exception {
					// TODO Auto-generated method stub
					if(!future.isSuccess()){
						log.error("bind port error " + future.cause().getMessage());
					}
				}
			});
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

