package proxyserver.utils;

import java.io.FileInputStream;
import java.util.Properties;

public class ProxyConfig {
	private static ProxyConfig instance = new ProxyConfig();
	
	private ProxyConfig(){
	}
	
	public static void initialize(String conf) throws Exception{
		Properties props = new Properties();
		props.load(new FileInputStream(conf));
		fillBean(props);
	}
	
	private static void fillBean(Properties props) {
		// TODO Auto-generated method stub
		if(props.containsKey("clientport")){
			instance.clientPort = Integer.valueOf(props.getProperty("clientport"));
		}
		
		if(props.containsKey("intenalport")){
			instance.internalPort = Integer.valueOf(props.getProperty("intenalport"));
		}
		
		if(props.containsKey("maxsession")){
			instance.maxSession = Integer.valueOf(props.getProperty("maxsession"));
		}
		
		if(props.containsKey("timeoutsec")){
			instance.timeoutSec = Integer.valueOf(props.getProperty("timeoutsec"));
		}
		
		if(props.containsKey("logpath")){
			instance.logPath = String.valueOf(props.getProperty("logpath"));
		}
		
		if(props.containsKey("authcmd")){
			instance.authCmd = Integer.valueOf(props.getProperty("authcmd"));
		}
	}

	private int clientPort;
	private int internalPort;
	private int maxSession;
	private int timeoutSec;
	private String logPath;
	private int authCmd;

	public static ProxyConfig getInstance() {
		return instance;
	}
	
	public int getClientPort() {
		return clientPort;
	}
	public int getInternalPort() {
		return internalPort;
	}
	public int getMaxSession() {
		return maxSession;
	}
	public int getTimeoutSec() {
		return timeoutSec;
	}
	public String getLogPath() {
		return logPath;
	}
	public int getAuthCmd() {
		return authCmd;
	}
}
