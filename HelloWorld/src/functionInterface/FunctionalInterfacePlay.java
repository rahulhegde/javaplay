package functionInterface;

public class FunctionalInterfacePlay {
	
	void FunctionalInterface_ConvertStringToInt( ) {
		ConvertDataTypeHelper<String, Integer> helperObj = (from) -> Integer.valueOf(from);
		Integer intValue = helperObj.Convert("2");
		System.out.println("integer: " + intValue);
	}
	
	public void FunctionalInterfacePlay_Entry() {
		FunctionalInterface_ConvertStringToInt();
	}

}
