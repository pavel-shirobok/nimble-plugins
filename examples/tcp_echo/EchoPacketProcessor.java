package tcp_echo;

import com.ramshteks.nimble.plugins.net.PacketProcessor;
import com.ramshteks.nimble.plugins.net.IPacketProcessorFactory;
import com.ramshteks.nimble.plugins.net.TcpConnectionInfo;

/**
 * ...
 *
 * @author Pavel Shirobok (ramshteks@gmail.com)*/


public class EchoPacketProcessor extends PacketProcessor {
	public static final IPacketProcessorFactory factory = connectionInfo -> new EchoPacketProcessor();

	public EchoPacketProcessor() {
		super();
	}

	@Override
	public void processBytesFromSocket(TcpConnectionInfo connectionInfo, byte[] bytes) {
		pushSendPacket(getUTFStringBytes("Hello, world"));
	}

	@Override
	public void processBytesToSocket(TcpConnectionInfo connectionInfo, byte[] bytes) {
		pushSendPacket(bytes);
	}

	public  static byte[] getUTFStringBytes(String string){
		byte[] say_bytes = null;
		//noinspection EmptyCatchBlock
		try{
			say_bytes = string.getBytes("UTF-8");
		}catch (Exception e){}
		return say_bytes;
	}
}
