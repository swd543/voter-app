import java.util.Random;
import java.util.Arrays;
import java.lang.Math;

public class FixedPoint {
    public static int bisection(int arr[], int bottom, int top) {
        int middle = (bottom + top)/2;
        if (middle == arr[middle]) {
            return middle;
        } else if (middle > arr[middle]) {
            return bisection(arr, middle +1, top);
        } else if (middle <  arr[middle]){
            return bisection(arr, bottom, middle -1);
        } else {
            return -1;
        }
    }
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        Random rand = new Random();
        int[] arr = new int[n];
        //int x = rand.nextInt(3) + 1;
        arr[0] = -((int)Math.sqrt(n));

        for (int i = 1; i <= n-1; i++) {
            arr[i] = arr[i-1] + 3;
            System.out.println(arr[i]);
        }

		/* 
		for (int j = 0; j < n; j++){
			if (arr[j] == j) {
				System.out.println(arr[j] + " is a fixed point");
			} else {
				continue;
			}
		} */

        System.out.println("Fixed point is " + bisection(arr, 0, n-1));

    }
}