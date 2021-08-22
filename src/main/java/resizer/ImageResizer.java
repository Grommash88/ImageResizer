package resizer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import javax.imageio.ImageIO;
import util.AppLogger;
import org.imgscalr.Scalr;

public class ImageResizer extends Thread {

  private final List<File> files;
  private final String dstFolder;
  private final int newWidth;
  private final long start;

  public ImageResizer(List<File> files, int newWidth, String dstFolder, long start) {

    this.files = files;
    this.dstFolder = dstFolder;
    this.newWidth = newWidth;
    this.start = start;
  }

  @Override
  public void run() {

    try {
      for (File file : files) {
        BufferedImage image = ImageIO.read(file);
        if (image == null) {
          continue;
        }
        int newHeight = (int) Math.round(
            image.getHeight() / (image.getWidth() / (double) newWidth)
        );
        BufferedImage newImage = Scalr.resize(image, newWidth, newHeight, Scalr.OP_ANTIALIAS);

        File newFile = new File(dstFolder.concat("/".concat(file.getName())));
        ImageIO.write(newImage, "jpg", newFile);
      }
    } catch (Exception ex) {
      ex.printStackTrace();
      AppLogger.logException(ex);
    }
  }
}
