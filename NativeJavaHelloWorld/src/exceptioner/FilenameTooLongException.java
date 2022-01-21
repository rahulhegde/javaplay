package exceptioner;

public class FilenameTooLongException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4075394356488815898L;
	
	private final String filename;
	private final int filenameLength;
	
	public FilenameTooLongException(String name, int length) {
		super("filename is too long");
		filename = name;
		filenameLength = length;
	}
	
	public String getDescription() {
		return String.format("filename: %s with length %s exceed maximum length",filename,filenameLength);
	}

}
