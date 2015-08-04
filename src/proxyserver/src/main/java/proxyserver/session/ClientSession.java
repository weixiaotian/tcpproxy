package proxyserver.session;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import proxyserver.utils.ProxyConfig;
import proxyserver.utils.Stopwatch;

/**
 * 保存一个连接会话
 * @author tianxiaowei
 *
 */
public class ClientSession {
	private static Logger log = LoggerFactory.getLogger(ClientSession.class);
	
	private Channel channel;
	private Stopwatch keepalive;
	
	public ClientSession(Channel channel){
		this.channel = channel;
		keepalive = new Stopwatch();
	}
	
	public void write(Object msg){
		ChannelFuture future = channel.writeAndFlush(msg);
		if(future.isSuccess()){
			log.info("send msg ok");
		}else{
			log.error("send msg error");
		}
	}
	
	public void keepalive(){
		keepalive.update();
	}
	
	public boolean checkTimeout(){
		return keepalive.getSeconds() > ProxyConfig.getInstance().getTimeoutSec();
	}
	
	public void close(){
		ChannelFuture future = channel.close();
		if(future.isSuccess()){
			log.info("close channel ok");
		}else{
			log.error("close channel error");
		}
	}
}
