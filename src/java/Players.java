
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * @author Rangana De Silva and Kalana Suraweera
*/

public class Players {

    private int[][] arr = new int[4][3];
    private String keyStroke = "";
    private static int score = 0;

    public Players() {
        //setting intial scores to 0
        for (int i = 0; i < 4; i++) {
            arr[i][0] = 0;
        }
        //player 1 initial position
        arr[0][1] = 0;
        arr[0][2] = 0;
        //player 2 initial position
        arr[1][1] = 44;
        arr[1][2] = 0;
        //player 3 position
        arr[2][1] = 44;
        arr[2][2] = 44;
        //player 4 position
        arr[3][1] = 0;
        arr[3][2] = 44;
    }

    public void setScore(int player, int value) {
        score = score + value;
        arr[player][0] = score;
        
    }

    /**
     *
     * @param player
     * @return
     */
    public int getPositionx(int player) {
        return arr[player][1];
    }

    public int getPositiony(int player) {
        return arr[player][2];
    }

    public void setPosition(int player, String keyStroke) {
        switch (keyStroke) {
            case "38"://uparrow
                if (arr[player][2] == 0) {//player at bottom
                    arr[player][2] = 44;
                }//player to top
                else {

                    arr[player][2]--;
                }//move down
                break;
            case "39"://rightarrow
                if (arr[player][1] == 44) {//player at right corner
                    arr[player][1] = 0;
                }//player to left corner
                else {

                    arr[player][1]++;
                }//move right

                break;
            case "40"://downarrow
                if (arr[player][2] == 44) {//player at top
                    arr[player][2] = 0;
                }//player to bottom
                else {

                    arr[player][2]++;
                }//move up

                break;
            case "37"://leftarrow
                if (arr[player][1] == 0) {//player at left corner
                    arr[player][1] = 44;
                }//player to right corner
                else {

                    arr[player][1]--;
                }//move left
                break;
            default:

        }
    }

    JsonArray getJson() {
        JsonArrayBuilder builder = Json.createArrayBuilder();
        builder.add(Json.createArrayBuilder().add("P1").add(arr[0][0]).add(arr[0][1]).add(arr[0][2]));
        builder.add(Json.createArrayBuilder().add("P2").add(arr[1][0]).add(arr[1][1]).add(arr[1][2]));
        builder.add(Json.createArrayBuilder().add("P3").add(arr[2][0]).add(arr[2][1]).add(arr[2][2]));
        builder.add(Json.createArrayBuilder().add("P4").add(arr[3][0]).add(arr[3][1]).add(arr[3][2]));
        return builder.build();
    }

}
