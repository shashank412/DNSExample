import java.io.*;
import java.net.*;
import java.util.*;
public class DNSClient {

	private static String DNS = "DNS";
	
	public static void main(String [] args) throws UnknownHostException
	{
		String serverName = args[0];
		int portNumber = Integer.parseInt(args[1]);
		String url = args[2];
		
		InetAddress ipAddress = InetAddress.getByName(serverName);
		String sendDataPacket = null;
		String timeStamp = System.currentTimeMillis()+"";
		
		try
		{
			sendDataPacket= DNS+" "+url+" "+timeStamp;
			byte [] sendData = sendDataPacket.getBytes();
			DatagramSocket socket = new DatagramSocket();
			DatagramPacket dnsReqPacket = new DatagramPacket(sendData, sendData.length, ipAddress, portNumber);
			socket.send(dnsReqPacket);
			
			byte[] receiveData = new byte[1024];
			
			DatagramPacket dnsRespPacket = new DatagramPacket(receiveData,receiveData.length);
			
			socket.receive(dnsRespPacket);
			
			String recvDataPacket = new String(dnsRespPacket.getData(),0,dnsRespPacket.getLength());
			
			if(recvDataPacket.length()!=0)
			{
				String [] arr = recvDataPacket.split(" ");
				System.out.println("Host Name : "+arr[0]);
				System.out.println("Resolved IP Address : "+arr[1]);
				System.out.println("Processed in : "+Math.abs(Long.parseLong(timeStamp)-Long.parseLong(arr[2])));
				
			}
			else 
			{
				System.out.println("Cannot resolve the Host Name");
			}
			
			socket.close();
			
		}catch (Exception e) {
			System.out.println("Exception while creating and sending the data packet "+e.getMessage());
		}
		
		
	}
}
