package itri.io.emulator.simulator;

import itri.io.emulator.common.RandomTools;
import itri.io.emulator.parameter.OperationInfo;

import java.io.IOException;
import java.io.RandomAccessFile;

public class WriteOperation extends Operation {

  public WriteOperation(OperationInfo info) {
    super(info);
  }

  @Override
  public void operate(RandomAccessFile file) {
    byte[] ranBytes = RandomTools.generateByte(length);
    try {
      file.seek(offset);
      if (!isSync) {
        file.write(ranBytes);
      } else {
        synchronized (file) {
          file.write(ranBytes);
        }
      }
    } catch (IOException e) {
      System.out.println("Write error happens at " + offset + ", should write　" + length
          + " bytes.");
    }
  }
}
