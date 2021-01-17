package com.sample;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.offset.PointOption;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class Main extends Application {

    // sutff that I don't undertand but still needs to be used across methods
    static AndroidDriver driver;
    static int strikes;
    static boolean launched = false;
    static TouchAction action;
    static boolean check = true;

    static GridPane pane = new GridPane();
    static Text title = new Text("~~The Melo Moon Live Stream~~");
    static Text donors = new Text("");

    static int NumUsers = 150;

    //bunch of arrays for stats
    static String[] donorList = new String[NumUsers];
    //int[] donorCount = new int[NumUsers];
    static int[] sortedCoins = new int[NumUsers];
    static String[] sortedUsers = new String[NumUsers];
    static String[] scrollTemp = {" "};
    static int index = 0;

    //making a bunch of text objects to display text
    static Text text = new Text();
    static Text text2 = new Text();
    static Text text3 = new Text();
    static Text text4 = new Text();
    static Text text5 = new Text();
    static Text text6 = new Text();
    static Text message = new Text();

    //making button objects to get to 'locations' in the GUI
    static Button home = new Button("Home");
    static Button start = new Button("Begin");
    static Button game = new Button("Play Game");
    static Button guessing = new Button("Guessing Game");
    static Button riddle = new Button("Riddle");
    static Button next = new Button("Next");
    static Button next2 = new Button("Next");
    static Button stats = new Button("Stats");
    static Button history = new Button(("History"));
    static Button up = new Button("Up");
    static Button down = new Button("Down");
    static Button scoreboard = new Button("Scoreboard");
    static Button close = new Button("Close");

    //variable to refrence which game is being played
    static int[] gameNum = {-1};

    //text Fields so the user can input text
    static TextField textfield = new TextField();

    //the main method launches the GUI
    public static void main(String[] args) {
        Application.launch(args);
    }

    //the GUI
    public void start(Stage primaryStage) {

        //starting the live stream variables
        //===============================================================================================================
        primaryStage.setTitle("TikTok Livestream");

        //lsit of strings for the number of people who donate
        String[] user = new String[1];

        //new pane in a grid fashion
        pane.setHgap(20);
        pane.setVgap(20);
        pane.setAlignment(Pos.CENTER);

        //centering most things
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setHalignment(HPos.CENTER);
        pane.getColumnConstraints().add(column1);

        //text and buttons for the very start of te program
        Text reminder = new Text("Bluestacks, ADB and Appium should be running btw");
        Text startText = new Text("Use Default(BEST) User? (themelomoon)");
        TextField startField = new TextField();
        Button yes = new Button("Yes");
        Button no = new Button("Other user");
        Button startButton = new Button("Enter");
        Button startContinue = new Button("Continue");
        Button startBack = new Button("Back");

        //during the live stream buttons
        //==============================================================================================================
        //setting up the home screen with a title and a list of donators


        //strings to store input from the user (note that they're arrays because lambdas don't like non-final variables
        String[] textFIn = new String[1];
        String[] answer = new String[1];

        //longer arrays for storeing the most recent guesses for games
        String[] users = new String[5];
        String[] guesses = new String[5];
        int[] numGuesses = new int[1];

        TextField guessField = new TextField();

        //genreal stuff
        //==============================================================================================================

        //setting the starting scene
        Scene scene = new Scene(pane, 1100, 550);
        String css = Main.class.getResource("stylesheet.css").toExternalForm();
        scene.getStylesheets().add(css);
        primaryStage.setScene(scene);
        primaryStage.show();

        //adding the very starting objects to the pane
        pane.add(reminder, 0, 0);
        pane.add(startText, 0, 1);
        pane.add(yes, 0, 3);
        pane.add(no, 0, 4);
        pane.add(close, 0, 6);

        //starting the livesteram button actions
        //==============================================================================================================
        //button for if they want to use best user
        yes.setOnAction(value ->
        {
            pane.getChildren().clear();
            user[0] = "themelomoon";
            startText.setText("Livestream for: " + user[0]);
            pane.add(startText, 0, 0);
            pane.add(startContinue, 0, 1);
            pane.add(startBack, 0, 4);
        });

        //button for if they want to use a worse user
        no.setOnAction(value -> {
            pane.getChildren().clear();
            startText.setText("Enter the cap of a user then:");
            pane.add(startText, 0, 0);
            pane.add(startField, 0, 1);
            pane.add(startButton, 0, 2);
        });

        //if they pressed no gives user a text field to imput user as well, this button gets th etext from the text field
        startButton.setOnAction(value -> {
            user[0] = startField.getText();
            pane.getChildren().clear();
            startText.setText("Livestream for: " + user[0]);
            pane.add(startText, 0, 0);
            pane.add(startContinue, 0, 1);
            pane.add(startBack, 0, 4);
        });

        //back button for if they decide they want a different user
        startBack.setOnAction(value -> {
            pane.getChildren().clear();
            pane.add(reminder, 0, 0);
            startText.setText("Use Default(BEST) User? (themelomoon)");
            pane.add(startText, 0, 1);
            pane.add(yes, 0, 3);
            pane.add(no, 0, 4);
            pane.add(close, 0, 6);
        });

        //confermation button once they have the user they want
        startContinue.setOnAction(value -> {
            //gives user a loading screen

            //move to a loading screen
            pane.getChildren().clear();
            startText.setText("Loading ... see Bluestacks and Appium do stuff for enjoyment");
            pane.add(startText, 0, 0);
            pane.add(startBack, 0, 1);
            pane.add(home, 0, 2);

            try {
                TiktokToProfile(user[0]);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }

            while(check) {
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            pane.getChildren().clear();

            //if statement to determine if the program crashed and needs to be restarted
            if (strikes == 0) {
                startText.setText("Almost there, just hit Start when you're live");
                pane.add(startText, 0, 0);
                text.setText("Enter Message:");
                pane.add(text, 0, 1);
                pane.add(textfield, 0, 2);
                pane.add(start, 0, 3);
            } else {
                //closes Bluestacks if can't get to the proper page
                driver.closeApp();
                launched = false;
                startText.setText("It appears you capped my program, try it again will you");
                pane.add(startText, 0, 0);
                pane.add(startBack, 0, 1);
            }
        });

        //during the livesteram button actions
        //==============================================================================================================

        //press action for the home button
        //adds the title, a list of donators if there are any, and a button to the games
        home.setOnAction(value ->
        {
            home();
        });

        //button to start the livestream
        start.setOnAction(value ->
        {
            //display entered message
            message.setText(textfield.getText());

            //tap the profile pick to start the live
            try {
                //first result
                waitTap(152, 186, 3);

                //profile
                waitTap(270, 142, 5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //set up the GUI for the live
            home();

            //runnable to get the names of donators
            Runnable r = new Runnable() {
                public void run() {
                    //
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    //set up some variables
                    int index = 0;
                    int[] donorLocations = {128, 700, 128, 652, 128, 604, 270, 118};
                    MobileElement chat = (MobileElement) driver.findElementById("com.zhiliaoapp.musically:id/ady");
                    String newName = null;

                    //loop to get donators
                    while (true) {
                        //attempt to tap a donator
                        try {
                            waitTap(donorLocations[index++], donorLocations[index++], 0);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        //check if chat is still enabled
                        try {
                            chat.isEnabled();
                        } catch (Exception exception) {  //if this fails then the chat is disabled and we got a donator

                            //get the donators name
                            newName = driver.findElementById("com.zhiliaoapp.musically:id/c4h").getText();

                            if(!newName.equals(donorList[0])) {
                                //if they are new add them to the list
                                for (int c = donorList.length - 1; c > 0; c--)
                                    donorList[c] = donorList[c - 1];
                                donorList[0] = newName;

                                //update the donators list
                                text.setText(donorList[0]);
                                text2.setText(donorList[1]);
                                text3.setText(donorList[2]);
                                text4.setText(donorList[3]);
                                text5.setText(donorList[4]);
                            }

                            //tap out
                            try {
                                waitTap(270, 270, 0);
                            } catch (Exception ignore) { }

                        }

                        //once the last donators spot has been reached, reset ot start
                        if (index >= donorLocations.length) {
                            index = 0;
                        }
                    }
                }
            };

            new Thread(r).start();
        });


        //button to list of games
        game.setOnAction(value ->
        {
            pane.getChildren().clear();
            pane.add(guessing, 0, 10);
            pane.add(riddle, 7, 10);
        });

        //button for the guessing game
        //basicly you add what type of thing you want to guess
        guessing.setOnAction(value ->
        {
            pane.getChildren().clear();
            text.setText("Guess the ________ Game");
            pane.add(text, 0, 0);
            textfield.clear();
            textfield.setPrefColumnCount(20);
            pane.add(textfield, 0, 3);
            pane.add(next, 0, 4);
            gameNum[0] = 0;
        });

        //button action for the riddle game
        //you enter a riddle to solve
        riddle.setOnAction(value ->
        {
            pane.getChildren().clear();
            text.setText("Enter Riddle");
            pane.add(text, 0, 0);
            textfield.clear();
            textfield.setPrefColumnCount(40);
            pane.add(textfield, 0, 3);
            pane.add(next, 0, 4);
            gameNum[0] = 1;
        });

        //button for after you have picked a game and are ready to proceed
        //stores the game string and askes for the answer
        next.setOnAction(value ->
        {
            textFIn[0] = textfield.getText();
            textfield.clear();
            pane.getChildren().clear();
            text.setText("Enter Answer");
            pane.add(text, 0, 0);
            textfield.setPrefColumnCount(20);
            pane.add(textfield, 0, 3);
            pane.add(next2, 0, 4);
            numGuesses[0] = 0;
        });

        //button for after you enter the answer to a game
        //displays the game
        next2.setOnAction(value ->  // NOTE: this is not currently working
        {

            //store the answer in a varaible
            answer[0] = textfield.getText();
            textfield.clear();
            pane.getChildren().clear();

            //make a element to read the chat
            MobileElement chat = (MobileElement) driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/androidx.viewpager.widget.ViewPager/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.FrameLayout[17]/android.widget.RelativeLayout/androidx.recyclerview.widget.RecyclerView/android.widget.RelativeLayout[4]/android.widget.TextView");
            String input = " ";

            //display text based on the game being played
            switch (gameNum[0]) {
                case 0:
                    text.setText("Guess the " + textFIn[0] + " Game");
                    break;
                case 1:
                    text.setText(textFIn[0]);
                    break;
                default:
                    text.setText("We capped it");
            }

            //setting up the GUI
            pane.add(text, 0, 0);

            donors.setText("Incorrect Guesses:");
            pane.add(donors, 0, 2);

            text6.setText(" ");
            text5.setText(" ");
            text4.setText(" ");
            text3.setText(" ");
            text2.setText(" ");
            pane.add(text6, 0, 7);
            pane.add(text5, 0, 6);
            pane.add(text4, 0, 5);
            pane.add(text3, 0, 4);
            pane.add(text2, 0, 3);

            pane.add(home, 0, 10);

            //loop to get the chat and check for the right answer
            while (!StringUtils.containsIgnoreCase(guesses[0], answer[0])) {  //check for rith answer
                if (!chat.getText().equals(input)) {  //if the chat changed check if its right
                    input = chat.getText();  //get the chat

                    //shift the most recent chat down
                    for (int c = users.length - 1; c > 0; c--)
                        users[c] = users[c - 1];
                    for (int c = guesses.length - 1; c > 0; c--)
                        guesses[c] = guesses[c - 1];
                    numGuesses[0]++;

                    //seperate the guess form the user name
                    int colon = input.indexOf("：");
                    users[0] = input.substring(0, colon - 1);
                    guesses[0] = input.substring(colon + 1);

                    //based on the number of guesses display the right number of guesses
                    switch (numGuesses[0]) {
                        default:
                            text6.setText(users[4] + " guessed: " + guesses[4]);
                        case 4:
                            text5.setText(users[3] + " guessed: " + guesses[3]);
                        case 3:
                            text4.setText(users[2] + " guessed: " + guesses[2]);
                        case 2:
                            text3.setText(users[1] + " guessed: " + guesses[1]);
                        case 1:
                            text2.setText(users[0] + " guessed: " + guesses[0]);
                    }
                }
            }

            //once the loop ends the right winner will be displayed
            pane.getChildren().clear();
            text.setText("The winner is: " + users[0] + " for guessing " + guesses[0]);
            pane.add(text, 0, 0);
            pane.add(home, 0, 4);
        });

        //button to see stats for the donators
        stats.setOnAction(value ->
        {
            pane.getChildren().clear();
            text.setText("Stats:");
            pane.add(text, 1, 0);
            pane.add(history, 0, 2);
            //pane.add(scoreboard, 2, 2);
            pane.add(home, 1, 5);
        });

        history.setOnAction(value -> {
            ScrollerSetUp("History", donorList);
        });
        up.setOnAction(value -> {
            scroll(index, true, donorList, new String[]{" "});
        });
        down.setOnAction(value -> {
            scroll(index, false, donorList, new String[]{" "});
        });

        //close the program compleatly
        close.setOnAction(value -> {
            if (launched) {
                driver.closeApp();
                launched = false;
            }
            primaryStage.close();
        });
    }

    //method to open Tiktok and navigate almost to the users livestream
    public static void TiktokToProfile(String userName) throws IOException, InterruptedException {

        Runnable run = new Runnable() {
            public void run() {
                //strikes determine if the program crashed
                strikes = 0;

                //set the capabilities to connect to the right device
                DesiredCapabilities cap = new DesiredCapabilities();

                cap.setCapability("deviceName", "BlueStacks");
                cap.setCapability("udid", "emulator-5554");
                cap.setCapability("platformName", "Android");
                cap.setCapability("automationName", "UiAutomator2");
                cap.setCapability("appPackage", "com.zhiliaoapp.musically");
                cap.setCapability("appActivity", "com.ss.android.ugc.aweme.main.MainActivity");
                cap.setCapability("adbExecTimeout", 60000);
                cap.setCapability("uiautomator2ServerInstallTimeout", 100000);
                cap.setCapability("newCommandTimeout", 90000);
                cap.setCapability("noReset", true);
                cap.setCapability("unicodeKeyboard", "true");
                cap.setCapability("resetKeyboard", "true");

                //launch the app
                URL url = null;
                try {
                    url = new URL("http://127.0.0.1:4723/wd/hub");
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                driver = new AndroidDriver(url, cap);
                launched = true;
                action = new TouchAction(driver);

                //click on various elements to get to the live

                try {
                    //screen
                    waitTap(270, 440, 10);

                    //discover
                    waitTap(162,931, 3);

                    //search bar
                    TimeUnit.SECONDS.sleep(3);
                    driver.findElementById("com.zhiliaoapp.musically:id/ai5").sendKeys(userName);

                    //search em'
                    waitTap(70, 100, 3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                check = false;
            }
        };

        new Thread(run).start();
    }

    //checks if an array (String[]array) contains a value (String value) and outputs the index
    public static int[] contains(String[] array, String value) {
        int[] output = new int[2];
        for (int c = 0; c < array.length; c++) {
            if (array[c] != null)
                if (array[c].equals(value)) {
                    output[0] = 1;
                    output[1] = c;
                    return output;
                }
        }
        return output;
    }

    //finds the max value of an array
    public static int max(int[] inArray) {
        int max = inArray[0];
        for (int c = 1; c < inArray.length; c++)
            if (inArray[c] > max)
                max = inArray[c];

        return max;
    }

    //method to tap a spot on screen after a given amount of time
    public static void waitTap(int x,int y, int waitTime ) throws InterruptedException {
        TimeUnit.SECONDS.sleep(waitTime);
        action.press(PointOption.point(x, y)).release().perform();
    }

    public static void home() {
        pane.getChildren().clear();
        pane.add(title, 1, 0);
        pane.add(message, 1, 1);
        if (donorList[0] != null)
            donors.setText("Donators:");
        pane.add(donors, 1, 2);
        text.setText(donorList[0]);
        pane.add(text, 1, 3);
        text2.setText(donorList[1]);
        pane.add(text2, 1, 4);
        text3.setText(donorList[2]);
        pane.add(text3, 1, 5);
        pane.add(text4, 1, 6);
        pane.add(text5,1, 7);
        //pane.add(donor, 1, 8);
        pane.add(game, 0, 13);
        stats.setText("Stats");
        pane.add(stats, 1, 13);
        pane.add(close, 2, 13);
    }

    public static void ScrollerSetUp(String title, String[] scrollVec) {
        pane.getChildren().clear();

        text.setText(title);

        text2.setText(scrollVec[0]);
        text3.setText(scrollVec[1]);
        text4.setText(scrollVec[2]);
        text5.setText(scrollVec[3]);
        text6.setText(scrollVec[4]);

        pane.add(text,  1, 0);
        pane.add(text2, 1, 2);
        pane.add(text3, 1, 3);
        pane.add(text4, 1, 4);
        pane.add(text5, 1, 5);
        pane.add(text6, 1, 6);

        pane.add(home, 1, 10);
        pane.add(down, 2, 6);

        scrollTemp = scrollVec;
        index = 0;
    }

    public static void scroll(int index, boolean upDown, String[] vec1, String[] vec2) {
        if (upDown) { //up
            index--;
        } else {      //down
            index++;
        }

        String[] test = {" "};
        int l = vec1.length;

        if (vec2 == test) {
            vec2 = new String[l];
            for(int i = 0; i < l; i++)
                vec2[i] = " ";
        }

        text2.setText((index + 1) + " - " + vec1[index]     + "  " + vec2[index]);
        text3.setText((index + 2) + " - " + vec1[index + 1] + "  " + vec2[index + 1]);
        text4.setText((index + 3) + " - " + vec1[index + 2] + "  " + vec2[index + 2]);
        text5.setText((index + 4) + " - " + vec1[index + 3] + "  " + vec2[index + 3]);
        text6.setText((index + 5) + " - " + vec1[index + 4] + "  " + vec2[index + 4]);

        if (index != 0)
            pane.add(up, 2, 2);

        if (index != NumUsers - 5)
            pane.add(down, 2, 6);
    }
}