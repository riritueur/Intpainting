package topologyV2;
import java.util.Arrays;

/**
	Tag active edges of a boundary
*/
public class Tag {
		int[] index;
		boolean[] active;
		int nbActive;
		Boundary boundary; 
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
		Point SeedPoint(){
			for(int k=0;k<active.length;k++)
				if(active[k]){
					Edge edge=boundary.edges.get(k);
					Point point=edge.border()[0];
					if(point.onBorder()) return point;}
			for(int k=0;k<active.length;k++)
				if(active[k]){
					Edge edge=boundary.edges.get(k);
					Point point=edge.border()[0];
					return point;}
			return null;}


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