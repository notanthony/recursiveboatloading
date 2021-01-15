import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

class Main {

	static int bestK;
	static int n;
	static boolean currX[];
	static boolean bestX[];
	static HashMap<Visit, Boolean> visited;
	static ArrayList<Integer> length;

	public static void main(String args[]) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		try {
			int numberOfCases = Integer.parseInt(reader.readLine());
			for (int y = 0; y < numberOfCases; y++) {
				reader.readLine();

				int sideLength = Integer.parseInt(reader.readLine()) * 100;
				int carCount = 0;
				length = new ArrayList<>();

				Integer carLength;
				while ((carLength = Integer.parseInt(reader.readLine())) != 0) {
					carCount++;
					length.add(carLength);
				}

				n = carCount;
				visited = new HashMap<>(sideLength*n/50);
				currX = new boolean[carCount];
				bestX = new boolean[carCount];
				bestK = 0;
				BacktrackSolve(0, sideLength, sideLength);

				System.out.println(bestK);
				for (int x = 0; x < bestK; x++) {
					if (bestX[x]) {
						System.out.println("port");
					} else {
						System.out.println("starboard");
					}
				}
				if (y != (numberOfCases - 1)) {
					System.out.println();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	static class Visit {
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + cars;
			result = prime * result + length;
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			Visit other = (Visit) obj;
			if (cars != other.cars)
				return false;
			if (length != other.length)
				return false;
			return true;
		}
		private int cars;
		private int length;
		Visit (int cars, int length) {
			this.cars = cars;
			this.length = length;
		}
		
		
	}

	private static void BacktrackSolve(int currK, int ls, int rs) {
		boolean left = false, right = false;
		if (currK < n) {
			Integer currL = length.get(currK);
			int newLS = ls - currL;
			int currKInc = currK +1;
			Visit lp = new Visit(newLS,currKInc);
			left = newLS >= 0 && !visited.containsKey(lp);
			if (left) {
				currX[currK] = true;
				BacktrackSolve(currKInc, newLS, rs);
				visited.put(lp, true);
			}
			int newRS = rs - currL;
			Visit rp = new Visit(ls,currKInc);
			right = (newRS) >= 0 && !visited.containsKey(rp);
			if (right) {
				currX[currK] = false;
				BacktrackSolve(currKInc, ls, newRS);
				visited.put(rp, true);
			}
		}
		if (currK > bestK && (currK >= n || (!left && !right))) {
			bestK = currK;
			System.arraycopy(currX, 0, bestX, 0, currK);
		}
	}
}