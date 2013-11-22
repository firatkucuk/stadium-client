
package im.firat.reversi.stadiumclient;

import im.firat.reversi.stadiumclient.models.GameStatus;
import im.firat.reversi.stadiumclient.models.ReversiGameStatus;
import javax.ws.rs.*;



public interface GameService {



    //~ --- [METHODS] --------------------------------------------------------------------------------------------------

    @DELETE
    @Path("/cancel/{cancellationCode}")
    @Produces("application/json")
    public ReversiGameStatus cancelGame(@PathParam("cancellationCode") String cancellationCode);



    //~ ----------------------------------------------------------------------------------------------------------------

    @GET
    @Path("/status")
    @Produces("application/json")
    public ReversiGameStatus getGameStatus();



    //~ ----------------------------------------------------------------------------------------------------------------

    @PUT
    @Path("/move/{authCode}/{piece}")
    @Produces("application/json")
    public ReversiGameStatus movePiece(@PathParam("authCode") String authCode,
            @PathParam("piece") String piece);



    //~ ----------------------------------------------------------------------------------------------------------------

    @POST
    @Path("/start")
    @Produces("application/json")
    public GameStatus startGame();
}
