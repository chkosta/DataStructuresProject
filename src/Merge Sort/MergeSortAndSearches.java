import java.io.File;
import java.util.Scanner;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MergeSortAndSearches {

    public void MergeSort(Integer[] array, int l, int h) {

        int low = l;
        int high = h;
        int mid = (low + high) / 2;

        if (low >= high) {
            return;
        }

        MergeSort(array, low, mid);
        MergeSort(array, mid + 1, high);
        int end_low = mid;
        int start_high = mid + 1;
        while ((l <= end_low) && (start_high <= high)) {
            if (array[low] < array[start_high]) {
                low++;
            } else {
                int Temp = array[start_high];
                for (int k = start_high - 1; k >= low; k--) {
                    array[k + 1] = array[k];
                }
                array[low] = Temp;
                low++;
                end_low++;
                start_high++;
            }
        }
    }

    public void PrintingFile(Integer[] array) {

        System.out.println("The sorted numbers are below: ");
        System.out.println("-----------------------------");
        for (int i = 0; i < array.length; i++) {
            System.out.println(array[i]);
        }
    }

    public void LinearSearch(Integer[] array, int n) {

        for (int i = 0; i < array.length; i++) {
            if (array[i] == n) {
                System.out.println("\nNumber " + n + " found in position " + i + " of the table!");
                return;
            }
        }
        System.out.println("\nNumber " + n + " not found!");
        return;
    }

    public void BinarySearch(Integer[] array, int n) {

        int low = 0;
        int high = array.length - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (n == array[mid]) {
                System.out.println("\nNumber " + n + " found in position " + mid + " of the table!");
                return;
            }
            if (n < array[mid]) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        System.out.println("\nNumber " + n + " not found!");
        return;
    }

    public void InterpolationSearch(Integer[] array, int n) {

        int left = 0;
        int right = array.length - 1;
        while (left <= right && n >= array[left] && n <= array[right]) {
            int next;
            next = ((n - array[left]) / (array[right] - array[left])) * (right - left) + left;
            if (array[next] == n) {
                System.out.println("\nNumber " + n + " found in position " + next + " of the table!");
                return;
            }
            if (array[next] < n) {
                left = next + 1;
            } else
                right = next - 1;
        }
        System.out.println("\nNumber " + n + " not found!");
        return;
    }

    public static void main(String[] args) throws Exception {

        // Read numbers from integers.txt and sort them using MergeSort
        MergeSortAndSearches msas = new MergeSortAndSearches();
        try {
            String filepath = "C:\\Users\\chris\\Documents\\Exercises\\CEID\\4ο ΕΞ\\Δομές Δεδομένων\\Project\\Project\\src\\integers.txt";
            File file = new File(filepath);
            Scanner sc = new Scanner(file);
            List<Integer> lines = new ArrayList<Integer>();

            while (sc.hasNextLine()) {
                int i = sc.nextInt();
                lines.add(i);
            }
            sc.close();
            Integer[] InputArray = lines.toArray(new Integer[lines.size()]);
            msas.MergeSort(InputArray, 0, InputArray.length - 1);
            System.out.println(
                    "Press ENTER to sort all the items using the MergeSort algorithm...");
            try {
                System.in.read();
            } catch (Exception e) {
            }
            msas.PrintingFile(InputArray);

            // Search for a number using Linear search, Binary search and
            // Interpolation search
            System.out.println("\nWould you like to search for another number?(YES/NO)");
            String ans = System.console().readLine();
            Scanner scint = new Scanner(System.in);

            if (ans.equals("YES") || ans.equals("yes")) {
                loop: do {
                    System.out.println("\nWhich number are you looking for?");
                    int num = scint.nextInt();
                    System.out.println("\nSearch method:");
                    System.out.println("--------------");
                    System.out.println("1. Linear search");
                    System.out.println("2. Binary search");
                    System.out.println("3. Interpolation search");
                    int decision = scint.nextInt();
                    switch (decision) {
                        case 1:
                            msas.LinearSearch(InputArray, num);
                            break;
                        case 2:
                            msas.BinarySearch(InputArray, num);
                            break;
                        case 3:
                            msas.InterpolationSearch(InputArray, num);
                            break;
                        default:
                            System.out.println("\nWrong choice!");
                            continue loop;
                    }
                    System.out.println("\nWould you like to search for another number?(YES/NO)");
                    ans = System.console().readLine();
                } while (ans.equals("YES") || ans.equals("yes"));
            }

            if (ans.equals("NO") || ans.equals("no")) {
                System.out.println("\nEND OF PROGRAM");
                System.out.println("--------------");
            }

        } catch (IOException ie) {
            System.out.print("There was a problem!: " + ie);
        }
    }
}
