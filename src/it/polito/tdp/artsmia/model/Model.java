package it.polito.tdp.artsmia.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.event.ConnectedComponentTraversalEvent;
import org.jgrapht.event.EdgeTraversalEvent;
import org.jgrapht.event.TraversalListener;
import org.jgrapht.event.VertexTraversalEvent;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.GraphIterator;

import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class Model {
	
	private class EdgeTraversedListener implements TraversalListener<ArtObject, DefaultWeightedEdge> {

		@Override
		public void connectedComponentFinished(ConnectedComponentTraversalEvent arg0) {
			
		}

		@Override
		public void connectedComponentStarted(ConnectedComponentTraversalEvent arg0) {
			
		}

		@Override
		public void edgeTraversed(EdgeTraversalEvent<DefaultWeightedEdge> e) {
			// uso una mappa che mi mappa target come chiave e source come valore
			
			ArtObject sourceVertex = graph.getEdgeSource(e.getEdge());
			ArtObject targetVertex = graph.getEdgeTarget(e.getEdge());
			
			// Se il grafo non e' orientato, devo controllare l'ordine
			// Tra i due il vertice target deve essere presente una sola volta
			
			if (!backVisitTree.containsKey(targetVertex) && backVisitTree.containsKey(sourceVertex) ) {
				backVisitTree.put(targetVertex, sourceVertex);
			} else if (!backVisitTree.containsKey(sourceVertex) && backVisitTree.containsKey(targetVertex)) {
				backVisitTree.put(sourceVertex, targetVertex);
			}
			
		}

		@Override
		public void vertexFinished(VertexTraversalEvent<ArtObject> arg0) {
			
		}

		@Override
		public void vertexTraversed(VertexTraversalEvent<ArtObject> arg0) {
			
		}

	}
	
	private Graph<ArtObject, DefaultWeightedEdge> graph;
	private Map<Integer, ArtObject> idMap;
	private Map<ArtObject, ArtObject> backVisitTree;
	
	public Model() {
		graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		idMap = new HashMap<Integer, ArtObject>();
		
	}
	
	public void creaGrafo() {
		ArtsmiaDAO dao = new ArtsmiaDAO();
		dao.listObjects(idMap);
		
		Graphs.addAllVertices(graph, idMap.values());
		
		List<Link> links = dao.listLinks();
		
		for(Link l:links) {
			ArtObject source = idMap.get(l.getId_o1());
			ArtObject target = idMap.get(l.getId_02());
			int weight = l.getCnt();
			
			Graphs.addEdge(graph, source, target, weight);
		}
		
	}
	
	public List<ArtObject> listCorrelati( ArtObject source ) {
		
		List<ArtObject> result = new ArrayList<ArtObject>();
		
		backVisitTree = new HashMap<>();
		GraphIterator<ArtObject, DefaultWeightedEdge> it = new BreadthFirstIterator<>(this.graph, source);
		
		// voglio che la classe sia dentro al modello, la uso solo qui
		it.addTraversalListener(new Model.EdgeTraversedListener());
		backVisitTree.put(source, null);
		
		while (it.hasNext()) {
			result.add(it.next());
		}
				
		
		return result;
		
	}
	
	public int getVertexSize() {
		return graph.vertexSet().size();
	}
	
	public int getEdgeSize() {
		return graph.edgeSet().size();
	}

	public List<ArtObject> getObjects() {
		List<ArtObject> result = new ArrayList<ArtObject>();
		
		for (ArtObject a:idMap.values()) {
			result.add(a);
		}
		return result;
	}
	
	public List<ArtObject> walkTo(ArtObject target) {
		if (!backVisitTree.containsKey(target)) {
			//il percorso non esiste
			return null;
		}
		
		List<ArtObject> walk = new LinkedList<>();
		
		
		ArtObject a = target;
		
		while (a!= null) {
			walk.add(target);
			// walk.add(0,target) me le mette in ordine a costo o(1), o(n) usando arraylist
			a = backVisitTree.get(a);
			// posso farlo con getParent
		}
		
		return walk;
	}

}
