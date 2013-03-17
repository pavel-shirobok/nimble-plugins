package com.ramshteks.nimble.plugins.net;

/**
 * ...
 *
 * @author Pavel Shirobok (ramshteks@gmail.com)
 */
public interface IPacketProcessorFactory {
	PacketProcessor createNewInstance(TcpConnectionInfo connectionInfo);
}
