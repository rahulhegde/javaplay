package createdelete;

import java.lang.ref.Cleaner;

public class HotelRoom implements AutoCloseable {
	private int isDirty;
	private int roomNumber;
	private RoomCleaner roomCleaner;

	// use of AutoCloseable with Cleaner is prefered approach.
	// cleaner helper - create a static cleaner instance that spins a daemon thread
	// All cleanable procedure are added to the cleaner instance which is triggered upon GC calls finalizer.
	// Any exception in cleanable procedure does not impact cleaner call to next cleanable
	private static Cleaner cleaner = Cleaner.create();
	private final Cleaner.Cleanable cleanable;

	HotelRoom(int roomNumber) {
		System.out.println("HotelRoom is dirty - constructor");
		isDirty = 1;
		this.roomNumber = roomNumber;
		roomCleaner = new RoomCleaner(this);
		cleanable = cleaner.register(this, roomCleaner);
	}

	// cleanable runnable (RoomCleaner) should be static else RoomCleaner instance been part of HotelRoomer class 
	// will automatically hold a reference of HotemRoom. Thus hotelRoom will never going out of scope and GCed.
	// This is classic case of memory leak.
	static private class RoomCleaner implements Runnable {
		HotelRoom hotelRoom;
		RoomCleaner(HotelRoom hr) {
			hotelRoom = hr;
			//System.out.println("HotelRoom - RoomCleaner constructor for room number: " + roomNumber);
		}

		public void run() {
			// run the clean job
			hotelRoom.isDirty = 0;
			System.out.println("HotelRoom - RoomCleaner done for room number: " + hotelRoom.roomNumber + ", threadid: "
					+ Thread.currentThread().getId());
//			try {
//				throw new AssertionError("test exception");
//			} catch (Exception ex) {
//				System.out.println("exception message caught - " + ex.getMessage());
//			}
			System.out.println("exception caught and continuing..");
		}
	}

	@Override
	public void close() {
		// autocloseable is not required when cleaner is used as the same will be invoked when 
		// object has phantom reference.
		cleanable.clean();
	}

}
