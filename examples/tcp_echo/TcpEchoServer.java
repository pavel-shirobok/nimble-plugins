package tcp_echo;

import com.ramshteks.nimble.Nimble;
import com.ramshteks.nimble.plugins.net.IDGenerator;
import com.ramshteks.nimble.plugins.logger.StandardOutLoggerPlugin;
import com.ramshteks.nimble.plugins.statistic.ServerStatistics;
import com.ramshteks.nimble.plugins.net.TcpServer;

/**
 * ...
 *
 * @author Pavel Shirobok (ramshteks@gmail.com)*/


public class TcpEchoServer {

	public static void main(String[] args){

		//common event machine
		Nimble nimble = new Nimble();

		TcpServer tcpServer = new TcpServer(EchoPacketProcessor.factory, new IDGenerator(0, 100000));

		nimble.addPlugin(tcpServer);
		nimble.addPlugin(new StandardOutLoggerPlugin());
		nimble.addPlugin(new ServerStatistics());
		nimble.start();
	}

}
