package proxyserver.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import proxyserver.protocol.ProtocolHeader;
import proxyserver.protocol.ProtocolMessage;

public class ProtocolDecoder extends LengthFieldBasedFrameDecoder{
	public ProtocolDecoder() {
		super(65535, 0, 2, 0,0);
		// TODO Auto-generated constructor stub
	}
	
	 protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
		 ByteBuf decBuf = (ByteBuf) super.decode(ctx, in);
		 if(decBuf == null)
			 return null;
		 
		 ProtocolMessage message = new ProtocolMessage();
		 message.setHeader(new ProtocolHeader());
		 short length = decBuf.readShort();
		 message.getHeader().setLength(length);
		 byte mark = decBuf.readByte();
		 message.getHeader().setMark(mark);
		 byte accLength = decBuf.readByte();
		 message.getHeader().setAccountLength(accLength);
		 ByteBuf accData = decBuf.readBytes(accLength);
		 String account = new String(accData.array(),"utf-8");
		 message.getHeader().setUserAccount(account);
		 int cmd = decBuf.readInt();
		 message.getHeader().setCmd(cmd);
		 short seq = decBuf.readShort();
		 message.getHeader().setSeq(seq);
		 byte offset = decBuf.readByte();
		 message.getHeader().setOffset(offset);
		 byte flag = decBuf.readByte();
		 message.getHeader().setFlag(flag);
		 short statusCode = decBuf.readShort();
		 message.getHeader().setStatusCode(statusCode);
		 ByteBuf body = decBuf.readBytes(length - offset);
		 message.setBody(body.array());
		 return message;
	 }
}
