package org.tiling.alhambra;

import java.io.Serializable;
import java.util.*;

/**
 * I am a set of {@link Prototile}s used for building tilings.
 * @see Prototile
 */
public class PrototileSet implements Serializable {
	private List prototiles; // keep it ordered even though it is a set
	private List allTiles;
	public PrototileSet() {
		prototiles = new ArrayList();
	}
	public PrototileSet(Collection prototiles) {
		this();
		addAll(prototiles);
	}
	public PrototileSet(Prototile prototile) {
		this();
		add(prototile);
	}
	public boolean add(Prototile prototile) {
		if (prototiles.contains(prototile)) {
			return false;
		} else {
			boolean modified = prototiles.add(prototile);
			if (modified) {
				allTiles = null;
			}
			return modified;
		}
	}
	public boolean addAll(Collection prototiles) {
		boolean modified = false;
		for (Iterator i = prototiles.iterator(); i.hasNext(); ) {
		    if (add((Prototile) i.next())) {
				modified = true;
		    }
		}
		return modified;
	}
	public List getAllTiles() {
		if (allTiles == null) {
			allTiles = new ArrayList();
			for (Iterator i = iterator(); i.hasNext(); ) {
				allTiles.addAll(((Prototile) i.next()).getTransformedTiles());
			}
		}
		return allTiles;
	}
	public List getPrototiles() {
		return prototiles;
	}
	public Iterator iterator() {
		return prototiles.iterator();
	}
	public boolean remove(Prototile prototile) {
		boolean modified = prototiles.remove(prototile);
		if (modified) {
			allTiles = null;
		}
		return modified;
	}
}
