package topologyV2;
import java.util.Arrays;

/**
	Tag active edges of a boundary
*/
public class Tag {
		/**
			Array that for each (non oriented) Edge of the bounding box affects either
				-1 if the edge does not belong to the boundary
				k the index of the edge in the array boundary.edges
		*/
		int[] index;		// reference to the edge
		/**
			Is True if the edge is active (i.e. not yet treated)
		*/
		boolean[] active;	// tag = True/False
		/**
			Nb of Edge yet to treat
		*/
		int nbActive;		// nb of active edges
		/**
			boundary to Tag
		*/
		Boundary boundary; 
		/**
			Set all the fields
		*/
		public Tag(Boundary b){
			this.boundary = b;
			initializeIndex(b);
			initializeActive(b);
			setNbActive();
		}
		
		public void initializeIndex(Boundary b){
			index = new int[boundary.bb.nbEdges];
			for(int i=0; i<b.bb.width*(b.bb.height ) + b.bb.height*b.bb.width;i++)
				index[i] = -1;
			for(Edge e : b.edges)
				index[e.label] = e.label;
		}
		
		private void initializeActive(Boundary b) {
			int i=0;
			for(Edge edge:boundary.edges) index[edge.label]=i++;
			active=new boolean[i];
			Arrays.fill(active,true);
		}
		
		private void setNbActive(){
			nbActive = 0;
			for(int i=0;i<active.length;i++){
				if(active[i])
					nbActive ++;
			}
		}
		/** 
			@return a valid SeedPoint if exists.
			A valid Seed Point 
				- Is the initial vertex of an active edge
				- For connected components of the active set that touched the boundary of the bounding box, the
				active point is located on the boundary.
				- For connected components of the active set that do not touched the boundary, any point of the active
				connected component is a valid seed point.
		*/
		Point SeedPoint(){
			// We look for boundary points first
			for(int k=0;k<active.length;k++)
				if(active[k]){
					Edge edge=boundary.edges.get(k);
					Point point=edge.border()[0];
					if(point.onBorder()) return point;}
			// If none found, we look for inner points
			for(int k=0;k<active.length;k++)
				if(active[k]){
					Edge edge=boundary.edges.get(k);
					Point point=edge.border()[0];
					return point;}
			return null;}
		/** 
			@return the index of an active outer edge to point (if exists) ; -1 if no active outer edge is found
		*/
		int indexActiveOuterEdge(Point point){
			for (Edge edge:point.outerEdges())
			{	
				int k=index[edge.label];
				if(k!=-1)
					if(boundary.edges.get(k).orientation==edge.orientation)
						if(active[k]) return k;
			}
			return -1;}
	}