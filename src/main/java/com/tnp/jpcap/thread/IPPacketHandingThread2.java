package com.tnp.jpcap.thread;

import com.tnp.jpcap.util.LinkQueue;
import com.tnp.jpcap.util.ThreadBase;

import jpcap.packet.IPPacket;
import jpcap.packet.TCPPacket;

public class IPPacketHandingThread2 extends ThreadBase {
	private LinkQueue<IPPacket> mlqIPPacket = null;

	@Override
	public void beforeThread() throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Before thread");
		this.mlqIPPacket = this.getJpcapSignalBean().getIPPacket();
	}

	@Override
	public void processThread() throws Exception {
		// TODO Auto-generated method stub
		while (this.miThreadState != 0) {
			try {
				IPPacket ipPkg = this.mlqIPPacket.dequeueWait(1);
				
				if(ipPkg != null){
					System.out.println("IP Packet: " + ipPkg.toString());
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
		this.mlqIPPacket = null;
	}

}
