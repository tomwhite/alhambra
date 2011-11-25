# This short tutorial demonstrates how to write a simple tile, a right-angled isosceles triangle.

# All these (Java) imports are needed since out Triangle tile uses
# these core classes.
from java.awt.geom import AffineTransform
from org.tiling.alhambra import Prototile
from org.tiling.alhambra import SimpleTile
from org.tiling.alhambra import SymmetryGroup
from org.tiling.alhambra import Vertex

# Subclass org.tiling.alhambra.SimpleTile to benefit from its implementation of org.tiling.alhambra.Tile
class Triangle(SimpleTile):
  """A very simple demonstration tile: a right-angled isosceles triangle."""
  def __init__(self, t=AffineTransform()):
    vertices = [Vertex(0, 0), Vertex(1, 0), Vertex(0, 1)] # a list of the untransformed vertices
    SimpleTile.__init__(self, vertices, t)
    self.preTransformation = t
  # the transform method has to be overridden to return a transformed tile of the correct type
  def transform(self, t):
    t = AffineTransform(t)
    t.concatenate(self.preTransformation)
    triangle = Triangle(t)
    triangle.getUI().setBackground(self.getUI().getBackground())
    return triangle

# Specify the symmetries of a tile by wrapping a Triangle with a SymmetryGroup object to describe
# how it may be transformed in the tiling. In this case rotational symmetry of order four.
class ProtoTriangle(Prototile):
  def __init__(self):
    Prototile.__init__(self, Triangle(), SymmetryGroup(4, 0))

# When this module is run add a Triangle to Alhambra's prototile menu. Note that 'alhambra' is
# the instance of Alhambra in which we are running, and is the way we interact with Alhambra
# using scripts.
if __name__ == 'main':
  alhambra.getPrototileMenuView().add(ProtoTriangle())


