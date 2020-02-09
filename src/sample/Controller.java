package sample;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.FileNotFoundException;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Controller {
    ArrayList<Etage> etages = new ArrayList<>();
    Assenceur assenceur1;
    private HandleAssService handle;
    private HandleSpawnService spawn=new HandleSpawnService();
    @FXML
    private  AnchorPane building;

    public void initialize() throws FileNotFoundException {
        assenceur1=new Assenceur(floorCalcul(4),500,4);
        handle=new HandleAssService(assenceur1);
        for(int i=1;i<6;i++)
        {
            etages.add(new Etage(floorCalcul(i),i));
        }

        for (Etage e:etages) {
            e.etageL.setWidth(595);
            e.etageL.setHeight(10);
            e.etageL.setY(e.getY());
            e.etageR.setWidth(300);
            e.etageR.setHeight(10);
            e.etageR.setY(e.getY());
            e.etageR.setX(700);
            e.etageG.getChildren().add(e.etageL);
            e.etageG.getChildren().add(e.etageR);
        }
        building.getChildren().add(assenceur1.assenceurG);


        spawn();
        spawn();
        spawn();
        handle.restart();


    }

    public void spawn(){
        Random r=new Random();
        int dest=0;
        int src=r.nextInt((4-1)+1)+1;
        while (true){
            dest=r.nextInt((4-1)+1)+1;
            if(dest!=src)
                break;
        }
        if(etages.get(src).personnes.size()<=3) {
            Personne p = new Personne(src, dest, 100, floorCalcul(src), Integer.toString(src));
            etages.get(src).AjouterPersonne(p);
            assenceur1.addCall(src);
            building.getChildren().add(walk_in(p));
        }
    }

    public void checkForAssenceur(Personne p,Assenceur a){
        if(a.getCurrent()==p.getCurrent()){
            board(assenceur1,p);
        }
        else{
            assenceur1.addCall(p.getSrc());
        }
    }
    public void checkDepartures(Assenceur a){
        System.out.println("People Inside: "+a.personnes.size());
        for (Personne p:a.personnes){
            if(p.getDest()==a.getCurrent()){
                building.getChildren().add(walk_out(p));
            }
        }
    }

    public void checkPersonnes(Assenceur a){
        System.out.println("Assenceur est à etage : "+a.getCurrent());
        ArrayList<Personne> tmp=etages.get(a.getCurrent()).personnes;
        if(!tmp.isEmpty()){
            System.out.println("Boarding People");
            Iterator iterator = etages.get(a.getCurrent()).personnes.iterator();
            while (iterator.hasNext())
            {
                Personne temp = (Personne) iterator.next();
                board(assenceur1,temp);
                iterator.remove();
            }
        }
    }

    public Group walk_in(Personne p){
        p.setState(Personne.State.RIGHT);
        TranslateTransition walk=new TranslateTransition();
        walk.setDuration(Duration.seconds(2));
        walk.setNode(p.passG);
        walk.setByX(550);
        walk.play();
        walk.setOnFinished(e->p.setState(Personne.State.WAIT));
        return p.passG;
    }

    public Group walk_out(Personne p){
        p.setState(Personne.State.RIGHT);
        TranslateTransition walk=new TranslateTransition();
        walk.setDuration(Duration.seconds(1.5));
        p.personne.setY(p.getY()-90);
        p.personne.setX(650);
        p.passG=new Group();
        p.passG.getChildren().add(p.personne);
        walk.setNode(p.passG);
        walk.setByX(700);
        walk.play();
        Random r=new Random();
        int f=r.nextInt(2);
        walk.setOnFinished(e->{
            assenceur1.personnes.remove(p);
            building.getChildren().remove(p.passG);
            for(int i=0;i<=f;i++)
                spawn();
        });
        return p.passG;
    }

    public void upAssenceur(Assenceur a){
        assenceur1.setState( Assenceur.State.UP);
        assenceur1.setClose(true);
        assenceur1.setOpen(false);
        System.out.println("Before i go up im in : "+a.getCurrent());
        if (assenceur1.getCurrent() != 4){
            TranslateTransition elevt = new TranslateTransition(Duration.seconds(1.5));
            elevt.setNode(a.assenceurG);
            elevt.setByY(-150);
            elevt.setOnFinished(e -> {a.setCurrent(a.getCurrent()+1);updatePersonnes(a);a.removeCall(a.getCurrent());checkDepartures(a);checkPersonnes(a);});
            elevt.play();
            System.out.println("up");
        }
    }
    public void updatePersonnes(Assenceur a){
        for (Personne p:a.personnes)
        {
            p.setCurrent(a.getCurrent());
            p.setY(floorCalcul(p.getCurrent()));
        }
    }
    public void downAssenceur(Assenceur a){
        assenceur1.setState( Assenceur.State.DOWN);
        assenceur1.setClose(true);
        assenceur1.setOpen(false);
        System.out.println("Before i go down im in : "+a.getCurrent());
        if (assenceur1.getCurrent() != 1){
            TranslateTransition elevt = new TranslateTransition(Duration.seconds(1.5));
            elevt.setNode(a.assenceurG);
            elevt.setByY(150);
            elevt.setOnFinished(e -> {
                a.setCurrent(a.getCurrent()-1);
                updatePersonnes(a);
                a.removeCall(a.getCurrent());
                checkDepartures(a);
                checkPersonnes(a);
            });
            elevt.play();
            System.out.println("down");
        }
    }

    public void call(int d){
        assenceur1.addCall(d);
    }

    public void board(Assenceur a,Personne p){
        p.setState(Personne.State.RIGHT);
        TranslateTransition board=new TranslateTransition();
        board.setDuration(Duration.seconds(1));
        board.setNode(p.passG);
        board.setToX(700);
        board.play();
        board.setOnFinished(e->{
            //call(p.dest);
            building.getChildren().remove(p.passG);
            etages.get(p.getCurrent()).personnes.remove(p);
            a.addPersonne(p);
            //handle.restart();
        });
        p.setX(p.getX()+150);
        //handle.cancel();
    }

    public int floorCalcul(int n){
        switch (n){
            case 4:
                return 150;
            case 3:
                return 300;
            case 2:
                return 450;
            default:
                return 600;
        }
    }

    private class HandleAssService extends Service {
        Assenceur a;
        private HandleAssService(Assenceur a) {
            this.a=a;
            setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent e) {
                    e.getSource().getValue();
                }
            });
        }

        @Override
        protected Task createTask() {
            return new Task() {
                @Override
                protected Object call() throws Exception {
                    int i=0;
                    while (true)
                    {
                        if(!a.calls.isEmpty()) {
                            System.out.println("calls : "+a.calls);
                            int next = (int) a.calls.get(0);
                            if (a.getCurrent() < next) {
                                upAssenceur(a);
                            } else {
                                downAssenceur(a);
                            }
                            Thread.sleep(3000);
                    }
                        else{
                            System.out.println("im gonna sleep 1s");
                            Thread.sleep(1000);

                        }
                    }
                }
            };
        }
    }

    private class HandleSpawnService extends Service {
        private HandleSpawnService() {
            setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent e) {
                    e.getSource().getValue();
                }
            });
        }

        @Override
        protected Task createTask() {
            return new Task() {
                @Override
                protected Object call() throws Exception {
                    while (true)
                    {

                    }
                }
            };
        }
    }
}
