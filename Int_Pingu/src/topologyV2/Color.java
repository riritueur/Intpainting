package topologyV2;

public class Color {
	public byte[] val = new byte[3];
	public static int dist(Color a,Color b){
		return ( Math.abs(byteToInt(a.val[0]) - byteToInt(b.val[0]) ) + Math.abs(byteToInt(a.val[1]) - byteToInt(b.val[1]) ) + Math.abs(byteToInt(a.val[2]) - byteToInt(b.val[2]) ) );
	}
	
	public static int byteToInt(byte a){
		return (int)(a& 0xFF);
	}
	
	public void set(Color c){
		this.val[0] = c.val[0];
		this.val[1] = c.val[1];
		this.val[2] = c.val[2];
	}
	boolean isequalto(Color c){
		if(this.val[0] == c.val[0] && this.val[1] == c.val[1] && this.val[2] == c.val[2]){
			return true;
		}
		return false;
	}
	public Color(byte a,byte b,byte c){
		val[0] = a;
		val[1] = b;
		val[2] = c;
	}
	public Color(int a,int b,int c){
		val[0] = (byte)a;
		val[1] = (byte)b;
		val[2] = (byte)c;
	}
	@Override public String toString(){
		int a = val[0] & 0xFF;
		int b = val[1] & 0xFF;
		int c = val[2] & 0xFF;
		return "Color: " + a + ", " + b + ", " + c;
	}
}

