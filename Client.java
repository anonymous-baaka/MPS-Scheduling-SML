import java.net.*;
import java.io.*;

public class Client {
	public static void main(String[] args) throws Exception {
	try{
		Socket socket=new Socket("127.0.0.1",8888);
		DataInputStream inStream=new DataInputStream(socket.getInputStream());
		DataOutputStream outStream=new DataOutputStream(socket.getOutputStream());
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		String clientMessage="",serverMessage="",controllerMessage="";

		//while(!clientMessage.equals("bye"))
		{
			System.out.println("Welcome!!\nPlease wait...");
			controllerMessage=inStream.readUTF();
			System.out.println(controllerMessage);

			controllerMessage=inStream.readUTF();
			System.out.println(controllerMessage);
		}
		outStream.close();
    	outStream.close();
    	socket.close();
	}catch(Exception e){
		System.out.println(e);
	}
	}
}