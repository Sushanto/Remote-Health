package RHCrypto;

/**
* RHCexeption is the custom exception class for RHCrypto
*/

public class RHCException extends Exception {

	static final long serialVersionUID = 366905L;
	
	/**
	* Empty constructor (Don't use this one)
	*/
	public RHCException () {}
	
	/**
	* The constructor which constructs the Eception with the given message
	* @param message The info message of this Excetion object
	*/
	public RHCException (String message) {
		super(message);
	}
	
	/**
	* Returns a descritpion of the exception
	* @return String containign the description of the message
	*/
	@Override
	public String toString() {
		return ("RHCException: " + this.getMessage());
	}
	
}
