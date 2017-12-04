package topologyV2;

import java.awt.image.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BoundingBox{	// define the domain size


	public int[] bb;      // bb=[imin,jmin,imax,jmax] where (imin,jmin) is the upper left corner (imax,jmax) is the lower right corner
	public int width;       // width of the bouding box (imax-imin)
	public int height;      // height of the bouding box (jmax-jmin)
	public int size;        // size of the bouding box (width*height)
	public int nbEdges;            // number of edges belonging to the bounding box
	public int nbEdgesHorizontal;  // number of horizontal edges belonging to the bounding box
	public int nbEdgesVertical;    // number of vertical edges belonging to the bounding box
	
	
	public BoundingBox(int[] bb_)
	{
		bb=bb_;
		width=bb[2]-bb[0];
		height=bb[3]-bb[1];
		size=height*width;
		nbEdgesHorizontal=width*(height+1);
		nbEdgesVertical=(width+1)*height;
		nbEdges=nbEdgesHorizontal+nbEdgesVertical;
	}
	
	
	public BoundingBox(BufferedImage image )
	{
		this(new int[]{0,0,image.getWidth(),image.getHeight()});
	}
	
	
	public BoundingBox(String fileName)  throws IOException 
	{
		this(ImageIO.read(new File(fileName)));
	}
	
	
	public BoundingBox(BoundingBox boundingBox)
	{
		this(boundingBox.bb);
	}
		
		//VPROF
		public BoundingBox crop(Patch patch)
		{
			return new BoundingBox(new int[] {bb[0]-patch.boundingBox.bb[0],
				bb[1]-patch.boundingBox.bb[1],
				bb[2]-patch.boundingBox.bb[2],
				bb[3]-patch.boundingBox.bb[3]});
		}
		
		/*
		public void crop(Patch p){
			ArrayList<Point> points = new ArrayList<Point>();
			for(int i=bb[0];i<=bb[1];i++){
				for(int j=bb[2];j<=bb[3];j++){
					if(p.point.isInBoundingBox(this)){
						if(new Point(this,p.bb.bb[0],p.bb.bb[1]).isInBoundingBox(this) && new Point(this,p.bb.bb[2],p.bb.bb[3]).isInBoundingBox(this)){
							points.add(p.point);
						}
					}
				}
			}
			this.bb[0]=points.get(0).i;
			this.bb[1]=points.get(points.size()-1).i;
			this.bb[2]=points.get(0).j;
			this.bb[3]=points.get(points.size()-1).j;
		}*/

	
}