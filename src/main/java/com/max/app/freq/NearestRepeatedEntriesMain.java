package com.max.app.freq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class NearestRepeatedEntriesMain {

    private static final Logger LOG = LoggerFactory.getLogger(NearestRepeatedEntriesMain.class);

    private static final class CursorAndWindow {
        String word;
        int index;
        int windowSize = Integer.MAX_VALUE;
    }

    /**
     * 13.6 FIND THE NEAREST REPEATED ENTRIES IN AN ARRAY
     */
    public static void main(String[] args) throws Exception {

        Path bookPath = Paths.get("/Users/mstepan/repo/app-java13-template/src/main/resources/book-war-and-peace.txt");

        try (Stream<String> linesStream = Files.lines(bookPath)) {

            List<String> allLines = linesStream.
                    flatMap(singleLine -> Arrays.stream(singleLine.split("\\W+"))).
                    map(word -> word.trim().toLowerCase()).
                    filter(word -> word.length() > 10).collect(Collectors.toList());

            final CursorAndWindow cursor = new CursorAndWindow();

            final int guessInitialCapacity = 64 * 4 / 3;
            final float loadFactor = 0.75F;
            final boolean accessOrderEvictionPolicy = true;

            Map<String, Integer> lastOccurrenceMap = new LinkedHashMap<>(guessInitialCapacity, loadFactor,
                                                                         accessOrderEvictionPolicy) {
                @Override
                protected boolean removeEldestEntry(Map.Entry<String, Integer> candidateForRemoval) {

                    boolean willBeRemoved = (cursor.index - candidateForRemoval.getValue()) > cursor.windowSize;

                    if (willBeRemoved) {
                        LOG.info("Removing element, key = " + candidateForRemoval.getKey() +
                                         ", value = " + candidateForRemoval.getValue() +
                                         ", curWindow: " + (cursor.index - candidateForRemoval.getValue()) +
                                         ", cursor.index: " + cursor.index +
                                         ", cursor.windowSize: " + cursor.windowSize +
                                         ", map.size: " + size());

                    }

                    return willBeRemoved;
                }
            };

            for (int i = 0; i < allLines.size(); ++i, cursor.index = i) {

                String word = allLines.get(i).trim().toLowerCase();

                Integer prevIndex = lastOccurrenceMap.get(word);

                if (prevIndex == null) {
                    lastOccurrenceMap.put(word, i);
                }
                else {

                    int curWindowSize = i - prevIndex;

                    if (cursor.windowSize > curWindowSize) {
                        cursor.windowSize = curWindowSize;
                        cursor.word = word;
                    }

                    lastOccurrenceMap.put(word, i);
                }
            }

            LOG.info("word: '{}', index: {}, windowSize: {}", cursor.word, cursor.index, cursor.windowSize);
        }

        LOG.info("NearestRepeatedEntriesMain done. java version {}", System.getProperty("java.version"));
    }

}
