package test.roundrobin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RoundRobin {
    
    public static void Add_Process(List<List<Process>> priorityTable, Process process, int priority) {
        priorityTable.get(priority - 1).add(process);
    }
    
    public static List<Process> Process_Search(List<List<Process>> priorityTable, int targetElement)
    {
        List<Process> Target_Processes = new ArrayList<>();
        for (int i = 0; i < priorityTable.size(); i++) {
            List<Process> row = priorityTable.get(i);
            for (int j = 0; j < row.size(); j++) {
                if (row.get(j).ArrivalTime == targetElement) {
                    Target_Processes.add(row.get(j)); 
                }
            }
        }
        return Target_Processes;
    }
    
    public static Process Process_Search_Priority(List<Process> priorityTable,int tick)
    {
        int max = 8;
        int min = 99999;
        Process Priority = null;
        List<Process> MaxPrioritys = new ArrayList<>();
        
        for (int i = 0; i < priorityTable.size(); i++) {          
            if (priorityTable.get(i).ArrivalTime < min && priorityTable.get(i).ArrivalTime <= tick) {
                min = priorityTable.get(i).ArrivalTime;
                MaxPrioritys.add(priorityTable.get(i));
            }
        }
        
        
        for (int i = 0; i < MaxPrioritys.size(); i++) {          
            if (MaxPrioritys.get(i).Priority < max) {
                max = MaxPrioritys.get(i).Priority;
            }
        }
        
        
        
        for (int i = 0; i < MaxPrioritys.size(); i++) {          
            if (MaxPrioritys.get(i).Priority == max) { 
                Priority = MaxPrioritys.get(i);
            }
        }
        
        
        
        
        return Priority;
    }
    
    
    
    
    
    public static void main(String[] args) {
        System.out.println("Would you like to manually enter parameters? Y/N");
        String Input = UserInput.getString();
        
        int numRows = 7;

        List<List<Process>> priorityTable = new ArrayList<>();
        
        for (int i = 0; i < numRows; i++) {
            priorityTable.add(new ArrayList<>());
        }
        
        if("Y".equals(Input) || "y".equals(Input))
        {
            System.out.println("How many processes would you like? ");
            int Ammount = UserInput.getInteger();
            
            for(int i = 0;i < Ammount;i++)
            {
                System.out.println("\n" + "Process:" + i + " add Name, ArrivalTime, BurstTime, Priority:");
                Process User_Process = new Process(UserInput.getString(),UserInput.getInteger(),UserInput.getInteger(),UserInput.getInteger());
                
                int userPriority = User_Process.getPriority();
                Add_Process(priorityTable, User_Process, userPriority);
            }
        }
        else if("N".equals(Input) || "n".equals(Input))
        {   
            System.out.println("How many processes would you like? ");
            int Amount = UserInput.getInteger();
            Random random_Priority = new Random();
            System.out.println("Enter maximum Arrival Time ");
            int AT = UserInput.getInteger();
            Random random_AT = new Random();
            System.out.println("Enter maximum Burst Time ");
            int BT = UserInput.getInteger();
            Random random_BT = new Random();
            
            for(int i = 0; i < Amount;i++)
            {
                Process User_Process = new Process(String.valueOf(i),random_AT.nextInt(AT+1),random_BT.nextInt(BT) + 1,random_Priority.nextInt(7) + 1);
                
                int userPriority = User_Process.getPriority();
                Add_Process(priorityTable, User_Process, userPriority);
            }
            
        }
        
        System.out.println("\n" + "Processes Table:" + "\n");       
        for (int i = 0; i < numRows; i++) {
            List<Process> priorityList = priorityTable.get(i);
            System.out.println("Priority " + (i + 1) + ": " + "\n" + priorityList);
        }
        
        
        System.out.println("\n" + "Enter Time Quantom \n");
        int Time_Quantom = UserInput.getInteger();
        
        boolean end = false;
        
        List<Process> Arrival_Table = new ArrayList<>();
        
        
        int Arrival_Sum = 0;
        
        for (List<Process> row : priorityTable) {
            for (Process value : row) {
                Arrival_Sum += value.ArrivalTime; 
            }
        }
        
        
        
        for (int i = 0; i <= Arrival_Sum; i++) {
            List<Process> Current_Processes = Process_Search(priorityTable, i);
            if(!Current_Processes.isEmpty())
            {
                for(int y = 0; y < Current_Processes.size();y++)
                {
                    Arrival_Table.add(Process_Search(priorityTable, i).get(y));
                }
            }
        }
        
        System.err.println("Arrival Table: \n");
        
        for (int i = 0; i < Arrival_Table.size(); i++) {
            System.err.println("Process " + i + "\n" + Arrival_Table.get(i));
        }
        
        
        Process Excecuting_Process = Arrival_Table.get(0);
        int Tick = Arrival_Table.get(0).ArrivalTime;
        Excecuting_Process.Initialized = true;
        Excecuting_Process.Tick_Responded = 0;
        
        int Processes_Num = Arrival_Table.size();
        int Avg_Turnaround = 0;
        int Avg_Response = 0;
        
        
        System.err.println("Initializing Round Robin Algorithm \n");
        
        while(end != true)
        {   
            System.out.println("\nTick: "+ Tick);
            System.out.println("Excecuting: "+ Excecuting_Process);
            if(Excecuting_Process != null)
            {
                if(Excecuting_Process.RemainingTime > Time_Quantom)
                {
                   Excecuting_Process.RemainingTime -= Time_Quantom;
                   System.err.println("Process " + Excecuting_Process.Name + " Back on ready queue, " + Excecuting_Process.RemainingTime + " Ticks remaining");
                   Process Temp = Excecuting_Process;
                   Arrival_Table.remove(Excecuting_Process);
                   Arrival_Table.add(Temp);
                   Tick += Time_Quantom;
                   Excecuting_Process = Process_Search_Priority(Arrival_Table,Tick);
                   if(Excecuting_Process != null && Excecuting_Process.Initialized == false)
                   {
                       Excecuting_Process.Initialized = true;
                       Excecuting_Process.Response = Tick - Excecuting_Process.ArrivalTime;
                   }else if(Excecuting_Process == null) Tick+=1;
                   
                }
                else
                {
                   int Remaining_Quantom = Time_Quantom - Excecuting_Process.RemainingTime;
                   System.err.println("Process " + Excecuting_Process.Name + " Excecuted \n" + "Response: " + Excecuting_Process.Response + " Turnaround: " + (Tick - Excecuting_Process.ArrivalTime));
                   
                   Avg_Response += Excecuting_Process.Response;
                   Avg_Turnaround += Tick - Excecuting_Process.ArrivalTime;
                   
                   Arrival_Table.remove(Excecuting_Process);
                   Excecuting_Process = Process_Search_Priority(Arrival_Table,Tick);
                   
                   if(Excecuting_Process != null && Excecuting_Process.Initialized == false)
                   {
                       Excecuting_Process.Initialized = true;
                       Excecuting_Process.Response = Tick;
                   }
                   else if(Excecuting_Process == null) Tick+=1;
                   if(Remaining_Quantom != 0 && Excecuting_Process != null)
                   {
                        while(Remaining_Quantom > Excecuting_Process.RemainingTime)
                        {
                            Remaining_Quantom -= Excecuting_Process.RemainingTime;
                            System.err.println("Process " + Excecuting_Process.Name + " Excecuted in Quantom \n" + "Response: " + Excecuting_Process.Response + " Turnaround: " + (Tick - Excecuting_Process.ArrivalTime));
                            
                            Avg_Response += Excecuting_Process.Response;
                            Avg_Turnaround += Tick - Excecuting_Process.ArrivalTime;

                            int temp_remaining = Excecuting_Process.RemainingTime;
                            
                            Arrival_Table.remove(Excecuting_Process);
                            Excecuting_Process = Process_Search_Priority(Arrival_Table,Tick);
                            if(Excecuting_Process != null && Excecuting_Process.Initialized == false)
                            {
                                Excecuting_Process.Initialized = true;
                                Excecuting_Process.Response = Tick;
                            }
                            Tick += temp_remaining;
                        }
                   }
                   
                }
            }else 
            {
                Tick +=1;
                Excecuting_Process = Process_Search_Priority(Arrival_Table,Tick);
            }
            
            if(Arrival_Table.isEmpty())
            {
               end = true;
            }
        }
        
        System.err.println("\nRound Robin finished with \nAverage Response: " + (float)Avg_Response/Processes_Num + "\nAverage Turnaround: "+ (float)Avg_Turnaround/Processes_Num);
     }

}
