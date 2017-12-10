package topologyV2;

import java.util.ArrayList;

public class Component {
	
	public ArrayList<Point> points;
	public Component(Tag tag,Point seedPoint) {
		points=new ArrayList<Point>();
		Point currentPoint=seedPoint;
		int k=tag.indexActiveOuterEdge(currentPoint);
		while(k!=-1) {
			points.add(tag.boundary.edges.get(k).border()[0]);
			tag.active[k]=false;
			tag.nbActive-=1;
			currentPoint=tag.boundary.edges.get(k).border()[1];
			k=tag.indexActiveOuterEdge(currentPoint);}
		if(!(seedPoint.isEqualTo(currentPoint))) points.add(currentPoint);}
	
	
	@Override
	public String toString() {
		String result = "Component :\n";
		for(int i=0;i<points.size();i++)
			result += "Point : " + i + " - " + points.get(i).toString() + "\n";
		return result;
	}
}