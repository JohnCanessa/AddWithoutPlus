import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

/*
 * Add two integers without a plus.
 */
public class Solution {

	// **** global sum map ****
	static HashMap<Integer, ArrayList<Integer>> sm;
	
	/*
	 * Build sum map.
	 */
	static void buildSumMap() {

		// **** create the sum map ****
		sm = new HashMap<Integer, ArrayList<Integer>>();

		// *** populate the hash map ****
		int a 	= 0;
		int b 	= 0;
		int c 	= 0;
		for (int key = 0; key < 8; key++) {
			
			// **** set a, b and c ****
			a = key & 0x01;
			b = (key >> 1) & 0x01;
			if (key <= 3)
				c = 0;
			else
				c = 1;

//			// ???? ????
//			System.out.println("buildSumMap <<< key: " + key + " c: " + c + " b: " + b + " a: " + a);

			// **** set sum and carry ****
			ArrayList<Integer> value = new ArrayList<Integer>();
			value.add((c + a + b) & 0x01);			// [0] sum
			value.add(((c + a + b) >> 1) & 0x01);	// [1] carry
			
			// **** save sum and carry ****
			sm.put(key, value);
		}
	}
	
	/*
	 * Sum values without using a plus sign.
	 */
	static int sumValues(int aa, int bb) {
		
		// **** simple checks ****
		if ((aa == 0) && (bb == 0))
			return 0;
		if (aa == 0)
			return bb;
		if (bb == 0)
			return aa;
			
		// **** for starters ****
		int sum = 0;
		
		// **** loop until no more bits are left to process ****
		int a 			= 0;
		int b 			= 0;
		int c 			= 0;
		int shiftCnt	= 0;

		while (!((aa == 0) && (bb == 0))) {

			// **** extract a and b ****
			a = aa & 0x01;
			b = bb & 0x01;
			
//			// ???? ????
//			System.out.println("sumValues <<< c: " + c + " b: " + b + " a: " + a);
						
			// **** build key ****
			int key = c << 2 | b << 1 | a;
			
//			// ???? ????
//			System.out.println("sumValues <<< key: " + key);
			
			// **** look up key in hash map to get sum and carry ****
			ArrayList<Integer> value = sm.get(key);
			
//			// ???? ????
//			System.out.println("sumValues <<< value: " + value.toString());
			
			// **** update sum ****
			sum <<= 1;
			sum |= value.get(0);
			
			// **** update carry ****
			c = value.get(1);
			
//			// ???? ????
//			System.out.println("sumValues <<< sum: " + sum + " c: " + c);
			
			// **** to process next bits ****
			aa >>= 1;
			bb >>= 1;
			
			// **** count this shift ****
			shiftCnt++;
			
//			// ???? ????
//			System.out.println("sumValues <<< aa: " + aa + " bb: " + bb + " shiftCnt: " + shiftCnt + "\n");
		}

//		// ???? ????
//		System.out.println("sumValues <<< sum: " + sum + " c: " + c);

		// **** ****
		sum <<= 1;
		sum |= c;
	
//		// ???? ????
//		System.out.println("sumValues <<< sum: " + sum);
		
		// **** invert bits ****
		int temp = sum;
		sum = 0;
		for ( ; shiftCnt >= 0; shiftCnt--) {
			sum <<= 1;
			sum |= temp & 0x01;
			temp >>= 1;
		}
				
		// **** return sum ****
		return sum;
	}
	
	
	/*
	 * Simple way to add two values.
	 * Recursive method.
	 * Stop recursion when there is nothing to carry.
	 * Need to know this method.
	 */
	static int addValues(int ss, int cc) {
		
		// **** stop when there is nothing to carry ****
		if (cc == 0) 
			return ss;
		
		// **** add without carry (XOR) ****
		int sum = ss ^ cc;
		
		// **** carry but do not add (shifted AND) ****
		int carry = (ss & cc) << 1;
		
		// **** add the sum and carry (must be in this order) ****
		return addValues(sum, carry);
	}
	
	
	/*
	 * Test scaffolding.
	 */
	public static void main(String[] args) {

		
//		// **** open scanner ****
//		Scanner sc = new Scanner(System.in);
//		
//		// **** read values to sum ****
//		int a = sc.nextInt();
//		int b = sc.nextInt();
//
//		// **** close scanner ****
//		sc.close();

			
		// **** generate two random integers within the specified range ****
		Random rand = new Random(System.currentTimeMillis());
		int a = Math.abs(rand.nextInt() % 100000);
		int b = Math.abs(rand.nextInt() % 100000);
		
		
		// ???? ????
		System.out.println("main <<< a: " + a + " b: " + b);
		
		// **** for future reference ****
		int s = a + b;
		
//		// ???? ????
//		System.out.println("main <<< s: " + s);
		
		// **** build sum map ****
		buildSumMap();
		
//		// ???? ????
//		System.out.println("main <<< sm: " + sm.toString() + "\n");

		
		// **** ****
		int sum = 0;
		
		// **** sum values ****
		sum = sumValues(a, b);
		
		// **** compare results ****
		if (s == sum)
			System.out.println("main <<< sumValues sum: " + sum + " == s: " + s);
		else
			System.err.println("main <<< sumValues sum: " + sum + " != s: " + s);
		
		// **** add values ****
		sum = addValues(a, b);
		
		// **** compare results ****
		if (s == sum)
			System.out.println("main <<< addValues sum: " + sum + " == s: " + s);
		else
			System.err.println("main <<< addValues sum: " + sum + " != s: " + s);
	}

}
