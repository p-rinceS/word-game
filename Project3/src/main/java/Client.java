import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javafx.application.Platform;



public class Client extends Thread{

	
	Socket socketClient;
	
	ObjectOutputStream out;
	ObjectInputStream in;
	
	List<Integer> letterPos = new ArrayList<Integer>();
	int wordSize;
	
	int portID;
	
	private Consumer<Serializable> callback;
	
	Client(Consumer<Serializable> call){
	
		callback = call;
	}
	
	
	
	public void setPortID(int num) {
		
		portID = num;
	}
	
	public int getPortID() {

		return portID;
	}
	
	
	public void run() {
		
		
		try {
		socketClient= new Socket("127.0.0.1", portID);
	    out = new ObjectOutputStream(socketClient.getOutputStream());
	    in = new ObjectInputStream(socketClient.getInputStream());
	    socketClient.setTcpNoDelay(true);
		}
		catch(Exception e) {
			System.out.println("Port not found");
		}
		
		while(true) {
			 
			try {
				
			Object data = in.readObject();
			
			if (data instanceof String) {
			
			String message = (String) data;
			
			if (message.startsWith("SIZE: ")) {
			    String sizeString = message.substring(6); // Extract the substring after "SIZE: "
			    int size = Integer.parseInt(sizeString);
			    setWordSize(size);
			}
			
			callback.accept(message);
			}
			
			else if (data instanceof List) {

	    	List<Integer> letterPositions = (List<Integer>) data;
	    	this.letterPos = letterPositions;	    	
	    	
			callback.accept((Serializable) letterPositions);
			
	
			}
			
			}
				
			catch(Exception e) {
				break;
			}
    
		}
	
    }
	
	
	
	
    public void close() {
        try {
            // Close the resources (socket, streams)
            out.close();
            in.close();
            socketClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	public void sendLetterGuess(String data) {
		
		try {
			out.writeObject(data);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	public void sendCategory(Categories category) {
		
		try {
			
			out.writeObject(category);
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
	public int getWordSize() {
		return wordSize;
	};
	public void setWordSize(int num) {
		
		this.wordSize = num;
	}
	
	public void sendWinStats(WinStats stats) {
		try {
			out.writeObject(stats);
			
		}catch(IOException e){
			
			e.printStackTrace();
			
		}
		
	}
	
	
	public List<Integer> getLetterPositions(){
		return letterPos;
	};


}
