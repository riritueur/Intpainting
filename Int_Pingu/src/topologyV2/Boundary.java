package topologyV2;

import java.util.ArrayList;

// http://www.cmap.polytechnique.fr/~pantz/inpainting/webdoc/topology/Boundary.html

public class Boundary{
	
    public ArrayList<Edge> edges;
    BoundingBox bb; 
   
    public Boundary(Mask m) {
    	this.bb=m;
    	edges = new ArrayList<Edge>();
    	//V�rification horizontale
    	for(int i=0;i<m.width-1;i++){
    		for(int j=0;j<m.height;j++){
    			if(m.val[i][j] && !m.val[i+1][j]){
    				edges.add(new Edge(m, 1, i+1, j, 1));
    			}
    			if(!m.val[i][j] && m.val[i+1][j]){
    				edges.add(new Edge(m, 1,i+1, j+1, -1));
    			}
    		}
    	}
    	
    	//V�rification verticale
    	for(int i=0;i<m.width;i++){
    		for(int j=0;j<m.height-1;j++){
    			if(m.val[i][j] && !m.val[i][j+1]){
    				edges.add(new Edge(m, 0,i+1, j+1, -1));
    			}
    			if(!m.val[i][j] && m.val[i][j+1]){
    				edges.add(new Edge(m, 0, i, j+1, 1));
    			}
    		}
    	}
    }
    
    @Override
    public String toString() { 
    	String s ="Edges: \n";
    	for(Edge e : edges) {
    		s += e.toString() + "\n";
    	}
    	return s;
    } 
}