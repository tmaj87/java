package pl.tmaj.demo;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Application {

    public static void main(String[] args) {
        Game game = new Game();
        game.add(new Lucio());
        game.start();
    }
}

class Game {

    private final List<Character> players = new CopyOnWriteArrayList<>();

    public void add(Character character) {
        players.add(character);
    }

    public void start() {
        players.forEach(Character::hello);
    }
}

class Lucio implements Character {

    @Override
    public void hello() {
        System.out.println("I'm rocking it");
    }
}

interface Character {

    void hello();
}
