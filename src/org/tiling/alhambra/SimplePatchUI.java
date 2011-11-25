package org.tiling.alhambra;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.tiling.UI;

/**
 * I am a simple implementation of UI for SimplePatch.
 */
public class SimplePatchUI implements UI, Serializable {

	static final long serialVersionUID = 4844412734149794476L;

	private static Color defaultBackgroundColor = Color.white;

	/**
	 * @serial a list of the tileUIs that make up this patchUI.
	 */
	protected List tileUIs;

	/**
	 * @serial the background colour of the patch.
	 */
	private Color backgroundColor;

	private transient RenderingHints qualityHints;
	private transient Stroke stroke;

	public SimplePatchUI() {
		this(Collections.EMPTY_LIST);
	}

	public SimplePatchUI(List tileUIs) {
		this.tileUIs = new ArrayList(tileUIs);
		initialiseGraphics();
		backgroundColor = defaultBackgroundColor;
	}

	protected void initialiseGraphics() {
		qualityHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
											RenderingHints.VALUE_ANTIALIAS_ON);
		qualityHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		stroke = new BasicStroke(SimpleTileUI.TILE_EDGE_WIDTH);
	}

	private void readObject(ObjectInputStream ois) throws IOException {
		try {
			ois.defaultReadObject();
			initialiseGraphics();
		} catch (ClassNotFoundException e) {
			throw new IOException();
		}
	}

	public void add(UI tileUI) {
		tileUIs.add(tileUI);
		clearErrors();
	}
	public void remove(UI tileUI) {
		tileUIs.remove(tileUI);
		clearErrors();
	}

	public void paint(Graphics2D g2) {
		g2.setRenderingHints(qualityHints);
		g2.setStroke(stroke);

		synchronized(tileUIs) {
			for (Iterator i = tileUIs.iterator(); i.hasNext(); ) {
				((UI) i.next()).paint(g2);
			}
		}

		if (errorUI != null) {
			errorUI.paint(g2);
		}
	}

	public void setBackground(Color c) {
		backgroundColor = c;
	}

	public Color getBackground() {
		return backgroundColor;
	}

	public Rectangle2D getBounds2D() {
		Rectangle2D bounds = new Rectangle2D.Double(0.0, 0.0,
			SimpleTileUI.TILE_EDGE_WIDTH, SimpleTileUI.TILE_EDGE_WIDTH);
		synchronized(tileUIs) {
			for (Iterator i = tileUIs.iterator(); i.hasNext(); ) {
				bounds.add(((UI) i.next()).getBounds2D());
			}
		}
		return bounds;
	}

	private static final Color ERROR_BACKGROUND = new Color(255, 0, 0, 128);	/**
	 * @serial a UI that is used for drawing errors, e.g for tiles that fail to fit.
	 */
	protected UI errorUI;	public void addError(UI errorUI) {
		this.errorUI = (UI) errorUI.clone();
		this.errorUI.setBackground(ERROR_BACKGROUND);
	}	private void clearErrors() {
		this.errorUI = null;
	}	public Object clone() {
		try {
			SimplePatchUI patchUI = (SimplePatchUI) super.clone();
			
			if (tileUIs != null) {
				patchUI.tileUIs = new ArrayList();
				for (int i = 0; i < tileUIs.size(); i++) {
					patchUI.tileUIs.add(((UI) tileUIs.get(i)).clone());
				}
			}
			
			// don't clone backgroundColour since Color is immutable (and not Cloneable anyway)

			patchUI.initialiseGraphics();
			
			return patchUI;
		} catch (CloneNotSupportedException e) {
			// this shouldn't happen, since we are Cloneable
			throw new InternalError();
		}		
	}}