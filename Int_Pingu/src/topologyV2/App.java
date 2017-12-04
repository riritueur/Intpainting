package topologyV2;

import java.io.IOException;

public class App {

	public static void main(String[] args) throws IOException, NullPointerException{
		Matrix matrix = new Matrix("Image\\bungee-free.bmp");
		Matrix matrix2 = new Matrix("Image\\bungee-free-mask.bmp");
		Mask mask = new Mask(matrix2,new Color(0,0,0));
		Inpainting inpainting = new Inpainting(matrix,mask);
		inpainting.restore(5, 5);
		inpainting.image.save("Image\\aaaaa.bmp");
	}

}
