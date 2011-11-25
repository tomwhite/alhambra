# I define some simple horn shape tiles with curved sides.

import math

from java.awt import Color
from java.awt.geom import AffineTransform
from java.awt.geom import CubicCurve2D
from java.awt.geom import GeneralPath
from java.awt.geom import Rectangle2D

from java.util import ArrayList
from java.util import Collections

from org.tiling import UI
from org.tiling.alhambra import Curve
from org.tiling.alhambra import LabelledEdge
from org.tiling.alhambra import Prototile
from org.tiling.alhambra import SimpleTile
from org.tiling.alhambra import PolygonTileUI
from org.tiling.alhambra import SymmetryGroup
from org.tiling.alhambra import Vertex

# C

class CHorn(SimpleTile):
  def __init__(self, n, t=AffineTransform()):
    vertices = [Vertex(0, 0), Vertex(1, 0), Vertex(math.cos(math.pi / n), math.sin(math.pi / n))]
    edges = [LabelledEdge(vertices[0], vertices[1], 1, Curve.C_CURVE),\
             LabelledEdge(vertices[1], vertices[2]),\
             LabelledEdge(vertices[2], vertices[0], -1, Curve.C_CURVE)]
    SimpleTile.__init__(self, edges, t)
    self.preTransformation = t
    self.n = n
    self.setUI(CHornUI(t, n))

  def transform(self, t):
    t = AffineTransform(t)
    t.concatenate(self.preTransformation)
    w = CHorn(self.n, t)
    w.getUI().setBackground(self.getUI().getBackground())
    return w

class CHornUI(PolygonTileUI):
  def __init__(self, t, n):
    self.preTransform = t
    self.n = n
    PolygonTileUI.__init__(self, Color(210, 83, 185), t)
    self.initialiseGraphics()

  def initialiseGraphics(self):
    wiggle=CubicCurve2D.Double(0, 0, 0.3, 0.2, 0.7, 0.2, 1, 0)
    reverseWiggle=CubicCurve2D.Double(1, 0, 0.7, 0.2, 0.3, 0.2, 0, 0)

    self.polygonalTilePath = GeneralPath() 
    self.polygonalTilePath.append(wiggle, 1)

    self.polygonalTilePath.lineTo(math.cos(math.pi / self.n), math.sin(math.pi / self.n))

    t = AffineTransform.getRotateInstance(math.pi / self.n)
    wiggle2 = GeneralPath(reverseWiggle)
    wiggle2.transform(t)
    self.polygonalTilePath.append(wiggle2, 1)

    self.polygonalTilePath.closePath()
    self.polygonalTilePath.transform(self.preTransform)


class ProtoCHorn(Prototile):
  def __init__(self, n):
    Prototile.__init__(self, CHorn(n), SymmetryGroup(n * 2, 1))

# S

class SHorn(SimpleTile):
  def __init__(self, n, t=AffineTransform()):
    vertices = [Vertex(0, 0), Vertex(1, 0), Vertex(math.cos(math.pi / n), math.sin(math.pi / n))]
    edges = [LabelledEdge(vertices[0], vertices[1], 1, Curve.S_CURVE),\
             LabelledEdge(vertices[1], vertices[2]),\
             LabelledEdge(vertices[2], vertices[0], 1, Curve.S_CURVE)]
    SimpleTile.__init__(self, edges, t)
    self.preTransformation = t
    self.n = n
    self.setUI(SHornUI(t, n))

  def transform(self, t):
    t = AffineTransform(t)
    t.concatenate(self.preTransformation)
    w = SHorn(self.n, t)
    w.getUI().setBackground(self.getUI().getBackground())
    return w

class SHornUI(PolygonTileUI):
  def __init__(self, t, n):
    self.preTransform = t
    self.n = n
    PolygonTileUI.__init__(self, Color(210, 83, 185), t)
    self.initialiseGraphics()

  def initialiseGraphics(self):
    wiggle=CubicCurve2D.Double(0, 0, 0.3, -0.3, 0.7, 0.3, 1, 0)
    reverseWiggle=CubicCurve2D.Double(1, 0, 0.7, 0.3, 0.3, -0.3, 0, 0)

    self.polygonalTilePath = GeneralPath() 
    self.polygonalTilePath.append(wiggle, 1)

    self.polygonalTilePath.lineTo(math.cos(math.pi / self.n), math.sin(math.pi / self.n))

    t = AffineTransform.getRotateInstance(math.pi / self.n)
    wiggle2 = GeneralPath(reverseWiggle)
    wiggle2.transform(t)
    self.polygonalTilePath.append(wiggle2, 1)

    self.polygonalTilePath.closePath()
    self.polygonalTilePath.transform(self.preTransform)

class ProtoSHorn(Prototile):
  def __init__(self, n):
    Prototile.__init__(self, SHorn(n), SymmetryGroup(n * 2, 0))

if __name__ == 'main':
  cList = ArrayList()
  sList = ArrayList()
  for n in range(2, 6):
    cList.add(ProtoCHorn(n))
    sList.add(ProtoSHorn(n))
  alhambra.getPrototileMenuView().add(cList)
  alhambra.getPrototileMenuView().add(sList)
  

