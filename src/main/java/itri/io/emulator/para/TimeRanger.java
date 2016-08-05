package itri.io.emulator.para;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * The executiont time of an operation: post operation time - pre operation time.
 */
public class TimeRanger {
  private final static SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss:SSS");

  private String preOp;
  private String pstOp;

  private long preOpTime;
  private long pstOpTime;

  public TimeRanger(String preOp, String pstOp) {
    this.preOp = preOp;
    this.pstOp = pstOp;
    try {
      this.preOpTime = TIME_FORMAT.parse(preOp).getTime();
      this.pstOpTime = TIME_FORMAT.parse(pstOp).getTime();
    } catch (ParseException e) {
      System.err.println(e.getMessage());
      this.preOpTime = -1l;
      this.pstOpTime = -1l;
    }
  }

  public String getPreOpTime(boolean humanReadable) {
    if (humanReadable || preOpTime == -1l) return preOp;
    return String.valueOf(preOpTime);
  }

  public String getPstOpTime(boolean humanReadable) {
    if (humanReadable || pstOpTime == -1l) return pstOp;
    return String.valueOf(pstOpTime);
  }

  public long getExecutionTime() {
    return pstOpTime - preOpTime;
  }

  @Override
  public String toString() {
    return String.valueOf(pstOpTime - preOpTime);
  }
}
