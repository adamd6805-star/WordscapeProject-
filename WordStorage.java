import java.util.*;

public class WordStorage {
    private static final Map<Integer, List<String>> wordsByLength = new HashMap<>();

    //The list of words 
    static {
        wordsByLength.put(4, Arrays.asList(
            "GAME", "PLAY", "WORD", "JAVA", "CODE", "TEST", "DATA", "NODE", "TREE", "LIST",
            "PULL", "PUSH", "LOOP", "EXIT", "JOIN", "TASK", "FILE", "PATH", "LINK", "VIEW",
            "SAVE", "LOAD", "EDIT", "COPY", "PAST", "LOVE", "REDO", "FIND", "REPLACE", "SORT",
            "MATH", "LOGS", "TIME", "DATE", "ZONE", "USER", "NAME", "PASS", "ROLE", "TYPE",
            "POOR", "MADE", "HARD", "EASY", "FAST", "SLOW", "HIGH", "MEAN", "OPEN", "BEAD", "STAR", "DULL"
        ));

        wordsByLength.put(5, Arrays.asList(
            "APPLE", "BRAVE", "CLOUD", "DANCE", "EARTH", "FLAME", "GLORY", "HEART", "IGLOO", "JUMBO",
            "KITES", "LIGHT", "MUSIC", "NIGHT", "OCEAN", "PIANO", "QUICK", "RIVER", "SNAKE", "TIGER",
            "UMBRA", "VIVID", "WATER", "XENON", "YACHT", "ZEBRA", "HEARD", "SWEET", "BRICK", "CROWN", "DREAM",
            "FROST", "GRACE", "HONEY", "JELLY", "KNIFE", "LUNCH", "MONEY", "NURSE", "OASIS", "PRIDE", "QUEST", 
            "RANCH", "SWORD", "TOWER", "UNITY", "VIRUS", "WORST", "YOUTH", "ZONED", "BLAST", "CRISP", "DRIVE", "ELITE", 
            "FABLE", "GHOST", "HUMAN", "IDEAL", "JUDGE", "KNOCK"
        ));

        wordsByLength.put(6, Arrays.asList(
            "BANANA", "CANDLE", "DANGER", "ELEGANT", "FAMILY", "GUITAR", "HUNTER", "ISLAND", "JOURNEY", "KITTEN",
            "LANTERN", "MYSTERY", "NATURE", "ORANGE", "PENCIL", "QUARTER", "RUBBER", "SILVER", "TUNNEL", "UNIQUE",
            "VIOLIN", "WONDER", "XENIAL", "YELLOW", "ZODIAC", "GENIUS", "HUNGRY", "JOURNAL", "KINDLY", "LUXURY", "MOMENT", 
            "NOBLE", "OPTICS", "PRISON", "QUIET", "RANDOM", "SHADOW", "TUNING", "UNFOLD", "VINTAGE", "WISDOM", "YOUNG", "ZIGZAG", 
            "BRIGHT", "CIRCLE", "DREAMS", "EXPERT", "FUTURE", "GARDEN", "HEROIC", "IMPACT", "JOURNE", "KINDLE", "LEGACY", "MIRROR", "NATION"
        ));
    }

    //The method to get the words based on the length of the word tiles
    public static List<String> getWordsByLength(int length) {
        return wordsByLength.getOrDefault(length, Collections.emptyList());
    }

    //The method to get a random word 
    public static String getRandomWord(int length) {
        List<String> words = getWordsByLength(length);
        if (words.isEmpty()) return null;
        Random rand = new Random();
        return words.get(rand.nextInt(words.size()));
    }

    //The method that checks if the word is valid based on the WordStorage Class
    public static boolean isValidWord(String word, int length) {
        if (word.length() != length) {
            return false;
        }
        for (char c : word.toCharArray()) {
            if (!Character.isLetter(c)) {
                return false;
            }
        }
        return true;
    }


    
}