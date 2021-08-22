package enumirations;

public enum ExceptionMsg {

  INCORRECT_DATA_MSG("Incorrect data."),
  INVALID_PATH_MSG("Invalid path."),
  DIR_NOT_EXIST(
      "The specified file does not exist or is not a folder.".concat(System.lineSeparator())
          .concat("Specify the path to the folder to process.")),
  SP_FILE_NOT_DIR(
      "The specified file is not a folder.".concat(System.lineSeparator())
          .concat("Specify the path to the folder where you want to save data."));

  private String msg;

  ExceptionMsg(String msg) {

    this.msg = msg;
  }

  public String getMsg() {
    return msg;
  }
}
