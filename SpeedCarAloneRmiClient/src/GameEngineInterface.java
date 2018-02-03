
import java.awt.Color;
import java.rmi.RemoteException;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

public interface GameEngineInterface extends java.rmi.Remote {

  

    /**
     * quand le client se connecte pour la prmiere fois
     *
     * @param client
     * @return
     * @throws RemoteException
     */
    public long connect(ClientInterface client) throws RemoteException;

    /**
     * Quand le client se déconnecte et quitte completement le jeu
     *
     * @param userId
     * @throws RemoteException
     */
    public void disconnect(long userId) throws RemoteException;

  

    /**
     * Pour démarrer une partie
     *
     * @param userId
     * @return
     * @throws RemoteException
     */
    public boolean startGame(long userId) throws RemoteException;

    /**
     * Pour lancer une partie
     *
     * @param userId
     * @throws RemoteException
     */
    public void beginGame(long userId) throws RemoteException;

    /**
     * Initialisation de la grille
     * @param userId
     * @throws RemoteException 
     */
    public void newGrid(long userId) throws RemoteException;

 
    /**
     * 
     * @param userId
     * @return
     * @throws RemoteException 
     */
    public int getScoreClient(long userId) throws RemoteException;

    /**
     * 
     * @param userId
     * @param choice
     * @param flag
     * @throws RemoteException 
     */
    public void moveCar(long userId, String choice, boolean flag) throws RemoteException;

} // end interface
