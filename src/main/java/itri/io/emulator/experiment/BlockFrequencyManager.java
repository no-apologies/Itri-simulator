package itri.io.emulator.experiment;

import itri.io.emulator.parameter.FileSize;

public class BlockFrequencyManager implements Comparable<BlockFrequencyManager>{
  private BlockWithFrequency[] blocks;
  private long totalFrequency;
  
  public BlockFrequencyManager(FileSize size) {
    int len = (int) (size.getSize() / Block.UNIT_4K);
    len = (int) (size.getSize() % Block.UNIT_4K == 0 ? len : len + 1);
    blocks = new BlockWithFrequency[len];
    for (int i = 0; i < blocks.length; i++) {
      blocks[i] = new BlockWithFrequency(i);
    }
    totalFrequency = 0;
  }

  public void updateBlocksFrequency(long offset, int length) {
    int startBlock = (int) (offset / Block.UNIT_4K);
    int endBlock = (int) ((offset + length) / Block.UNIT_4K);
    int remainder = (int) ((offset + length) % Block.UNIT_4K);
    if (remainder == 0) endBlock--;
    for (int i = startBlock; i <= endBlock; i++) {
      blocks[i].updateFrequency();
      totalFrequency++;
    }
  }

  public BlockWithFrequency[] getBlocks() {
    return blocks;
  }

  public int getBlocksSize() {
    return blocks.length;
  }

  public long getBlocksFrequency() {
    long fre = 0;
    for (BlockWithFrequency blc : blocks) {
      fre += blc.getFrequency();
    }
    return fre;
  }
  public long getTotalFrequency(){
	return totalFrequency;
  }

  @Override
  public int compareTo(BlockFrequencyManager o) {
    return Long.compare(this.getTotalFrequency(), o.getTotalFrequency());
  }
}
