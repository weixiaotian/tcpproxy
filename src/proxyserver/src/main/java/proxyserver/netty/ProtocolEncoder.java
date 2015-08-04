package proxyserver.netty;

import proxyserver.protocol.ProtocolMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;


public class ProtocolEncoder extends MessageToByteEncoder<ProtocolMessage>{

	@Override
	protected void encode(ChannelHandlerContext ctx, ProtocolMessage msg,
			ByteBuf out) throws Exception {
		// TODO Auto-generated method stub
		out.writeShort(msg.getHeader().getLength());
		out.writeByte(msg.getHeader().getMark());
		out.writeByte(msg.getHeader().getAccountLength());
		out.writeBytes(msg.getHeader().getUserAccount().getBytes("utf-8"));
		out.writeInt(msg.getHeader().getCmd());
		out.writeShort(msg.getHeader().getSeq());
		out.writeByte(msg.getHeader().getOffset());
		out.writeByte(msg.getHeader().getFlag());
		out.writeShort(msg.getHeader().getStatusCode());
		out.writeBytes(msg.getBody());
	}

}
