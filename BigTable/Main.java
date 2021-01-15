import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

class Main {

	static int bestK;
	static int n;
	static boolean currX[];
	static boolean bestX[];
	static boolean visited[][];
	static ArrayList<Integer> length;

	public static void main(String args[]) {
		
		/*
		for file inputs
		try {
			reader = new BufferedReader(new FileReader("file name");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		*/
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
				visited = new boolean[carCount + 1][sideLength + 1];
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

	private static void BacktrackSolve(int currK, int ls, int rs) {
		boolean left = false, right = false;
		if (currK < n) {
			Integer currL = length.get(currK);
			int newLS = ls - currL;
			left = newLS >= 0 && !visited[currK + 1][newLS];
			if (left) {
				currX[currK] = true;
				BacktrackSolve(currK + 1, newLS, rs);
				visited[currK + 1][newLS] = true;
			}
			right = (rs - currL) >= 0 && !visited[currK + 1][ls];
			if (right) {
				currX[currK] = false;
				BacktrackSolve(currK + 1, ls, rs - currL);
				visited[currK + 1][ls] = true;
			}
		}
		if (currK > bestK && (currK >= n || (!left && !right))) {
			bestK = currK;
			System.arraycopy(currX, 0, bestX, 0, currK);
		}

	}

}