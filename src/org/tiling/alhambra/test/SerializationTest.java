package org.tiling.alhambra.test;


import java.util.*;

import javax.swing.*;

import junit.framework.*;

import org.tiling.alhambra.*;
import org.tiling.alhambra.app.*;
import org.tiling.alhambra.geom.*;
import org.tiling.alhambra.gui.*;
import org.tiling.gui.*;
import org.tiling.util.Serializer;

public class SerializationTest extends SerializationTestCase {
	public SerializationTest(String name) {
		super(name);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(suite());
	}

	public static Test suite() {
		return new TestSuite(SerializationTest.class);
	}

	public void testCanvas2D() {
		testPersist(new Canvas2D());
	}

 
 
	public void testTileView() {
		testPersist(new TileView(5));
	}

	public void testPrototileView() {
		Set prototiles = new HashSet();
		prototiles.add(org.tiling.alhambra.AbstractPrototileFactory.getFactory("org.tiling.alhambra.tile.polyomino.PolyominoFactory").createPrototile("tetromino_L"));
		PrototileView prototileView = new PrototileView(true, 5);
		prototileView.add(prototiles);
		testPersist(prototileView);
	}









	public void failedTestJDesktopPane() {
		JDesktopPane desktop = new JDesktopPane();
		Viewer2D viewer = new Viewer2D();
		desktop.add(viewer, JLayeredPane.DEFAULT_LAYER);
		desktop.moveToFront(viewer);
		testPersist(desktop);
	}	public void failedTestViewer2D() {
		testPersist(new Viewer2D());
	}	public void testPrototileMenuView() {
		testPersist(new PrototileMenuView());
	}}