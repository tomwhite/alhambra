package org.tiling.alhambra.test;

import java.util.*;

import junit.framework.*;

import org.tiling.alhambra.*; 
import org.tiling.alhambra.ga.*; 
import org.tiling.alhambra.tiler.*;

public class GAEncoderTest extends TestCase {

	private Edge e1, e2, e3, e4;
	private SimpleTile tile1;

	public GAEncoderTest(String name) {
		super(name);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(suite());
	}

	public static Test suite() {
		return new TestSuite(GAEncoderTest.class);
	}

	public void setUp() {
		e1 = new Edge(new Vertex(0, 0), new Vertex(1, 0));		
		e2 = new Edge(new Vertex(1, 0), new Vertex(1, 1));
		e3 = new Edge(new Vertex(1, 1), new Vertex(0, 1));
		e4 = new Edge(new Vertex(0, 1), new Vertex(0, 0));
		tile1 = new SimpleTile(new Edge[] {e1, e2, e3, e4});
		Prototile prototile = AbstractPrototileFactory.getFactory("org.tiling.alhambra.tile.polyomino.PolyominoFactory").createPrototile("tetromino_L");
		prototileSet = new PrototileSet(prototile);
	}

	public void testGetEdgeNeigbourhood() {	
		List edges = tile1.getSides();

		List subset = new SimpleGAEncoder(prototileSet, 1).getEdgeNeighbourhood(edges, 0);
		assertEquals("size 1", 1, subset.size());
		assertEquals("1 e1", e1, subset.get(0));

		subset = new SimpleGAEncoder(prototileSet, 2).getEdgeNeighbourhood(edges, 0);
		assertEquals("size 2", 2, subset.size());
		assertEquals("2 e1", e1, subset.get(0));
		assertEquals("2 e2", e2, subset.get(1));

		subset = new SimpleGAEncoder(prototileSet, 3).getEdgeNeighbourhood(edges, 0);
		assertEquals("size 3", 3, subset.size());
		assertEquals("3 e4", e4, subset.get(0));
		assertEquals("3 e1", e1, subset.get(1));		
		assertEquals("3 e2", e2, subset.get(2));
		
		subset = new SimpleGAEncoder(prototileSet, 4).getEdgeNeighbourhood(edges, 0);
		assertEquals("size 4", 4, subset.size());
		assertEquals("4 e4", e4, subset.get(0));
		assertEquals("4 e1", e1, subset.get(1));
		assertEquals("4 e2", e2, subset.get(2));
		assertEquals("4 e3", e3, subset.get(3));		
	}
	
	public void testEncodeEdgeNeighbourhood() {
		GAEncoder simpleGAEncoder = new SimpleGAEncoder(prototileSet, 1);
		GAEncoder shortGAEncoder = new ShortGAEncoder(prototileSet, 1);
		
		List edgeNeighbourhood;

		// ._
		//  _|
		edgeNeighbourhood = getEdges(new Vertex[] {
			new Vertex(0, 0),	
			new Vertex(1, 0),	
			new Vertex(1, -1),	
			new Vertex(0, -1),	
		});
		assertEquals(Integer.parseInt("230", 4), simpleGAEncoder.encodeEdgeNeighbourhood(edgeNeighbourhood));
		assertEquals(Integer.parseInt("00", 3), shortGAEncoder.encodeEdgeNeighbourhood(edgeNeighbourhood));

		// .__
		//    |
		edgeNeighbourhood = getEdges(new Vertex[] {
			new Vertex(0, 0),	
			new Vertex(1, 0),	
			new Vertex(2, 0),	
			new Vertex(2, -1),	
		});
		assertEquals(Integer.parseInt("300", 4), simpleGAEncoder.encodeEdgeNeighbourhood(edgeNeighbourhood));
		assertEquals(Integer.parseInt("01", 3), shortGAEncoder.encodeEdgeNeighbourhood(edgeNeighbourhood));

		//    _
		// ._| 
		edgeNeighbourhood = getEdges(new Vertex[] {
			new Vertex(0, 0),	
			new Vertex(1, 0),	
			new Vertex(1, 1),	
			new Vertex(2, 1),	
		});
		assertEquals(Integer.parseInt("010", 4), simpleGAEncoder.encodeEdgeNeighbourhood(edgeNeighbourhood));
		assertEquals(Integer.parseInt("02", 3), shortGAEncoder.encodeEdgeNeighbourhood(edgeNeighbourhood));

		//  _
		// ._|
		edgeNeighbourhood = getEdges(new Vertex[] {
			new Vertex(0, 0),	
			new Vertex(1, 0),	
			new Vertex(1, 1),	
			new Vertex(0, 1),	
		});
		assertEquals(Integer.parseInt("210", 4), simpleGAEncoder.encodeEdgeNeighbourhood(edgeNeighbourhood));
		assertEquals(Integer.parseInt("22", 3), shortGAEncoder.encodeEdgeNeighbourhood(edgeNeighbourhood));

		//   _
		// .| |
		edgeNeighbourhood = getEdges(new Vertex[] {
			new Vertex(0, 0),	
			new Vertex(0, 1),	
			new Vertex(1, 1),	
			new Vertex(1, 0),	
		});
		assertEquals(Integer.parseInt("301", 4), simpleGAEncoder.encodeEdgeNeighbourhood(edgeNeighbourhood));
		assertEquals(Integer.parseInt("00", 3), shortGAEncoder.encodeEdgeNeighbourhood(edgeNeighbourhood));


	}

	private PrototileSet prototileSet;	public static final List getEdges(Vertex[] polygon) {
		List edges = Edge.getEdges(polygon);
		if (polygon.length > 2) {
			edges.remove(edges.size() - 1);
		}
		return edges;
	}}