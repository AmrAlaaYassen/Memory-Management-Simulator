package memorySimulator ;
import java.util.ArrayList;
import java.util.Scanner;

public class Memory {
    private ArrayList <Partition> partitions ;

    Memory () {
        partitions = new ArrayList<Partition>() ;
    }

    Memory (ArrayList<Partition>pPartitions) {
        partitions = pPartitions ;
    }
    Memory (int initialMemorySize ) {
        Partition mainMemory = new Partition(0,initialMemorySize,0,false) ;
        partitions = new ArrayList<Partition>()  ;
        partitions.add(mainMemory) ;
    }

    public void Allocation () {
        try {
            Scanner scanner = new Scanner(System.in) ;
            System.out.println("Enter Method Number \n 1- FirstFit \n 2- bestFit \n 3- WorstFit");
            int method = scanner.nextInt() ;
            System.out.print("Enter The Memory Size : ");
            int memoryToBeAllocated = scanner.nextInt() ;
            switch (method) {
                case 1:
                    firstFit(memoryToBeAllocated);
                    break;
                case 2:
                    bestFit(memoryToBeAllocated);
                    break;
                case 3 :
                    worstFit(memoryToBeAllocated);
                    break;
                default:
                    System.out.println("This Method Does Not EXIST!");
                    break;
            }
        }catch (Exception ex) {
            System.out.println(ex) ;
        }

    }

    public boolean deAllocation () {
        try {
            System.out.println("Enter Starting Memory Address");
            Scanner scanner = new Scanner(System.in);
            int startingMemoryAddress = scanner.nextInt();

            for (Partition partition : partitions) {
                if (partition.getStartingMemoryAddress() == startingMemoryAddress) {
                    if (!partition.getStatus()) {
                        System.out.println("Partition is Freed And Available ALREADY!");
                        return false;
                    }
                    partition.setStatus(false);
                    partition.setAllocatedMemorySize(0);
                    return true;
                }
            }

            return false;
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return false;
    }



    public void deFragmentation ( ){
        try {
            System.out.println("Enter Method Number \n 1- Internal Fragmentation \n 2- Contiguous Blocks \n 3- Non Contiguous Blocks");
            Scanner scanner = new Scanner(System.in) ;
            int method =scanner.nextInt() ;
            switch (method) {
                case 1 :
                    internalFragmentation();
                    break;
                case 2:
                    fragmentationContiguousBlocks();
                    break;
                case 3:
                    fragmentationNonContiguousBlocks();
                    break;
                default:
                    System.out.println("Method does NOT Exist!");
                    break;
            }
        }catch (Exception ex){
            System.out.println(ex);
        }

    }
    private void firstFit (int memoryToBeAllocated) {
        boolean fit = false ;

        for (Partition partition :partitions){

            if(partition.getStatus()) continue;  // mean that partition has been used before

            if (partition.getPartitionSize() >= memoryToBeAllocated) {

                Partition remainingPartition = partition.allocate(memoryToBeAllocated) ;
                fit = true ;
                if (remainingPartition != null && remainingPartition.getPartitionSize()>0) {
                    partitions.add(remainingPartition) ;
                }
                break;
            }
        }

        if (!fit)
            System.out.println("there is no Partition free enough for that SIZE! ");

    }

    private void worstFit (int memoryToBeAllocated){
        int index = -1 , max = -1 ;
        for (Partition partition :partitions) {
            if (partition.getStatus()) continue;  // mean that partition has been used before

            if (partition.getPartitionSize() >= memoryToBeAllocated && partition.getPartitionSize() > max) {
                index = partitions.indexOf(partition) ;
                max = partition.getPartitionSize() ;
            }
        }

        if (index!= -1) {

            Partition remainingPartition = partitions.get(index).allocate(memoryToBeAllocated) ;
            if (remainingPartition != null && remainingPartition.getPartitionSize()>0) {
                partitions.add(remainingPartition) ;
            }
        }else
            System.out.println("there is no Partition free enough for that SIZE! ");

    }

    private void bestFit (int memoryToBeAllocated) {
        int index = -1 , min = 100000000 ;
        for (Partition partition :partitions) {
            if(partition.getStatus()) continue;

            if (partition.getPartitionSize() >= memoryToBeAllocated && partition.getPartitionSize()<min) {
                min = partition.getPartitionSize() ;
                index = partitions.indexOf(partition) ;
            }
        }

        if (index != -1){
            Partition remainingMemory = partitions.get(index).allocate(memoryToBeAllocated) ;
            if (remainingMemory != null && remainingMemory.getPartitionSize() >0){
                partitions.add(remainingMemory) ;
            }
        } else {
            System.out.println("there is no Partition free enough for that SIZE! ");
        }
    }

    private void internalFragmentation () {
        for (Partition partition :partitions) {
            if(partition.getPartitionSize()-partition.getAllocatedMemorySize() != 0 && partition.getAllocatedMemorySize() !=0) {
                System.out.println("fragmentation case 1");
                int len =  partition.getPartitionSize() - partition.getAllocatedMemorySize() ; ;
                Partition remainingMemory = new Partition(partition.getStartingMemoryAddress()+partition.getPartitionSize(),
                        len,0,false) ;
                partitions.add(remainingMemory) ;
                partition.setPartitionSize(partition.getPartitionSize()-len);
                partition.setStatus(true);
            }
        }
    }

    private void fragmentationContiguousBlocks () {
        for (int i = 0 ; i < partitions.size()-1 ; i++ ) {


            if (!partitions.get(i).getStatus()&&!partitions.get(i+1).getStatus()) {

                partitions.get(i) .setPartitionSize(partitions.get(i) .getPartitionSize()+partitions.get(i+1).getPartitionSize());
                partitions.remove(i+1) ;
                i-- ;
            }
        }
    }

    private void fragmentationNonContiguousBlocks() {
        Partition holder = new Partition() ;
        holder.setStatus(false);

        for (int i = 0 ; i < partitions.size() ; i++ ){
            if(!partitions.get(i).getStatus()){ // mean if partition is a free partition
                holder.addSpace(partitions.get(i).getPartitionSize());
                partitions.remove(i) ;
                i-- ;
                this.modifyStartingPositions();

            }
        }

        holder.setStartingMemoryAddress(partitions.get(partitions.size()-1).getStartingMemoryAddress()+
                partitions.get(partitions.size()-1).getPartitionSize());
        partitions.add(holder) ;
    }

    private void modifyStartingPositions () {
        for (int i =0 ; i<partitions.size() ; i++ ){
            if (i == 0 ) {
                partitions.get(i).setStartingMemoryAddress(0);
                continue;
            }
            partitions.get(i).setStartingMemoryAddress(partitions.get(i-1).getStartingMemoryAddress()+partitions.get(i-1).getPartitionSize());
        }
    }
    @Override
    public String toString() {

        for (Partition partition : partitions){
            System.out.println("starting memory address : "+partition.getStartingMemoryAddress() + "\n" + "Partition Size : " +partition.getPartitionSize());
            System.out.println("Allocated memory : " +partition.getAllocatedMemorySize() + "\n" + "Status : "  + partition.getStatus());
            System.out.println("================================================================");
        }
      return "\n" ;
    }
}
