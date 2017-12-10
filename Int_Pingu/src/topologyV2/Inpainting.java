package topologyV2;

import java.io.IOException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class Inpainting {

	private static int tgv=3*255*255+1;
	public Matrix image;
	private Mask m;
	private int[][] penMask;
	private BoundingBox window;
	
	public Inpainting(Matrix image,Mask m){
		if(image.bb[1] == m.bb[1] && image.bb[2] == m.bb[2] && image.bb[3] == m.bb[3] && image.bb[0] == m.bb[0]){
			this.image = image;
			this.m = m;
			window = new BoundingBox(image);
			penMask = new int[image.width][image.height];
			for(int i=0;i<image.width;i++){
				for(int j=0;j<image.height;j++){
					if(m.val[i][j])
						penMask[i][j] = tgv;
					else
						penMask[i][j] = 0;
				}
			}
		}
	}
	
	//VPROF
	private BoundingBox searchingBox(Component component,int halfSizeBox){
		Point pt0=component.points.get(0);
		int[] bb=new int[]{pt0.i,pt0.j,pt0.i,pt0.j};
		for(Point pt:component.points){
			bb[0]=Math.min(bb[0],pt.i);
			bb[1]=Math.min(bb[1],pt.j);
			bb[2]=Math.max(bb[2],pt.i);
			bb[3]=Math.max(bb[3],pt.j);
		};
		return new BoundingBox(new int[]{Math.max(bb[0]-halfSizeBox,0),
			Math.max(bb[1]-halfSizeBox,0),
			Math.min(bb[2]+halfSizeBox,window.width),
			Math.min(bb[3]+halfSizeBox,window.height)});
	}
	
	/*private BoundingBox searchingBox(Component co, int a){
		int imin = searchIMin(co) - a;
		int imax = searchIMax(co) + a;
		int jmin = searchJMin(co) - a;
		int jmax = searchJMax(co) + a;
		
		
		while(imin < window.bb[0])
			imin++;
		while(jmin < window.bb[2])
			jmin++;
		while(imax > window.bb[1])
			imax--;
		while(jmax > window.bb[3])
			jmax--;
		
		int[] dimensions = {imin,imax,jmin,jmax};
		return new BoundingBox(dimensions);
	}
	
	private int searchIMin(Component co){
		int result = co.points.get(0).i;
		for(Point p : co.points){
			if(p.i < result)
				result = p.i;
		}
		return result;
	}

	private int searchIMax(Component co){
		int result = co.points.get(0).i;
		for(Point p : co.points){
			if(p.i > result)
				result = p.i;
		}
		return result;
	}

	private int searchJMin(Component co){
		int result = co.points.get(0).j;
		for(Point p : co.points){
			if(p.j < result)
				result = p.j;
		}
		return result;
	}
	
	private int searchJMax(Component co){
		int result = co.points.get(0).j;
		for(Point p : co.points){
			if(p.j > result)
				result = p.j;
		}
		return result;
	}*/
	
	//VPROF
	private void copyPatch(Point best_point,Patch patch){
		int i=best_point.i;
		int j=best_point.j;
		for(int dx=patch.boundingBox.bb[0];dx<patch.boundingBox.bb[2];dx++)
			for(int dy=patch.boundingBox.bb[1];dy<patch.boundingBox.bb[3];dy++)
			{
				int I=patch.point.i+dx;
				int J=patch.point.j+dy;
				if(m.val[I][J])
					image.val[I][J].set(image.val[i+dx][j+dy]);
					m.val[I][J]=false;
			}
	}
	
	/*
	private void copyPatch(Point po,Patch pa){
		Point origineDepart = new Point(window,pa.point.i+pa.bb.bb[0],pa.point.j+pa.bb.bb[1]);
		Point origineArrivee = new Point(window,po.i+pa.bb.bb[0],po.j+pa.bb.bb[1]);
		Color[][] couleurs = new Color[pa.bb.width][pa.bb.height];
		pa.point.i = po.i;
		pa.point.j = po.j;
		window.crop(pa);
		for(int i=origineDepart.i;i<origineDepart.i+pa.bb.width;i++){
			for(int j=origineDepart.j;j<origineDepart.j+pa.bb.height;j++)
				couleurs[i][j] = image.val[origineDepart.i+i][origineDepart.j+j];
		}
		
		for(int i=origineArrivee.i;i<origineArrivee.i+pa.bb.width;i++){
			for(int j=origineArrivee.j;j<origineArrivee.j+pa.bb.height;j++){
				if(m.val[i][j]){
					image.val[i][j] = couleurs[i][j];
					m.val[i][j] = false;
				}
			}
		}
	}
	*/
	
	private int[] argmin(double[][] tab){
		int[] result = {0,0};
		for(int i=0; i<tab.length; i++){
			for(int j=0; j<tab[i].length; j++){
				if(tab[result[0]][result[1]] > tab[i][j]){
					result[0] = i;
					result[1] = j;
				}
			}
		}
		return result;
	}
	
	
	//VPROF
	private Point best_match(Patch patch,BoundingBox Box){
		if(Box==null)
			Box=new BoundingBox(new int[]{0,0,m.width,m.height});
		BoundingBox searchBox=Box.crop(patch);
		double[][] norms=new double[searchBox.width][searchBox.height];	
		for(int i=0;i<searchBox.width;i++)
			for(int j=0;j<searchBox.height;j++) norms[i][j]=0;
		for(int dx=patch.boundingBox.bb[0];dx<patch.boundingBox.bb[2];dx++)
			for(int dy=patch.boundingBox.bb[1];dy<patch.boundingBox.bb[3];dy++){
				int I=patch.point.i+dx;			int J=patch.point.j+dy;
				int i_min=searchBox.bb[0]+dx; 	
				int j_min=searchBox.bb[1]+dy;	
				for(int k=0;k<searchBox.width;k++) for(int l=0;l<searchBox.height;l++)
						norms[k][l]+=penMask[i_min+k][j_min+l];
				if (!m.val[I][J])
					for(int k=0;k<searchBox.width;k++)  for(int l=0;l<searchBox.height;l++)
							norms[k][l]+=Color.dist(image.val[i_min+k][j_min+l],image.val[I][J]);
			}
		int[] bestIndex=argmin(norms);
		return new Point(m,bestIndex[0]+searchBox.bb[0],bestIndex[1]+searchBox.bb[1]);
	}
	
	/*
	// *cries internally* //
	private Point best_match(Patch p, BoundingBox bob){
		int[][] distances = new int[bob.bb[2]-bob.bb[0]][bob.bb[3]-bob.bb[1]];
		for(int i=0;i<distances.length;i++){
			for(int j=0;j<distances[i].length;j++)
				distances[i][j] = tgv;
		}
		for(int i=bob.bb[0];i<=bob.bb[1];i++){
			for(int j=bob.bb[2];j<=bob.bb[3];j++){
				if(!p.intersectsMask(m)){
					Patch temp = new Patch(new Point(bob,j,i),p.halfwidth, bob);
					if(temp.boundingBox.bb[0] == p.boundingBox.bb[0] && temp.boundingBox.bb[1] == p.boundingBox.bb[1] && temp.boundingBox.bb[2] == p.boundingBox.bb[2] && temp.boundingBox.bb[3] == p.boundingBox.bb[3]){
						distances[i][j] = 0;
						for(int k=temp.boundingBox.bb[0];k<=temp.boundingBox.bb[1];k++){
							for(int l=temp.boundingBox.bb[2];l<=temp.boundingBox.bb[3];l++)
								distances[i][j] += Color.dist(image.val[temp.point.i+k][temp.point.j+l], image.val[p.point.i+k][p.point.j+l]);
						}
					}
				}
			}
		}
		int[] coordMini = argmin(distances);
		return new Point(bob,coordMini[0],coordMini[1]);
	}*/
	
	//VPROF
	public void restore(int halfwidth,int searchWidth) throws IOException {
		Boundary b=new Boundary(m);
		Components C=new Components(b);
		while (C.size()!=0){
			for(Component component:C.components){
				BoundingBox searchBox=null;
				if(searchWidth!=0)
					searchBox=searchingBox(component,searchWidth);
				for(Point point:component.points)
				{ 
					if (m.touchedBy(point)){
						Patch patch=new Patch(point,halfwidth,window);
						Point best_point=best_match(patch,searchBox);
						copyPatch(best_point,patch);
					}
				}
			}
			b=new Boundary(m);
			C=new Components(b);
			if(searchWidth!=0) searchWidth+=halfwidth;
		}
	};
	
//	public void restore(int a,int b) throws IOException{
//		Components comps = new Components(new Boundary(m));
//		for(Component comp : comps.components){
//			BoundingBox sBox = searchingBox(comp,b);
//			for(Point p : comp.points){
//				if(m.touchedBy(p)){
//					Patch target = new Patch(p, a, window);
//					Point bmCenter = best_match(target,sBox);
//					copyPatch(bmCenter,target);
//				}
//			}
//			b+=a;
//		}
//	}
	
public static void main(String[] str) throws IOException, ParseException{
		
		int halfwidth=0;
		int searchWidth=0;
		String maskCom=null;
		String imageToRestore=null;
		String restoredImage=null;
		Inpainting i;
		
		CommandLine commandLine;
		
        Option option_half = Option.builder("p")
            .required(true)
            .desc("Halfwidth option")
            .longOpt("halfwidth")
            .build();
        Option option_search = Option.builder("s")
            .required(true)
            .desc("Searching width option")
            .longOpt("searchingbox")
            .build();
        Option option_mask = Option.builder("m")
            .required(true)
            .desc("Mask option")
            .longOpt("mask")
            .build();
        Option option_image = Option.builder("i")
            .required(true)
            .desc("Image to restore option")
            .longOpt("imagetorestore")
            .build();
        Option option_restored = Option.builder("o")
            .required(true)
            .desc("Image restored option")
            .longOpt("restoredimage")
            .build();
        
        Options options = new Options();
        CommandLineParser parser = new DefaultParser();

        options.addOption(option_half);
        options.addOption(option_search);
        options.addOption(option_mask);
        options.addOption(option_image);
        options.addOption(option_restored);

        try
        {
            commandLine = parser.parse(options, str);

            if (commandLine.hasOption("p"))
            {
                halfwidth = Integer.parseInt(commandLine.getOptionValue("p"));
            }

            if (commandLine.hasOption("s"))
            {
            	searchWidth = Integer.parseInt(commandLine.getOptionValue("s"));
            }

            if (commandLine.hasOption("m"))
            {
            	maskCom = commandLine.getOptionValue("m");
            }
            
            if (commandLine.hasOption("i"))
            {
            	imageToRestore = commandLine.getOptionValue("i");
            }

            if (commandLine.hasOption("o"))
            {
            	restoredImage = commandLine.getOptionValue("o");
            }
            
            Matrix matrix = new Matrix(imageToRestore);
    		Mask mask = new Mask(maskCom,new Color(0,0,0));
    		i = new Inpainting(matrix,mask);
    		i.restore(halfwidth, searchWidth);
    		i.image.save(restoredImage);
        }
        catch (org.apache.commons.cli.ParseException e) {
			e.printStackTrace();
		}
	}
	
	
}
