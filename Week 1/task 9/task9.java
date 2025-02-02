import java.io.*;
import java.util.*;

class LogFileAnalyzer {

    private List<String> keywords;

    public LogFileAnalyzer(List<String> keywords) {
        this.keywords = keywords;
    }

    public void analyzeLogFile(String inputFile, String outputFile) {
        Map<String, Integer> keywordCount = new HashMap<>();

        for (String keyword : keywords) {
            keywordCount.put(keyword, 0);
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                for (String keyword : keywords) {
                    if (line.contains(keyword)) {
                        keywordCount.put(keyword, keywordCount.get(keyword) + 1);
                    }
                }
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
                for (Map.Entry<String, Integer> entry : keywordCount.entrySet()) {
                    writer.write(entry.getKey() + ": " + entry.getValue());
                    writer.newLine();
                }
                System.out.println("Analysis complete. Results saved to " + outputFile);
            }

        } catch (IOException e) {
            System.out.println("Error reading or writing file: " + e.getMessage());
        }
    }
}

public class task9 {
    public static void main(String[] args) {
        List<String> keywords = Arrays.asList("ERROR", "WARNING", "INFO");

        
        LogFileAnalyzer analyzer = new LogFileAnalyzer(keywords);
        analyzer.analyzeLogFile("input.log", "analysis_result.txt");
    }
}
