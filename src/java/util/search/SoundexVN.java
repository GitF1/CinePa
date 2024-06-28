
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util.search;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author PC
 */
public class SoundexVN {

    private final Map<String, List<String>> soundexMap;

    public SoundexVN(Map<String, List<String>> soundexMap) {
        this.soundexMap = soundexMap;
    }

    public List<String> suggestSoundex(String input) {
        String encodedInput = encode(input);
        return soundexMap.getOrDefault(encodedInput, List.of());
    }
    public static List<String> movieTitles = new ArrayList<>(List.of(
            "Bố Già", "Lật Mặt", "Mắt Biếc", "Em Là Bà Nội Của Anh", "Bố Là Tất Cả",
            "Ba Người Con", "Hai Phượng", "Cua Lại Vợ Bầu", "Chị Mười Ba", "Lôi Báo",
            "Song Lang", "Về Nhà Đi Con", "Trạng Tí", "Ròm", "Dòng Máu Anh Hùng",
            "Tháng Năm Rực Rỡ", "Để Mai Tính", "Nắng", "Đôi Mắt Âm Dương", "Sài Gòn Anh Yêu Em",
            "Cô Ba Sài Gòn", "Em Chưa 18", "Tèo Em", "Anh Thầy Ngôi Sao", "Tiệc Trăng Máu",
            "Tình Yêu Từ Hai Phía", "Vệ Sĩ Sài Gòn", "Gái Già Lắm Chiêu", "Trạng Quỳnh", "Ống Kính Sát Nhân",
            "798Mười", "Số Nhọ", "Cô Gái Đến Từ Hôm Qua", "Lời Nguyền Huyết Ngải", "Chàng Vợ Của Em",
            "Siêu Sao Siêu Ngố", "Tháng 5 Để Dành", "Mỹ Nhân Kế", "Bụi Đời Chợ Lớn", "Tôi Thấy Hoa Vàng Trên Cỏ Xanh",
            "Găng Tay Đỏ", "Áo Lụa Hà Đông", "Nhà Có 5 Nàng Tiên", "Vợ Ơi... Em Ở Đâu?", "Thiên Mệnh Anh Hùng",
            "Long Ruồi", "Hotboy Nổi Loạn", "Lấy Chồng Người Ta", "Chàng Trai Năm Ấy", "Kẻ Chơi Xấu",
            "Oan Hồn", "Quả Tim Máu", "Tấm Cám: Chuyện Chưa Kể", "Fan Cuồng", "Cô Dâu Đại Chiến",
            "Nhà Có 5 Nàng Tiên", "49 Ngày", "Lạc Giới", "Dạ Cổ Hoài Lang", "Nắng 2",
            "Cạm Bẫy: Hơi Thở Của Quỷ", "Yêu", "12 Chòm Sao: Vẽ Đường Cho Yêu Chạy", "Đời Cho Ta Bao Lần Đôi Mươi", "Ba Vợ Cưới Vợ Ba",
            "Yêu Em Bất Chấp", "Tình Đầu Thơ Ngây", "Chạy Đi Rồi Tính", "Truy Sát", "Truyền Thuyết Về Quán Tiên",
            "Siêu Trộm", "Cho Em Gần Anh Thêm Chút Nữa", "Tháng Năm Dữ Dội", "Dạ Cổ Hoài Lang", "Oan Hồn",
            "Truyền Thuyết Về Quán Tiên", "Lửa Phật", "Cú Và Chim Se Sẻ", "Tâm Sự Bà Bầu", "Chí Phèo Ngoại Truyện",
            "Vòng Eo 56", "Sứ Mệnh Trái Tim", "Trăng Nơi Đáy Giếng", "Tiên Nữ Không Kiêng Cữ", "Khi Con Là Nhà",
            "Ngốc Ơi Tuổi 17", "Lật Mặt 2: Phim Trường", "Quý Cô Thừa Kế", "Cậu Bé Rồng", "Ngôi Nhà Bươm Bướm",
            "Phía Cuối Cầu Vồng", "Ông Ngoại Tuổi 30", "Người Lạ Ơi", "Trăng Máu", "Thiên Thần Hộ Mệnh",
            "Lật Mặt 3: Ba Chàng Khuyết", "Bầu Trời Trong Trẻo", "Căn Hộ 69", "Trăng Nơi Đáy Giếng", "Tình Khúc Bạch Dương", "Hành Động"
    ));

    private static final String[] VIETNAMESE_CHARACTERS = {
        "a", "á", "à", "ả", "ã", "ạ", "ă", "ắ", "ằ", "ẳ", "ẵ", "ặ", "â", "ấ", "ầ", "ẩ", "ẫ", "ậ",
        "e", "é", "è", "ẻ", "ẽ", "ẹ", "ê", "ế", "ề", "ể", "ễ", "ệ",
        "i", "í", "ì", "ỉ", "ĩ", "ị",
        "o", "ó", "ò", "ỏ", "õ", "ọ", "ô", "ố", "ồ", "ổ", "ỗ", "ộ", "ơ", "ớ", "ờ", "ở", "ỡ", "ợ",
        "u", "ú", "ù", "ủ", "ũ", "ụ", "ư", "ứ", "ừ", "ử", "ữ", "ự",
        "y", "ý", "ỳ", "ỷ", "ỹ", "ỵ",
        "d", "đ",
        // Uppercase
        "A", "Á", "À", "Ả", "Ã", "Ạ", "Ă", "Ắ", "Ằ", "Ẳ", "Ẵ", "Ặ", "Â", "Ấ", "Ầ", "Ẩ", "Ẫ", "Ậ",
        "E", "É", "È", "Ẻ", "Ẽ", "Ẹ", "Ê", "Ế", "Ề", "Ể", "Ễ", "Ệ",
        "I", "Í", "Ì", "Ỉ", "Ĩ", "Ị",
        "O", "Ó", "Ò", "Ỏ", "Õ", "Ọ", "Ô", "Ố", "Ồ", "Ổ", "Ỗ", "Ộ", "Ơ", "Ớ", "Ờ", "Ở", "Ỡ", "Ợ",
        "U", "Ú", "Ù", "Ủ", "Ũ", "Ụ", "Ư", "Ứ", "Ừ", "Ử", "Ữ", "Ự",
        "Y", "Ý", "Ỳ", "Ỷ", "Ỹ", "Ỵ",
        "D", "Đ"
    };

    public static String encode(String word) {
        // Normalize and remove diacritics
        String normalized = Normalizer.normalize(word, Normalizer.Form.NFD)
                .replaceAll("[\\p{InCombiningDiacriticalMarks}]", "")
                .toUpperCase();

        if (normalized.length() == 0) {
            return "";
        }

        char[] soundex = new char[4];
        soundex[0] = normalized.charAt(0);

        char[] mapping = {'0', '1', '2', '3', '0', '1', '2', '0', '0', '2', '2', '4', '5', '5', '0', '1', '2', '6', '2', '3', '0', '1', '0', '2', '0', '2'};

        int soundexIndex = 1;
        char last = mapCharacter(normalized.charAt(0), mapping);

        for (int i = 1; i < normalized.length() && soundexIndex < 4; i++) {
            char mapped = mapCharacter(normalized.charAt(i), mapping);

            if (mapped != '0' && mapped != last) {
                soundex[soundexIndex++] = mapped;
                last = mapped;
            }
        }

        for (int i = soundexIndex; i < 4; i++) {
            soundex[i] = '0';
        }

        return new String(soundex);
    }

    private static char mapCharacter(char character, char[] mapping) {
        if (character >= 'A' && character <= 'Z') {
            return mapping[character - 'A'];
        }
        // For characters outside 'A' to 'Z', return '0' or handle as needed
        return '0';
    }

    public static List<String> generateWords(int numberOfWords, int wordLength) {
        List<String> words = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < numberOfWords; i++) {
            StringBuilder word = new StringBuilder();
            for (int j = 0; j < wordLength; j++) {
                word.append(VIETNAMESE_CHARACTERS[random.nextInt(VIETNAMESE_CHARACTERS.length)]);
            }
            words.add(word.toString());
        }

        return words;
    }

    public static Map<String, List<String>> applySoundex(List<String> words) {
        Map<String, List<String>> soundexMap = new HashMap<>();

        for (String word : words) {
            String encoded = encode(word);
            soundexMap.computeIfAbsent(encoded, k -> new ArrayList<>()).add(word);
        }

        return soundexMap;
    }

    public static int LevenshteinDistance(String s1, String s2) {
        int[][] dp = new int[s1.length() + 1][s2.length() + 1];

        for (int i = 0; i <= s1.length(); i++) {
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else {
                    dp[i][j] = Math.min(dp[i - 1][j - 1]
                            + costOfSubstitution(s1.charAt(i - 1), s2.charAt(j - 1)),
                            Math.min(dp[i - 1][j] + 1,
                                    dp[i][j - 1] + 1));
                }
            }
        }

        return dp[s1.length()][s2.length()];
    }

    public static int costOfSubstitution(char a, char b) {
        return a == b ? 0 : 1;
    }

    public List<String> suggest(String input) {
        List<String> candidates = suggestSoundex(input);
        List<String> suggestions = new ArrayList<>();

        int threshold = 100; // Adjust as needed

        for (String candidate : candidates) {
            int distance = LevenshteinDistance(input, candidate);
            System.out.println("distance: " + distance);
            if (distance <= threshold) {
                suggestions.add(candidate);
            }
        }

        return suggestions;
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String input;

        do {
            System.out.print("Enter a search term (or 'exit' to quit): ");
            input = scanner.nextLine().trim();

            if (!input.equalsIgnoreCase("exit")) {
                // Apply Soundex to the dictionary
                Map<String, List<String>> soundexMap = new HashMap<>();

                for (String word : movieTitles) {
                    String encoded = SoundexVN.encode(word);
                    soundexMap.computeIfAbsent(encoded, k -> new ArrayList<>()).add(word);
                }

                SoundexVN search = new SoundexVN(soundexMap);

                List<String> suggestions = search.suggest(input);
                List<String> patternMatches = new KMP().patternSearch(input, movieTitles);

                System.out.println("Suggestions for '" + input + "': " + suggestions);
                System.out.println("Pattern Search Matches for '" + input + "': " + patternMatches);
                System.out.println();
            }

        } while (!input.equalsIgnoreCase("exit"));

        System.out.println("Exiting the program. Goodbye!");
        scanner.close();
        
        // Apply Soundex to the dictionary
//        Map<String, List<String>> soundexMap = new HashMap<>();
//
//        for (String word : movieTitles) {
//            String encoded = SoundexVN.encode(word);
//            soundexMap.computeIfAbsent(encoded, k -> new ArrayList<>()).add(word);
//        }
//
//        SoundexVN search = new SoundexVN(soundexMap);
//
//        List<String> suggestions = search.suggest(input);
//        List<String> patternMatches = new KMP().patternSearch(input, movieTitles);
//
//        System.out.println("Suggestions for '" + input + "': " + suggestions);
//        System.out.println("Pattern Search Matches for '" + input + "': " + patternMatches);
    }
}
