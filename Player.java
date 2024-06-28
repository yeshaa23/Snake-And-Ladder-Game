import java.util.Scanner;

public class Player{
    //states
    private String userName;
    private int position;
    private boolean hasPowerUp;
    private int powerUpTurnsRemaining;

    //methods
    //constructor
    public Player(String userName){
        this.userName = userName;
        this.position = 0;
        this.hasPowerUp = false;
        this.powerUpTurnsRemaining = 0;
    }

    //setter
    public void setUserName(String userName){
        this.userName = userName;
    }
    public void setPosition(int position){
        this.position = position;
    }
    public void setPowerUp(boolean hasPowerUp){
        this.hasPowerUp = hasPowerUp;
    }
    public void setPowerUpTurnsRemaining(int powerUpTurnsRemaining){
        this.powerUpTurnsRemaining = powerUpTurnsRemaining;
    }

    //getter
    public String getUserName(){
        return userName;
    }

    public int getPosition(){
        return position;
    }
    public boolean hasPowerUp(){
        return hasPowerUp;
    }
    public int getPowerUpTurnsRemaining(){
        return powerUpTurnsRemaining;
    }

    //rolldice method
    public int rollDice(){
        int dice = (int) (Math.random()*6)+1;
        if(hasPowerUp){
            dice *= 2;
            powerUpTurnsRemaining--;
            if(powerUpTurnsRemaining == 0){
                hasPowerUp = false;
            }
        }
        return dice;
    }

    //move around method
    public void moveAround(int x, int boardSize){
        if(this.position + x > boardSize)
            this.position = boardSize - ((this.position + x) % boardSize);
        else this.position += x;
    }
    public void decrementPowerUpTurns(){
        if(powerUpTurnsRemaining > 0){
            powerUpTurnsRemaining--;
            if(powerUpTurnsRemaining == 0){
                hasPowerUp = false;
            }
        }
    }

}