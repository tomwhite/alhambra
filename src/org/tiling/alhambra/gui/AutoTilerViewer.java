package org.tiling.alhambra.gui;

import org.tiling.alhambra.*;
import org.tiling.alhambra.geom.*;
import org.tiling.alhambra.tiler.*;
import org.tiling.gui.Canvas2D;
import org.tiling.gui.Viewer2D;

import java.awt.BorderLayout;
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

import javax.swing.*;

/**
 * I am a specialization of Viewer2D that allows automatically-generated
 * tilings to be viewed.
 * @see org.tiling.alhambra.tiler.AutoTiler
 */
public class AutoTilerViewer extends Viewer2D {

	private AutoTiler autoTiler;

	private JButton start; 
	private JButton stop; 
	private JButton next; 

	public AutoTilerViewer(AutoTiler autoTiler) {
		this(autoTiler, "Auto Tiler Viewer");
	}

	public AutoTilerViewer(AutoTiler autoTiler, String title) {

		super(title);

		this.autoTiler = autoTiler;
		setCanvas2D(new Canvas2D(autoTiler.getPatch().getUI()));

		JPanel panel = new JPanel();
		start = new JButton("Start");
		stop = new JButton("Stop"); 
		next = new JButton("Next"); 
		start.addActionListener(new StartTilerActionListener());
		stop.addActionListener(new StopTilerActionListener()); 
		next.addActionListener(new NextTilerActionListener());
		updateButtons();
		panel.add(start);
		panel.add(stop);
		panel.add(next);
		getContentPane().add(panel, BorderLayout.SOUTH);

		setVisible(true);

		initialiseThread();
	}

	protected void initialiseThread() {
		Thread tilerThread = new Thread(autoTiler);
		tilerThread.setPriority(Thread.MIN_PRIORITY);
		tilerThread.start();
	}

	private void readObject(ObjectInputStream ois) throws IOException {
		try {
			ois.defaultReadObject();
			initialiseThread();
		} catch (ClassNotFoundException e) {
			throw new IOException();
		}
	}

	private void updateButtons() {
		if (autoTiler.isThreadSuspended()) {
			start.setEnabled(true); 
			stop.setEnabled(false); 
			next.setEnabled(true); 
		} else {
			start.setEnabled(false); 
			stop.setEnabled(true); 
			next.setEnabled(false); 
		}
	}
	
	public class StartTilerActionListener extends AbstractAction {
		public StartTilerActionListener() {
			super("Start tiling");
		}
		public synchronized void actionPerformed(ActionEvent e) {
			autoTiler.setThreadSuspended(false);
			updateButtons();
		}
	}
	
	public class StopTilerActionListener extends AbstractAction {
		public StopTilerActionListener() {
			super("Stop tiling");
		}
		public synchronized void actionPerformed(ActionEvent e) {
			autoTiler.setThreadSuspended(true);
			updateButtons();
			repaint();
		}
	}	

	public class NextTilerActionListener extends AbstractAction { 
		public NextTilerActionListener() {
			super("Add the next tile");
		}
		public void actionPerformed(ActionEvent e) { 
			autoTiler.addTile();
			repaint();
		} 
	}	 
 

}