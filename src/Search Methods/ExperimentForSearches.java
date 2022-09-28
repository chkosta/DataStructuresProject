import java.util.Random;
import java.io.File;
import java.util.Scanner;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

public class ExperimentForSearches {

    public void MergeSort(Integer[] inarray, int l, int h) {

        int low = l;
        int high = h;
        int mid = (low + high) / 2;

        if (low >= high) {
            return;
        }

        MergeSort(inarray, low, mid);
        MergeSort(inarray, mid + 1, high);
        int end_low = mid;
        int start_high = mid + 1;
        while ((l <= end_low) && (start_high <= high)) {
            if (inarray[low] < inarray[start_high]) {
                low++;
            } else {
                int Temp = inarray[start_high];
                for (int k = start_high - 1; k >= low; k--) {
                    inarray[k + 1] = inarray[k];
                }
                inarray[low] = Temp;
                low++;
                end_low++;
                start_high++;
            }
        }
    }

    public void LinearSearch(Integer[] inarray, Integer[] rarray) {

        outerloop: for (int i = 0; i < rarray.length; i++) {
            innerloop: for (int j = 0; j < inarray.length; j++) {
                if (rarray[i].equals(inarray[j])) {
                    System.out.println("Number " + rarray[i] + " found in position " + j + " of the table!");
                    continue outerloop;
                }
            }
            System.out.println("Number " + rarray[i] + " not found!");
        }
        int worst = rarray.length * inarray.length;
        int average = rarray.length * inarray.length;
        System.out.println("-----------------------------------------------------");
        System.out.println("\nWorst search time: " + worst + " calculations");
        System.out.println("Average search time: " + average + " calculations");
    }

    public void BinarySearch(Integer[] inarray, Integer[] rarray) {

        outerloop: for (int i = 0; i <= rarray.length - 1; i++) {
            int low = 0;
            int high = inarray.length - 1;
            while (low <= high) {
                int mid = (low + high) / 2;
                if (rarray[i].equals(inarray[mid])) {
                    System.out.println("Number " + rarray[i] + " found in position " + mid + " of the table!");
                    continue outerloop;
                }
                if (rarray[i] < inarray[mid]) {
                    high = mid - 1;
                } else {
                    low = mid + 1;
                }
            }
            System.out.println("Number " + rarray[i] + " not found!");
        }
        double worst = (double) Math.log(inarray.length) * (double) rarray.length;
        double average = (double) Math.log(inarray.length) * (double) rarray.length;
        System.out.println("-----------------------------------------------------");
        System.out.println("\nWorst search time: " + worst + " calculations");
        System.out.println("Average search time: " + average + " calculations");
    }

    public void InterpolationSearch(Integer[] inarray, Integer[] rarray) {

        outerloop: for (int i = 0; i <= rarray.length - 1; i++) {
            int left = 0;
            int right = inarray.length - 1;
            while (left <= right && rarray[i] >= inarray[left] && rarray[i] <= inarray[right]) {
                int next;
                next = ((rarray[i] - inarray[left]) / (inarray[right] - inarray[left])) * (right - left) + left;
                if (inarray[next].equals(rarray[i])) {
                    System.out.println("Number " + rarray[i] + " found in position " + next + " of the table!");
                    continue outerloop;
                }
                if (inarray[next] < rarray[i]) {
                    left = next + 1;
                } else
                    right = next - 1;
            }
            System.out.println("Number " + rarray[i] + " not found!");
        }
        int worst = inarray.length * rarray.length;
        double average = (double) Math.log(Math.log(inarray.length)) * (double) rarray.length;
        System.out.println("-----------------------------------------------------");
        System.out.println("\nWorst search time: " + worst + " calculations");
        System.out.println("Average search time: " + average + " calculations");
    }

    public static void main(String[] args) throws Exception {

        // Experiment between the 4 different methods regarding search time
        ExperimentForSearches efs = new ExperimentForSearches();
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

            Random r = new Random();
            Integer ran[] = new Integer[1000];
            int low = 0;
            int high = 500000;

            System.out.println("Press ENTER to start the process of generating 1000 random numbers...");
            try {
                System.in.read();
            } catch (Exception e) {
            }
            System.out.println("Search the following 1000 numbers:");
            System.out.println("----------------------------------");
            for (int i = 0; i < ran.length; i++) {
                ran[i] = r.nextInt(high - low) + low;
                System.out.println(ran[i]);
            }

            efs.MergeSort(InputArray, 0, InputArray.length - 1);

            System.out.println(
                    "\nPress ENTER to start the process of Linear Search....");
            try {
                System.in.read();
            } catch (Exception e) {
            }
            System.out.println("\n\nLINEAR SEARCH METHOD:");
            System.out.println("---------------------");
            efs.LinearSearch(InputArray, ran);

            System.out.println("\nPress ENTER to start the process of Binary Search...");
            try {
                System.in.read();
            } catch (Exception e) {
            }
            System.out.println("\n\nBINARY SEARCH METHOD:");
            System.out.println("-----------------------");
            efs.BinarySearch(InputArray, ran);

            System.out.println("\nPress ENTER to start the process of Interpolation Search...");
            try {
                System.in.read();
            } catch (Exception e) {
            }
            System.out.println("\n\nINTERPOLATION SEARCH METHOD:");
            System.out.println("------------------------------");
            efs.InterpolationSearch(InputArray, ran);

            System.out.println("\nEND OF PROGRAM");
            System.out.println("--------------");

        } catch (IOException ie) {
            System.out.print("There was a problem!: " + ie);
        }
    }
}
