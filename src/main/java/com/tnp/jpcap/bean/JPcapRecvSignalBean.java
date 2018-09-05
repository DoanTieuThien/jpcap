package com.tnp.jpcap.bean;

import com.tnp.jpcap.util.LinkQueue;

import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;
import jpcap.NetworkInterfaceAddress;
import jpcap.PacketReceiver;
import jpcap.packet.ARPPacket;
import jpcap.packet.ICMPPacket;
import jpcap.packet.IPPacket;
import jpcap.packet.Packet;
import jpcap.packet.TCPPacket;
import jpcap.packet.UDPPacket;

public class JPcapRecvSignalBean {
	private JpcapCaptor mjpcap = null;
	private LinkQueue<TCPPacket> mlqTCPPacket = null;
	private LinkQueue<UDPPacket> mlqUDPPacket = null;
	private LinkQueue<ICMPPacket> mlqICMPPacket = null;
	private LinkQueue<ARPPacket> mlqARPPacket = null;
	private LinkQueue<IPPacket> mlqIPPacket = null;
	private LinkQueue<Packet> mlqOtherPacket = null;

	public JPcapRecvSignalBean() {
		this.mlqTCPPacket = new LinkQueue<>();
		this.mlqUDPPacket = new LinkQueue<>();
		this.mlqICMPPacket = new LinkQueue<>();
		this.mlqARPPacket = new LinkQueue<>();
		this.mlqIPPacket = new LinkQueue<>();
		this.mlqOtherPacket = new LinkQueue<>();
	}

	public LinkQueue<TCPPacket> getTCPPacket() {
		return mlqTCPPacket;
	}

	public LinkQueue<UDPPacket> getUDPPacket() {
		return mlqUDPPacket;
	}

	public LinkQueue<ICMPPacket> getICMPPacket() {
		return mlqICMPPacket;
	}

	public LinkQueue<ARPPacket> getARPPacket() {
		return mlqARPPacket;
	}

	public LinkQueue<IPPacket> getIPPacket() {
		return mlqIPPacket;
	}

	public LinkQueue<Packet> getOtherPacket() {
		return mlqOtherPacket;
	}

	public void start(String ipAddress) throws Exception {
		NetworkInterface netCard = getNetworkByIPAddress(ipAddress);

		if (netCard == null) {
			throw new Exception("Can not found net card by address " + ipAddress);
		}
		this.mjpcap = JpcapCaptor.openDevice(netCard, 65536, true, 10);
		process();
	}

	private NetworkInterface getNetworkByIPAddress(String ipAddress) {
		NetworkInterface net = null;
		NetworkInterface[] devices = JpcapCaptor.getDeviceList();

		if (devices == null || devices.length < 1) {
			return net;
		}
		for (NetworkInterface n : devices) {
			for (NetworkInterfaceAddress address : n.addresses) {
				if (address.address.getHostAddress().equals(ipAddress)) {
					net = n;
					return net;
				}
			}
		}
		return net;
	}

	private void process() throws Exception {
		if (mjpcap != null) {
			PacketReceiver handler = new PacketReceiver() {
				
				@Override
				public void receivePacket(Packet packet) {
					if (packet != null) {
						try {
							if (packet instanceof TCPPacket) {
								mlqTCPPacket.enqueueNotify((TCPPacket) packet);
							} else if (packet instanceof UDPPacket) {
								mlqUDPPacket.enqueueNotify((UDPPacket) packet);
							} else if (packet instanceof ICMPPacket) {
								mlqICMPPacket.enqueueNotify((ICMPPacket) packet);
							} else if (packet instanceof ARPPacket) {
								mlqARPPacket.enqueueNotify((ARPPacket) packet);
							} else if (packet instanceof IPPacket) {
								mlqIPPacket.enqueueNotify((IPPacket) packet);
							} else {
								mlqOtherPacket.enqueueNotify(packet);
							}
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
					try {
						Thread.sleep(20);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
			mjpcap.loopPacket(-1, handler);
		}
	}

	public void stop() {
		if (mjpcap != null) {
			try {
				mjpcap.breakLoop();
				mjpcap.close();
			} catch (Exception exp) {
				exp.printStackTrace();
			}
		}

		this.mlqARPPacket.clear();
		this.mlqICMPPacket.clear();
		this.mlqIPPacket.clear();
		this.mlqTCPPacket.clear();
		this.mlqUDPPacket.clear();
		this.mlqOtherPacket.clear();

		this.mlqARPPacket = null;
		this.mlqICMPPacket = null;
		this.mlqIPPacket = null;
		this.mlqTCPPacket = null;
		this.mlqUDPPacket = null;
		this.mlqOtherPacket = null;
		this.mjpcap = null;
	}
}
