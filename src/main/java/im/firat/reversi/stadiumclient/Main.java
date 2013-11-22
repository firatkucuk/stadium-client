package im.firat.reversi.stadiumclient;

import im.firat.reversi.stadiumclient.models.ReversiGameStatus;
import java.util.*;
import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;



public class Main {



    //~ --- [INSTANCE FIELDS] ------------------------------------------------------------------------------------------

    private final Timer timer = new Timer();



    //~ --- [CONSTRUCTORS] ---------------------------------------------------------------------------------------------

    public Main(String serverUrl, String authCode, int player) {

        TimerTask pollerTask = new PollerTask(serverUrl, authCode, player);

        timer.scheduleAtFixedRate(pollerTask, 5000, 5000);
    }



    //~ --- [METHODS] --------------------------------------------------------------------------------------------------

    public static void main(String[] args) {

        String serverUrl = System.getenv("RS_SERVER_URL");

        if (serverUrl == null || serverUrl.isEmpty()) {

            serverUrl = "http://localhost:8080/reversi-stadium/";
        }

        String authCode = System.getenv("RS_AUTH_CODE");

        if (authCode == null || authCode.isEmpty()) {

            System.err.println("No authentication code for current player found");

            return;
        }

        String player = System.getenv("RS_PLAYER");

        if (player == null || player.isEmpty()) {
            System.err.println("No player environmental variable defined.");

            return;
        } else if (!player.equals("1") && !player.equals("2")) {
            System.err.println("Player variable should be 1 or 2.");

            return;
        }

        new Main(serverUrl, authCode, Integer.parseInt(player));
    }



    //~ --- [INNER CLASSES] --------------------------------------------------------------------------------------------

    private class PollerTask extends TimerTask {



        //~ --- [INSTANCE FIELDS] --------------------------------------------------------------------------------------

        private String      authCode;
        private int         player;
        private GameService service;



        //~ --- [CONSTRUCTORS] -----------------------------------------------------------------------------------------

        private PollerTask(String serverUrl, String authCode, int player) {

            this.authCode = authCode;
            this.player   = player;

            List<JacksonJsonProvider> providers = Arrays.asList(new JacksonJsonProvider());
            service = JAXRSClientFactory.create(serverUrl, GameService.class, providers);
        }



        //~ --- [METHODS] ----------------------------------------------------------------------------------------------

        @Override
        public void run() {

            ReversiGameStatus reversiGameStatus = service.getGameStatus();

            if (reversiGameStatus.isCancelled()) {
                timer.cancel();
            } else if (!reversiGameStatus.isStarted()) {
                timer.cancel();
            } else if (reversiGameStatus.getCurrentPlayer() == player) {

                List<String> availableMoves = reversiGameStatus.getAvailableMoves();
                Random       random         = new Random();
                int          randomInt      = random.nextInt(availableMoves.size());
                String       nextMove       = availableMoves.get(randomInt);

                service.movePiece(authCode, nextMove);
            }
        }
    }
}
