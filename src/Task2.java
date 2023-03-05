//    Задача 2** (не обязательно)
//    В файловую систему одного суперкомпьютера проник вирус, который сломал контроль
//        за правами доступа к файлам. Для каждого файла известно,
//        с какими действиями можно к нему обращаться:
//
//    запись W,
//    чтение R,
//    запуск X.
//    Файл files.txt
//    В первой строке содержится число N — количество файлов, содержащихся в данной файловой системе.
//
//    В следующих N строчках содержатся имена файлов и допустимых с ними операций,
//        разделенные пробелами.
//
//    Файл operations.txt
//    Далее указано число M — количество запросов к файлам.
//
//    В последних M строках указан запрос вида Операция Файл.
//
//    К одному и тому же файлу может быть применено любое колличество запросов.
//
//    Вам требуется восстановить контроль над правами доступа к файлам.
//
//    Файл results.txt
//    Ваша программа для каждого запроса должна будет выводить Файл: Операция: OK,
//        если над файлом выполняется допустимая операция, или же Файл: Операция: Access denied,
//        если операция недопустима.
//
//    Пример входных данных
//    4
//    helloworld.exe R X
//    pinglog W R
//    nya R
//    goodluck X W R
//    5
//    read nya
//    write helloworld.exe
//    execute nya
//    read pinglog
//    write pinglog
//    Пример выходных данных
//    nya: read: OK
//    helloworld.exe: write: Access denied
//    nya: execute: Access denied
//    pinglog: read: OK
//    pinglog: write: OK

import java.io.BufferedReader;
import java.io.IOException;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Task2 {
  public static final String SEP = " ";
  public static Map<String, List<String>> filePermission = new HashMap<>();
  public static Map<String, String> operConv = new HashMap<>() {{
    put("write", "w");
    put("read", "r");
    put("execute", "x");
  }};
  public static List<List<String>> operationList = new ArrayList<>();

  public static void readFilesList(File filesPath) throws IOException {
    BufferedReader inputFileReader = new BufferedReader(new FileReader(filesPath));
    try {
      int n = Integer.parseInt(inputFileReader.readLine());
      for (int i = 0; i < n; ++i) {
        List<String> permissions = new ArrayList<>();
        String row = inputFileReader.readLine();
        int sepPoz = row.indexOf(SEP);
        String filename = row.substring(0, sepPoz);
        do {
          permissions.add(row.substring(sepPoz + 1, sepPoz + 2));
          sepPoz = sepPoz + 2;
        } while (sepPoz < row.length());
        filePermission.put(filename, permissions);
      }
    } catch (FileNotFoundException e) {
      System.out.println("File '" + filesPath + "' not found" + e.getMessage());
    } catch (NumberFormatException e) {
      System.out.println("Invalid data format" + e.getMessage());
    } catch (EOFException e) {
      System.out.println("Unexpected end of file " + filesPath + e.getMessage());
    } catch (IndexOutOfBoundsException e) {
      System.out.println("Index negative or larger that string length" + e.getMessage());
    } finally {
      inputFileReader.close();
    }
  }

  public static void readOperations(File operationsPath) throws IOException {
    BufferedReader inputOperationsReader = new BufferedReader(new FileReader(operationsPath));
    try {
      int m = Integer.parseInt(inputOperationsReader.readLine());
      for (int i = 0; i < m; ++i) {
        String row = inputOperationsReader.readLine();
        int sepPoz = row.indexOf(SEP);
        String operation = row.substring(0, sepPoz);
        String filename = row.substring(sepPoz + 1);
        List<String> singleOperation = new ArrayList<>();
        singleOperation.add(filename);
        singleOperation.add(operation);
        operationList.add(singleOperation);
      }
    } catch (FileNotFoundException e) {
      System.out.println("File '" + operationsPath + "' not found" + e.getMessage());
    } catch (NumberFormatException e) {
      System.out.println("Invalid data format" + e.getMessage());
    } catch (EOFException e) {
      System.out.println("Unexpected end of file " + operationsPath + e.getMessage());
    } catch (IndexOutOfBoundsException e) {
      System.out.println("Index negative or larger that string length" + e.getMessage());
    } finally {
      inputOperationsReader.close();
    }

  }

  public static void checkOperations(File resultsPath) throws IOException {
    FileWriter resultsWriter = new FileWriter(resultsPath);
    try {
      for (List<String> strings : operationList) {
        String filename = strings.get(0);
        String operation = strings.get(1);
        String operToCheck = operConv.get(operation).toUpperCase();
        if (filePermission.get(filename).contains(operToCheck)) {
          String result = String.format("%s: %s: %s%n", filename, operation, "Ok");
          resultsWriter.write(result);
        } else {
          String result = String.format("%s: %s: %s%n", filename, operation, "Access denied");
          resultsWriter.write(result);
        }
      }
    } catch (IndexOutOfBoundsException e) {
      System.out.println("Invalid index" + e.getMessage());
    } catch (NullPointerException e) {
      System.out.println("File name or operation missing" + e.getMessage());
    }
    resultsWriter.close();
  }

  public static void main(String[] args) throws IOException {
    try {
    File filesPath = new File("res/files.txt");
    File operationsPath = new File("res/operations.txt");
    File resultsPath = new File("res/results.txt");
    resultsPath.createNewFile();
    readFilesList(filesPath);
    readOperations(operationsPath);
    checkOperations(resultsPath);
  } catch (NullPointerException e) {
      System.out.println("Filename is empty" + e.getMessage());
    }
  }
}