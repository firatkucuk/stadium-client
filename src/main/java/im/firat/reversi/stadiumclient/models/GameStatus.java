
package im.firat.reversi.stadiumclient.models;

public class GameStatus {



    //~ --- [INSTANCE FIELDS] ------------------------------------------------------------------------------------------

    private String            cancellationCode;
    private String            playerBlackAuthCode;
    private String            playerWhiteAuthCode;
    private ReversiGameStatus reversiGame;



    //~ --- [CONSTRUCTORS] ---------------------------------------------------------------------------------------------

    public GameStatus() {

    }



    //~ --- [METHODS] --------------------------------------------------------------------------------------------------

    public String getCancellationCode() {

        return cancellationCode;
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    public String getPlayerBlackAuthCode() {

        return playerBlackAuthCode;
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    public String getPlayerWhiteAuthCode() {

        return playerWhiteAuthCode;
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    public ReversiGameStatus getReversiGame() {

        return reversiGame;
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    public void setCancellationCode(String cancellationCode) {

        this.cancellationCode = cancellationCode;
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    public void setPlayerBlackAuthCode(String playerBlackAuthCode) {

        this.playerBlackAuthCode = playerBlackAuthCode;
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    public void setPlayerWhiteAuthCode(String playerWhiteAuthCode) {

        this.playerWhiteAuthCode = playerWhiteAuthCode;
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    public void setReversiGame(ReversiGameStatus reversiGame) {

        this.reversiGame = reversiGame;
    }
}
