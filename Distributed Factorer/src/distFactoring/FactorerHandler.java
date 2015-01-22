package distFactoring;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;

public class FactorerHandler implements Runnable{
	private static ArrayList<Factorer> factorers=new ArrayList<Factorer>();
	private static boolean done=false;
	
	public void run(){
		while(true){
			for(Factorer f: factorers){
				Scanner scan = null;
				try {
					scan = new Scanner(f.getSocket().getInputStream());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				while(scan.hasNext()){
					Server.found(scan.nextBigInteger());
					close();
				}
				
			}
			
			if(done){
				for(Factorer fe: factorers){
					PrintWriter out = null;
					try {
						out = new PrintWriter(fe.getSocket().getOutputStream());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					out.println("DONE");
				}
			}
		}
	}
	
	
	public static void add(Factorer factor){
		factorers.add(factor);
	}
	
	public static void close(){
		done=true;
	}
	
	public static void pushIntegerToFactor(BigInteger big) throws IOException{
		for(Factorer f: factorers){
			PrintWriter print=new PrintWriter(f.getSocket().getOutputStream());
			print.print("FACTOR" + big);
		}
	}
}
