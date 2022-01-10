package Start;

import Enumerations.State;
import Game.*;
import Interfaces.IPlay;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Play implements IPlay {
    Scanner sc = new Scanner(System.in);

    @Override
    public void run() throws Exception {
        initialiseGame();
        while (Game.getInstance().getGameState() == State.ACTIVE) {
            for (int i = 0; i < Game.getInstance().getPlayers().length; i++) {
                if (!checkJail(i))
                    playersTurn(i);
            }
        }
    }

    @Override
    public void initialiseGame() throws FileNotFoundException {
        System.out.println("Welcome To " + Game.getInstance().getName() + "\n----------------------------------");
        System.out.print("How many players are playing?: ");
        Game.getInstance().setAmountOfPlayers(sc.nextInt());
        System.out.print("\n");
        for (int i = 0; i < Game.getInstance().getPlayers().length; i++) {
            System.out.println("Enter Player(" + (i + 1) + ") name: ");
            Game.getInstance().getPlayers()[i].setName(sc.next());
        }
    }

    @Override
    public void playersTurn(int i) throws Exception {
        Player player = Game.getInstance().getPlayers()[i];
        System.out.println(printTurnInfo(player));

        System.out.println(">> 'R' to roll");
        int total = 0;
        if (sc.next().equals("R"))
            total = throwDice(total);

        System.out.println(">> " + player.getName() + " jumps '" + total + "' positions");
        Node tempNode = Game.getInstance().getSquares().jumpPos(total, i);
        System.out.println("\n>> " + player.getName() + " has landed on " + "Square(" + player.getPosition().getPlace() + ")\n--------------------------------" + tempNode.getData().toString());
        Game.getInstance().generateOptions(tempNode, i);
    }

    @Override
    public boolean checkJail(int i) throws Exception {
        if (Game.getInstance().getPlayers()[i].isSkipTurn()) {
            System.out.println("\n>> " + Game.getInstance().getPlayers()[i].getName() + "'s turn has been missed!");
            Game.getInstance().getPlayers()[i].setSkipTurn(false);
            return true;
        }
        return false;
    }

    @Override
    public int throwDice(int total) throws FileNotFoundException {
        for (int j = 0; j < Game.getInstance().getAmountDiceThrows(); j++) {
            int nums[] = Game.getInstance().throwDice();
            //System.out.println("Dice throw " + (j + 1) + ": " + nums[j]);
            total += nums[j];
        }
        return total;
    }

    @Override
    public String printTurnInfo(Player player) {
        return "\n--------------------------------\n   " + player.getName() + "'s turn : Square(" + player.getPosition().getPlace() + ")\n" +
                "Animal(" + player.getAnimals() + ") : Resources(" + (int) player.getResources() + ")" +
                "\n--------------------------------" + player.getPosition().getData().toString() + "--------------------------------";
    }
}
