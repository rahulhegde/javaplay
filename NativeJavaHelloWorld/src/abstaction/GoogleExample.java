package abstaction;

public class GoogleExample {

	private boolean isPrime(int number) {
		boolean isPrime = true;
		for (int i = 2; i < number; i++) {
			if (number % i == 0) {
				isPrime = false;
				break;
			}
		}
		return isPrime;
	}

	private String FindNextPrimeNumberIndexList(int searchIndex) {
		final int startIndex = 0;
		final int endIndex = 10000;

		// 2 3 5 7 11 13 17

		boolean breakLoop = true;
		int i = 2;
		int counter = 0;
		StringBuilder primeNumberString = new StringBuilder();
		while (breakLoop) {
			boolean isPrime = isPrime(i);
			if (isPrime) {
				// System.out.println("prime found: " + i + ", counter: " + counter + ",
				// searchIndex: " + searchIndex);
				counter++;
				if (counter > searchIndex) {

					primeNumberString.append(Integer.toString(i));
					if (primeNumberString.toString().length() >= 5) {
						breakLoop = false;
					}
					// System.out.println("prime number string: " + primeNumberString.toString());
				}

				if (counter > endIndex) {
					breakLoop = false;
				}

			}
			i++;
		}
		System.out.println("search index: " + searchIndex + ", example play " + primeNumberString.toString().substring(0, 5));
		return primeNumberString.toString().substring(0, 5);
	}
	
	private void printPrimeNumber() {
		for (int i = 2; i < 10000; i++) {
			if (isPrime(i)) {
				System.out.println(i);
			}
		}
	}

	public void GoogleExmaplePlay() {
//		FindNextPrimeNumberIndexList(0);
//		FindNextPrimeNumberIndexList(3);
//		FindNextPrimeNumberIndexList(22);
		FindNextPrimeNumberIndexList(3671);

		
		//printPrimeNumber();
	}
}
