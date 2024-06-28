public class PowerUp {
    private int powerUpPosition;
    private int turnsToMove;

    public PowerUp(int powerUpPosition){
        this.powerUpPosition = powerUpPosition;
        this.turnsToMove = 0;
    }
    public int getPowerUpPosition(){

        return powerUpPosition;
    }
    public void setPowerUpPosition(int powerUpPosition){

        this.powerUpPosition = powerUpPosition;
    }
    public int getTurnsToMove(){

        return turnsToMove;
    }
    public void resetTurn(){

        this.turnsToMove = 0;
    }
}
