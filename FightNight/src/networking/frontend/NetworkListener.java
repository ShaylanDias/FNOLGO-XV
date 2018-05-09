package networking.frontend;

/**
 *
 * Interface for a class that will receive messages from the server 
 * 
 * @author john_shelby
 *
 */
public interface NetworkListener {

	/**
	 * Called when a successful connection with a server is made.
	 * 
	 * @param nm The NetworkMessager that this client program should use to communicate messages to other connected clients.
	 */
	public void connectedToServer(NetworkMessenger nm);
	
	/**
	 * Called when data is received from other connected clients, or from the server itself.
	 * 
	 * @param ndo The data that was received.
	 */
	public void networkMessageReceived(NetworkDataObject ndo);
	
}
