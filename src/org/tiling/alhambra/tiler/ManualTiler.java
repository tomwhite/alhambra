package org.tiling.alhambra.tiler;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import org.tiling.alhambra.Edge;
import org.tiling.alhambra.Patch;
import org.tiling.alhambra.PrototileSet;
import org.tiling.alhambra.Tile;
import org.tiling.alhambra.TileJoin;

/**
 * I am a Tiler that runs under a user's direction. The
 * user typically chooses where to add a new tile and which
 * of the choice of tiles that fit to add.
 */
public abstract class ManualTiler extends Tiler {
	protected List currentTiles = new ArrayList();
	protected int currentTilesIndex = -1;
	private PropertyChangeSupport propertyChangeSupport;
	private String message;
	public ManualTiler(Patch patch, PrototileSet prototileSet) {
		super(patch, prototileSet);
		propertyChangeSupport = new PropertyChangeSupport(this);
	}
	
	public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {
		propertyChangeSupport.addPropertyChangeListener(propertyChangeListener);
	}
	/**
	 * I add a tile to the given edge. The tile
	 * added is the first tile from the choice of tiles.
	 */
	public abstract TileJoin addTile(Edge edge);
	public List getCurrentTiles() {
		return currentTiles;
	}
	public int getCurrentTilesIndex() {
		return currentTilesIndex;
	}
	/**
	 * I replace the previously added tile with
	 * the next tile from the choice of tiles.
	 */
	 public abstract TileJoin nextTile();
	public void removePropertyChangeListener(PropertyChangeListener propertyChangeListener) {
		propertyChangeSupport.removePropertyChangeListener(propertyChangeListener);
	}
	/**
	 * I replace the previously added tile with
	 * the given tile.
	 */
	 public abstract TileJoin replaceTile(Tile tile);
	protected void setMessage(String message) {
		String oldMessage = this.message;
		this.message = message;
		propertyChangeSupport.firePropertyChange("message", oldMessage, message);
	}
}
