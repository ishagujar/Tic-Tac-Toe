package com.example.tic_tac_toe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    //0=cross,1=circle
    int activePlayer = 0;
    boolean gameIsActive = true;//T
    // he variable which keeps track of game is active or not after the winner is declared
    //2 means unplayed
    int[] gameState = { 2 , 2 , 2 , 2 , 2 ,2 , 2 , 2 , 2 };
    //Consider [][] as set of arrays inside an array which consist of three-horizontal winning positions,three-vertical winning positions & two-diagonal winning positions
    int[][] winningPositions={{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};
    public void dropIn(View view)
    {
        ImageView counter =(ImageView) view;
        //System.out.println(counter.getTag().toString());//get the tags
        int tappedCounter=Integer.parseInt(counter.getTag().toString());//Convert it into integer
        //As we had the drawback of once user tapped the box,it would change from circle to cross & vice-versa,thus we have overcome it through the following:
        if(gameState[tappedCounter]==2 && gameIsActive) //If the user is unplayed
        {
            gameState[tappedCounter]=activePlayer;
            counter.setTranslationY(-1000f);//Take it to the top of the screen(1000 pixels to the top of the screen)
            if (activePlayer == 0) {
                counter.setImageResource(R.drawable.cross);//Set the image in image view
                activePlayer = 1;//As the user clicks,if it is cross,it sets the cross image and next chance goes to circle
            }
            else {
                counter.setImageResource(R.drawable.circle);//Set the image in image view
                activePlayer = 0;//As the user clicks,if it is circle,it sets the circle image and next chance goes to cross
            }
            counter.animate().translationYBy(1000f).setDuration(360);//Animate back down
            //We are using for command to traverse through an array
            for(int[] winningPosition :winningPositions) { //winningPosition is within the winningPositions
                if (gameState[winningPosition[0]] == gameState[winningPosition[1]]
                        && gameState[winningPosition[1]] == gameState[winningPosition[2]] && gameState[winningPosition[0]] != 2) {
                    //System.out.println(gameState[winningPosition[0]]);//You may use 1,2 instead of 0 in game
                    gameIsActive=false;//When somebody wins
                    String winner="Circle";
                    if(gameState[winningPosition[0]] == 0)
                    {
                        winner="Cross";
                    }
                    //Someone has won !
                    TextView winnerMessage= findViewById(R.id.winnerMessage);//Create a textView to display the winner's message
                    winnerMessage.setText(String.format("%s has won !! ", winner));
                    LinearLayout layout= findViewById(R.id.playAgainLayout);//Create a Linear Layout for textView and Button
                    layout.setVisibility(View.VISIBLE);
                }
                else
                {
                    //Draw i.e no one has played
                    //Loop through gameState array & check to see if everything is not 2 & if that's the case,we finish the game
                    boolean gameIsOver=true;
                    for(int counterState :gameState)//counterState position is within the gameState
                    {
                        if (counterState == 2)//If one of them is equal to 2,the game is not finished yet.
                        {
                            gameIsOver = false;
                        }
                    }
                    if(gameIsOver)
                    {
                        TextView winnerMessage= findViewById(R.id.winnerMessage);//Create a textView to display the game's message that it's a draw
                        winnerMessage.setText(R.string.it_s_a_draw);//Displays "It's a draw !!"  on the textView
                        LinearLayout layout= findViewById(R.id.playAgainLayout);//Create a Linear Layout for textView and Button
                        layout.setVisibility(View.VISIBLE);
                    }

                }
            }
        }
    }
    public void playAgain(View view)
    {
        gameIsActive=true;//When the user taps to play again
        //Message displayed on the TextView should disappeared
        LinearLayout layout= findViewById(R.id.playAgainLayout);//Create a Linear Layout for textView and Button
        layout.setVisibility(View.INVISIBLE);
        //Set the gameState and the Player back to the default
        {
            activePlayer=0;
            //2 means unplayed
            //gameState={ 2 , 2 , 2 , 2 , 2 ,2 , 2 , 2 , 2 };
            //Update the each item in an array gameState back to 2 i.e unplayed,we have use "for loop"
            for(int i=0;i<gameState.length;i++)
            {
                gameState[i] = 2;
            }
            //Sets all the images within the GridLayout
            GridLayout gridLayout=(GridLayout) findViewById(R.id.girdLayout);
            //Loop through the items in there,which are imageViews
            for(int i=0;i<gridLayout.getChildCount();i++) //gridLayout.getChildCount() tells us that how many imageViews there are,inside the girdLayout i.e 9 image views
            {
               //Get each of those imageViews
                ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);//getChildAt(i) just gets the ith child which is the ith imageView in this case
                //setImageResource(0) sets an empty image
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
