import java.util.*;
import java.net.*;
public class DNSServer {
	private static String DNS = "DNS";
	private static String defaultDomain = "wiu.ed";
	private static String WWW = "www.";
	private static DatagramPacket sendDataPacket=null;
	public static void main(String[] args) throws SocketException {
		
		int portNum = 0;
		if(args.length!=0)
		{
			portNum = Integer.parseInt(args[0]);
		}
		else
		{
			System.out.println("Please Specify the port on which the server has to run");
			return;
		}
		DatagramSocket serverSocket = new DatagramSocket(portNum);
		
		while(true)
		{
			try
			{
				String dataPacket = null;
				byte[] receivepacket = new byte[1024];
	            DatagramPacket receivePacket = new DatagramPacket(receivepacket, receivepacket.length);
	            serverSocket.receive(receivePacket);
	            dataPacket = new String(receivePacket.getData());
	            if(!dataPacket.startsWith(DNS))
	            {
	            	String respText = "Data Packet Validation Failed, Ignoring the Packet ...";
	            	System.out.println(respText);
	            	sendDataPacket = new DatagramPacket(respText.getBytes(),respText.getBytes().length);
	            	serverSocket.send(sendDataPacket);
	            	continue;
	            }
	            else
	            {
	            	String [] dataElements = dataPacket.split(" ");
	            	String url = dataElements[1];
	            	boolean qualified = false;
	            	if(isValid(url))
	            	{
	            		qualified = true;
	            	}
	            	else
	            	{
	            		if(!url.contains("."))
	            		{
	            			url+=defaultDomain;
	            		}
	            		else
	            		{
	            			url = WWW+url;
	            		}
	            	}
	            	
	            }
	            
			}
			catch (Exception e) 
			{
				// TODO: handle exception
			}
		}
		
		
		
		
		
		
	}
	
	public static String getDomainName(String url) throws URISyntaxException {
	    URI uri = new URI(url);
	    String domain = uri.getHost();
	    return domain.startsWith(WWW) ? domain.substring(4) : defaultDomain;
	}
	
	
	public static boolean isValid(String url)
    {
        /* Try creating a valid URL */
        try {
            new URL(url).toURI();
            return true;
        }
         
        // If there was an Exception
        // while creating URL object
        catch (Exception e) {
            return false;
        }
    }

}
