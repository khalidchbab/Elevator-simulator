package sample;

import javafx.scene.Group;
import java.sql.Time;
import java.util.ArrayList;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Random;

import javafx.event.EventHandler;
public class Personne {
    public enum State {LEFT, RIGHT, UP, DOWN, WAIT, INSIDE};
    private int x;
    private int y;
    private int weight;
    private int src;
    private int dest;
    private int current;

    private String name;

    Group passG=new Group();
    Rectangle personne;

    private State state;

    public Personne(int src, int dest, int weight, int y, String name) {
        this.src = src;
        this.current = src;
        this.dest = dest;
        this.weight = weight;
        this.x = -50;
        this.y = y;
        this.state = State.RIGHT;
        this.name = name;
        Random r=new Random();
        int w=r.nextInt(10-1)+1;
        int h=r.nextInt(10-1)+1;
        Text t= new Text();
        t.setText("Src : "+src+" Dest : "+dest);
        t.setX(this.x);
        t.setY(this.y-100-h);
        personne=new Rectangle();
        personne.setFill(Color.color(Math.random(), Math.random(), Math.random()));
        personne.setHeight(90+h);
        personne.setWidth(30+w);
        personne.setX(this.x);
        personne.setY(this.y-90-h);
        this.passG.getChildren().add(personne);
        this.passG.getChildren().add(t);
    }



    public Group getPassG() {
        return passG;
    }

    public int getWeight() {
        return weight;
    }


    public State getState() {
        return state;
    }

    public int getCurrent() {
        return current;
    }

    public int getDest() {
        return dest;
    }

    public int getSrc() {
        return src;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public void setDest(int dest) {
        this.dest = dest;
    }

    public void setPassG(Group passG) {
        this.passG = passG;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setSrc(int src) {
        this.src = src;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Rectangle getPersonne() {
        return personne;
    }

    public String getName() {
        return name;
    }
}