package org.tiling.alhambra.ga;

import org.tiling.alhambra.Edge;
import org.tiling.alhambra.SimplePatch;
import org.tiling.alhambra.Tile;
import org.tiling.alhambra.TileJoin;
import org.tiling.alhambra.tiler.ManualTiler;

public class GAManualTiler extends ManualTiler {
	private GATiler gaTiler;
	public GAManualTiler(GAChromosome gaChromosome) {
		this(new GATiler(gaChromosome, new SimplePatch()));
	}
	private GAManualTiler(GATiler gaTiler) {
		super(gaTiler.getPatch(), gaTiler.getPrototileSet());
		this.gaTiler = gaTiler;
	}
	public TileJoin addTile(Edge edge) {
		return gaTiler.addTile(edge);
	}
	public TileJoin nextTile() {
		return null;
	}
	public TileJoin replaceTile(Tile tile) {
		return null;
	}
}
