package sample;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Assenceur {
    public enum State {UP, DOWN, WAIT};

    private State state;
    private boolean isOpen;
    private boolean isClose;
    private State direction;

    private int x;
    private int y;
    private int current;
    private int maxWeight;

    Rectangle assenceur;
    Group assenceurG;

    Image img;
    ImageView imgv;

    ArrayList<Personne> personnes;
    ArrayList<Integer> calls=new ArrayList<Integer>();

    public Assenceur(int y,int maxWeight,int current) throws FileNotFoundException
    {
        x=600;
        this.y=y;
        this.current=current;
        this.maxWeight=maxWeight;
        isClose=false;
        isOpen=true;
        state=State.WAIT;
        assenceurG=new Group();
        personnes=new ArrayList<>();

        assenceur=new Rectangle();
        assenceur.setHeight(130);
        assenceur.setWidth(100);
        assenceur.setX(this.x);
        assenceur.setY(this.y-130);

        this.img=new Image(new FileInputStream("C:\\Users\\KhalidCH\\Desktop\\JAVA\\Assenceur Tries\\Assenceur 5\\src\\images\\elevator.png"));
        this.imgv=new ImageView(img);
        imgv.setX(this.x-5);
        imgv.setY(this.y-130);
        imgv.setFitWidth(110);
        imgv.setFitHeight(130);

        this.assenceurG.getChildren().add(imgv);

    }

    public void addPersonne(Personne p)
    {
        if(!personnes.contains(p))
        {
            personnes.add(p);
            addCall(p.getDest());
        }
    }
    public void removePersonne(Personne p)
    {
        if(!personnes.contains(p))
        {
            personnes.remove(p);
        }
    }


    public void addCall(int n)
    {
        if(!calls.contains(n))
        {
            System.out.println("Adding to calls : "+n);
            calls.add(n);
        }
    }
    public void removeCall(Integer n)
    {
        if(calls.contains(n))
        {
            System.out.println("Removing to calls : "+n);
            calls.remove(n);
        }
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setDirection(State direction) {
        this.direction = direction;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public void setClose(boolean close) {
        isClose = close;
    }

    public void setAssenceurG(Group assenceurG) {
        this.assenceurG = assenceurG;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    public void setImgv(ImageView imgv) {
        this.imgv = imgv;
    }

    public void setMaxWeight(int maxWeight) {
        this.maxWeight = maxWeight;
    }

    public void setPersonnes(ArrayList<Personne> personnes) {
        this.personnes = personnes;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public int getCurrent() {
        return current;
    }

    public State getState() {
        return state;
    }

    public State getDirection() {
        return direction;
    }

    public Group getAssenceurG() {
        return assenceurG;
    }

    public ArrayList<Personne> getPersonnes() {
        return personnes;
    }

    public int getMaxWeight() {
        return maxWeight;
    }

    public Image getImg() {
        return img;
    }

    public ImageView getImgv() {
        return imgv;
    }
}
