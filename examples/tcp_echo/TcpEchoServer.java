package tcp_echo;

import com.ramshteks.nimble.Nimble;
import com.ramshteks.nimble.plugins.logger.StandardOutLoggerPlugin;
import com.ramshteks.nimble.plugins.net.IDGenerator;
import com.ramshteks.nimble.plugins.net.TcpReceptor;
import com.ramshteks.nimble.plugins.net.TcpServer;
import com.ramshteks.nimble.plugins.statistic.ServerStatistics;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * ...
 *
 * @author Pavel Shirobok (ramshteks@gmail.com)*/


public class TcpEchoServer {

	public static void main(String[] args){

		//common event machine
		Nimble nimble = new Nimble();

		InetAddress inetAddress;
		try {
			inetAddress = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return;
		}

		TcpServer tcpServer = new TcpServer(EchoPacketProcessor.factory, new IDGenerator(0, 100000));

		tcpServer.addReceptor(new TcpReceptor(), inetAddress, 2305);

		nimble.addPlugin(tcpServer);
		nimble.addPlugin(new StandardOutLoggerPlugin());
		nimble.addPlugin(new ServerStatistics());
		nimble.start();

	}

}
