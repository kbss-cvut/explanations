package cz.cvut.kbss.utils;

import java.util.HashSet;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NaiveCombinations {

    public static <T> Set<Set<T>> generateCombinations(
        final Set<T> input, final int length) {
        Set<Set<T>> result;
        Set<Set<T>> all = new HashSet<>();

        if (input.size() == 0) {
            result = all;
        } else if (length == input.size()) {
            all.add(input);
            result = all;
        } else {
            if (length == 1) {
                for (T t : input) {
                    Set<T> list = new HashSet<T>();
                    list.add(t);
                    all.add(list);
                }
            } else {
                final T first = input.iterator().next();
                final Set<T> newSet = new HashSet<T>(input);
                newSet.remove(first);
                final Set<Set<T>> computed = generateCombinations(newSet, length - 1);
                for (Set<T> t : computed) {
                    Set<T> newSet2 = new HashSet<T>(t);
                    newSet2.add(first);
                    all.add(newSet2);
                }
                all.addAll(generateCombinations(newSet, length));
            }
            result = all;
        }

        return result;
    }

    public static void main(String[] args) {
        final Set<Object> o = new HashSet<>(1, 2, 3, 4, 5, 6);
        for (int i = 1; i < o.size(); i++) {
            log.info("" + generateCombinations(o, i).size());
        }
    }
}