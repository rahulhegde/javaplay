package abstaction;


public class PrimitiveHolder<Boolean> {
	private Boolean value;

	public PrimitiveHolder(Boolean value) {
		super();
		this.value = value;
		System.out.println("PrimitiveHolder constructor " + this.value.getClass());
	}

	public Boolean getValue() {
		return value;
	}

	public void setValue(Boolean value) {
		if (value != this.value) {
			System.out.println("** Both the values are not same objects " + value + " " +  this.value);
			
		} else {
			System.out.println("** Both the values are same objects " + value + " " +  this.value);

		}
		System.out.println("PrimitiveHolder setValue - 1 " + System.identityHashCode(this.value));
		this.value = value;
		System.out.println("PrimitiveHolder setValue - 2 " + System.identityHashCode(this.value));
	}
	
	
}
