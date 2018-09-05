package com.tnp.jpcap.thread;

import com.tnp.jpcap.util.LinkQueue;
import com.tnp.jpcap.util.ThreadBase;

import jpcap.packet.TCPPacket;

public class TCPPacketHandingThread extends ThreadBase {
	private LinkQueue<TCPPacket> mlqTCPPacket = null;

	@Override
	public void beforeThread() throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Before thread");
		this.mlqTCPPacket = this.getJpcapSignalBean().getTCPPacket();
	}

	@Override
	public void processThread() throws Exception {
		// TODO Auto-generated method stub
		while (this.miThreadState != 0) {
			try {
				TCPPacket tcpPkg = this.mlqTCPPacket.dequeueWait(1);
				
				if(tcpPkg != null){
					System.out.println("TCP Packet: " + tcpPkg.toString());
				}
			} catch (Exception exp) {
				exp.printStackTrace();
			}
		}
	}

	@Override
	public void afterThread() throws Exception {
		// TODO Auto-generated method stub
		System.out.println("After thread");
		this.mlqTCPPacket = null;
	}

}
