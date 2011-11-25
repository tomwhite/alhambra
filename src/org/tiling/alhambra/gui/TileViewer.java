package org.tiling.alhambra.gui;

import org.tiling.gui.Canvas2D;
import org.tiling.gui.Viewer2D;

import javax.swing.*;

/**
 * A specialization of Viewer2D that allows TileViews
 * to be viewed.
 */
public class TileViewer extends Viewer2D {

	public TileViewer(TileView tileView) {
		this(tileView, "Tiler View");
	}

	public TileViewer(TileView tileView, String title) {

		super(title);
		setCanvas2D(tileView);
		setVisible(true);
	}

}