package org.tiling.alhambra.app;

import javax.swing.*;

/**
 * I am an applet that allows interactive construction of tilings, using the mouse.
 */
public class AlhambraApplet extends JApplet {

	public AlhambraApplet() {
		Alhambra alhambra = new Alhambra(this);
	}

}