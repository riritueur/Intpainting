package topologyV2;
import java.io.IOException;
/**
	A mask defines the area to reconstruct
*/
public class Mask extends BoundingBox
	{
		/**
			val[i][j]=
				True is the pixel (i,j) is masked
				False is the pixle (i,j) is to be kept unchanged
		*/
		public boolean[][] val;
		/**
			@param point a  point of the BoundingBox
			@return True if on the pixel that as point for a corner belongs to the mask (4 pixels at most)
		*/
		public boolean touchedBy(Point p){
			if(p.onCorner()){
				if(p.i == 0 && p.j == 0){
					if(this.val[0][0])
						return true;
					return false;
				}
				else{
					if(p.i == 0){
						if(this.val[0][height])
							return true;
						return false;
					}
					else{
						if(p.j == 0){
							if(this.val[width][0])
								return true;
							return false;
						}
							else{
								if(this.val[width][height])
									return true;
								return false;
							}
					}
				}
			}
			else{
				if(p.onBorder()){
					if(p.i == 0){
						if(val[0][p.j] || val[0][p.j-1])
							return true;
						return false;
					}
					else{
						if(p.j == 0){
							if(val[p.i][0] || val[p.j-1][0])
								return true;
							return false;
						}
						else{
							if(p.j<p.i){
								if(val[p.i-1][p.j] || val[p.i-1][p.j-1])
									return true;
								return false;
							}
							else{
								if(val[p.i][p.j-1] || val[p.i-1][p.j-1])
									return true;
								return false;
							}
						}
					}
				}
				else{
					if(val[p.i][p.j] || val[p.i-1][p.j] || val[p.i][p.j-1] || val[p.i-1][p.j-1])
						return true;
					return false;
				}
			}
		}
		/**
			@param matrix contains the image that defines the mask
			@param color the color of the pixel belonging to the mask
			
			Construct a mask of same bounding box than the matrix and
			set val[i][j] to True if pixel (i,j) is equal to color
		*/
		public Mask(Matrix matrix,Color color) 
		{
			super(matrix);
			val=new boolean[width][height];
			for(int j=0;j<height;j++)
				for(int i=0;i<width;i++)
					val[i][j]=matrix.val[i][j].isequalto(color);
		}
		/**
			@param fileName File Name of a bmp image 3x8bits that defines the mask
			@param color the color of the pixels that defines the mask
		*/
		
		public Mask(String fileName,Color color) throws IOException 
		{
			this(new Matrix(fileName),color);
		}
	}