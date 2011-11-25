package org.tiling.alhambra.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

import javax.swing.JColorChooser;
import javax.swing.JDialog;

/**
 * I am a facade to the swing JColorChooser class.
 */
public class ColourChooser implements Serializable {

	private JColorChooser jColorChooser;
	private Color lastColour;

	private static ColourChooser instance = new ColourChooser();

	private ColourChooser() {
		jColorChooser = new JColorChooser();
	}

	public static ColourChooser getInstance() {
		return instance;
	}

	public Color chooseColour(Component parent, Color initialColour) {
		jColorChooser.setColor(initialColour);
		ColorTracker ok = new ColorTracker(jColorChooser);
		JDialog dialog = JColorChooser.createDialog(parent, "Colour Chooser", true, jColorChooser, ok, null);
		dialog.show();
		return ok.getColor();
	}

	class ColorTracker implements ActionListener, Serializable {
		JColorChooser chooser;
		Color color;

		public ColorTracker(JColorChooser c) {
			chooser = c;
		}

		public void actionPerformed(ActionEvent e) {
			color = chooser.getColor();
		}

		public Color getColor() {
			return color;
		}
	}

}