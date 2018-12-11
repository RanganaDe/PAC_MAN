/*
@author Rangana De Silva and Kalana Suraweera
*/

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonWriter;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
public class GameLogic {

    private Grid dots;
    private Players players;
    private JsonObject reply;

    public GameLogic() {
        //creating jsonArray for dots (color,x,y)
        dots = new Grid();
        //creating jsonArray for players (score,x,y)
        players = new Players();
        //storing the initial reply stating the initial state of the game
        reply = Json.createObjectBuilder().add("DOTS", dots.getJson()).add("PLAYERS", players.getJson()).build();
        //for testing purposes
        System.out.println(dots.toString());
        System.out.println(players.toString());
        System.out.println(reply.toString());
    }

    public JsonObject getReply() {
        return this.reply;
    }

    public void updatePosition(String keystroke,int id) {
        players.setPosition(id, keystroke);// 0 --> player 0
        dots.removeFood(players.getPositionx(id), players.getPositiony(id));//remove food when player passes over
        players.setScore(id, dots.getValue());
        reply = Json.createObjectBuilder().add("DOTS", dots.getJson()).add("PLAYERS", players.getJson()).build();
    }

//    public static void main(String args[]) {
//        JsonArrayBuilder builder = Json.createArrayBuilder();
//        for(int i=0;i<5;i++) {
//            builder.add(Json.createObjectBuilder().add("num", i).add("num2", i*4));
//        }
//        JsonArray arr=builder.build();
//        JsonWriter writer=Json.createWriter(System.out);
//        for(int i=0;i<5;i++) {
//            writer.writeObject(arr.getJsonObject(i).getJsonObject("num"));
//            System.out.print("\t");
//            writer.writeObject(arr.getJsonObject(i).getJsonObject("num2"));
//            System.out.println();
//        }
//        
//        
//    }
}
