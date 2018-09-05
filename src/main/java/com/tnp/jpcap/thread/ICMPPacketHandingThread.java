package com.tnp.jpcap.thread;

import com.tnp.jpcap.util.LinkQueue;
import com.tnp.jpcap.util.ThreadBase;

import jpcap.packet.ICMPPacket;

public class ICMPPacketHandingThread extends ThreadBase {
	private LinkQueue<ICMPPacket> mlqICMPPacket = null;

	@Override
	public void beforeThread() throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Before thread");
		this.mlqICMPPacket = this.getJpcapSignalBean().getICMPPacket();
	}

	@Override
	public void processThread() throws Exception {
		// TODO Auto-generated method stub
		while (this.miThreadState != 0) {
			try {
				ICMPPacket icmpPkg = this.mlqICMPPacket.dequeueWait(1);
				
				if(icmpPkg != null){
					System.out.println("ICMP Packet: " + icmpPkg.toString());
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
		this.mlqICMPPacket = null;
	}

}
