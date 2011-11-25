# I demonstrate how to tile the plane randomly. I do it very poorly!

from org.tiling.alhambra import PrototileSet
from org.tiling.alhambra import SimplePatch
from org.tiling.alhambra.gui import AutoTilerViewer
from org.tiling.alhambra.tiler import AutoTiler
from org.tiling.alhambra.tiler import FittingTiler
from java.util import Random

class MindlessAutoTiler(AutoTiler):
  """An edge is chosen at random then from all the tiles that 
    fit that edge (if there are any) one is chosen at random and added 
    to the tiling."""
  def __init__(self, prototileSet):
    AutoTiler.__init__(self, SimplePatch(), prototileSet)
    self.random = Random()
    self.fittingTiler = FittingTiler(self.patch, prototileSet)
    allTiles = prototileSet.getAllTiles()
    self.patch.add(allTiles.get(self.random.nextInt(allTiles.size())))

  def addRandomTile(self, edge):
    fittingTiles = self.fittingTiler.getFittingTiles(edge); 
    if fittingTiles.isEmpty(): 
      return None
    return self.patch.add(fittingTiles.get(self.random.nextInt(fittingTiles.size())))

  def addTile(self):
    edges = self.patch.getSides()		 
    return self.addRandomTile(edges.get(self.random.nextInt(edges.size())))

class CircularAutoTiler(MindlessAutoTiler):
  """A random tile is added to the first edge of the patch. Then a 
     random tile is selected that fits at the edge after the last 
     edge of the tile just added (in a ccw direction). This 
     process is repeated."""
  def __init__(self, prototileSet):
    MindlessAutoTiler.__init__(self, prototileSet)
    self.nextEdgeIndex = 0

  def addTile(self):
    edges = self.patch.getSides()	 
    join = self.addRandomTile(edges.get(self.nextEdgeIndex))
    if join:
      self.nextEdgeIndex = self.nextEdgeIndex + join.getNewEdges().size()
    else:
      self.nextEdgeIndex = self.nextEdgeIndex + 1
    self.nextEdgeIndex = self.nextEdgeIndex % edges.size()
    return join

autoTiler = CircularAutoTiler(alhambra.getCurrentPrototileSet())
alhambra.addJInternalFrame(AutoTilerViewer(autoTiler, "Random Tiler"))


