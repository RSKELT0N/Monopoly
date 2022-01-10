package Game;

import Interfaces.IChanceCard;

import java.io.FileNotFoundException;

public class ChanceCard implements IChanceCard {

    private final int maxChanceCards;
    private final int minChanceCards;
    private final int resourceChanceAmount;
    private final int animalChanceAmount;

     ChanceCard(int minChance, int maxChance,int resourceChance,int animalChance) {
        this.minChanceCards = minChance;
        this.maxChanceCards = maxChance;
        this.resourceChanceAmount = resourceChance;
        this.animalChanceAmount = animalChance;

    }

    public void randomChance(Node node, Player player) throws Exception {
            int chance = (int) ((Math.random() * maxChanceCards-minChanceCards)+1)+minChanceCards;

            switch(chance) {
                case 1: increaseResourcesChance(node,player); break;
                case 2: increaseAnimalsChance(node,player);break;
                case 3: sentToJailChance(node,player);break;
                case 4: randomPropertyChance(node,player);break;
                case 5: break;
            }
    }

    @Override
    public void increaseResourcesChance(Node node, Player player) throws FileNotFoundException {
         Game.getInstance().getPlayers()[player.getId()].setResources(resourceChanceAmount);
         System.out.println(">> "+Game.getInstance().getPlayers()[player.getId()].getName()+" has just been rewarded "+this.resourceChanceAmount+"!");
    }

    @Override
    public void increaseAnimalsChance(Node node, Player player) throws FileNotFoundException {
        Game.getInstance().getPlayers()[player.getId()].setAnimals(animalChanceAmount);
        System.out.println(">> "+Game.getInstance().getPlayers()[player.getId()].getName()+" has just been rewarded "+this.animalChanceAmount+"!");
    }

    @Override
    public void deleteRandomNodeChance(Node node, Player player) {

    }

    @Override
    public void sentToJailChance(Node node, Player player) throws Exception {
         Game.getInstance().getPlayers()[player.getId()].setPosition(Game.getInstance().getSquares().findJail());
        System.out.println(">> "+Game.getInstance().getPlayers()[player.getId()].getName()+" has just been sent To Jail!");
    }

    @Override
    public void randomPropertyChance(Node node, Player player) throws FileNotFoundException {
         Game.getInstance().getPlayers()[player.getId()].setPosition(Game.getInstance().getSquares().get((int) ((Math.random() * Game.getInstance().getSquares().size() - 0) + 1)));
        System.out.println(">> "+Game.getInstance().getPlayers()[player.getId()].getName()+" has just lan ded on a Random Square("+Game.getInstance().getPlayers()[player.getId()].getPosition().getPlace()+")");
    }
}
