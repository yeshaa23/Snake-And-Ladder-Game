import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SnakeAndLadder{
    // states
    private ArrayList<Player> players;
    private ArrayList<Snake> snakes;
    private ArrayList<Ladder> ladders;
    private PowerUp powerUp;
    private int boardSize;
    private int level;
    private int status; // 0 = the game isn't started yet, 1 = the game is started, 2 = the game is over
    private int playerInTurn;
    private int turnSinceLastPowerUpMove;

    public SnakeAndLadder(int boardSize){
        this.boardSize = boardSize;
        this.players = new ArrayList<Player>();
        this.snakes = new ArrayList<Snake>();
        this.ladders = new ArrayList<Ladder>();
        this.level = 1; // default level
        this.status = 0;
        this.turnSinceLastPowerUpMove = 0;
        this.powerUp = new PowerUp(generateValidRandomPosition());
    }
    private int generateValidRandomPosition(){
        Random random = new Random();
        int position;
        boolean isValid;
        do {
            position = random.nextInt(boardSize - 1) + 1; // Ensure position is between 1 and 99
            isValid = true;

            // Check if position is on a snake or ladder
            for (Snake snake : snakes) {
                if (snake.getFromPosition() == position || snake.getToPosition() == position) {
                    isValid = false;
                    break;
                }
            }

            for (Ladder ladder : ladders) {
                if (ladder.getFromPosition() == position || ladder.getToPosition() == position) {
                    isValid = false;
                    break;
                }
            }

            // Check if position is on a player
            for (Player player : players) {
                if (player.getPosition() == position) {
                    isValid = false;
                    break;
                }
            }

        } while (!isValid);

        return position;
    }

    private int generateBiasRandomPosition(Player player) {
        Random random = new Random();
        int position;
        boolean isValid;
        int biasRange = 10; // Define a bias range to increase the likelihood of power-ups near players

        do {
            position = player.getPosition() + random.nextInt(biasRange * 2 + 1) - biasRange;
            if (position < 1) {
                position = 1;
            } else if (position >= boardSize) {
                position = boardSize - 1;
            }

            isValid = true;

            // Check if position is on a snake or ladder
            for (Snake snake : snakes) {
                if (snake.getFromPosition() == position || snake.getToPosition() == position) {
                    isValid = false;
                    break;
                }
            }

            for (Ladder ladder : ladders) {
                if (ladder.getFromPosition() == position || ladder.getToPosition() == position) {
                    isValid = false;
                    break;
                }
            }

            // Check if position is on a player
            for (Player p : players) {
                if (p.getPosition() == position) {
                    isValid = false;
                    break;
                }
            }

        } while (!isValid);

        return position;
    }

    public void setLevel(int level){
        this.level = level;
    }

    public int getLevel(){
        return level;
    }

    public void initiateGame(){
        // set the ladders and snakes based on the level
        int[][] ladders;
        int[][] snakes = {
                {29,9},
                {38,15},
                {47,5},
                {53,33},
                {62,37},
                {86,54},
                {92,70},
                {97,25}
        };

        if (level == 1) {
            ladders = new int[][]{
                    {2,23},
                    {8,34},
            };
            snakes = new int[][]{
                    {29, 9},
                    {38, 15}
            };
        } else if (level == 2) {
            ladders = new int[][]{
                    {2,23},
                    {8,34},
                    {20,77},
            };
            snakes = new int[][]{
                    {29, 9},
                    {38, 15},
                    {47, 5}
            };
        } else if (level == 3) {
            ladders = new int[][]{
                    {2, 23},
                    {8, 34},
                    {20, 77},
                    {32, 68}
            };
            snakes = new int[][]{
                    {29, 9},
                    {38, 15},
                    {47, 5},
                    {53, 33}
            };
        } else if (level == 4) {
            ladders = new int[][]{
                    {2, 23}, {8, 34},
                    {20, 77}, {32, 68},
                    {41, 79}};
            snakes = new int[][]{
                    {29, 9}, {38, 15},
                    {47, 5}, {53, 33},
                    {62, 37}
            };
        } else {
            ladders = new int[][]{
                    {2,23}, {8,34},
                    {20,77}, {32,68},
                    {41,79}, {74,88},
                    {82,100}, {85,95}
            };
            snakes = new int[][]{
                    {29, 9}, {38, 15},
                    {47, 5}, {53, 33},
                    {62, 37}, {86, 54},
                    {92, 70}, {97, 25}};
        }

        addLadders(ladders);
        addSnakes(snakes);
    }

    public void setBoardSize(int boardSize){
        this.boardSize = boardSize;
    }

    public void setStatus(int status){
        this.status = status;
    }

    public void setPlayerInTurn(int playerInTurn){
        this.playerInTurn = playerInTurn;
    }

    public void addPlayer(Player p){
        this.players.add(p);
    }

    public void addSnake(Snake s){
        this.snakes.add(s);
    }

    public void addSnakes(int[][] s){
        for(int i = 0; i < s.length; i++){
            Snake snake = new Snake(s[i][0], s[i][1]);
            this.snakes.add(snake);
        }
    }

    public void addLadder(Ladder l){
        this.ladders.add(l);
    }

    public void addLadders(int[][] l){
        for(int m = 0; m < l.length; m++){
            Ladder ladder = new Ladder(l[m][0], l[m][1]);
            this.ladders.add(ladder);
        }
    }

    public int getBoardSize(){
        return boardSize;
    }

    public int getStatus(){
        return status;
    }

    public int getPlayerInTurn(){
        return playerInTurn;
    }

    public ArrayList<Player> getPlayers(){
        return players;
    }

    public ArrayList<Snake> getSnakes(){
        return snakes;
    }

    public ArrayList<Ladder> getLadders(){
        return ladders;
    }

    public int getTurn(){
        if (this.status == 0) {
            return 0; //start with the first player
        } else {
            if (playerInTurn == 0) {
                return 1;
            } else if (playerInTurn == 1) {
                return 2;
            } else {
                return 0;
            }
        }
    }

    public void movePlayer(Player p, int x) throws LineUnavailableException, IOException {
        Scanner sc = new Scanner(System.in);
        p.moveAround(x, this.boardSize);

        // checking the ladder
        for (Ladder l : this.ladders) {
            if (p.getPosition() == l.getFromPosition()){
                p.setPosition(l.getToPosition());
                File file = new File("open-new-level-143027.wav");
                AudioInputStream audioStream = null;
                try {
                    audioStream = AudioSystem.getAudioInputStream(file);
                } catch (UnsupportedAudioFileException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Clip clip = null;
                try {
                    clip = AudioSystem.getClip();
                } catch (LineUnavailableException e) {
                    throw new RuntimeException(e);
                }
                clip.open(audioStream);

                clip.start();
                System.out.println(p.getUserName() + " got ladder from " + l.getFromPosition() + " climb to " + l.getToPosition());
            }
        }

        // checking the snake
        for (Snake s : this.snakes) {
            if (p.getPosition() == s.getFromPosition()){
                p.setPosition(s.getToPosition());
                File file = new File("Rattle-Snake-Hissing-A1-www.fesliyanstudios.com.wav");
                AudioInputStream audioStream = null;
                try {
                    audioStream = AudioSystem.getAudioInputStream(file);
                } catch (UnsupportedAudioFileException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Clip clip = null;
                try {
                    clip = AudioSystem.getClip();
                } catch (LineUnavailableException e) {
                    throw new RuntimeException(e);
                }
                clip.open(audioStream);

                clip.start();
                System.out.println(p.getUserName() + " got snake from " + s.getFromPosition() + " slide down to " + s.getToPosition());
            }
        }

        // checking the power-up
        if(p.getPosition() == powerUp.getPowerUpPosition()){
            p.setPowerUp(true);
            p.setPowerUpTurnsRemaining(2);
            powerUp.setPowerUpPosition(generateValidRandomPosition());
            powerUp.resetTurn();
            File file = new File("mixkit-bonus-earned-in-video-game-2058.wav");
            AudioInputStream audioStream = null;
            try {
                audioStream = AudioSystem.getAudioInputStream(file);
            } catch (UnsupportedAudioFileException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Clip clip = null;
            try {
                clip = AudioSystem.getClip();
            } catch (LineUnavailableException e) {
                throw new RuntimeException(e);
            }
            clip.open(audioStream);

            clip.start();
            System.out.println(p.getUserName() + " picked up a power-up! Dice rolls are doubled for the next turns.");
        }

        System.out.println(p.getUserName() + " new position is " + p.getPosition());

        // checking if win
        if (p.getPosition() == this.boardSize) {
            File file = new File("videoplayback (7).wav");
            AudioInputStream audioStream = null;
            try {
                audioStream = AudioSystem.getAudioInputStream(file);
            } catch (UnsupportedAudioFileException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Clip clip = null;
            try {
                clip = AudioSystem.getClip();
            } catch (LineUnavailableException e) {
                throw new RuntimeException(e);
            }
            clip.open(audioStream);

            clip.start();
            this.status = 2;
            System.out.println("The winner is: " + p.getUserName());
        }

        // checking if game over
        if(this.status == 2){
            String input = sc.nextLine();
            int g = 0;
            if (input.isEmpty()){
                System.out.println("GAME OVER!!!");
            }
        }
    }

    public void play() throws LineUnavailableException, IOException {
        Scanner sc = new Scanner(System.in);

        File file = new File("game-start-6104.wav");
        AudioInputStream audioStream = null;
        try {
            audioStream = AudioSystem.getAudioInputStream(file);
        } catch (UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Clip clip = null;
        try {
            clip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }
        clip.open(audioStream);

        clip.start();

        System.out.println("Enter first player name:");
        String firstPlayer = sc.nextLine();
        System.out.println("Enter second player name:");
        String secondPlayer = sc.nextLine();
        System.out.println("Enter third player name:");
        String thirdPlayer = sc.nextLine();

        Player p1 = new Player(firstPlayer);
        Player p2 = new Player(secondPlayer);
        Player p3 = new Player(thirdPlayer);

        System.out.println("Choose game level (1, 2, 3, 4, or 5):");
        int chosenLevel = sc.nextInt();
        sc.nextLine(); // consume the newline

        setLevel(chosenLevel);
        initiateGame();

        addPlayer(p1);
        addPlayer(p2);
        addPlayer(p3);

        do {
            int t1 = getTurn();
            setStatus(1);
            setPlayerInTurn(t1);

            Player playerInTurn = getPlayers().get(t1);
            System.out.println("---------------------------------");
            System.out.println("Player in turn is " + playerInTurn.getUserName());

            System.out.println(playerInTurn.getUserName() + " it's your turn, please press enter to roll dice");
            String input = sc.nextLine();
            int x = 0;
            if (input.isEmpty()){
                x = playerInTurn.rollDice();
            }

            System.out.println("Dice number: " + x);
            movePlayer(playerInTurn, x);

            // Update power-up turn counters
            if(playerInTurn.hasPowerUp()){
                playerInTurn.decrementPowerUpTurns();
            }
            turnSinceLastPowerUpMove++;
            if(turnSinceLastPowerUpMove >= 5){
                powerUp.setPowerUpPosition(generateBiasRandomPosition(playerInTurn));
                powerUp.resetTurn();
                turnSinceLastPowerUpMove = 0;
                System.out.println("Power-up moved to a new position!");
            }

        } while (getStatus() != 2);
    }
}