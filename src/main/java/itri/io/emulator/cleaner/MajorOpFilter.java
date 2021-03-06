package itri.io.emulator.cleaner;

import itri.io.emulator.cleaner.FilterOption.MajorOpOption;
import itri.io.emulator.common.ColumnConstants;
import itri.io.emulator.common.Parameters;
import itri.io.emulator.parameter.MajorOp;

import org.apache.commons.csv.CSVRecord;

/**
 * Major Operation Filter
 */
public class MajorOpFilter extends Filter {
  private MajorOpOption[] mjOption;

  public MajorOpFilter(Parameters params) {
    this.mjOption = new MajorOpOption[params.getMajorNames().length];
    int index = 0;
    for (String mj : params.getMajorNames()) {
      mjOption[index++] = MajorOp.getMajorOpOption(mj);
    }
  }

  @Override
  public boolean filter(CSVRecord record) {
    for (MajorOpOption mjo : mjOption) {
      switch (mjo) {
        case IRP_READ: {
          if (MajorOp.isReadOp(record.get(ColumnConstants.MAJOR_OP))) return true;
          break;
        }
        case IRP_WRITE: {
          if (MajorOp.isWriteOp(record.get(ColumnConstants.MAJOR_OP))) return true;
          break;
        }
        case IRP_OTHER: {
          if (MajorOp.isOtherOp(record.get(ColumnConstants.MAJOR_OP))) return true;
          break;
        }
        case IRP_ALL: {
          if(MajorOp.isIRP(record.get(ColumnConstants.MAJOR_OP))) return true;
          break;
        }
      }
    }
    return false;
  }

  @Override
  public void setFilterOptions(Object options) {
    if (options.getClass() != MajorOpOption[].class) return;
    this.mjOption = (MajorOpOption[]) options;
  }
}
