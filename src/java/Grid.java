
import java.util.Random;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * @author Rangana De Silva and Kalana Suraweera
*/

public class Grid {

    private int[][] dots = new int[45][45];
    private String[] dotNames = {"B", "G", "R"};
    private int value ;

    public Grid() {
        Random rand = new Random();

        for (int i = 0; i < 45; i++) {
            for (int j = 0; j < 45; j++) {
                dots[i][j] = 3;
                //System.out.println("blank = "+dots[i][j]);
            }
        }
        for (int i = 0; i < 100; i++) {

            int x = rand.nextInt(44);
            int y = rand.nextInt(44);
            dots[x][y] = i % 3;
            //System.out.println("rand color val = "+dots[x][y]);
        }

    }

    public void removeFood(int x, int y) {//player gets points when food is removed.
        //red,green,blue --> 1,2,4
        switch (dots[x][y]) {
            case 0:
                //blue
                value = 4;
                break;
            case 1:
                //green
                value = 2;
                break;
            case 2:
                //red
                value = 1;
                break;
            default:
                value = 0;
                break;
        }
        dots[x][y] = 3;//blank
    }
    public int getValue(){
    return value;}

    JsonArray getJson() {
        JsonArrayBuilder builder = Json.createArrayBuilder();

        for (int i = 0; i < 45; i++) {
            for (int j = 0; j < 45; j++) {
                if (dots[i][j] < 3) {
                    builder.add(Json.createArrayBuilder().add(dotNames[dots[i][j]]).add(i).add(j));

                }

            }
        }

        return builder.build();

    }
}
