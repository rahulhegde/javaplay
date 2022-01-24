package enums;

import java.util.HashMap;
import java.util.Map;

public enum Current {
	ON(1) {
		void specialHandling() {
			System.out.println("constant-specific method implementations for " + this);
		}
	},
	OFF(2) {
		void specialHandling() {
			System.out.println("constant-specific method implementations for " + this);
		}
	},
	INVALID(4) {
		void specialHandling() {
			System.out.println("constant-specific method implementations for " + this);
		}
	};

	private final int label;
	

	// for enumeration class only, every enumerator is defined as public static and hence created first by the 
	// compiler. the colormap which is another static reference is not yet created and hence will not be accessible
	// inside the constructor of the Current (gives compiler error). 
	// Suggestion is to write static initializer.
    static final Map<String,Current> colorMap = new HashMap<String,Current>();
    static {
    	for (Current c : Current.values()) {
    		colorMap.put(c.toString(), c);
    		System.out.println("static initializer block");
    	}
    }

    private Current(int label) {
		this.label = label;
		System.out.println("inside constructor - " + this);

		// compiler error if enabled.
		//colorMap.put("1", this);
	}
	
	public boolean isSame(int state) {
		return this.label == state;
	}

	// constant-specific method implementations:
	// each constant can define specific handling and must define implementation
	abstract void specialHandling();
	
	// common constant specific or instance specific implementation - 
	void specialHandlingPerConstant() {
		switch (this) {
		case ON:
			System.out.println("specialHandlingPerConstant - ON");
			break;
		case OFF:
			System.out.println("specialHandlingPerConstant - OFF");
			break;
		case INVALID:
		default:
			System.out.println("specialHandlingPerConstant - INVALID");
			break;
		}
	}
	
	// implementation is not enum constant specific, static or common implementation across enums
	// to make in instance specific, pass the Enum to the API
	static void printCurrentStateEnums() {
		for(Current current : Current.values()) {
			System.out.println("specialHandlingAcrossConstantUsingStaticMethod - " + current);
		}
	}
}