package com.tnp.jpcap.util;

import com.tnp.jpcap.bean.JPcapRecvSignalBean;

public abstract class ThreadBase {
	public int miDelayTime = 10;
	
	private JPcapRecvSignalBean jpcapSignalBean = null;
	public int miThreadState = 1;

	public abstract void beforeThread() throws Exception;

	public abstract void processThread() throws Exception;

	public abstract void afterThread() throws Exception;

	public JPcapRecvSignalBean getJpcapSignalBean() {
		return jpcapSignalBean;
	}

	public void setJpcapSignalBean(JPcapRecvSignalBean jpcapSignalBean) {
		this.jpcapSignalBean = jpcapSignalBean;
	}

	public void start() {
		this.miThreadState = 1;
		Runnable r = new Runnable() {
			@Override
			public void run() {
				runThread();
			}
		};
		Thread t = new Thread(r);

		t.start();
	}

	public void stop() {
		this.miThreadState = 0;
	}

	private void runThread() {
		while (this.miThreadState != 0) {
			try {
				beforeThread();
				processThread();
			} catch (Exception exp) {
				exp.printStackTrace();
			} finally {
				try {
					afterThread();
				} catch (Exception exp) {
					exp.printStackTrace();
				}
			}

			for (int i = 0; i < this.miDelayTime && this.miThreadState != 0; i++) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
