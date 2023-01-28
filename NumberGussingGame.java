import java.util.Random;
import java.util.Scanner;

public class NumberGussingGame{
    public static void main(String[] args){
        Random rand = new Random();
        int randomNumber = rand.nextInt(100)+1;
       
        Scanner scanner = new Scanner(System.in);
         boolean win=false;
        int numberOfTries = 0;
        int i=0;
        while(win == false){
            System.out.println("ENTER A NUMBER BETWEEN 0 TO 100");
             int guess = scanner.nextInt() ;
               numberOfTries++;
         
            if(guess == randomNumber){
                win = true;
            }
             else if(i>4){
               System.out.println("You loose! the right answer was: "+ randomNumber);
               return;
             }
             else if(guess < randomNumber){
                i++;
                System.out.println("You are too low!");
                System.out.println("Guess again: Turns left: "+(5-i));
            }
             else if(guess > randomNumber){
                 i++;
                System.out.println("You are too high!");
                System.out.println("Guess again: Turns left: "+(5-i));
            }
          }
         System.out.println("Correct! You Win");
         System.out.println("The number was " + randomNumber);
    System.out.println("It took you " + numberOfTries + " guesses to get it right");
    System.out.println("Your score is "+((11-numberOfTries)*10)+" out of 100");
          }
         
      }
