/**

package passObject;

import com.example.demo.Tile;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Random;

public class TileConverter {

    ArrayList<Tile> tileList = new ArrayList<Tile>();
    ListIterator<Tile> iterator = tileList.listIterator();

    public void newTileList(int height, int width, int mines){
        //Code creates a list of Tiles, with mines, numbers, and empties sprinkled in

        for (int i = 0; i < (height * width); i++){
            //tileList.add(new Tile(new NearbyTile()));
        }
        for (int i = 0; i < mines; i++){
            Random rand = new Random();
            // nextInt as provided by Random is exclusive of the top value so you need to add 1
            int randomNum = rand.nextInt(height * width);

            Tile tile = tileList.get(randomNum);
            tile.updateMine(-1);
            Mine mine = new Mine(tileData);
            //tileList.set(randomNum, mine);

        }

    }

    public Tile nextTile(){

    }

    public <T> T convertTile(Tile tile){
        //Grab data and upload it to a new object
        NearbyTile tileData = tile.getNearbyTile();
        return T.setNearbyTile();
    }
}

 **/

