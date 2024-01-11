import java.io.IOException;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javafx.application.Platform;
import javafx.scene.control.ListView;
/*
 * Clicker: A: I really get it    B: No idea what you are talking about
 * C: kind of following
 */

public class Server{

	int count = 1;	
	ArrayList<ClientThread> clients = new ArrayList<ClientThread>();
	TheServer server;
	private Consumer<Serializable> callback;
	wordChoiceLogic wordChoice = new wordChoiceLogic();
	
	Server(int port, Consumer<Serializable> call){
	
		callback = call;
		server = new TheServer(port);
		server.start();
	}
	

	
	
	public class TheServer extends Thread{
		
		
		private int portID = 0;
		
		TheServer(int pID){
			
			portID = pID;
			
		}

		public void run() {
		
			try(ServerSocket mysocket = new ServerSocket(portID);){
		        mysocket.setReuseAddress(true);

		    System.out.println("Word Game! (Server is listening for player)");
		  
			
		    while(true) {
		
				ClientThread c = new ClientThread(mysocket.accept(), count);
				callback.accept("(A player has connected!)" + "client #" + count);
				clients.add(c);
				c.start();
				
				count++;
				
			    }
			}//end of try
				catch(Exception e) {
					callback.accept("Server socket did not launch");
				}
			}//end of while
			
		}
	
	
	

		class ClientThread extends Thread{
			
		
			Socket connection;
			int count;
			ObjectInputStream in;
			ObjectOutputStream out;
			String clientName;
			
			ClientThread(Socket s, int count){
				this.connection = s;
				this.count = count;	
		        this.clientName = "Client " + count;

			}
			
			public void updateClients(String message) {
				for(int i = 0; i < clients.size(); i++) {
					ClientThread t = clients.get(i);
					try {
					 t.out.writeObject(message);
					}
					catch(Exception e) {	
				        e.printStackTrace();
					}
				}
			}
			
			public void updateClient(ClientThread client, String message) {
			    try {
			        client.out.writeObject(message);
			    } catch (Exception e) {
			        e.printStackTrace();
			    }
			}
			
			public void run(){
					
				try {
					in = new ObjectInputStream(connection.getInputStream());
					out = new ObjectOutputStream(connection.getOutputStream());
					connection.setTcpNoDelay(true);	
				}
				catch(Exception e) {
					System.out.println("Streams not open");
				}
				
				updateClients("New player on server: " + clientName);
					
				 while(true) {
					    try {
					    	Object data = in.readObject();
					    	
					    	System.out.println(data.toString());
					    	
					    	
					    	if (data instanceof Categories) {
					    	
					    		Categories recievedCategories = (Categories)data;
					    		
					    		updateClients("Categories Recieved: " + recievedCategories.getCategory());
					    		
					    		
					    		wordChoice.setChosenWord(wordChoice.chooseWord(recievedCategories.getCategory()));
					    		updateClient(clients.get(count-1), "SIZE: " + wordChoice.getChosenWord().length());
					    		
					    		callback.accept("word chosen: " + wordChoice.getChosenWord());
					    		callback.accept("word chosen for " + clientName);

					    	}
					    	
					    	if (data instanceof String) {
						    	
					    		String dataString = (String) data;
					    		
					    		// TODO:
					    		// processGuess
					    		
				
					    		
					    			// new CLASS called processGuessLogic
					    			// compares guess with word
					    			// if guess is in word
					    			// return array of indices of where in word
					    			// if array is empty, letter not found, if array is filled, letter found at those positions
					    		
					    		
					    		
					    		updateClient(clients.get(count-1), "Guess recieved: \"" + dataString + "\"");
					    		
					    		// send seialized object of processWord, (array of indices).
					    		clients.get(count-1).out.writeObject(processWord(dataString));
					    	}
					    	
					    	
					    	if (data instanceof WinStats) {
					    		
					    		WinStats recievedWinStatistics = (WinStats)data;
					    		
					    		updateClients(clientName + " has won!");
					    		callback.accept(clientName + " has won with " + recievedWinStatistics.getGuessesLeft() + " unused guesses!");

					    		
					    		
					    	}
					    	
					    	
					    	
					    	
					    						    	
					    }
					    catch(Exception e) {
					        e.printStackTrace();
					    	callback.accept("OOOOPPs...Something wrong with the socket from client: " + count + "....closing down!");
					    	updateClients("Client #"+count+" has left the server!");
//					    	clients.remove(this);
					    	break;
					    }
					}
				}//end of run
			
			
			
			
		    public String getClientName() {
		        return clientName;
		    }
		    
		    public List<Integer> processWord(String guess) {
		    	
		    	ProcessWord pw = new ProcessWord(null,null);
		    
	    		pw.setGuess(guess.toLowerCase());
	    		pw.setWord(wordChoice.getChosenWord());
	    		    		
	    		return (pw.getLetterPositions());
		    	
		    }

			
			
		}//end of client thread
}


	
	

	
