# I generate a nice view of all 62 Decapods.

from java.util import ArrayList

from org.tiling.alhambra import PrototileSet
from org.tiling.alhambra import SimplePatch
from org.tiling.alhambra.gui import TilingEditor
from org.tiling.alhambra.gui import TileSelectionListener
from org.tiling.alhambra.gui import TileView
from org.tiling.alhambra.tile.penrose.cartwheel import Decapod
from org.tiling.gui import Viewer2D

class DecapodSelectionListener(TileSelectionListener):
  def valueChanged(self, event):
    decapod = event.getSelectedTiles()[0]
    alhambra.addJInternalFrame(TilingEditor(SimplePatch(decapod), PrototileSet(alhambra.getPrototiles()), "Decapod seed"));

tileView = TileView(32, 0)
tileView.addTileSelectionListener(DecapodSelectionListener())
decapods = Decapod.getEquivalenceClassRepresentatives()

for i in range(len(decapods) / 7 + 1):
  list = ArrayList()
  for j in range(7):
    index = 7 * i + j
    if index < len(decapods):
      list.add(decapods[index].getTile())
#  print list
  tileView.add(list)
viewer = Viewer2D("All 62 Decapods")
viewer.setCanvas2D(tileView)
viewer.setEnabled(1)
viewer.setVisible(1)
alhambra.addJInternalFrame(viewer)


