package exceptioner;

public class ResourceExhaustedException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	private final long resourceRequired;
	
	private static final int RESOURCE_REQUIRED = 10;
	
	public ResourceExhaustedException(long resourceRequired) {
		super("resource limitation reached");
		this.resourceRequired = resourceRequired;
	}
	
	public String getDescription() {
		return String.format("Maximum resource required: %s hits limit with provided resource %s", RESOURCE_REQUIRED, resourceRequired);
	}
}
