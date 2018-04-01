package com.nucleusfox.idle.textvocabulary;

import edu.princeton.cs.algs4.TrieST;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Stack;

/**
 * The main text processing class with hybrid quicksort algorithm with insertion for small partitions.
 *
 * @author nucleusfox
 *
 */
public class WordsStatistics {
    private static final int CUTOFF =  4;

    private String filename;
    private String[][] stat;
    private TrieST<Integer> words = new TrieST<>();
    private WordCount[] wordCount;
    private boolean recursiveSort = false;
    private String language = "EN";


    public WordsStatistics() { }

    public WordsStatistics(String language) {
        this.language = language;
    }

    public WordsStatistics countWordsFromFile(String filename) throws IOException {
        parseWordsFromFile(filename);
//		sortResult();
//		printResultToFile();

        return this;
    }

    public WordsStatistics countWordsFromString(String input) {
        parseWordsFromString(input);
//		sortResult();
//		printResultToFile();

        return this;
    }


    /**
     * Instructs the instance to use recursive quicksort.
     */
    public WordsStatistics setRecursiveSort() {
        recursiveSort = true;
        return this;
    }

    /**
     * Sorts the statistics array <i>stat[][2]</i> by Count. The method performs the quicksort either
     * recursively (by invoking the method <i>quickSortRecursive</i>) or imperatively (by invoking the
     * method <i>quickSortImperative</i>) depending on the flag <i>recursiveSort</i>. If the flag <i>recursiveSort</i>
     * is set up the sort is recursive. Use the method <i>setRecursiveSort</i> to set up this flag.
     */
    public WordsStatistics sortResult() {
        copyToStat();
        if (recursiveSort) quickSortRecursive(stat);
        else quickSortImperative(stat);
        copyToWordCountArray();

        return this;
    }

    /**
     * Prints the result statistics array to the file. The filename for the results is the same as for the input
     * with the ending '.wordsCount'.
     *
     * @throws IOException - issues with file access
     */
    public void printResultToFile() throws IOException {
        try (FileOutputStream fos = new FileOutputStream(new File(filename.concat(".wordsCount")))) {
            for (String[] st : stat) {
                System.out.println(st[0] + " - " + st[1]);
                fos.write((st[0] + " - " + st[1] + '\n').getBytes());
            }
        }
    }

    /**
     * Returns the count of the word in the given stream
     *
     * @param word - the word string
     * @return count of the given word
     */
    public int getCount(String word) {
        return words.get(word);
    }

    /**
     * @return array of word counts
     */
    public WordCount[] getCountArray() {
        return wordCount;
    }

    /**
     * Returns the first <i>top</i> words from the statistics array with their counts.
     *
     * @param top - the number of top counts to return
     * @return sub-array of <i>wordCount</i> of size <i>top</i>
     */
    public WordCount[] getCountArrayTop(int top) {
        if(top > wordCount.length) throw new IllegalArgumentException();
        return Arrays.copyOf(wordCount, top);
    }

    /**
     * Creates a FileInputStream from the file found by the full path <i>filename</i> and parse words from this stream.
     * Adds all words from the stream to the TrieST <i>words</i> variable of the instance. The method ignores the following
     * symbols by their ANSI-code if they are presented in the stream:
     * 33, 34, 44, 46, 63, 58, 59, 60, 62, 47, 92, 126
     *
     * @throws IOException - file access issues
     */
    private WordsStatistics parseWordsFromFile(String filename) throws IOException {
        try (FileInputStream fis = new FileInputStream(new File(filename))) {
            StringBuilder wordPart = new StringBuilder();
            int max = 0;
            while (fis.available() > 0) {
                int c = fis.read();
                if (c == -1)
                    break;
                if (c == 33 || c == 34 || c == 44 || c == 46 || c == 63 || c == 58 || c == 59 || c == 60 || c == 62
                        || c == 47 || c == 92 || c == 126)
                    continue;
                max = processCharacter(c, max, wordPart);
//                if (c == 32 || c == 10 || c == 13) {
//                    String str = strB.toString();
//                    if (!str.isEmpty()) {
//                        str = str.toLowerCase();
//                        Integer i = words.get(str);
//                        i = i == null ? 0 : i;
//                        words.put(str, i + 1);
//                        str = "";
//                        max = i > max ? i : max;
//                    }
//                    strB.delete(0, strB.length());
//                } else {
//                    strB.append((char) c);
//                }
            }
        }
        copyToStat();
        copyToWordCountArray();

        return this;
    }

    /**
     * @param input - the text to parse
     */
    private WordsStatistics parseWordsFromString(String input) {

        StringBuilder wordPart = new StringBuilder();
        int max = 0;
        for (int i = 0; i <= input.length(); i++) {
            int c = 32;
            if (i < input.length()) {
                c = (int) input.charAt(i);
                if (c == 33 || c == 34 || c == 44 || c == 46 || c == 63 || c == 58 || c == 59 || c == 60 || c == 62
                        || c == 47 || c == 92 || c == 126)
                    continue;
            }
            max = processCharacter(c, max, wordPart);
//            if (c == 32 || c == 10 || c == 13) { //i == input.length()
//                String str = strB.toString();
//                if (!str.isEmpty()) {
//                    str = str.toLowerCase();
//                    Integer index = words.get(str);
//                    index = index == null ? 0 : index;
//                    words.put(str, index + 1);
//                    str = "";
//                    max = index > max ? index : max;
//                }
//                strB.delete(0, strB.length());
//            } else {
//                strB.append((char) c);
//            }
        }

        copyToStat();
        copyToWordCountArray();

        return this;
    }


    private int processCharacter(int c, int max, StringBuilder wordPart) {
        if (c == 32 || c == 10 || c == 13) { //i == input.length()
            String str = wordPart.toString();
            if (!str.isEmpty()) {
                str = str.toLowerCase();
                Integer index = words.get(str);
                index = index == null ? 0 : index;
                words.put(str, index + 1);
                str = "";
                max = index > max ? index : max;
            }
            wordPart.delete(0, wordPart.length());
        } else {
            wordPart.append((char) c);
        }
        return max;
    }

    // TODO Add the method to process the InputStream either from the FileInputStream or from the direct input

    /**
     * Copies the words from the TrieST <i>words</i> variable of the instance to the statistics array <i>stat[][2]</i>.
     * The column <i>stat[i][1]</i> contains the word and the column <i>stat[i][2]</i> contains its Count.
     */
    private void copyToStat() {
        stat = new String[words.size()][2];
        int i = 0;
        for (String word : words.keys()) {
            stat[i][0] = word;
            stat[i][1] = words.get(word).toString();
            i++;
        }
    }

    private void copyToWordCountArray() {
        wordCount = new WordCount[stat.length];
        for (int i = 0; i < stat.length; i++) {
            wordCount[i] = new WordCount(new Word(language, stat[i][0]), Integer.valueOf(stat[i][1]));
        }
    }

    /**
     * Sorts the statistics array <i>data[][2]</i> by Count. Implements recursive quicksort with the insertion
     * cutoff for arrays size less or equal to <i>CUTOFF</i>. The initial method of the algorithm.
     */
    private static void quickSortRecursive(String [][] data) {
        quickSortRecursive(data, 0, data.length-1);
    }

    /**
     * Sorts the statistics array <i>data[][2]</i> by Count. Implements recursive quicksort with the insertion
     * cutoff for arrays size less or equal to <i>CUTOFF</i>. The recursive method of the algorithm.
     */
    private static void quickSortRecursive(String[][] data, int lo, int hi) {
        if (data == null || lo >= hi) return;

        // cutoff to insertion sort for small sub-arrays
        if (hi - lo <= CUTOFF) {
            insertion(data, lo, hi);
            return;
        }

        int lt = lo, gt = hi;
//        String v = data[lo][1];
        int v = Integer.valueOf(data[lo][1]);//Integer.valueOf(data[(lo + hi)/2][1]);
        int i = lo + 1;
        while (i <= gt) {
            if      (Integer.valueOf(data[i][1]) > v) swap(data, lt++, i++);
            else if (Integer.valueOf(data[i][1]) < v) swap(data, i, gt--);
            else	i++;
        }

        // data[lo..lt-1] < v = data[lt..gt] < data[gt+1..hi].
        quickSortRecursive(data, lo, lt-1);
        if (v >= 0) quickSortRecursive(data, lt, gt);
        quickSortRecursive(data, gt+1, hi);
    }

    /**
     * Sorts the sub-array of the array <i>data[][]</i> from <i>data[lo][]</i> to <i>data[hi][]</i> by first argument.
     * Implements the insertion sort.
     */
    private static void insertion(String[][] data, int lo, int hi) {
        for (int i = lo; i <= hi; i++)
            for (int j = i; j > lo && Integer.valueOf(data[j][1]) > Integer.valueOf(data[j-1][1]); j--)
                swap(data, j, j-1);
    }

    /**
     * Swaps values of the elements <i>data[i][0]</i> and <i>data[j][0]</i> in the given array <i>data</i>.
     */
    private static void swap(String[][] data, int i, int j) {
        String temp = data[i][0];
        data[i][0] = data[j][0];
        data[j][0] = temp;

        temp = data[i][1];
        data[i][1] = data[j][1];
        data[j][1] = temp;
    }

    // TODO Rewrite method to print into the given OutputStream?    

    /**
     * Sorts the statistics array <i>data[][2]</i> by Count. Implements imperative quicksort with the insertion
     * cutoff for arrays size less or equal to <i>CUTOFF</i>.
     */
    private static void quickSortImperative(String [][] data) {
        Stack<Integer> stack = new Stack<>();
        stack.push(0);						//1 left, 2 right <- Push
        stack.push(data.length-1);

        while (stack.size() > 0)
        {
            int hi = stack.pop(); 	// 2 left, 1 right -> Pop
            int lo = stack.pop();

            if (hi - lo <= CUTOFF) {
                insertion(data, lo, hi);
                continue;
            }

            int lt = lo + 1;
            int pivotIndex = lo;
            int gt = hi;


            int v = Integer.valueOf(data[pivotIndex][1]);

            if (lt > gt)
                continue;

            // TODO rewrite cycle as 1 loop?
            while (lt < gt)
            {
                while ((lt <= gt) && (Integer.valueOf(data[lt][1]) >= v)) lt++;	//increment right to find the greater element than the pivot
                while ((lt <= gt) && (Integer.valueOf(data[gt][1]) <= v)) gt--;	//decrement right to find the smaller element than the pivot
                if (gt >= lt) swap(data, lt, gt);								//swap only if right index is greater than the left
            }

            if (pivotIndex <= gt)
                if( Integer.valueOf(data[pivotIndex][1]) < Integer.valueOf(data[gt][1]))
                    swap(data, pivotIndex, gt);

            if (lo < gt)
            {
                stack.push(lo);				// left, right - left part
                stack.push(gt - 1);
            }

            if (hi > gt)					// left, right - right part 
            {
                stack.push(gt + 1);
                stack.push(hi);
            }
        }
    }

    public static void main(String[] args) {
        WordsStatistics ws = new WordsStatistics();
        System.out.println("The result is:");

        for (WordCount wc : ws.countWordsFromString("Hello hey hekko hello miss\nhekko jk hekko lkn df lkn").sortResult().getCountArray()) {
            System.out.println("Word = " + wc.getWord().getWord() + ", count = " + wc.getCount());
        }

    }

}