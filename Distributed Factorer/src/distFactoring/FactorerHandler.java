package distFactoring;

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
				Scanner scan=new Scanner(f.getSocket().getInputStream());
				
				while(scan.hasNext()){
					Server.found(scan.nextBigInteger());
					close();
				}
				
			}
			
			if(done){
				for(Factor fe: factorers){
					PrintWriter out=new PrintWriter(fe.getSocket().getOutPutStream());
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
	
	public static void pushIntegerToFactor(BigInteger big){
		for(Factorer f: factorers){
			PrintWriter print=new PrintWriter(f.getSocket.getOutPutStream());
			print.print("FACTOR" + big);
		}
	}
}
