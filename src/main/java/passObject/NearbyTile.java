package passObject;


import java.util.HashMap;

public class NearbyTile {

    private HashMap<String, NearbyTile> nearbyTiles = new HashMap<>();
    public static final String[] DIRECTIONS = {"NORTH", "NORTH_EAST", "EAST", "SOUTH_EAST", "SOUTH", "SOUTH_WEST", "WEST", "NORTH_WEST"};

    public NearbyTile(){
        nearbyTiles.put("NORTH", null);
        nearbyTiles.put("NORTH_EAST", null);
        nearbyTiles.put("EAST", null);
        nearbyTiles.put("SOUTH_EAST", null);
        nearbyTiles.put("SOUTH", null);
        nearbyTiles.put("SOUTH_WEST", null);
        nearbyTiles.put("WEST", null);
        nearbyTiles.put("NORTH_WEST", null);
    }

    public NearbyTile(String str, NearbyTile nearbyTile){
        this.nearbyTiles.put(str, nearbyTile);

    }

    public NearbyTile getNearbyTile(String str) {
        return nearbyTiles.get(str);
    }

    public void setNearbyTile(String str, NearbyTile tile) {
        this.nearbyTiles.put(str, tile);
    }


//    class Mine extends Button {
//        public Mine(){
//            //Style mines here
//        }
//    }
//    class Flag extends Label {
//        public Flag(){
//            //Style flags here
//        }
//    }
}
