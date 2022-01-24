import java.util.ArrayList;
import java.util.List;

public class GCPlay {
	void Test_GCInvoke() {
		
		//add following to VM Args: -verbose:gc
		List<List<Integer>> arrayListStore = new ArrayList<List<Integer>>(100);
		for (int i =0; i < 100; i++) {
			arrayListStore.add(i, new ArrayList<>(1000000));
			arrayListStore.add(i, null);
		}
		
	}
	
	void GCPlayTest( ) {
		Test_GCInvoke();
	}
}
