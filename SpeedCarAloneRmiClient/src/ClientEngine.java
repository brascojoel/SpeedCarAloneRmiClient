
import java.io.Serializable;
import java.rmi.ConnectException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author brasc
 */
public class ClientEngine  implements ClientInterface, IClient, Serializable {

    private static final long serialVersionUID = 2533181686936469703L;
    private static final Logger LOGGER = Logger.getLogger(ClientEngine.class
            .getName());
    private String name;
    private GameEngineInterface server;
    private GUI gui;
   
    private long id;

    /**
     * Constructeur
     *
     * @param username
     * @throws RemoteException
     */
    public ClientEngine(String username,GUI gui) throws RemoteException {
        super();
        this.name = username;
         this.gui =  gui;
         this.gui.setClient(this);
        System.out.println("ClientEngine.<init>() gui "+gui);
    }

  
    @Override
    public String getName() throws RemoteException {
        return name;
    }

    public String getUsername() {
        return name;
    }

    /**
     * la méthode connect appelée par le bouton "connexion" de la fenêtre de
     * connexion.
     *
     * Main
     */
    @Override
    public boolean connect(String url, int port) {
      // si pas d'url donnée, on la met à localhost pour jouer en local
		if (url == null || url.isEmpty()) {
			url = "localhost";
		}
		try {
			Registry registry = LocateRegistry.getRegistry(url, port);

			server = (GameEngineInterface) registry.lookup(Constants.SERVER_PATH);

			// enveloppe le client en UnicastRemoteObject et l'enregistre sur le serveur
			ClientInterface client = (ClientInterface) UnicastRemoteObject.exportObject(
					this, 0);
			id = server.connect(client);
		} catch (Exception ex) {
			LOGGER.log(Level.SEVERE, "connection failed at " + url + " on "
					+ port, ex);
                         System.out.println("Désolé problème de connexion ");
			return false;
		}
		// si on reçoit bien un id (donc enregistrement a été accepté)
		if (id != 0) {
			LOGGER.fine("connection established at " + url + " on " + port);
                        System.out.println("Vous êtes connecté ");
			// alors on charge les données de jeu en appelant le serveur
			//load();
			return true;
		}
                else
                     System.out.println("échec de connexion ");
		LOGGER.fine("connection refused at " + url + " on " + port);
		return false;
	}

    public void disPlayGui(){
        
       
           
                    
                   
                    gui.setVisible(true);
                  
             
    }
    @Override
    public boolean isConnected() {
        return (server != null && id != 0);
    }

    /**
     * Gère le problème de connexion
     */
    private void onConnectionLost() {
        if (isConnected()) {
           
            server = null;
            id = 0;
         
        }
    }

    /**
     * Permet de se déconnecter
     */
    @Override
    public void disconnect() {
        
        if (isConnected()) {
            gui.dialog("jeu interrompu");
            try {
                server.disconnect(id);
                LOGGER.fine("disconnection done");
            } catch (ConnectException ce) {
                onConnectionLost();
            } catch (RemoteException e) {
                LOGGER.log(Level.SEVERE, "disconnection failed", e);
            }
        }
    }

   

 
    @Override
    public void newGame() {
        if (isConnected() ) {
            boolean started = false;
            try {
                 System.out.println("id : "+id);
                started = server.startGame(id);
            } catch (ConnectException ce) {
                onConnectionLost();
            } catch (RemoteException e) {
                LOGGER.log(Level.SEVERE, "starting game failed", e);
            } finally {
//                start(started);
            }
        }
    }

    @Override
    public void close() {
        /*if (arena.isInProgress()) {
            arena.setState(ArenaState.Interrupted);
        }
        leaveParty(arena.getName());*/
    }

    @Override
    public void update(Vector<Rectangle> vDisplayRoad, Vector<Rectangle> vDisplayObstacles, Vector<Rectangle> vDisplayCars, Car myCar, int pos, int nbParticipants, boolean bGameOver, String sPosition) throws RemoteException {

      
          System.out.println("ClientEngine.update() ");
        
        
         if (isConnected() ) {

             System.out.println("ClientEngine.update() "+isConnected()+"  GUI"+gui);
            gui.update(vDisplayRoad, vDisplayObstacles, vDisplayCars, myCar, pos, nbParticipants, bGameOver, sPosition);

        
        }
         else
              System.out.println("ClientEngine.update() hors "+isConnected()+"  GUI"+gui);

      
    }

   
    @Override
    public void newGrid() {

        try {
            server.newGrid(id);
            System.out.println("ClientEngine.newGrid()");
        } catch (ConnectException ce) {
            onConnectionLost();
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, "starting game failed", e);
        }

    }

    @Override
    public void beginGame() {

        if (isConnected() ) {
           boolean started = false;
            try {
                
                server.beginGame(id);
                started = true;
                System.out.println("ClientEngine.beginGame()");
            } catch (ConnectException ce) {
                onConnectionLost();
            } catch (RemoteException e) {
                LOGGER.log(Level.SEVERE, "starting game failed", e);
            }
           finally {
               // start(started);
            }
        }
    }

    @Override
    public void setGui(GUI gui) {
        System.out.println("ClientEngine.setGui()"+gui);
        this.gui = gui;
    }

    /**
     * Permet de déplacer la voiture
     *
     * @param choice
     * @param flag
     */
    @Override
    public void moveCar(String choice, boolean flag) {

        try {
            server.moveCar(id, choice, flag);
        } catch (RemoteException ex) {
            Logger.getLogger(ClientEngine.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public int getScore() {
        try {
           return  server.getScoreClient(id);
        } catch (RemoteException ex) {
            Logger.getLogger(ClientEngine.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    @Override
    public void setPlayButton(boolean flag) throws RemoteException {
        gui.jButton1.setVisible(flag);
    }

   
   
}
