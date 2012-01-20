package source;

import java.util.Arrays;

/*Write a recursive function
 bool IsMeasurable(int target, int weights[], int nWeights)
 that determines whether it is possible to measure out the desired target amount with a
 given set of weights. The available weights are stored in the array weights, which
 has nWeights as its effective size. For instance, the sample set of two weights
 could be represented using the following pair of variables:
 int sampleWeights[] = { 1, 3 };
 int nSampleWeights = 2;
 Given these values, the function call

 IsMeasurable(2, sampleWeights, nSampleWeights)

 should return true because it is possible to measure out 2 ounces using the sample
 weight set as illustrated in the preceding diagram. On the other hand, calling
 IsMeasurable(5, sampleWeights, nSampleWeights)
 should return false because it is impossible to use the 1- and 3-ounce weights to add
 up to 5 ounces.*/

public class MeasureWeight {

	public boolean isMeasurable(int target, int[] weightArray) {
		Arrays.sort(weightArray);
		return isMeasurableRecursive(target, weightArray, 0);
	}

	private boolean isMeasurableRecursive(int target, int[] weightArray,
			int index) {
		if (target <= 0 || index > weightArray.length - 1) {
			return false;
		}
		if (Arrays.binarySearch(weightArray, index, weightArray.length, target) >= 0) {
			return true;
		}
		return isMeasurableRecursive(target - weightArray[index], weightArray,
				index + 1)
				|| isMeasurableRecursive(target + weightArray[index],
						weightArray, index + 1)
				|| isMeasurableRecursive(target, weightArray, index + 1);

	}

	public static void main(String args[]) {
		int target = 8;
		int weights[] = { 7, 3, 2 };
		System.out.println(new MeasureWeight().isMeasurable(target, weights));
	}
}
