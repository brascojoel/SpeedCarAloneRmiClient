

public interface IClient {

    public boolean connect(String url, int port);

 
    public void disconnect();

 

    public String getUsername();
    
    public int getScore();

    public void setGui(GUI gui);

  

    public void newGrid();

 

    public void newGame();

    public void beginGame();

    public void moveCar(String choice, boolean flag);

    public void close();

    public boolean isConnected();
}
