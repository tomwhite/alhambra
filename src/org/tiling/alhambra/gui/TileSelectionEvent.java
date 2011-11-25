package org.tiling.alhambra.gui;

import java.util.EventObject;
import java.util.List;

public class TileSelectionEvent extends EventObject {
	
	private List selectedTiles;
	
	public TileSelectionEvent(Object source, List selectedTiles) {
		super(source);
		this.selectedTiles = selectedTiles;
	}
	
	public List getSelectedTiles() {
		return selectedTiles;
	}
}