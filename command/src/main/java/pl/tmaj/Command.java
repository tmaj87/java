package pl.tmaj;

public interface Command {

    String execute();

    String execute(String... args);

}
