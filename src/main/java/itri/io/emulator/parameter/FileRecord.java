package itri.io.emulator.parameter;

import itri.io.emulator.common.FileDirectoryFactory;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Those wanted records collected in the flusher for flush.
 * This will be refactored in the coming future.
 */
public class FileRecord {
  private String groupName;
  private LinkedList<Record> records;

  public FileRecord(String groupName) {
    this.groupName = groupName;
    records = new LinkedList<>();
  }

  public void addRecord(Record record) {
    records.addLast(record);
  }

  public Record getRecentlyAddedRecord() {
    return records.getLast();
  }
  
  public String getGroupName() {
    return groupName;
  }

  public List<Record> getRecords() {
    return records;
  }

  public void clear() {
    records.clear();
  }

  public void bufferedWriteToFile(String outPath) {
    try (BufferedWriter writer =
         new BufferedWriter(new FileWriter(FileDirectoryFactory.createNewFile(outPath)))) {
      writer.write("File: " + groupName + "\n");
      for (Record entry : records) {
        writer.write(entry + "\n");
      }
      writer.flush();
    } catch (IOException ie) {
      System.err.println(ie.getMessage());
    }
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder(groupName);
    builder.append("\n[\n");
    for (Record entry : records) {
      builder.append("\t" + entry + "\n");
    }
    builder.append("]\n");
    return builder.toString();
  }
}
