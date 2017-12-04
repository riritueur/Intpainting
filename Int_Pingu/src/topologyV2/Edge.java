package topologyV2;

public class Edge {
	private static int[][] v={{1,0},{0,1}};
	public int direction,i,j;
	public int orientation;
	public int label;
	private BoundingBox bb;
	
	public int getI(){return i;}
	public int getJ(){return j;}
	public BoundingBox getBB(){return bb;}
	
	public Point[] border(){
		Point[] retour = new Point[2];
		retour[0] = new Point(bb, i, j);
		retour[1] = new Point(bb,i+(orientation * v[direction][0]), j+(orientation * v[direction][1]));
		return retour;
	}
	
	@Override
	public String toString(){
		return "Edge: Direction: " + direction + ", i: " + i + ", j: " + j + ", orientation: " + orientation;
	}
	
	public Edge(BoundingBox bb,int direction,int i,int j,int orientation){
		this.bb = bb;
		this.i = i;
		this.j = j;
		this.direction = direction;
		this.orientation = orientation;
		generateLabel();
	}
	
	public void generateLabel(){
	if(direction==0)
		label=(i+(orientation-1)/2*v[direction][0])+(j+(orientation-1)/2*v[direction][1])*bb.width;
	else
		label=bb.nbEdgesHorizontal+(i+(orientation-1)/2*v[direction][0])*bb.height+(j+(orientation-1)/2*v[direction][1]);}
}
