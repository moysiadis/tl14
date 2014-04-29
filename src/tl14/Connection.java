package tl14;
import java.io.*;
import java.net.*;		

public class Connection {
	
	public int serverPortNo;
	public String serverIp;
	private Socket connectSock;
	private BufferedReader in;
	private PrintWriter out;
	
	public Connection(){
	}
	
	public void connectToserver() throws IOException{
		InetAddress serverAddr;
		
		try {
			serverAddr=InetAddress.getByName(serverIp);
			connectSock = new Socket(serverAddr,serverPortNo);
			out=new PrintWriter(connectSock.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(connectSock.getInputStream()));
		} catch (IOException e) {
			if(connectSock==null)
				connectSock.close();
			
			throw e;
			//παραθυρο unable to connect
			
		}
		
	}
	
	public void setConnectionDetails(int port,String ip){
		serverPortNo=port;
		serverIp=ip;
	}
	
	public int Send(String out){
		if (this.out != null && !this.out.checkError()) {
            this.out.print(out);
            this.out.flush();
            
            return 1;
            //i=receive();	            
           }
		return 2;
	}
	
	public String Receive() throws IOException{
		String serverMsg="";
		boolean done=false;
		
		while(!done){
			try {
				serverMsg.concat(in.readLine());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				
				serverMsg="fail";
				throw e;
			}
		}
		return serverMsg;
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
