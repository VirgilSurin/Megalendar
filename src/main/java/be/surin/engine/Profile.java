package be.surin.engine;

public class Profile {

    private String name;
    private Agenda agenda;

    public Profile(String name) {
        this.name = name;
        agenda = new Agenda(name);
    }

    // Will be used when loading a saved profile
    public Profile(String name, Agenda agenda) {
        this.name = name;
        this.agenda = agenda;
    }

    public Agenda getAgenda() {
        return agenda;
    }

    public String getName() {
        return name;
    }

    public void setAgenda(Agenda agenda) {
        this.agenda = agenda;
    }

    public void setName(String name) {
        this.name = name;
    }
}
