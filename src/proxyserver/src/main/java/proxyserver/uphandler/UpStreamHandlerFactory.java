package proxyserver.uphandler;

import proxyserver.utils.ProxyConfig;

public class UpStreamHandlerFactory {
	
	public static UpStreamHandler getHandler(int cmd){
		if(cmd == ProxyConfig.getInstance().getAuthCmd()){
			return AuthCmdHandler.INSTANCE;
		}else{
			return SampleUpHandler.INSTANCE;
		}
	}
}
