package sample;

import javafx.scene.Group;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class Etage {
    private int y;
    private int num;

    Group etageG=new Group();
    Rectangle etageL=new Rectangle();
    Rectangle etageR=new Rectangle();

    ArrayList<Personne> personnes = new ArrayList<>();
    ArrayList<Personne> departing = new ArrayList<>();

    public Etage(int y,int num){
        this.y=y;
        this.num=num;
    }
    public void AjouterPersonne(Personne p){
        if(!personnes.contains(p))
            personnes.add(p);
    }

    public int getY() {
        return y;
    }

    public int getNum() {
        return num;
    }

    public ArrayList<Personne> getPersonnes() {
        return personnes;
    }

    public ArrayList<Personne> getDeparting() {
        return departing;
    }
}
