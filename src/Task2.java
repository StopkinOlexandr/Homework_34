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
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Task2 {
  public static final String SEP = " ";

  public static void main(String[] args) throws IOException {
    Map<String, List<String>> filePermission = new HashMap<>();
    Map<String, String> operConv = new HashMap<>() {{
      put("write", "w");
      put("read", "r");
      put("execute", "x");
    }};
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    int n = Integer.parseInt(br.readLine());
    for (int i = 0; i < n; ++i) {
      List<String> permissions = new ArrayList<>();
      String row = br.readLine();
      int sepPoz = row.indexOf(SEP);
      String filename = row.substring(0, sepPoz);
      do {
        permissions.add(row.substring(sepPoz + 1, sepPoz + 2));
        sepPoz = sepPoz + 2;
      } while (sepPoz < row.length());
      filePermission.put(filename, permissions);
    }

    int m = Integer.parseInt(br.readLine());
    for (int i = 0; i < m; ++i) {
      String row = br.readLine();
      int sepPoz = row.indexOf(SEP);
      String operation = operConv.get(row.substring(0, sepPoz));
      String filename = row.substring(sepPoz + 1);

      if (filePermission.get(filename).contains(operation)) {
        System.out.printf("%s: %s: %s%n", filename, operation, "Ok");
      } else {
        System.out.printf("%s: %s: %s%n", filename, operation, "Access denied");
      }
    }
  }
}