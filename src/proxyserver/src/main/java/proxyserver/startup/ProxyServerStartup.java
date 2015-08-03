package proxyserver.startup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import proxyserver.netty.ProxyServer;
import proxyserver.utils.ProxyConfig;

public class ProxyServerStartup {
	private static Logger log = LoggerFactory.getLogger(ProxyServerStartup.class);
	public static void main(String[] args) {
		if(args.length <=0){
			log.error("please input config file path");
			return;
		}
		
		try{
			ProxyConfig.initialize(args[0]);
			log.info("step 1:init log file sucess");
		}catch(Exception ex){
			log.error("init log error,please check path and conf", ex);
			return;
		}
		
		try{
			ProxyServer server = new ProxyServer(ProxyConfig.getInstance().getClientPort());
			server.start();
			log.info("step 2:proxy server start ok");
		}catch(Exception ex){
			log.error("proxy server start error!", ex);
			return;
		}
	}
}
