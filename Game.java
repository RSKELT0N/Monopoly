//Add pollution/each round
//Players landing on players square

package Game;

import Enumerations.*;
import Interfaces.IGame;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class Game implements IGame {
    private String name;
    private Player[] players;
    private SquareLinkedList squares;
    private Player winner;
    private Player playersTurn;
    private State gameState;
    private static Game game = null;
    private int amountOfPlayers;
    private int minDice = 1;
    private int maxDice = 6;
    private int amountDiceThrows;
    private final int resourceRent = 100;
    private final int animalRent = 30;
    private final int storeAnimals = 150;
    private final ChanceCard cards;
    private static Scanner sc = new Scanner(System.in);


    HashMap<SquareType, Integer> typeMap = new HashMap<SquareType, Integer>(); {
        typeMap.put(SquareType.FACTORY,100);
        typeMap.put(SquareType.POWERPLANT,150);
        typeMap.put(SquareType.EMBANKMENT,50);
        typeMap.put(SquareType.OILRIG,200);
        typeMap.put(SquareType.WILD,0);
        typeMap.put(SquareType.REPOSITORY,500);
    }

    ArrayList<PollutionType> pollutionArray = new ArrayList<>(); {
        pollutionArray.add(PollutionType.HIGH);
        pollutionArray.add(PollutionType.MEDIUM);
        pollutionArray.add(PollutionType.LOW);
    }

    HashMap<Player, HashSet<Square>> playerOwnership = new HashMap<Player, HashSet<Square>>();

    private Game(String name,int amountDiceThrows) throws FileNotFoundException {
        this.amountDiceThrows = amountDiceThrows;
        this.name = name;
        squares = new SquareLinkedList();
        winner = null;
        playersTurn = null;
        gameState = State.ACTIVE;
        cards = new ChanceCard(1,5,200,50);
        initialiseSquares();
    }

    @Override
    public void initialiseSquares() throws FileNotFoundException {
        File file = new File("/Users/ryanskelton/Desktop/ProjectSoftware/SoftwareProgramMonoply/src/Game/board.csv");
        Scanner scan = new Scanner(file);
        while(scan.hasNext()) {
            String line = scan.next();
            String parts[] = line.split(",");
            addSquareToLinkedList(parts[0],parts[1]);
        }
    }

    @Override
    public void addSquareToLinkedList(String colour, String structure) {
        Colours squareCol;
        SquareType squareStructure;
        switch (colour) {
            case "RED": squareCol = Colours.RED; break;
            case "YELLOW": squareCol = Colours.YELLOW; break;
            case "GREEN": squareCol = Colours.GREEN; break;
            case "BLUE": squareCol = Colours.BLUE; break;
            case "PURPLE": squareCol = Colours.PURPLE; break;
            case "WHITE": squareCol = Colours.WHITE; break;
            case "BROWN": squareCol = Colours.BROWN; break;
            default:
                throw new IllegalStateException("Unexpected value: " + colour);
        }
        switch (structure) {
            case "POWERPLANT": squareStructure = SquareType.POWERPLANT; break;
            case "OILRIG": squareStructure = SquareType.OILRIG; break;
            case "EMBANKMENT": squareStructure = SquareType.EMBANKMENT; break;
            case "FACTORY": squareStructure = SquareType.FACTORY; break;
            case "ZOO": squareStructure = SquareType.ZOO; break;
            case "WILD": squareStructure = SquareType.WILD; break;
            case "START": squareStructure = SquareType.START; break;
            case "JAIL": squareStructure = SquareType.JAIL; break;
            case "CHANCE": squareStructure = SquareType.CHANCE; break;
            default:
                throw new IllegalStateException("Unexpected value: " + structure);
        }
        squares.add(new Node(new Square(squareCol,squareStructure)));
    }

    @Override
    public int randomAnimalsForZoo() {
        return (int) (Math.random() * 20) + 5;
    }

    @Override
    public void payRent(Node node, Player player) throws Exception {
        System.out.println(">> You have landed on "+node.getOwner().getName()+"'s Property\nYou must pay 50 Resources & 15 animals [Y]/[N]");
        String ans = "";

        if(!ans.equals("Y")) {
            getPlayers()[player.getId()].setPosition(getSquares().findJail());
            generateOptions(getPlayers()[player.getId()].getPosition(),player.getId());
        }



        //Checking if square landed on is a Repository
        if(node.getData().getSquareType() == SquareType.REPOSITORY)
            //Sett animals of repository by 15
            node.getData().setStoredAnimals(animalRent);
        //else set inventory of animals by 15
        else getPlayers()[node.getOwner().getId()].setAnimals(animalRent);

        //Setting owners resources by +50
        getPlayers()[node.getOwner().getId()].setResources(resourceRent);

        //Reducing players animals by 15 and resources by 50
        getPlayers()[player.getId()].setResources(-resourceRent);
        getPlayers()[player.getId()].setAnimals(-animalRent);



    }


    @Override
    public void initialisePlayers() throws FileNotFoundException {
        for(int i = 0; i < amountOfPlayers; i++)
            players[i] = new Player("",3000,i);
        initialiseOwnership();
    }

    public int getAmountOfPlayers() {
        return amountOfPlayers;
    }

    @Override
    public int[] throwDice() {
        int diceThrows[] = new int[amountDiceThrows];
        for(int i = 0; i < amountDiceThrows; i++)
            diceThrows[i] = (int) (Math.random() * ((maxDice-minDice) +minDice)+minDice);
        return diceThrows;
    }

    @Override
    public void generateOptions(Node node, int player) throws Exception {
        int type = 0;
        int choice = 0;
        int data = 0;


        if(node.getData().getOwnership() == Ownership.UNPURCHASED)
            buySquare(node,getPlayers()[player]);
        else if(node.getData().getOwnership() == Ownership.PURCHASED && !checkOwnership(node,getPlayers()[player]))
            payRent(node,getPlayers()[player]);


        if(node.getData().getOwnership() == Ownership.PUBLIC || checkOwnership(node,getPlayers()[player]))
            switch (node.getData().getSquareType()) {
                case WILD: {
                    type = 1;
                    data = randomAnimalAmountFromWild(node);
                    System.out.println(">> You have collected " +data+" animals");
                    break;
                }
                case ZOO: data = randomAnimalsForZoo(); System.out.println(">> You have reached a ZOO - '"+data+"' animals have been lost"); type = 2; break;
                case JAIL: System.out.println(">> You have landed on Jail and will miss a turn!"); type = 3; break;
                case CHANCE: System.out.println(">> You landed on a Chance Card!"); type = 4; break;
                default: {
                    while (choice != 3) {
                        type = 0;
                        if(node.getData().getSquareType() != SquareType.WILD && node.getData().getSquareType() != SquareType.REPOSITORY)
                            System.out.println(">> [1] Would you like to destroy the '" + node.getData().getSquareType().getType() + "'");
                        else if(node.getData().getSquareType() != SquareType.REPOSITORY){
                            System.out.println(">> [1] Build a Repository(Resources: 500)");
                        } else if(node.getData().getSquareType() == SquareType.REPOSITORY)
                            System.out.println(">> [1] Store '"+node.getData().findDifference()+"' animals");
                        if (node.getData().getPollutionType() != PollutionType.LOW)
                            System.out.println(">> [2] Would you like to lower the Pollution levels from '" + node.getData().getPollutionType().getType() + "'");
                        System.out.println(">> [3] Would you like to skip go?\n");
                        choice = sc.nextInt();
                        determineOption(node,player,type,choice,data);
                    }
                    break;
                }
        }
        determineOption(node,player,type,choice,data);
    }

    @Override
    public void determineOption(Node node,int player, int type, int choice, int data) throws Exception {
        switch (type) {
            case 0:  {
                switch (choice) {
                    case 1: {
                        if(node.getData().getSquareType() != SquareType.WILD && node.getData().getSquareType() != SquareType.REPOSITORY) {
                            node.getData().setSquareType(SquareType.WILD);
                            getPlayers()[player].setResources(-typeMap.get(node.getData().getSquareType()));
                        } else if (node.getData().getSquareType() == SquareType.WILD) {
                            node.getData().setSquareType(SquareType.REPOSITORY);
                            getPlayers()[player].setResources(-typeMap.get(SquareType.REPOSITORY));
                            System.out.println(">> Repository has been built on Square("+node.getPlace()+")\n");
                        } else if (node.getData().getSquareType() == SquareType.REPOSITORY) {
                            int diff = node.getData().findDifference();
                            node.getData().setStoredAnimals(diff);
                            getPlayers()[player].setAnimals(-diff);
                        }
                        break;
                    }
                    case 2:  {
                        System.out.println(">> Which pollution level do you want to lower towards?");
                        for(int i = 0; i < pollutionArray.size(); i++)
                            if(node.getData().getPollutionType() != pollutionArray.get(i))
                                System.out.println(">> ["+pollutionArray.get(i).getType().substring(0,1)+"] "+pollutionArray.get(i).getType());

                            switch (sc.next()) {
                                case "H": getSquares().get(node.place).getData().setPollutionType(PollutionType.HIGH); break;
                                case "M": getSquares().get(node.place).getData().setPollutionType(PollutionType.MEDIUM); break;
                                case "L": getSquares().get(node.place).getData().setPollutionType(PollutionType.LOW); break;
                            }
                    }
                }
            }
            case 1: node.getData().setStoredAnimals(-data); getPlayers()[player].setAnimals(data); break;
            case 2: getPlayers()[player].setAnimals(-data); node.getData().setStoredAnimals(data);break;
            case 3: getPlayers()[player].setSkipTurn(true);
            case 4: cards.randomChance(node,getPlayers()[player]);
        }
    }

    @Override
    public int randomAnimalAmountFromWild(Node node) {
        int a =  (int) (Math.random() * (node.getData().getStoredAnimals() - 1)+1)+1;
        getSquares().get(node.place).getData().setStoredAnimals(-a);
        return a;
    }

    @Override
    public boolean checkOwnership(Node node, Player player) {
        return playerOwnership.get(player).contains(node.getData());
    }

    @Override
    public void initialiseOwnership() {
        for(int i = 0; i < getPlayers().length; i++) {
            playerOwnership.put(getPlayers()[i], new HashSet<Square>());
        }
    }

    @Override
    public boolean buySquare(Node node, Player player) {
        String ans = "";
        boolean bought = false;
        System.out.println(">> Do you want buy Square("+node.place+") [Y]/[N]");
        ans = sc.next();

        if(ans.equals("Y")) {
            playerOwnership.get(player).add(node.getData());
            getPlayers()[player.getId()].setResources(-node.getData().getPrice());
            node.getData().setOwnership(Ownership.PURCHASED);
            node.setOwner(player);
            bought = true;
        } else if(ans.equals("N"))
            bought = false;

        return bought;
    }

    public final void setAmountOfPlayers(int i) throws FileNotFoundException {
        this.amountOfPlayers = i;
        players = new Player[amountOfPlayers];
        initialisePlayers();
    }

    public static Game getInstance() throws FileNotFoundException {
        if(game == null)
            game = new Game("Monopoly",2);
        return game;
    }

    public String getName() {
        return name;
    }

    public int getAmountDiceThrows() {
        return amountDiceThrows;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SquareLinkedList getSquares() {
        return squares;
    }

    public void setSquares(SquareLinkedList squares) {
        this.squares = squares;
    }

    public Player getPlayersTurn() {
        return playersTurn;
    }

    public void setPlayersTurn(Player playersTurn) {
        this.playersTurn = playersTurn;
    }

    public Player[] getPlayers() {
        return players;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public State getGameState() {
        return gameState;
    }

    public HashMap<SquareType, Integer> getTypeMap() {
        return typeMap;
    }

    public ArrayList<PollutionType> getPollutionArray() {
        return pollutionArray;
    }

    public HashMap<Player, HashSet<Square>> getPlayerOwnership() {
        return playerOwnership;
    }
}
