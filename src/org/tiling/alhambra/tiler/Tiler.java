package org.tiling.alhambra.tiler;

import java.io.Serializable;



import org.tiling.alhambra.PrototileSet;import org.tiling.alhambra.Patch;/**
 * I am a strategy for building a Patch using tiles from a PrototileSet.
 */
public abstract class Tiler implements Serializable {






	protected Patch patch;	protected PrototileSet prototileSet;	public Tiler() {
	}	public Tiler(Patch patch, PrototileSet prototileSet) {
		setPatch(patch);
		setPrototileSet(prototileSet);
	}	public Patch getPatch() {
		return patch;
	}	public PrototileSet getPrototileSet() {
		return prototileSet;
	}	public void setPatch(Patch patch) {
		this.patch = patch;
	}	public void setPrototileSet(PrototileSet prototileSet) {
		this.prototileSet = prototileSet;
	}
}