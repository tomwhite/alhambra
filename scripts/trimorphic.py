# A trimorphic tile.

import math

from java.awt import Color
from java.awt.geom import AffineTransform
from java.awt.geom import CubicCurve2D
from java.awt.geom import GeneralPath
from java.awt.geom import Rectangle2D

from org.tiling import UI
from org.tiling.alhambra import Curve
from org.tiling.alhambra import LabelledEdge
from org.tiling.alhambra import Prototile
from org.tiling.alhambra import SimpleTile
from org.tiling.alhambra import PolygonTileUI
from org.tiling.alhambra import SymmetryGroup
from org.tiling.alhambra import Vertex

class Trimorphic(SimpleTile):
  vertices = [Vertex(0, 0), Vertex(1, 0), Vertex(1, 1), Vertex(0.5, 1.5), Vertex(0, 1)]
  edges = [LabelledEdge(vertices[0], vertices[1], 1, Curve.S_CURVE),\
           LabelledEdge(vertices[1], vertices[2], 1, Curve.S_CURVE),\
           LabelledEdge(vertices[2], vertices[3], 1, Curve.S_CURVE),\
           LabelledEdge(vertices[3], vertices[4], 1, Curve.S_CURVE),\
           LabelledEdge(vertices[4], vertices[0], 1, Curve.S_CURVE)]
  def __init__(self, t=AffineTransform()):
    SimpleTile.__init__(self, self.edges, t)
    self.preTransformation = t
    self.setUI(TrimorphicUI(t, CubicCurve2D.Double(0, 0, 0.3, 0.5, 0.7, -0.5, 1, 0)))

  def transform(self, t):
    t = AffineTransform(t)
    t.concatenate(self.preTransformation)
    tile = Trimorphic(t)
    tile.getUI().setBackground(self.getUI().getBackground())
    return tile

class TrimorphicUI(PolygonTileUI):
  def __init__(self, t, wiggle=CubicCurve2D.Double(0, 0, 0.5, 0.5, 0.5, -0.5, 1, 0)):
    self.preTransform = t
    self.wiggle = wiggle
    PolygonTileUI.__init__(self, Color(64, 183, 185), t)
    self.initialiseGraphics()

  def initialiseGraphics(self):
    self.polygonalTilePath = GeneralPath() 
    self.polygonalTilePath.append(self.wiggle, 1)

    t = AffineTransform.getTranslateInstance(1, 0)
    t.rotate(math.pi / 2)
    wiggle2 = GeneralPath(self.wiggle)
    wiggle2.transform(t)
    self.polygonalTilePath.append(wiggle2, 1)

    t = AffineTransform.getTranslateInstance(1, 1)
    t.rotate(3 * math.pi / 4)
    t.scale(1 / math.sqrt(2), 1 / math.sqrt(2))
    wiggle3 = GeneralPath(self.wiggle)
    wiggle3.transform(t)
    self.polygonalTilePath.append(wiggle3, 1)

    t = AffineTransform.getTranslateInstance(0.5, 1.5)
    t.rotate(5 * math.pi / 4)
    t.scale(1 / math.sqrt(2), 1 / math.sqrt(2))
    wiggle4 = GeneralPath(self.wiggle)
    wiggle4.transform(t)
    self.polygonalTilePath.append(wiggle4, 1)

    t = AffineTransform.getTranslateInstance(0, 1)
    t.rotate(3 * math.pi / 2)
    wiggle5 = GeneralPath(self.wiggle)
    wiggle5.transform(t)
    self.polygonalTilePath.append(wiggle5, 1)

    self.polygonalTilePath.closePath()
    self.polygonalTilePath.transform(self.preTransform)

class ProtoTrimorphic(Prototile):
  def __init__(self):
    Prototile.__init__(self, Trimorphic(), SymmetryGroup(4, 1))

if __name__ == 'main':
  alhambra.getPrototileMenuView().add(ProtoTrimorphic())


