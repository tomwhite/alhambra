package org.tiling.alhambra.gui;




import java.net.URL;

import java.util.Collection; 
 
import java.util.Iterator;
import java.util.List;
import java.util.Set;






import org.tiling.util.JarManager;

import org.tiling.alhambra.AbstractPrototileFactory;import org.tiling.alhambra.Prototile;/**
 * I am a view of all the prototiles known to the application.
 * @see TileView
 */
public class PrototileMenuView extends PrototileView {












	public PrototileMenuView() {
		super(false, 32, true);
		
		Set urls = JarManager.getInstance().getJarURLs();
		for (Iterator i = urls.iterator(); i.hasNext(); ) {
			String url = ((URL) i.next()).toString();
			Set factoryNames = JarManager.getInstance().find(url, AbstractPrototileFactory.class);
		
			for (Iterator j = factoryNames.iterator(); j.hasNext(); ) {
				String factoryName = (String) j.next();
				AbstractPrototileFactory factory = AbstractPrototileFactory.getFactory(factoryName);
				String[] familyNames = factory.getPrototileFamilyNames();
				for (int k = 0; k < familyNames.length; k++) {
					add(factory.createAllPrototiles(familyNames[k]));
				}
			}
		
		}

		setToolTipText("All Prototiles - click to add to current selection");
	}

 
	 

	


	public void setPrototiles(Collection prototiles) {
		if (getSelectedPrototiles().isEmpty()) {
			setSelectedPrototiles(prototiles);
		}
	}



}