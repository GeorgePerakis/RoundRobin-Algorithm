package test.roundrobin;

public class Process {
    String Name;
    int ArrivalTime;
    int BurstTime;
    int Priority;
    int RemainingTime;
    int Tick_Responded;
    int Tick_Excecuted;
    boolean Initialized;
    int Response;
    int Turnaround;
    
    
    public int getRemainingTime() {
        return RemainingTime;
    }

    public void setRemainingTime(int RemainingTime) {
        this.RemainingTime = RemainingTime;
    }

        
    public Process(String Name, int ArrivalTime, int BurstTime, int Priority) {
        this.Name = Name;
        this.ArrivalTime = ArrivalTime;
        this.BurstTime = BurstTime;
        this.Priority = Priority;
        RemainingTime = BurstTime;
    }

    public String getName() {
        return Name;
    }

    public int getResponse() {
        return Response;
    }

    public int getTurnaround() {
        return Turnaround;
    }
    
    public int getArrivalTime() {
        return ArrivalTime;
    }

    public int getBurstTime() {
        return BurstTime;
    }

    public int getPriority() {
        return Priority;
    }

    public void setArrivalTime(int ArrivalTime) {
        this.ArrivalTime = ArrivalTime;
    }

    public void setBurstTime(int BurstTime) {
        this.BurstTime = BurstTime;
    }

    public void setPriority(int Priority) {
        this.Priority = Priority;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public void setResponse(int Response) {
        this.Response = Response;
    }

    public void setTurnaround(int Turnaround) {
        this.Turnaround = Turnaround;
    }

    @Override
    public String toString() {
        return "Process{" + "Name=" + Name + ", ArrivalTime=" + ArrivalTime + ", BurstTime=" + BurstTime + ", Priority=" + Priority +'}';
    }
    
    
    
    
}
