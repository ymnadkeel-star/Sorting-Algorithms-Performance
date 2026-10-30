import java.util.Arrays;
import java.util.Random;

public class SortingBenchmark {

    static final int N = 100000;
    static Random random = new Random();

    public static void main(String[] args) {

        int[] randomArray = new int[N];
        int[] reverseArray = new int[N];
        int[] partialArray = new int[N];
        int[] sortedArray = new int[N];

        // Create random array
        for (int i = 0; i < N; i++) {
            randomArray[i] = random.nextInt(100000);
        }

        // Create reverse sorted array
        for (int i = 0; i < N; i++) {
            reverseArray[i] = N - i;
        }

        // Create fully sorted array
        for (int i = 0; i < N; i++) {
            sortedArray[i] = i;
        }

        // Create partially sorted array (80%)
        for (int i = 0; i < N; i++) {
            partialArray[i] = i;
        }

        for (int i = 0; i < N / 5; i++) {
            int a = random.nextInt(N);
            int b = random.nextInt(N);

            int temp = partialArray[a];
            partialArray[a] = partialArray[b];
            partialArray[b] = temp;
        }

        int[][] cases = {
            randomArray,
            reverseArray,
            partialArray,
            sortedArray
        };

        String[] caseNames = {
            "Random",
            "Reverse Sorted",
            "Partially Sorted",
            "Fully Sorted"
        };

        System.out.println("Sorting Algorithms Benchmark");
        System.out.println("Time in Microseconds (us)");
        System.out.println();

        System.out.printf("%-22s %-15s %-15s %-15s %-15s %-15s%n",
                "Case", "Selection", "Insertion",
                "Bubble", "Merge", "Quick");

        for (int i = 0; i < cases.length; i++) {

            System.out.printf("%-22s", caseNames[i]);

            System.out.printf("%-15d",
                    measure(cases[i], 1));

            System.out.printf("%-15d",
                    measure(cases[i], 2));

            System.out.printf("%-15d",
                    measure(cases[i], 3));

            System.out.printf("%-15d",
                    measure(cases[i], 4));

            System.out.printf("%-15d%n",
                    measure(cases[i], 5));
        }
    }

    // Measure execution time
    static long measure(int[] original, int algorithm) {

        int[] arr = Arrays.copyOf(original, original.length);

        long startTime = System.nanoTime();

        switch (algorithm) {

            case 1:
                selectionSort(arr);
                break;

            case 2:
                insertionSort(arr);
                break;

            case 3:
                bubbleSort(arr);
                break;

            case 4:
                mergeSort(arr, 0, arr.length - 1);
                break;

            case 5:
                quickSort(arr, 0, arr.length - 1);
                break;
        }

        long endTime = System.nanoTime();

        return (endTime - startTime) / 1000;
    }

    // Selection Sort
    static void selectionSort(int[] arr) {

        for (int i = 0; i < arr.length - 1; i++) {

            int min = i;

            for (int j = i + 1; j < arr.length; j++) {

                if (arr[j] < arr[min]) {
                    min = j;
                }
            }

            int temp = arr[i];
            arr[i] = arr[min];
            arr[min] = temp;
        }
    }

    // Insertion Sort
    static void insertionSort(int[] arr) {

        for (int i = 1; i < arr.length; i++) {

            int key = arr[i];
            int j = i - 1;

            while (j >= 0 && arr[j] > key) {

                arr[j + 1] = arr[j];
                j--;
            }

            arr[j + 1] = key;
        }
    }

    // Bubble Sort
    static void bubbleSort(int[] arr) {

        for (int i = 0; i < arr.length - 1; i++) {

            boolean swapped = false;

            for (int j = 0; j < arr.length - 1 - i; j++) {

                if (arr[j] > arr[j + 1]) {

                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;

                    swapped = true;
                }
            }

            if (!swapped) {
                break;
            }
        }
    }

    // Merge Sort
    static void mergeSort(int[] arr, int left, int right) {

        if (left < right) {

            int mid = left + (right - left) / 2;

            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);

            merge(arr, left, mid, right);
        }
    }

    static void merge(int[] arr, int left, int mid, int right) {

        int n1 = mid - left + 1;
        int n2 = right - mid;

        int[] L = new int[n1];
        int[] R = new int[n2];

        for (int i = 0; i < n1; i++) {
            L[i] = arr[left + i];
        }

        for (int j = 0; j < n2; j++) {
            R[j] = arr[mid + 1 + j];
        }

        int i = 0, j = 0, k = left;

        while (i < n1 && j < n2) {

            if (L[i] <= R[j]) {
                arr[k++] = L[i++];
            } else {
                arr[k++] = R[j++];
            }
        }

        while (i < n1) {
            arr[k++] = L[i++];
        }

        while (j < n2) {
            arr[k++] = R[j++];
        }
    }

    // Quick Sort
    static void quickSort(int[] arr, int low, int high) {

        if (low < high) {

            int pivotIndex = partition(arr, low, high);

            quickSort(arr, low, pivotIndex - 1);
            quickSort(arr, pivotIndex + 1, high);
        }
    }

    static int partition(int[] arr, int low, int high) {

        // Choose middle element as pivot
        int mid = low + (high - low) / 2;

        int temp = arr[mid];
        arr[mid] = arr[high];
        arr[high] = temp;

        int pivot = arr[high];

        int i = low - 1;

        for (int j = low; j < high; j++) {

            if (arr[j] <= pivot) {

                i++;

                int t = arr[i];
                arr[i] = arr[j];
                arr[j] = t;
            }
        }

        int t = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = t;

        return i + 1;
    }
}
