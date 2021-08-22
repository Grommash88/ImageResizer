package util;

import enumirations.ConsoleMsg;
import enumirations.ExceptionMsg;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import resizer.ImageResizer;

public class ImageResizerUtil {

  private static int newWidth;
  private static String dstFolder;

  //Метод запускает для каждого элемента массива свой поток.
  public static void resizeFiles(List<File> ascSortedItems, long start) {

    partition(ascSortedItems, Runtime.getRuntime().availableProcessors()).forEach(fa -> {
      ImageResizer resizer = new ImageResizer(fa, newWidth, dstFolder, start);
      resizer.start();
    });
  }

  //Метод установки ширины
  public static void setNewWidth(Scanner scanner) {

    while (true) {
      try {
        AppLogger.logMessage(ConsoleMsg.SET_IMG_WITH_MSG.getMsg());
        String insertString = scanner.nextLine();
        if (theCorrectFormat(insertString)) {
          newWidth = Integer.parseInt(insertString);
          break;
        } else {
          throw new IOException(ExceptionMsg.INCORRECT_DATA_MSG.getMsg());
        }
      } catch (IOException e) {
        AppLogger.logException(e);
      }
    }
  }

  //Задаем путь к папке с обрабатываемыми данными.
  public static File getSrcFolder(Scanner scanner) {

    while (true) {
      try {
        AppLogger.logMessage(ConsoleMsg.SET_DATA_DIR_MSG.getMsg());
        String string = scanner.nextLine();
        if (thisIsTheCorrectPath(string)) {
          File srcDir = new File(string);
          if (srcDir.isDirectory() && srcDir.exists()) {
            return srcDir;
          } else {
            throw new IOException(ExceptionMsg.DIR_NOT_EXIST.getMsg());
          }
        } else {
          throw new IOException(ExceptionMsg.INVALID_PATH_MSG.getMsg());
        }
      } catch (IOException e) {

        AppLogger.logException(e);
      }
    }
  }

  //Задаем путь для сохранения измененных изображений.
  public static void setDstFolder(Scanner scanner) {

    while (true) {
      try {
        AppLogger.logMessage(ConsoleMsg.SET_SAVE_DIR_MSG.getMsg());
        String string = scanner.nextLine();
        if (thisIsTheCorrectPath(string)) {
          File dstDir = new File(string);
          if (!dstDir.exists()) {
            Files.createDirectory(dstDir.toPath());
            dstFolder = string;
            break;
          } else {
            if (dstDir.isDirectory()) {
              dstFolder = string;
              break;
            } else {
              throw new IOException(ExceptionMsg.SP_FILE_NOT_DIR.getMsg());
            }
          }
        } else {
          throw new IOException(ExceptionMsg.INVALID_PATH_MSG.getMsg());
        }
      } catch (IOException e) {
        AppLogger.logException(e);
      }
    }
  }

  //Метод равномерно распределяет файлы, из входного массива.
  private static <T> List<List<T>> partition(List<T> ascSortedItems, int CPUCoreCount) {

    if (CPUCoreCount < 2) {
      if (CPUCoreCount < 1) {
        throw new IllegalArgumentException();
      }
      return Collections.singletonList(ascSortedItems);
    }
    int listSize = ascSortedItems.size();

    if (listSize <= CPUCoreCount) {
      return ascSortedItems.stream()
          .map(Collections::singletonList)
          .collect(Collectors.toList());
    }
    return IntStream.range(0, CPUCoreCount)
        .mapToObj(i -> ascSortedItems.subList(i * listSize / CPUCoreCount,
            (i + 1) * listSize / CPUCoreCount))
        .collect(Collectors.toList());
  }

  //Метод проверяет корректность указанной ширины.
  private static boolean theCorrectFormat(String string) {

    return string.matches("[0-9]*[1-9][0-9]+");
  }

  //Проверка корректности пути.
  private static boolean thisIsTheCorrectPath(String path) {

    return path.matches("[A-Z][:]\\\\.+") || path.matches("[A-Z][:]/.+") || path
        .matches("/users/.+");
  }
}
