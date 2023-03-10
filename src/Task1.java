import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Во всех задачах разбивайте решение на несколько коммитов:
//
//условие в комментарии и никакого кода
//решение без учёта файлов - чтение с клавиатуры и вывод на экран
//добавляем файлы, если они указаны в задаче
//разбиваем задачу на методы
//добавляем try..catch


// Задача 1
//Программисты, как вы уже знаете, постоянно учатся, а в общении между собой используют весьма
// специфический язык. Чтобы систематизировать ваш пополняющийся профессиональный лексикон,
// мы придумали эту задачу.
//
//Напишите программу создания небольшого словаря сленговых программерских выражений,
// чтобы она потом по запросу возвращала значения из этого словаря.
//
//Формат входных данных
//Файл dict.txt
//В первой строке задано одно целое число n — количество слов в словаре.
//
//В следующих n строках записаны слова и их определения, разделенные двоеточием и символом пробела.
//
//Ввод с клавиатуры
//В первой строке записано целое число m — количество поисковых слов, чье определение нужно вывести.
//
//В следующих m строках записаны сами слова, по одному на строке.
//
//Формат выходных данных
//Для каждого слова, независимо от регистра символов, если оно присутствует в словаре,
// необходимо вывести на экран его определение.
//
//Если слова в словаре нет, программа должна вывести "Не найдено", без кавычек.
//
//Примечание 1
//Мини-словарь для начинающих разработчиков можно посмотреть тут.
//
//Примечание 2
//Гарантируется, что в определяемом слове или фразе отсутствует двоеточие (:),
// следом за которым идёт пробел.
//
//Пример входных данных
//5
//Змея: язык программирования Python
//Баг: от англ. bug — жучок, клоп, ошибка в программе
//Конфа: конференция
//Костыль: код, который нужен, чтобы исправить несовершенство ранее написанного кода
//Бета: бета-версия, приложение на стадии публичного тестирования
//3
//Змея
//Жаба
//костыль
//Пример выходных данных
//язык программирования Python
//Не найдено
//код, который нужен, чтобы исправить несовершенство ранее написанного кода

public class Task1 {

  final public static String SEP = ": ";
  public static Map<String, String> dict = new HashMap<>();

  public static void readDict(File dictPath) throws IOException {
    BufferedReader inputFileReader = new BufferedReader(new FileReader(dictPath));
    try {
      int n = Integer.parseInt(inputFileReader.readLine());
      for (int i = 0; i < n; ++i) {
        String row = inputFileReader.readLine();
        int sepPoz = row.indexOf(SEP);
        String slang = row.substring(0, sepPoz);
        String explanation = row.substring(sepPoz + SEP.length());
        dict.put(slang, explanation);
      }
    } catch (FileNotFoundException e) {
      System.out.println("File '" + dictPath + "' not found" + e.getMessage());
    } catch (NumberFormatException e) {
      System.out.println("Invalid data format" + e.getMessage());
    } catch (EOFException e) {
      System.out.println("Unexpected end of file " + dictPath + e.getMessage());
    } catch (IndexOutOfBoundsException e) {
      System.out.println("Index negative or larger that string length" + e.getMessage());
    } finally {
      inputFileReader.close();
    }
  }

  public static List<String> inputSlangWords() throws IOException {
    List<String> output = new ArrayList<>();
    try {
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      int n = Integer.parseInt(br.readLine());
      for (int i = 0; i < n; ++i) {
        output.add(br.readLine().toLowerCase());
      }
    }catch (NumberFormatException e) {
      System.out.println("Invalid data format" + e.getMessage());
    }
    return output;
  }

  public static void printExplanation(List<String> explanations) {
    try {
      for (String explanation : explanations) {
        System.out.println(dict.getOrDefault(explanation, "Не найдено"));
      }
    }catch (NullPointerException e) {
      System.out.println("Argument is NULL");
    }
  }

  public static void main(String[] args) throws IOException {
    File dictPath = new File("res/dict.txt");
    readDict(dictPath);
    printExplanation(inputSlangWords());
  }
}
