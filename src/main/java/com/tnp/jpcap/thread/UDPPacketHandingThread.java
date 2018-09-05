package com.tnp.jpcap.thread;

import com.tnp.jpcap.util.LinkQueue;
import com.tnp.jpcap.util.ThreadBase;

import jpcap.packet.UDPPacket;

public class UDPPacketHandingThread extends ThreadBase {
	private LinkQueue<UDPPacket> mlqUDPPacket = null;

	@Override
	public void beforeThread() throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Before thread");
		this.mlqUDPPacket = this.getJpcapSignalBean().getUDPPacket();
	}

	@Override
	public void processThread() throws Exception {
		// TODO Auto-generated method stub
		while (this.miThreadState != 0) {
			try {
				UDPPacket udpPkg = this.mlqUDPPacket.dequeueWait(1);
				
				if(udpPkg != null){
					System.out.println("UDP Packet: " + udpPkg.toString());
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
		this.mlqUDPPacket = null;
	}

}
