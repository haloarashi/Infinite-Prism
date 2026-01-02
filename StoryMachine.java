import java.util.*;

enum TunnelColor{
    RUBIES, SAPPHIRES, EMERALDS;
}

enum Location{
    WALL, FLOOR, MOUNTAIN, CEILING;
}

public class StoryMachine{
    
    static Scanner sc = new Scanner(System.in);
    static int attempts = 10;
    static ArrayList<String> obtainedGold = new ArrayList<>();
    static TunnelColor currentColor = TunnelColor.RUBIES;
    static Location searchPosition = Location.WALL;
    
    public static void main(String[] args) {
        // intro
        System.out.println("""
                           ----------------------------------------------------------------
                            Hello, human. 
                            This is the Infinite Prism. 
                            Fetch me three bottles of gold and leave here, 
                            Or you shall be trapped for eternity...
                           ----------------------------------------------------------------
                           """);
        
        // main game loop
        for(int i = attempts; i > 0; i--){ // i is the number of remaining attempts, including the current one
            
            // initial menu
            System.out.println();
            System.out.println(i + " attempt(s) left, including this one");
            System.out.println();
            System.out.println(" 1: Check inventory");
            System.out.println(" 2: Continue");
            System.out.println();
            
            // option to show inventory
            if(detectInput(2) == 1){
                showInventory();
                i++;
                continue;
            }

            // menu for choosing one of three tunnels
            System.out.println("""
                           Three tunnels appear. 
                            1. Enter red tunnel
                            2. Enter blue tunnel
                            3. Enter green tunnel
                           """);
            
            switch(detectInput(3)){
                case 1:
                    currentColor = TunnelColor.RUBIES;
                    System.out.println("Rubies are everywhere, glittering in the torchlight...");
                    break;
                
                case 2:
                    currentColor = TunnelColor.SAPPHIRES;
                    System.out.println("Sapphire, sapphire, sapphire.");
                    break;
                
                case 3:
                    currentColor = TunnelColor.EMERALDS;
                    System.out.println("Emeralds!");
                    break;
            }
            
            search();
            determineGold();

            if(success){ // success screen
                System.out.println("""
                           
                                   ----------------------------------------------------------------
                                    I see you have collected 3 bottles of gold. 
                                    Well done. 
                                    You may leave. 
                                   ----------------------------------------------------------------
        
                                   Success! You escaped the Infinite Prism. 
                                   """);
                
                sc.close();
                break;
            }
        }
        
        if(!success){ // failure screen (if the player still hasn't collected three bottles of gold after 10 attempts)
            System.out.println("""
                           
                               ----------------------------------------------------------------
                                I have waited far too long. 
                                You are incapable. 
                                Goodbye. 
                               ----------------------------------------------------------------
                                   
                               Failed. You are stuck forever. 
                               """);
        }
        
        sc.close();
    }

    public static void showInventory(){
        if(obtainedGold.size() > 0){
            System.out.println();
            for(int k = 1; k <= obtainedGold.size(); k++){
                System.out.println(" - " + obtainedGold.get(k - 1));
            }
            System.out.println();
        }
        else{
            System.out.println();
            System.out.println("Inventory is empty.");
            System.out.println("Go find gold.");
            System.out.println();
        }
    }

    // menu for choosing a location to search for the gold
    public static void search(){
        System.out.println("""
                            1. Dig the wall
                            2. Dig the floor
                            3. Keep walking
                           """);

        switch(detectInput(3)){
            case 1:
                searchPosition = Location.WALL;
                break;

            case 2:
                searchPosition = Location.FLOOR;
                break;

            case 3:
                System.out.println("More " + currentColor + ". You see a mountain of " + currentColor + ".");
                System.out.println("""
                                    1. Inspect the mountain
                                    2. Drill the ceiling
                                   """);

                switch(detectInput(2)){
                    case 1:
                        searchPosition = Location.MOUNTAIN;
                        break;
                    
                    case 2:
                        searchPosition = Location.CEILING;
                        break;
                }
            break;
        }
    }
    
    // variables used in determineGold()
    static boolean prevRedGold = false;
    static boolean prevBlueGold = false;
    static boolean prevGreenGold = false;

    // checks if the gold is in the location the player is at
    // if the player has already gotten the gold from the location, show a message indicating that the gold was already found
    public static void determineGold(){ 
        boolean redGold = currentColor == TunnelColor.RUBIES 
                                && searchPosition == Location.MOUNTAIN;
        boolean blueGold = currentColor == TunnelColor.SAPPHIRES 
                                && searchPosition == Location.WALL;
        boolean greenGold = currentColor == TunnelColor.EMERALDS 
                                && searchPosition == Location.CEILING;

        if(redGold){ // if the gold in the red tunnel is located here
            if(!prevRedGold){ // if the player has not obtained the gold from this location
                obtainedGold.add("A magnificent bottle of gold, tinted in shimmering red.");
                System.out.println("You find a bottle of gold!");
                System.out.println("The prism master will like it, right?");
                prevRedGold = true;
            }
            else{ // if the player has already found this gold
                System.out.println("There's no second bottle of gold here.");
                System.out.println("Look somewhere else.");
            }
        } 
        else if(blueGold){
            if(!prevBlueGold){
                obtainedGold.add("A fabulous bottle of gold, glowing in radiant blue.");
                System.out.println("You find a bottle of gold!");
                System.out.println("Don't lose it.");
                prevBlueGold = true;
            }
            else{
                System.out.println("You already found the gold here.");
                System.out.println("Try another tunnel..?");
            }
        }
        else if(greenGold){
            if(!prevGreenGold){
                obtainedGold.add("A spectacular bottle of gold, gleaming in the shiniest green.");
                System.out.println("You find a bottle of gold!");
                System.out.println("Bring it to the prism master.");
                prevGreenGold = true;
            }
            else{
                System.out.println("You already have this gold.");
                System.out.println("Try somewhere else.");
            }
        }
        else{
            System.out.println("More " + currentColor + ". " + currentColor + " everywhere...");
            System.out.println("No gold though.");
        }

        if(obtainedGold.size() >= 3){ // if the player has found three gold
            success = true;
            return;
        }
    }

    static boolean success = false;
    
    public static int detectInput(int num){ // repeatedly asking for player input until the player provides a valid input
        while(true){
            try{
                int userInput = sc.nextInt();
                sc.nextLine();
                
                if(0 < userInput && userInput <= num){
                    return userInput;
                }

                System.out.println("Input a number from 1 to " + num);
            } catch(Exception e){
                System.out.println("Input a number from 1 to " + num);
                sc.nextLine();
            }
        }
    }
}