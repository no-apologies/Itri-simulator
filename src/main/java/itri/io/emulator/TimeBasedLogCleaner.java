package itri.io.emulator;

import itri.io.emulator.ConditionManager.ConditionIterator;
import itri.io.emulator.parameter.FileRecord;
import itri.io.emulator.parameter.Record;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

public class TimeBasedLogCleaner extends LogCleaner<Integer, FileRecord> {

  public TimeBasedLogCleaner(String filePath, IndexInfo info) {
    super(filePath, info);
  }

  @Override
  public Map<Integer, FileRecord> getLog() {
    return null;
  }

  @Override
  public void generate(ConditionManager manager, BufferedReader reader, IndexInfo info) {
    String line = null;
    String splited[] = null;
    int failCount = 0;
    int failMax = 3;
    int passedCount = 0;
    Conditions cond = null;
    int targetPassed = manager.getFiltersNumber();
    ConditionIterator iter = (ConditionIterator) manager.iterator();
    boolean firstLine = true, secondLine = true;

    LOOP:
    try {
      while ((line = reader.readLine()) != null) {
        /**
         * 1. Filter record
         */

        /**
         * This section should be removed, when the source file is .csv format. So far, the first
         * line are columns name, second line is "-------"
         **/
        if (firstLine) {
          firstLine = false;
          continue;
        }
        if (secondLine) {
          secondLine = false;
          continue;
        }
        /*************************************************************************/

        passedCount = 0;
        splited = trimedArrays(line);
        setChanged();
        notifyObservers(splited);
        while (iter.hasNext()) {
          cond = iter.next();
          try {
            if (cond.filter(splited, info)) passedCount++;
            else break;
          } catch (UnsupportedOperationException uoe) {
            // do nothing in this time based log generator
          }
        }
        iter.reset();
        if (passedCount != targetPassed) continue;

        /**
         * 2. Put passed record into appender for flush
         */
        setChanged();
        notifyObservers(new Record(splited, info));
      }
    } catch (IOException e) {
      System.err.println(e.getMessage());
      System.err.println("Reading " + line + " has problem. Try one more time." );
      if (++failCount < failMax) break LOOP;
    }
  }

  @Override
  public void groupBy(ConditionManager manager) {
    GroupByOption.Option[] groupByOption = { GroupByOption.Option.TIME_SEQ };
    manager.addGroupByCondition(groupByOption);
  }
}
