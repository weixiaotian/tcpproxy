package testclient.utils;

import java.io.FileInputStream;
import java.util.Properties;

public class ClientConfig {
	private static ClientConfig instance = new ClientConfig();
	
	public static ClientConfig getInstance() {
		return instance;
	}
	
	public static void initialize(String conf) throws Exception{
		Properties props = new Properties();
		props.load(new FileInputStream(conf));
		fillBean(props);
	}
	
	private static void fillBean(Properties props) {
		// TODO Auto-generated method stub
		if(props.containsKey("serverip")){
			instance.serverIp = String.valueOf(props.getProperty("serverip"));
		}
		
		if(props.containsKey("serverport")){
			instance.serverPort = Integer.valueOf(props.getProperty("serverport"));
		}
		
		if(props.containsKey("keepaliveintevel")){
			instance.keepAliveInterval = Integer.valueOf(props.getProperty("keepaliveintevel"));
		}		
	}
	private String serverIp;
	private int serverPort;
	private int keepAliveInterval;
	public String getServerIp() {
		return serverIp;
	}
	public int getServerPort() {
		return serverPort;
	}
	public int getKeepAliveInterval() {
		return keepAliveInterval;
	}
}
