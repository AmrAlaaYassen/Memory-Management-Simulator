package memorySimulator ;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in) ;
        System.out.println("Enter The Initial Memory Size : ");
        try {
            Memory memory = new Memory(scanner.nextInt()) ;
            while (true) {
                System.out.println("1- Allocation(policy type) ");
                System.out.println("2- DeAllocation ()");
                System.out.println("3-  Defragmentation(case type) ");
                System.out.println("4-  Print Memory status ");
                System.out.println("5- Exit");
                int option = scanner.nextInt() ;
                switch (option) {
                    case 1 :
                        memory.Allocation();
                        break;
                    case 2:
                        System.out.println(memory.deAllocation() );
                        break;
                    case 3 :
                        memory.deFragmentation();
                        break;
                    case 4 :
                        System.out.println(memory);
                        break;
                    case 5 :
                        return;
                    default:
                        System.out.println("Not An Exist Option ");
                        break;
                }
            }
        }catch (Exception ex ){
            System.out.println("Error while doing processing or taking an Input");
        }

    }
}
