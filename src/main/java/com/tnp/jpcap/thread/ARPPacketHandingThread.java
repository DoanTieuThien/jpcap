package com.tnp.jpcap.thread;

import com.tnp.jpcap.util.LinkQueue;
import com.tnp.jpcap.util.ThreadBase;

import jpcap.packet.ARPPacket;

public class ARPPacketHandingThread extends ThreadBase {
	private LinkQueue<ARPPacket> mlqARPPacket = null;

	@Override
	public void beforeThread() throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Before thread");
		this.mlqARPPacket = this.getJpcapSignalBean().getARPPacket();
	}

	@Override
	public void processThread() throws Exception {
		// TODO Auto-generated method stub
		while (this.miThreadState != 0) {
			try {
				ARPPacket arpPkg = this.mlqARPPacket.dequeueWait(1);
				
				if(arpPkg != null){
					System.out.println("ARP Packet: " + arpPkg.toString());
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
		this.mlqARPPacket = null;
	}

}
