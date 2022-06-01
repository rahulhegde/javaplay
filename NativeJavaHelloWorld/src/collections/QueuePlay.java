package collections;

import java.util.PriorityQueue;

public class QueuePlay {
	private static class Student implements Comparable<Student> {
		int id;
		double score;
		String name;
		
		Student(int id, double score, String name) {
			this.id = id;
			this.score = score;
			this.name = name;
		}
		
		@Override
		public int compareTo(Student o) {
			int retValue = 0;
			if (this.score > o.score) {
				retValue = 1;
			} else if (this.score < o.score) {
				retValue = -1;
			} else {
				System.out.println("equals comes here");
			}
			return retValue;
		}	
		
		@Override
		public String toString() {
			return "Student [id=" + id + ", score=" + score + ", name=" + name + "]";
		}
	}
	
	private void TestPriorityQueue() {
		PriorityQueue<Student> queue = new PriorityQueue<>();
		queue.add(new Student(1, 3.75, "A"));
		queue.add(new Student(2, 3.8, "B"));
		queue.add(new Student(3, 3.7, "C"));
		queue.add(new Student(4, 3.85, "D"));
		queue.add(new Student(5, 3.9, "E"));
		queue.add(new Student(6, 3.6, "F"));
		queue.add(new Student(7, 3.95, "G"));
		queue.add(new Student(8, 3.95, "H"));
		
		int size = queue.size();
		for (int i = 0; i < size; i++) {
			System.out.println(queue.poll());
		}
	}
	
	public void TestQueuePlay() {
		TestPriorityQueue();
	}
}
