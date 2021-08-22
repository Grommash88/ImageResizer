package enumirations;

public enum ConsoleMsg {

  SET_DATA_DIR_MSG(
      "Specify the path to the file source folder in the format of your operating system!!!"),
  SET_SAVE_DIR_MSG("Specify the path to the folder where you want to save the processed images!!!"),
  SET_IMG_WITH_MSG("Set the desired image width.");

  private String msg;

  ConsoleMsg(String msg) {

    this.msg = msg;
  }

  public String getMsg() {
    return msg;
  }
}
