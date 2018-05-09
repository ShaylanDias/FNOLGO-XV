package networking.frontend;

import java.net.InetAddress;

/**
 * 
 * Interface for something that sends a message across the server
 * 
 * @author john_shelby
 *
 */
public interface NetworkMessenger {

	/**
	 * Sends data to other connected clients.
	 * 
	 * @param messageType The type of message being sent. For client programs, this should always be NetworkDataObject.MESSAGE.
	 * @param message Any number of objects containing data to be sent.
	 */
	public void sendMessage(String messageType, Object... message);
	
	/**
	 * 
	 * Gets the InetAddress of this NetworkMessenger
	 * 
	 * @return The InetAddress of this NetworkMessenger
	 */
	public InetAddress getHost();
	
}
