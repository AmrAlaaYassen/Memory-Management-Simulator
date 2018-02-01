package memorySimulator ;

public class Partition {
    private int startingMemoryAddress ;
    private int partitionSize ;
    private int allocatedMemorySize ;
    private boolean status ;
    Partition () {
        startingMemoryAddress = -1 ;
        partitionSize = allocatedMemorySize = 0 ;
        status = true ;
    }
    Partition (int pStartingMemoryAddress , int pPartitionSize , int pAllocatedMemory , boolean pStatus) {
        this.startingMemoryAddress = pStartingMemoryAddress ;
        this.allocatedMemorySize = pAllocatedMemory ;
        this.partitionSize = pPartitionSize ;
        this.status = pStatus ;
    }
    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getAllocatedMemorySize() {
        return allocatedMemorySize;
    }

    public void setAllocatedMemorySize(int allocatedMemorySize) {
        this.allocatedMemorySize = allocatedMemorySize;
    }

    public int getPartitionSize() {
        return partitionSize;
    }

    public void setPartitionSize(int partitionSize) {
        this.partitionSize = partitionSize;
    }

    public int getStartingMemoryAddress() {
        return startingMemoryAddress;
    }

    public void setStartingMemoryAddress(int startingMemoryAddress) {
        this.startingMemoryAddress = startingMemoryAddress;
    }

    public void addSpace (int space ){
        this.partitionSize+=space ;
    }
    public Partition allocate (int length)  {

        if (partitionSize - length >= 0 ) {

           this.status = true ;
           int remainingMemory  = this.partitionSize-length ;
           this.partitionSize = length ;
           this.allocatedMemorySize = length ;

           return new Partition(this.getStartingMemoryAddress()+length , remainingMemory ,0 , false ) ;
        }
        return null ;
    }
}
