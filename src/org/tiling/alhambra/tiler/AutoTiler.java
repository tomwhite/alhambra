package org.tiling.alhambra.tiler;

import org.tiling.alhambra.*;

import java.util.Collection;

/**
 * I am a Tiler that can run without user intervention.
 * I can build a Patch dynamically using Tiles from a given Set of Prototiles.
 * Subclasses must define how the Patch is automatically generated.
 */
public abstract class AutoTiler extends Tiler implements Runnable {

	private boolean threadSuspended = true;



	/**
	 * Implement plane tiling functionality here. This method is
	 * called repeatedly by the run method.
	 */	
	public abstract TileJoin addTile();

	public void run() {
		while (true) {
			try {
				Thread.currentThread().sleep(50);
				if (threadSuspended) {
					synchronized(this) {
						while (threadSuspended) {
							wait();
						}
					}
				}
			} catch (InterruptedException e) {
				// do nothing
			}
			synchronized(patch) {
				addTile();
			}
//			repaint();
		}
	}

	public synchronized void setThreadSuspended(boolean threadSuspended) {
		this.threadSuspended = threadSuspended;
		if (!threadSuspended) {
			notify();
		}
	}

	public boolean isThreadSuspended() {
		return threadSuspended;
	}

	/**
	 * Construct an AutoTiler that will build tilings using prototiles.
	 */
	public AutoTiler(Patch patch, PrototileSet prototileSet) {
		super(patch, prototileSet);
	}}