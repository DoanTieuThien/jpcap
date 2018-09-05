package com.tnp.jpcap.thread;

import com.tnp.jpcap.util.LinkQueue;
import com.tnp.jpcap.util.ThreadBase;

import jpcap.packet.Packet;

public class OtherPacketHandingThread extends ThreadBase {
	private LinkQueue<Packet> mlqOtherPacket = null;

	@Override
	public void beforeThread() throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Before thread");
		this.mlqOtherPacket = this.getJpcapSignalBean().getOtherPacket();
	}

	@Override
	public void processThread() throws Exception {
		// TODO Auto-generated method stub
		while (this.miThreadState != 0) {
			try {
				Packet pkg = this.mlqOtherPacket.dequeueWait(1);
				
				if(pkg != null){
					System.out.println("OTHER Packet: " + pkg.toString());
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
		this.mlqOtherPacket = null;
	}

}
