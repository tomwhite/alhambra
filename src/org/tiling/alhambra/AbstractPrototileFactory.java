package org.tiling.alhambra;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.tiling.util.JarManager;

/**
 * I know how to build all known
 * Prototiles. A factory may divide Prototiles into families to 
 * form convenient groupings.
 */
public abstract class AbstractPrototileFactory {

	/**
	 * Use this method to get a concrete factory that can make prototiles.
	 * @param className the class name of the factory, e.g. "org.tiling.alhambra.tile.penrose.PenroseFactory".
	 */
	public static final AbstractPrototileFactory getFactory(String className) {

		try {
			Class clazz = JarManager.getInstance().loadClass(className);
			return (AbstractPrototileFactory) clazz.getMethod("getInstance", null).invoke(null, null);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * Subclasses must override this to return a prototile corresponding to name.
	 * @param name the name of the prototile.
	 */
	public abstract Prototile createPrototile(String name);

	/**
	 * Subclasses must override this to return an array of all the names of the
	 * prototiles it can build.
	 */
	public abstract String[] getPrototileNames();

	/**
	 * @return the family names of the prototiles that this factory can build.
	 */
	public String[] getPrototileFamilyNames() {
		return new String[] {"One Big Happy"};
	}

	/**
	 * @return the names of the prototiles in family.
	 */
	public String[] getPrototileNames(String family) {
		if (family == "One Big Happy") {
			return getPrototileNames();
		} else {
			return new String[0];
		}
	}

	/**
	 * Convenience method for obtaining all the prototiles that a
	 * factory can create.
	 * @return a List of Prototile objects
	 */
	public List createAllPrototiles() {
		List prototiles = new ArrayList();
		String[] prototileNames = getPrototileNames();
		for (int i = 0; i < prototileNames.length; i++) {
			Prototile prototile = createPrototile(prototileNames[i]);
			prototiles.add(prototile);
		}
		return Collections.unmodifiableList(prototiles);
	}

	/**
	 * Convenience method for obtaining all the prototiles in a family.
	 * @return a List of Prototile objects
	 */
	public List createAllPrototiles(String family) {
		List prototiles = new ArrayList();
		String[] prototileNames = getPrototileNames(family);
		for (int i = 0; i < prototileNames.length; i++) {
			Prototile prototile = createPrototile(prototileNames[i]);
			prototiles.add(prototile);
		}
		return Collections.unmodifiableList(prototiles);
	}

}