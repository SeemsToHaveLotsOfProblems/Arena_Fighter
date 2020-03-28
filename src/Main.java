import javafx.application.Application;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.Scene;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class Main extends Application {
    /*
     * This is the main class in my Arena Fighter game.
     * This is a simplistic game to reinforce and help me learn
     * Java and JavaFX.
     *
     * PROGRAM Info:
     *  Main Window:
     *      This window should show your characters name, gold, stats, number of fights, and fame.
     *      From this window you should be able to choose to "Fight in the Arena",
     *      "Train", or "Talk with fans."
     *
     *  Training Window:
     *      This window should show stats:
     *          Strength (Your attack power)
     *          Endurance (Mix between Health and Defense)
     *          Fatigue (Stat is decreased for every continuous fight)
     *          Determination (Special stat for randomly preventing Endurance drop or boosting training results)
     *      The player may choose to train up any stat they wish at the cost of their gold.
     *
     *  Talk with fans Window:
     *      Here you can see how many fans you have and here what they're saying about you.
     *      More functionality may be added later.
     *
     *  Arena Window:
     *      Here you may place bets on your fights, and pay an entry fee to fight.
     *      There are various classes you may choose to fight in. Each subsequent class
     *      grants a added bonus to your bet if you win.
     *      CLASSES:
     *          Beginner Class (Open to all, with a 10 gold entry fee.) * 2 bet & fame
     *          Regular Class (Open to those with at least 3 fights under their belt. 7 gold entry fee) * 4 bet * fame
     *          Recognized Class (Open to those with 3 fights, and 10 fame.) * 7 bet & fame
     *          Fan Favorite Class (Open to those with 10 fights and 50 fame) * 10 bet & fame
     *          Expert Class (Open to those with 25 Strength) * 15 bet & fame
     *          Champion Class (Open to those with 30 Str, 5 Det, 20 Fights, and 100 Fame) * 20 bet & fame
     *
     */

    //Global Variables
    Scene main, fans, training, arena;

    static String pName = "Aaren";
    static int numFans = 0;
    static double cash =20.50;
    static int numFights = 0;
    static int fightsWon = 0;
    static int fightsLost = 0;
    static double strength = 1.0;
    static double endurance = 5.0;
    static double fatigue = 1.0;
    static double determination = 1.5; //Do not change determination stat!
    static boolean detCheckSuccess = false;

    public static void main(String[] args){
        launch(args);
    }// End Main

    @Override
    public void start(Stage stage) throws Exception {
        //Displaying the contents of the stage
        stage = waitingRoom(stage);
        stage.setTitle("Arena Fighter");
        stage.show();


    }


    public static Stage waitingRoom(Stage stage) throws FileNotFoundException{
        //Waiting room should be finished but may need a small change here or there.

        //Arena Window
        Image image = new Image(new FileInputStream("src/graphics/Arena_Fighter_mainArea.png"));
        ImageView imageView = new ImageView(image);
        //Creating a Group object
        Group root = new Group(imageView);

        //Creating a scene object
        Scene scene = new Scene(root, 500, 450);

        //Adding scene to the stage
        stage.setScene(scene);

        //Buttons
        Button arena = new Button("Fight in the Arena");
        Button training = new Button("Training");
        Button talk = new Button("Talk with your fans");

        //Labels
        Label nameLabel, cashLabel, numFightsLabel, fameLabel;
        nameLabel = new Label("Name: " + pName);
        cashLabel = new Label("Cash: $" + cash);
        numFightsLabel = new Label("Number of fights: " + numFights);
        fameLabel = new Label("Fame: " + numFans);

        //Layout
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10,10,10,10));
        grid.setVgap(15.0);
        grid.setHgap(10.0);

        //Label placement
        GridPane.setConstraints(nameLabel, 0, 0);
        GridPane.setConstraints(cashLabel, 1, 0);
        GridPane.setConstraints(numFightsLabel, 2, 0);
        GridPane.setConstraints(fameLabel, 3, 0);
        //Button placement
        GridPane.setConstraints(talk, 0,15);
        GridPane.setConstraints(training, 0,16);
        GridPane.setConstraints(arena, 0,17);

        //Add Buttons To Scene
        grid.getChildren().addAll(arena, training, talk, nameLabel, cashLabel, numFightsLabel, fameLabel);
        root.getChildren().add(grid);

        //Button Handlers
        talk.setOnAction(e -> {
            try {
                talkWithFans(stage);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }); //Lambdas are so awesome!
        training.setOnAction(e -> {
            try {
                trainingRoom(stage);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }); //Lambdas are so awesome!
        arena.setOnAction(e -> {
            try {
                arenaFighting(stage);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }); //Lambdas are so awesome!


        return stage;
    } //DONE!!!!!


    public static Stage talkWithFans(Stage stage) throws FileNotFoundException{
        //Remember to change the image
        Image image = new Image(new FileInputStream("src/graphics/Arena_Fighter_fanArea.png"));
        ImageView imageView = new ImageView(image);

        //Creating a Group object
        Group root = new Group(imageView);

        //Creating a scene object
        Scene scene = new Scene(root, 500, 450);

        //Adding scene to the stage
        stage.setScene(scene);

        //Labels
        Label nameLabel = new Label("Name: " + pName);
        Label fameLabel = new Label("Fame: " + numFans);

        //Buttons
        Button backToWaitingRoom = new Button("Go back to Waiting room");
        Button talk = new Button("Talk to a fan");


        //Layout
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10,10,10,10));
        grid.setVgap(15.0);
        grid.setHgap(10.0);

        //Label Placement
        GridPane.setConstraints(nameLabel, 1, 0);
        GridPane.setConstraints(fameLabel, 2, 0);
        //Button Placement
        GridPane.setConstraints(backToWaitingRoom, 0, 17);
        GridPane.setConstraints(talk, 0, 16);

        //Adding components to scene
        grid.getChildren().addAll(nameLabel,fameLabel,backToWaitingRoom,talk);
        root.getChildren().add(grid);

        //Button functions
        talk.setOnAction(e -> {
            String convo = conversation();

            //Find out how to add transparent window and place text on it.
            Label txt = new Label(convo);
            Button nextText = new Button("Next/End");
            Image textBackground = null;
            try {
                textBackground = new Image(new FileInputStream("src/graphics/Arena_Fighter_textBackground.png"));
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
            ImageView imageView2 = new ImageView(textBackground);

            //Aligning text and buttons
            root.getChildren().add(imageView2); //Adding to root solves my problem
            GridPane.setConstraints(txt, 2, 21);
            GridPane.setConstraints(nextText, 10, 24);
            grid.toFront();

            //Adding stuff to the stage
            grid.getChildren().removeAll(backToWaitingRoom,talk);
            grid.getChildren().addAll(txt, nextText);

            nextText.setOnAction(a -> {
                //Close out text window and re-add the buttons.
                root.getChildren().remove(imageView2);
                grid.getChildren().removeAll(txt, nextText);
                grid.getChildren().addAll(backToWaitingRoom,talk);
            });
        });

        backToWaitingRoom.setOnAction(e -> {
            try {
                waitingRoom(stage);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        });


        return stage;
    } //DONE!!!!!

    public static String conversation(){
        String convo = "";
        Random rand = new Random();
        int num = 0;

        //Fame check
        if(numFans == 0){
            convo = "You have no fans yet.";
        } else if(numFans < 10) {
            num = rand.nextInt(4);
            if(num ==1){
                convo = "I think I've seen you fighting. Good luck out there!";
            } else if(num == 2) {
                convo = "Who're you, a fighter?";
            } else if(num == 3) {
                convo = "Oh I know you, you're the new guy! I'm rooting for you!";
            } else {
                convo = "The people who fight in the arena are so cool!";
            }
        } else if(numFans < 20) {
            num = rand.nextInt(4);
            if(num ==1){
                convo = "I think I've seen you fighting. Good luck out there!";
            }
        } else if(numFans < 40) {
            num = rand.nextInt(4);
            if(num ==1){
                convo = "I think I've seen you fighting. Good luck out there!";
            }
        } else if(numFans < 80) {
            num = rand.nextInt(4);
            if(num ==1){
                convo = "I think I've seen you fighting. Good luck out there!";
            }
        } else if(numFans <= 100) {
            num = rand.nextInt(4);
            if(num ==1){
                convo = "I think I've seen you fighting. Good luck out there!";
            }
        } else {
            num = rand.nextInt(4);
            if(num ==1){
                convo = "I think I've seen you fighting. Good luck out there!";
            }
        }

        return convo;
    }


    public static Stage trainingRoom(Stage stage) throws FileNotFoundException{
        //Remember to change the image
        Image image = new Image(new FileInputStream("src/graphics/Arena_Fighter_mainArea.png"));
        ImageView imageView = new ImageView(image);

        //Creating a Group object
        Group root = new Group(imageView);

        //Creating a scene object
        Scene scene = new Scene(root, 500, 450);

        //Adding scene to the stage
        stage.setScene(scene);

        //Creating a grid
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10,10,10,10));
        grid.setVgap(15.0);
        grid.setHgap(10.0);

        //Stat Labels
        Label stats, strengthLabel, enduranceLabel, fatigueLabel, determinationLabel;
        stats = new Label("STATS");
        strengthLabel = new Label("Strength: " + strength);
        enduranceLabel = new Label("Endurance: " + endurance);
        fatigueLabel = new Label("Fatigue: " + fatigue);
        determinationLabel = new Label("Determination: " + determination);

        //Label positioning
        GridPane.setConstraints(stats, 7, 0);
        GridPane.setConstraints(strengthLabel, 8, 1);
        GridPane.setConstraints(enduranceLabel, 8, 2);
        GridPane.setConstraints(fatigueLabel, 8, 3);
        GridPane.setConstraints(determinationLabel, 8, 4);

        //Buttons
        Button strTrain = new Button("Train Strength");
        Button endTrain = new Button("Train Endurance");
        Button fatTrain = new Button("Train Fatigue");
        Button back = new Button("Back to Waiting room");

        //Button positioning
        GridPane.setConstraints(strTrain, 0, 7);
        GridPane.setConstraints(endTrain, 0, 8);
        GridPane.setConstraints(fatTrain, 0, 9);
        GridPane.setConstraints(back, 0, 10);

        //Adding stuff to the grid
        grid.getChildren().addAll(stats, strengthLabel, enduranceLabel, fatigueLabel,
                determinationLabel, strTrain, endTrain, fatTrain, back);

        //Setting the scene
        root.getChildren().add(grid);

        //Button lambdas
        strTrain.setOnAction(e -> {
            //Removing stuff from grid
            grid.getChildren().removeAll(stats,strengthLabel,enduranceLabel,fatigueLabel,determinationLabel,
                    strTrain,endTrain,fatTrain,back);
            trainingMenu("Strength", grid, stage);
        });

        endTrain.setOnAction(e -> {
            //Removing stuff from grid
            grid.getChildren().removeAll(stats,strengthLabel,enduranceLabel,fatigueLabel,determinationLabel,
                    strTrain,endTrain,fatTrain,back);
            trainingMenu("Endurance", grid, stage);
        });

        fatTrain.setOnAction(e -> {
            //Removing stuff from grid
            grid.getChildren().removeAll(stats,strengthLabel,enduranceLabel,fatigueLabel,determinationLabel,
                    strTrain,endTrain,fatTrain,back);
            trainingMenu("Fatigue", grid, stage);
        });


        back.setOnAction(e -> {
            try {
                waitingRoom(stage);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        });


        return stage;
    } //DONE!!!!


    public static void trainingMenu(String nameOfStat, GridPane grid, Stage stage){
        //Variables
        int statToTrain = 0; // 1 = Strength, 2 = Endurance, 3 = Fatigue
        double cashToTrain = 0.0;
        if(nameOfStat.toLowerCase().equals("strength")){
            statToTrain = 1;
            cashToTrain = cashToTrain(strength);
        } else if(nameOfStat.toLowerCase().equals("endurance")){
            statToTrain = 2;
            cashToTrain = cashToTrain(endurance);
        } else {
            statToTrain = 3;
            cashToTrain = cashToTrain(fatigue);
        }


        //Remember to change the image
        try {
            Image image2 = new Image(new FileInputStream("src/graphics/Arena_Fighter_mainArea.png"));
            ImageView imageView2 = new ImageView(image2);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }

        //Labels
        Label nameLabel = new Label("Name:" + pName);
        Label cashLabel = new Label("Cash: $" + cash);

        //Buttons
        Button backToTraining = new Button("Back to training area");
        Button train = new Button("Train " + nameOfStat + " for $" + cashToTrain);

        //Position setting
        GridPane.setConstraints(nameLabel, 0, 0);
        GridPane.setConstraints(cashLabel, 1, 0);
        GridPane.setConstraints(train, 0, 10);
        GridPane.setConstraints(backToTraining, 0, 11);

        //Adding to the grid
        grid.getChildren().addAll(nameLabel, cashLabel, backToTraining, train);

        //Button functions
        backToTraining.setOnAction(a -> {
            try {
                trainingRoom(stage);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        });
        double finalCashToTrain = cashToTrain;
        int finalStatToTrain = statToTrain;
        train.setOnAction(b -> {
            //Lambda variables
            double statGains = 0;
            String statName = "";

            if (cash >= finalCashToTrain){
                //Should deduct the cash from player and boost stat by number returned from training method
                cash -= finalCashToTrain;

                if(finalStatToTrain == 1) {
                    //Strength
                    statGains = powerGain();
                    strength += statGains;
                    statName = "strength";
                } else if(finalStatToTrain == 2){
                    //Endurance
                    statGains = powerGain();
                    endurance += statGains;
                    statName = "endurance";
                } else {
                    //Fatigue
                    statGains = powerGain();
                    fatigue += statGains;
                    statName = "fatigue";
                }

                //Labels & Buttons
                Label statGainLabel = new Label("Congratulations your " + statName + " grew by " +
                        statGains + " points!");
                Label noStatGainsLabel = new Label("Your training failed!");
                Label bonusStatGainLabel = new Label("You were determined in your training and gained \n" +
                        "twice the results! \n" + statName + " grew by " + statGains + " points!");
                Button nextTextButton = new Button("Next/End");

                //Setting the layout
                GridPane.setConstraints(bonusStatGainLabel, 0, 10);
                GridPane.setConstraints(statGainLabel, 0, 10);
                GridPane.setConstraints(noStatGainsLabel, 0, 10);
                GridPane.setConstraints(nextTextButton, 1, 11);

                //Showing stuff on screen
                grid.getChildren().removeAll(train, backToTraining);

                if(statGains == 0){
                    grid.getChildren().addAll(noStatGainsLabel, nextTextButton);
                } else if(detCheckSuccess && statGains > 0){
                    detCheckSuccess = false;
                    grid.getChildren().addAll(bonusStatGainLabel, nextTextButton);
                    nextTextButton.setOnAction(e -> {
                        grid.getChildren().remove(bonusStatGainLabel);
                    });
                } else {
                    grid.getChildren().addAll(statGainLabel, nextTextButton);
                }

                nextTextButton.setOnAction(e -> {
                    grid.getChildren().removeAll(statGainLabel, noStatGainsLabel, nextTextButton);
                    try {
                        trainingRoom(stage);
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    }
                });

            } else {
                //Label should show not enough money and go back to training area.
                Label notEnoughCash = new Label("You don't have enough money to do that!");
                Button closeDialogue = new Button("Close");

                GridPane.setConstraints(notEnoughCash, 1, 13);
                GridPane.setConstraints(closeDialogue, 2, 14);

                grid.getChildren().removeAll(train, backToTraining);
                grid.getChildren().addAll(notEnoughCash, closeDialogue);

                closeDialogue.setOnAction(e -> {
                    grid.getChildren().removeAll(notEnoughCash, closeDialogue);
                    grid.getChildren().addAll(train, backToTraining);
                });
            }
        });
    } //DONE!!!!


    public static double cashToTrain(double val){
        Random rand = new Random();
        val = val * 2 + rand.nextInt((int)val);
        return val;
    }


    public static double powerGain(){
        //Do not change determination stat!
        Random rand = new Random();
        int statGain = rand.nextInt(5);
        double trainAgain = determination + rand.nextInt(2);
        if (trainAgain >= 2.5){
            statGain += rand.nextInt(5);
            detCheckSuccess = true;
        }
        return statGain;
    }


    public static Stage arenaFighting(Stage stage) throws FileNotFoundException{
        //Remember to change the image
        Image image = new Image(new FileInputStream("src/graphics/Arena_Fighter_mainArea.png"));
        ImageView imageView = new ImageView(image);

        //Creating a Group object
        Group root = new Group(imageView);


        return stage;
    }


}// End Class Main
