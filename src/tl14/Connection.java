package tl14;
import java.io.*;
import java.net.*;		

public class Connection {
	
	public int serverPortNo;
	public String serverIp;
	private Socket connectSock;
	private BufferedReader in;
	private PrintWriter out;
	
	public Connection(int port,String ip){
		serverPortNo=port;
		serverIp=ip;
	}
	
	private void connectToserver(){
		InetAddress serverAddr;
		
		try {
			serverAddr=InetAddress.getByName(serverIp);
			connectSock = new Socket(serverAddr,serverPortNo);
			out=new PrintWriter(connectSock.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(connectSock.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/*private convertIp{String IPADDRESS_PATTERN = 
    "(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";

Pattern pattern = Pattern.compile(IPADDRESS_PATTERN);
Matcher matcher = pattern.matcher(ipString);
    if (matcher.find()) {
        return matcher.group();
    }
    else{
        return "0.0.0.0";
    }}*/
}
