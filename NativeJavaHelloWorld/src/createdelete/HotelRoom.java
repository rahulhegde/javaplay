package createdelete;

import java.lang.ref.Cleaner;


public class HotelRoom implements AutoCloseable {
	private int isDirty;
	private int roomNumber;
	private RoomCleaner roomCleaner; 

	// cleaner helper 
	private static Cleaner cleaner = Cleaner.create();
	private final Cleaner.Cleanable cleanable;
	
	HotelRoom(int roomNumber) {
		System.out.println("HotelRoom is dirty - constructor");
		isDirty = 1;
		this.roomNumber = roomNumber;
		roomCleaner = new RoomCleaner();
		cleanable = cleaner.register(this, roomCleaner);
	}
	
	private class RoomCleaner implements Runnable {
		
		RoomCleaner() {
			System.out.println("HotelRoom - RoomCleaner constructor for room number: " + roomNumber);
		}
		
		public void run() {
			// run the clean job
			isDirty = 0;
			System.out.println("HotelRoom - RoomCleaner done for room number: " + roomNumber + ", threadid: " + Thread.currentThread().getId());
			try {
				throw new AssertionError("test exception");
			} catch (Exception ex) {
				System.out.println("exception message caught - " + ex.getMessage());
			}
			System.out.println("exception caught and continuing..");
		}
	}

	@Override
	public void close() {
		// autocloseable is not required when cleaner is used
		// as the same registered with GC.
		cleanable.clean();
	}
	
	
}
