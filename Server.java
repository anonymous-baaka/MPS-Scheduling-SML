// A Java program for a Server
import java.net.*;
import java.io.*;

class MyServerClientThread extends Thread{
	static int delays[]={10,5,8};
	int clientID;
	int result;
	int tokenNumber;
    int windowNumber;
	String timestamp;
	Socket s_clientSocket;

	MyServerClientThread(Socket _clientSocket, int _clientID){
		s_clientSocket=_clientSocket;
		clientID=_clientID;
	}
	@Override
	public void run(){
		try
		{
			DataInputStream inStream = new DataInputStream(s_clientSocket.getInputStream());
			DataOutputStream outStream = new DataOutputStream(s_clientSocket.getOutputStream());
			String clientMessage[];
			String serverMessage="";
			//
			System.out.println(" >> " + "Client No:" + clientID + " assigned to thread: "+Thread.currentThread().getId());
			serverMessage="Client No:" + clientID + " assigned to thread: "+Thread.currentThread().getId();
			outStream.writeUTF(serverMessage);
			outStream.flush();

			//while(!clientMessage.equals("bye"))
			{
				System.out.println("\nHERE1");
				String tmpStr=inStream.readUTF().toString();
				System.out.println("tm,pstr= "+tmpStr);

				clientMessage=tmpStr.split("#");
				//
				for(String s:clientMessage)
				{
					System.out.println(s);
				}

				//
				System.out.println("\nHERE2");
				tokenNumber=Integer.parseInt(clientMessage[0]);
				System.out.println("\nHERE3");
				timestamp=clientMessage[2];
				System.out.println("\nHERE4");
				windowNumber=Integer.parseInt(clientMessage[3]);
				
				System.out.println("\nHERE5");
				//clientMessage="#"+tokenNumber+"#"+clientID+"#"+timeStamp+windowNumber;
				//System.out.println("From Client-" +clientID+ ": Number is :"+clientMessage);
				Thread.sleep(getDelay(windowNumber));
				//result = Integer.parseInt(clientMessage.split(" ")[0]) + Integer.parseInt(clientMessage.split(" ")[1]);
				
				//serverMessage="From Server to Client-" + clientID + " sum is " +result;
				//System.out.println("Result= "+result+"\n Sending Result to controller...");
				serverMessage="Token #"+tokenNumber+" serviced successuflly!";
				outStream.writeUTF(serverMessage);
				outStream.flush();
				//System.out.println("Result sent!!");
			}
			inStream.close();
			outStream.close();
			s_clientSocket.close();
		}catch(Exception ex){
			System.out.println(ex);
		}finally{
			System.out.println("Client -" + clientID + " exit!! ");
		}
	}

	int getDelay(int i)
	{
		return delays[i];
	}
}
public class Server
{
	public static void main(String[] args) throws Exception
	{
		try{
			ServerSocket server=new ServerSocket(8889);
			int clientID=0;
      		System.out.println("Server Started ....");

			while(true){
			Socket serverClient=server.accept();  //server accept the client connection request
			//System.out.println(" >> " + "Client No:" + clientID + " started!");
			MyServerClientThread sct = new MyServerClientThread(serverClient,clientID); //send  the request to a separate thread
			sct.start();
			clientID++;
			}
		}catch(Exception e){
			System.out.println(e);
		  }
	}
}
