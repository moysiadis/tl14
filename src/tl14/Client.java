package tl14;

public class Client {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MainFrame mf=new MainFrame();
		
		Game gm=new Game(1, 4345, "localhost", "George", "", mf);
		gm.Connect();
		gm.receiveMsg(0);
		gm.closeConnection();
		System.exit(0);
	}

}
