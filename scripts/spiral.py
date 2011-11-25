# I generate spiral tilings using horn tiles.

import math

import horn

from java.awt import Color

from org.tiling.alhambra import Prototile
from org.tiling.alhambra import PrototileSet
from org.tiling.alhambra import SimpleTile
from org.tiling.alhambra import SymbolicTransform
from org.tiling.alhambra import SymmetryGroup
from org.tiling.alhambra import SimplePatch
from org.tiling.alhambra.gui import AutoTilerViewer
from org.tiling.alhambra.tiler import AutoTiler
from org.tiling.alhambra.tiler import DebuggingTiler
from org.tiling.alhambra.tiler import FittingTiler

class SpiralAutoTiler(AutoTiler):
  """Spiral tilings."""
  def __init__(self, prototile, cCurve):
    self.prototile = prototile
    AutoTiler.__init__(self, SimplePatch(), PrototileSet(prototile))
    self.n = self.prototile.getSymmetryGroup().getRotationalSymmetry() / 2
    self.fittingTiler = FittingTiler(self.patch, self.prototileSet)

    if cCurve:
      self.a = SymbolicTransform(self.prototile.getSymmetryGroup())
      self.b = SymbolicTransform(self.prototile.getSymmetryGroup()).reflect().rotate(-2)
      self.c = SymbolicTransform(self.prototile.getSymmetryGroup()).reflect().rotate(-1)
      self.d = SymbolicTransform(self.prototile.getSymmetryGroup()).rotate(-1)
    else:
      self.a = SymbolicTransform(self.prototile.getSymmetryGroup())
      self.b = SymbolicTransform(self.prototile.getSymmetryGroup()).rotate(self.n - 1)
      self.c = SymbolicTransform(self.prototile.getSymmetryGroup()).rotate(self.n)
      self.d = SymbolicTransform(self.prototile.getSymmetryGroup()).rotate(-1)

    self.step1 = 1
    self.black1 = 0

    self.step2 = 1
    self.black2 = 1

    tile = self.prototile.getTransformedTile(self.a)
    self.black1 = self.setTileColour(tile, self.black1)
    self.a.rotate(-1)
    self.patch.add(tile)
    self.currentSide1 = self.patch.getSides().get(self.patch.getSideIndex() + 2)

    tile = self.prototile.getTransformedTile(self.c)
    tile = self.fittingTiler.getFittingTile(0, tile, 0)
    self.black2 = self.setTileColour(tile, self.black2)
    self.c.rotate(-1)
    self.patch.add(tile)
    self.currentSide2 = self.patch.getSides().get(self.patch.getSideIndex() + 1)

  def addTile(self):
    for i in range (self.step1 / self.n):
      self.currentSide1 = self.addBlockPair(self.a, self.b, self.currentSide1, self.black1)

    tile = self.prototile.getTransformedTile(self.a)
    tile = self.fittingTiler.getFittingTile(self.currentSide1, tile, 0)
    self.black1 = self.setTileColour(tile, self.black1)
    tileJoin = self.patch.add(tile)
    self.currentSide1 = self.patch.getSides().get(self.patch.getSideIndex() + 1)
    self.a.rotate(-1)
    self.b.rotate(-1)
    self.step1 = self.step1 + 1

    for i in range (self.step2 / self.n):
      self.currentSide2 = self.addBlockPair(self.c, self.d, self.currentSide2, self.black1)

    tile = self.prototile.getTransformedTile(self.c)
    tile = self.fittingTiler.getFittingTile(self.currentSide2, tile, 0)
    self.black2 = self.setTileColour(tile, self.black2)
    tileJoin = self.patch.add(tile)
    self.currentSide2 = self.patch.getSides().get(self.patch.getSideIndex() + 1)
    self.c.rotate(-1)
    self.d.rotate(-1)
    self.step2 = self.step2 + 1

    return tileJoin

  def addBlockPair(self, x, y, currentSide, black):
    tile = self.prototile.getTransformedTile(x)
    tile = self.fittingTiler.getFittingTile(currentSide, tile, 0)
    self.setTileColour(tile, black)
    self.patch.add(tile)
    currentSide = self.patch.getSides().get(self.patch.getSideIndex() + 1)
    tile = self.prototile.getTransformedTile(y)
    tile = self.fittingTiler.getFittingTile(currentSide, tile, 2)
    self.setTileColour(tile, not black)
    self.patch.add(tile)
    return self.patch.getSides().get(self.patch.getSideIndex())

  def setTileColour(self, tile, black):
      if black:
        tile.getUI().setBackground(Color.black)
        return 0
      else:
        tile.getUI().setBackground(Color.white)
        return 1

if __name__ == 'main':
  autoTiler = SpiralAutoTiler(horn.ProtoCHorn(7), 1)
#  autoTiler = SpiralAutoTiler(horn.ProtoSHorn(8), 0)
  alhambra.addJInternalFrame(AutoTilerViewer(autoTiler, "Spirals"))

  

