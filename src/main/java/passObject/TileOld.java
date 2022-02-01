package passObject;

import javafx.scene.control.Button;
import passObject.NearbyTile;

public class TileOld extends Button {

    // Subclass of Button
    private NearbyTile nearbyTile;


    public TileOld (NearbyTile nearbyTile){
        //Every instance of Tile has a NearbyTile instance
        this.nearbyTile = nearbyTile;
    }

    public NearbyTile getNearbyTile() {
        return nearbyTile;
    }

    public void setNearbyTile(NearbyTile nearbyTile) {
        this.nearbyTile = nearbyTile;
    }
}
