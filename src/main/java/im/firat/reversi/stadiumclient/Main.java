package im.firat.reversi.stadiumclient;

import im.firat.reversi.domain.Game;
import im.firat.reversi.services.GameService;
import im.firat.reversi.stadiumclient.clients.GameClient;
import java.util.*;
import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;



public class Main {



    //~ --- [INSTANCE FIELDS] ------------------------------------------------------------------------------------------

    private final Timer timer = new Timer();



    //~ --- [CONSTRUCTORS] ---------------------------------------------------------------------------------------------

    public Main(final String baseAddress, final String authCode, final int player) {

        List<JacksonJsonProvider> providers  = Arrays.asList(new JacksonJsonProvider());
        GameClient                client     = JAXRSClientFactory.create(baseAddress, GameClient.class, providers);
        TimerTask                 pollerTask = new PollerTask(authCode, player, client);

        timer.scheduleAtFixedRate(pollerTask, 500, 500);
    }



    //~ --- [METHODS] --------------------------------------------------------------------------------------------------

    public static void main(String[] args) {

        final String baseAddress = "http://localhost:8080/reversi-stadium/rest/";
        final String authCode    = "nxtf0248";
        final int    player      = GameService.BLACK_PLAYER;

        new Main(baseAddress, authCode, player);
    }



    //~ --- [INNER CLASSES] --------------------------------------------------------------------------------------------

    private class PollerTask extends TimerTask {



        //~ --- [INSTANCE FIELDS] --------------------------------------------------------------------------------------

        private final String     authCode;
        private final GameClient client;
        private final int        player;



        //~ --- [CONSTRUCTORS] -----------------------------------------------------------------------------------------

        private PollerTask(final String authCode, final int player, final GameClient client) {

            this.authCode = authCode;
            this.player   = player;
            this.client   = client;
        }



        //~ --- [METHODS] ----------------------------------------------------------------------------------------------

        @Override
        public void run() {

            Game game = client.status();

            if (game.isCancelled()) {
                timer.cancel();
            } else if (!game.isStarted()) {
                timer.cancel();
            } else if (game.getCurrentPlayer() == player) {

                List<String> availableMoves = game.getAvailableMoves();
                Random       random         = new Random();
                int          randomInt      = random.nextInt(availableMoves.size());
                String       nextMove       = availableMoves.get(randomInt);

                client.move(authCode, nextMove);
            }
        }
    }
}
