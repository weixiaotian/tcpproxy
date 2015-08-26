package testclient.utils;

import testclient.protocol.ProtocolHeader;
import testclient.protocol.ProtocolMessage;

public class ProtocolHelper {
	public static ProtocolMessage createRequest(String account,int cmd,byte []body){
		ProtocolMessage req = new ProtocolMessage();
		req.setHeader(new ProtocolHeader());
		req.getHeader().setCmd(cmd);
		req.getHeader().setUserAccount(account);
		req.setBody(body);
		req.getHeader().setAccountLength((byte)account.length());
		//req.getHeader().setFlag();
		req.getHeader().setMark((byte)0x80);
		short length = (short) (body == null ? 0 + account.length() + 14 : body.length + account.length() + 14);//netty LengthFieldBasedFrameDecoder 解析包的时候不包括length字段的长度
		req.getHeader().setOffset((byte)(account.length() + 14));
		req.getHeader().setLength(length);
		return req;
	}
}
