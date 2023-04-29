import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class csv_parsing {
    public static void main(String[] args) throws IOException{
        Scanner scanner = new Scanner(System.in);
        String filePath = "C:\\Users\\Aleks\\Desktop\\airports.csv";
        List<Model> models = parseProductCsv(filePath);
        System.out.println("Введите название компании");
        String nameComp = scanner.nextLine();
        if(nameComp.charAt(0) != '\"'){
            nameComp ="\"" + nameComp;
        }
        if(Character.isLowerCase(nameComp.charAt(1))){
            nameComp = nameComp.replace(nameComp.charAt(1),Character.toUpperCase(nameComp.charAt(1)));
        }
        int quantitasComp = 0;
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < models.size(); i++) {
            if(models.get(i).allParameters.get(0).get(1).startsWith(nameComp)){
                System.out.println(models.get(i).allParameters.get(0).get(1) + " " + models.get(i).allParameters);
                quantitasComp++;
            }

        }
        long stopTime = System.currentTimeMillis() - startTime;
        System.out.println("Всего компаний найдено: " + quantitasComp + " Время на поиск ушло: " + stopTime);
    }
    private static List<Model> parseProductCsv(String filePath) throws IOException {
        List<Model> models = new ArrayList<>();
        List<String> fileLines = Files.readAllLines(Paths.get(filePath));
        System.out.println(fileLines.get(1000));
        for (String fileLine : fileLines) {
            String[] splitedText = fileLine.split(",");
            ArrayList<String> columnList = new ArrayList<>();
            for (int i = 0; i < splitedText.length; i++) {
                if (IsColumnPart(splitedText[i])) {
                    String lastText = columnList.get(columnList.size() - 1);
                    columnList.set(columnList.size() - 1, lastText + "," + splitedText[i]);
                } else
                    columnList.add(splitedText[i]);
            }
            Model model = new Model();
            model.allParameters.add(columnList);
            models.add(model);
        }
        return models;
    }
    private static boolean IsColumnPart(String text){
        String trimText = text.trim();
        return trimText.indexOf("\"") == trimText.lastIndexOf("\"") && trimText.endsWith("\"");
    }
}
class Model{
    ArrayList<ArrayList<String>> allParameters = new ArrayList<>();
}