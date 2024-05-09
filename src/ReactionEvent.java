import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class ReactionEvent {

    int seconds = 12;
    int startCounterAt = 5;
    long startedTime = 0;
    long pressedTime = 0;
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public void processBefore() throws IOException {
        System.out.println("""
                
                ###### Reaction Timer Application ######

                When the display shows "### GO ###", press ENTER to count the reaction time
                
                The countdown will start when there's 5 seconds remaining
                
                There's a delay of less then a second before the "### GO ###" to avoid patterns...

                Good luck
                
                """);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            int countdown = seconds;
            @Override
            public void run() {
                if(countdown<=startCounterAt && countdown>0){
                    System.out.println(countdown);
                }
                if(countdown <= 0){
                    try {
                        long delay = (long) (Math.random() * 1000);
                        TimeUnit.MILLISECONDS.sleep(delay);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("### GO ###");
                    startedTime = System.currentTimeMillis();
                    timer.cancel();
                }
                countdown--;
            }
        }, 0, 1000);
        processAfter();
    }

    public void run() throws IOException {
        processBefore();
    }

    private void processAfter() throws IOException {
        boolean runs = true;
        while(runs){
            Object input =br.readLine();
            if (!(input==null)){
                System.out.println(input);
                runs=false;
            }
        }
        pressedTime = System.currentTimeMillis();
        long reactionTime = (pressedTime - startedTime);
        System.out.println("Your reaction time was: " + reactionTime + " milliseconds!");
        System.exit(0);
    }
}
