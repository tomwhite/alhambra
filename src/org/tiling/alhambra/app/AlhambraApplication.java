package org.tiling.alhambra.app;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File; 
import java.io.IOException; 
import java.io.Serializable; 
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import org.tiling.alhambra.AbstractPrototileFactory;

import org.tiling.util.Archiver; 
import org.tiling.util.JarManager;

import org.tiling.alhambra.PrototileSet;import org.tiling.script.JythonInterpreter;/**
 * I am an application that allows interactive construction of tilings, using the mouse.
 */
public class AlhambraApplication extends JFrame {

	private static boolean persist;
	private static final String STORE_NAME = "alhambra";

	public AlhambraApplication(File tiles) {
		super("Alhambra");
		try {
			JarManager.getInstance().addURL(tiles.toURL());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		Alhambra alhambra = new Alhambra(this);
		setDefaultPrototiles(alhambra);
		addWindowListener(new WindowCloser(alhambra));
	}

	private void setDefaultPrototiles(Alhambra alhambra) {
		PrototileSet prototiles = new PrototileSet();
		AbstractPrototileFactory factory =
			AbstractPrototileFactory.getFactory("org.tiling.alhambra.tile.penrose.PenroseFactory");
		if (factory != null) {
			prototiles.add(factory.createPrototile("Kite"));
			prototiles.add(factory.createPrototile("Dart"));
			alhambra.setCurrentPrototileSet(prototiles);
		}
	}

	private static void startup() {
		runScript("startup");
	}
 
	private static void shutdown() {
		runScript("shutdown");
	}

	private static void runScript(String filename) {
		File startupScript = new File(new File(System.getProperty("user.home")), ".alhambra/" + filename);
		if (startupScript.exists()) {
			try {
				new JythonInterpreter().execute(startupScript);
			} catch (Exception e) {
				System.err.println("Exception running startup script; " + e);
			}
		}
	}
 
	public static void main(String[] args) {

		int shift = 0;
		if (args.length > shift && args[shift].equals("--persist")) { 
			persist = true; 
			shift++;
		} 
 
		File tiles = null;
		if (args.length > shift) { 
			tiles = new File(args[shift]); 
			shift++;
		} else {
			tiles = new File("tiles.jar");
		}
 
		if (persist) {
			try {
				Object object = (Object) Archiver.getInstance().retrieve(STORE_NAME);
				if (object != null) {
					Alhambra alhambra = (Alhambra) object;
					if (alhambra != null) {
						alhambra.setVisible(true);
						return;
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		AlhambraApplication app = new AlhambraApplication(tiles);

		startup();
	}

	protected class WindowCloser extends WindowAdapter implements Serializable {
		private Alhambra alhambra;

		public WindowCloser(Alhambra alhambra) {
			this.alhambra = alhambra;
		}

		public void windowClosing(WindowEvent e) {
			shutdown();
			if (persist) {
				try {
					Archiver.getInstance().store(STORE_NAME, alhambra);
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
			dispose();
			try {
				System.exit(0);
			} catch (SecurityException ex) {
			}
		}
	}

}