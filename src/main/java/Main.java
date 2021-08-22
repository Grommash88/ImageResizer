import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import util.ImageResizerUtil;

public class Main {

  public static void main(String[] args) {

    //Инициализация путей и ширины.
    Scanner scanner = new Scanner(System.in);
    File srcDir = ImageResizerUtil.getSrcFolder(scanner);
    ImageResizerUtil.setDstFolder(scanner);
    ImageResizerUtil.setNewWidth(scanner);
    //Работа с данными.
    ImageResizerUtil.resizeFiles(
        List.of(Objects.requireNonNull(srcDir.listFiles())), System.currentTimeMillis());
  }
}

