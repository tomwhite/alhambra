# I am a demo DebuggingTiler.

from java.util import Collections

from org.tiling.alhambra import PrototileSet
from org.tiling.alhambra import SimplePatch
from org.tiling.alhambra import SymbolicTransform
from org.tiling.alhambra.gui import AutoTilerViewer
from org.tiling.alhambra.tile.penrose import ProtoLB
from org.tiling.alhambra.tile.penrose.cartwheel import Decapod
from org.tiling.alhambra.tiler import AutoTiler
from org.tiling.alhambra.tiler import DebuggingTiler

class DebuggingDecapodTiler(DebuggingTiler):
  def __init__(self):
    self.prototile = ProtoLB()
    DebuggingTiler.__init__(self, SimplePatch(), PrototileSet(self.prototile))
    self.index = 0
    self.decapod = Decapod.BEETLE

  def nextTile(self):
    if self.index >= 10:
      return None
    st = SymbolicTransform(self.prototile.getSymmetryGroup())
    if self.decapod.toString()[self.index] == '0':
      st.rotate(2 * self.index)
    else:
      st.reflect(1)
      st.rotate(2 * self.index + 3)
    self.index = self.index + 1
    return self.prototile.getTransformedTile(st)

autoTiler = DebuggingDecapodTiler()
alhambra.addJInternalFrame(AutoTilerViewer(autoTiler, "Debugging Decapods"))


