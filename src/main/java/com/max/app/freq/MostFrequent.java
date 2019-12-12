package com.max.app.freq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;


/*
13.5 COMPUTE THE k MOST FREQUENT QUERIES.
 */
public final class MostFrequent {

    private static final Logger LOG = LoggerFactory.getLogger(MostFrequent.class);

    /**
     * Find top K most frequent elements.
     * <p>
     * N - all words count
     * M - unique words count
     * K - top K window size
     * <p>
     * time: O(N + M * lgK)
     * space: O(M + K)
     */
    public static void main(String[] args) throws Exception {

        Path bookPath = Paths.get("/Users/mstepan/repo/app-java13-template/src/main/resources/book-war-and-peace.txt");

        Set<String> skipWords = new HashSet<>();
        skipWords.add("a");
        skipWords.add("an");
        skipWords.add("the");
        skipWords.add("is");
        skipWords.add("on");
        skipWords.add("at");
        skipWords.add("in");
        skipWords.add("by");
        skipWords.add("for");
        skipWords.add("as");
        skipWords.add("to");
        skipWords.add("of");
        skipWords.add("are");
        skipWords.add("was");
        skipWords.add("were");
        skipWords.add("be");
        skipWords.add("did");

        final int k = 10;

        try (Stream<String> linesStream = Files.lines(bookPath)) {

            Map<String, Long> freq = linesStream.
                    flatMap(singleLine -> Arrays.stream(singleLine.split("\\W+"))).
                    map(word -> word.trim().toLowerCase()).
                    filter(word -> word.length() > 1 && !skipWords.contains(word)).
                    collect(groupingBy(word -> word, HashMap::new, counting()));

            WordAndFreq[] topKArr = findTopK(freq, k);

            for (WordAndFreq cur : topKArr) {
                LOG.info("{}: {}", cur.word, cur.freq);
            }
        }

        LOG.info("MostFrequent done. java version {}", System.getProperty("java.version"));
    }

    private static final class WordAndFreq {

        private static final Comparator<WordAndFreq> FREQ_ASC_CMP = Comparator.comparing(wordAndFreq -> wordAndFreq.freq);

        final String word;
        final long freq;

        WordAndFreq(Map.Entry<String, Long> entry) {
            this.word = entry.getKey();
            this.freq = entry.getValue();
        }

        @Override
        public String toString() {
            return word + ": " + freq;
        }
    }

    private static WordAndFreq[] findTopK(Map<String, Long> freq, int k) {

        assert k > 0 : "k should be positive value, found k = " + k;

        Queue<WordAndFreq> minHeap = new PriorityQueue<>(k, WordAndFreq.FREQ_ASC_CMP);

        for (Map.Entry<String, Long> entry : freq.entrySet()) {
            if (minHeap.size() < k) {
                minHeap.add(new WordAndFreq(entry));
            }
            else if (minHeap.peek().freq < entry.getValue()) {
                minHeap.poll();
                minHeap.add(new WordAndFreq(entry));
            }
        }

        WordAndFreq[] mostFrequentArr = new WordAndFreq[k];

        for (int i = mostFrequentArr.length - 1; i >= 0; --i) {
            assert !minHeap.isEmpty() : "empty minHeap detected";
            mostFrequentArr[i] = minHeap.poll();
        }

        return mostFrequentArr;


    }

}
