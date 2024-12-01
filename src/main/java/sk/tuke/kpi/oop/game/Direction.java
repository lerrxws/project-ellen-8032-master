package sk.tuke.kpi.oop.game;

public enum Direction {
    NORTH(0, 1), EAST(1, 0), SOUTH(0, -1), WEST(-1, 0),
    NORTHWEST(-1, 1), NORTHEAST(1,1), SOUTHWEST(-1,-1), SOUTHEAST(1, -1),
    NONE(0,0);

    private final int dx;
    private final int dy;

    Direction(int dx, int dy){
        this.dx = dx;
        this.dy = dy;
    }

    public float getAngle() {
        if(dx == 0){
            return (dy == 1) ? 0.0f : 180.0f;
        }
        /*if(dx == 0 && dy == 1){
            return 0.0f;
        }
        else if(dx == 0 && dy == -1){
            return 180.0f;
        }*/
        if(dx == 1){
            return (dy == 0) ? 270.0f : (dy == 1) ? 315.0f : 225.0f;
        }
        if(dx == -1){
            return (dy == 0) ? 90.0f : (dy == 1) ? 45.0f : 135.0f;
        }
        /*else if(dx == 1 && dy == 0){
            return 270.0f;
        }*/
        /*else if(dx == -1 && dy == 0){
            return 90.0f;
        }
        else if(dx == -1 && dy == 1){
            return 45.0f;
        }*/
        //else if(dx == 1 && dy == 1){
            //return 315.0f;
        //}
        /*else if(dx == -1 && dy == -1){
            return 135.0f;
        }*/
        /*else if(dx == 1 && dy == -1){
            return 225.0f;
        }*/

        return 0.0f;
        //double angle = Math.asin(dy / Math.sqrt(dx * dx + dy * dy));
        //return (float) angle;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    public Direction combine(Direction other) {
        if(other == null) {
            return NONE;
        }

        if(other == this){
            return other;
        }



        int newX = (other.getDx() != this.getDx()) ? other.getDx() + this.getDx() : other.getDx();
        int newY = (other.getDy() != this.getDy()) ? other.getDy() + this.getDy() : other.getDy();

        //newX = (newX >= 1) ? 1 : (newX <= -1) ? -1 : 0;
        //newY = (newY >= 1) ? 1 : (newY <= -1) ? -1 : 0;

        for(Direction newDirection : Direction.values()) {
            if(newX == newDirection.getDx() && newY == newDirection.getDy()) {
                return newDirection;
            }
        }

        return NONE;
    }

    public static Direction fromAngle(float angle) {
        if(angle == 0) {
            return NORTH;
        }
        if(angle == 45) {
            return NORTHWEST;
        }
        if(angle == 90){
            return WEST;
        }
        else if(angle == 135){
            return SOUTHWEST;
        }
        if(angle == 180){
            return SOUTH;
        }
        if(angle == 225){
            return SOUTHEAST;
        }
        if(angle == 270){
            return EAST;
        }
        if(angle == 315){
            return NORTHEAST;
        }

        return NONE;
    }
}
