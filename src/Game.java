import java.util.ArrayList;
import java.util.HashSet;
/**
 *
 *  This class is the main class of the "Throw Him Out" application.
 *  "Throw Him Out" is a simple text based adventure game.
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns and prints out the gameplay.
 * 
 * @author  Michael Kölling and David J. Barnes, modified by Valentin Magis, K20076746
 * @version 25.11.2020
 */

public class Game 
{
    private Parser parser;
    private Player player;
    private Entity calliou, secretary, bbcc;
    private HashSet<Entity> entityHashSet;
    private Item teddy, smartphone, juice, vodka, spaceDrugs, struwwelpeterBook, stone;
    private MusicPlayer musicPlayer;
    private Room palmRoom, pressBriefingRoom, westColonnade, roseGarden, cabinetRoom, presidentsSecretaryRoom, ovalOffice, cinema, kitchen, rooseveltRoom, playingRoom, bbccRoom;
    private final long NORMAL_TEXT_TIME = 3000,SHORT_TEXT_TIME = 2000;

    /**
     * Start the game.
     */
    public static void main (String[] args)
    {
        Game game = new Game();
        game.play();
    }

    /**
     * Create the game and initialise its internal map, items and entities
     */
    public Game() 
    {
        createRooms();
        createItems();
        createEntities();
        createPlayer();
        musicPlayer= new MusicPlayer();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        // create the rooms
        palmRoom = new Room("in the Palm Room");
        pressBriefingRoom = new Room("in the Press Briefing Room");
        westColonnade = new Room("in the West Collonade");
        roseGarden = new Room("in the Rose Garden");
        cabinetRoom = new Room("in the Cabinet Room");
        presidentsSecretaryRoom = new Room("in the Presidents Secretary Room");
        ovalOffice = new Room("in the Oval Office");
        cinema = new Room("in the Cinema");
        kitchen = new Room("in the Kitchen");
        rooseveltRoom = new Room("in the Roosevelt Room");
        playingRoom = new Room("in the Playing Room");
        bbccRoom = new Room("in the Bad Boy Chiller Crew-Room");

        // initialise room exits
        palmRoom.setExit("west1", pressBriefingRoom);
        palmRoom.setExit("west2", westColonnade);
        palmRoom.setExit("south", roseGarden);

        pressBriefingRoom.setExit("south1", westColonnade);
        pressBriefingRoom.setExit("south2", cabinetRoom);
        pressBriefingRoom.setExit("east",palmRoom);

        westColonnade.setExit("east", palmRoom);
        westColonnade.setExit("north", pressBriefingRoom);
        westColonnade.setExit("west1", cabinetRoom);
        westColonnade.setExit("west2",  presidentsSecretaryRoom);
        westColonnade.setExit("west3",  ovalOffice);

        roseGarden.setExit("north", palmRoom); 

        cabinetRoom.setExit("east", westColonnade);
        cabinetRoom.setExit("north", pressBriefingRoom);
        cabinetRoom.setExit("west1", rooseveltRoom);
        cabinetRoom.setExit("west2", kitchen);
        cabinetRoom.setExit("south", presidentsSecretaryRoom);

        presidentsSecretaryRoom.setExit("west", kitchen);
        presidentsSecretaryRoom.setExit("north", cabinetRoom);
        presidentsSecretaryRoom.setExit("south", ovalOffice);
        presidentsSecretaryRoom.setExit("east", westColonnade);

        ovalOffice.setExit("east", westColonnade);
        ovalOffice.setExit("west", cinema);
        ovalOffice.setExit("north", presidentsSecretaryRoom);

        cinema.setExit("north", kitchen);
        cinema.setExit("west", bbccRoom);
        cinema.setExit("east", ovalOffice);

        kitchen.setExit("north", rooseveltRoom);
        kitchen.setExit("south", cinema);
        kitchen.setExit("east2", cabinetRoom);
        kitchen.setExit("east1", presidentsSecretaryRoom);

        bbccRoom.setExit("north", playingRoom);
        bbccRoom.setExit("east", cinema);

        playingRoom.setExit("south", bbccRoom);
        playingRoom.setExit("east", rooseveltRoom);

        rooseveltRoom.setExit("east", cabinetRoom);
        rooseveltRoom.setExit("south", kitchen);
        rooseveltRoom.setExit("west", playingRoom);
    }

    /**
     * Create the Items and put them in the rooms
     */
    private void createItems()
    {
        teddy = new Item("teddy", 1, true,false);
        smartphone = new Item("smartphone", 1, true,false);
        vodka = new Item("vodka", 2, true,true);
        spaceDrugs = new Item("spacedrugs", 1, true,true);
        struwwelpeterBook = new Item("book of struwwelpeter", 1, false,false);
        stone = new Item("stone",54,true,false);
        juice = new Item("juice", 1, true,true);


        playingRoom.addItem(teddy);
        pressBriefingRoom.addItem(smartphone);
        cabinetRoom.addItem(vodka);
        rooseveltRoom.addItem(spaceDrugs);
        ovalOffice.addItem(struwwelpeterBook);
        kitchen.addItem(juice);
        presidentsSecretaryRoom.addItem(stone);
    }

    /**
     * Create the entities and put them in a room
     */
    private void createEntities()
    {
        calliou = new Entity("Calliou", ovalOffice);
        secretary = new Entity("Hot Secretary", palmRoom);
        bbcc = new Entity("Ayo, its the Bad Boy Chiller Crew", bbccRoom);

        entityHashSet = new HashSet<>();
        entityHashSet.add(calliou);
        entityHashSet.add(secretary);
        entityHashSet.add(bbcc);
    }

    /**
     * Creates a new player and sets the players initial status.
     */
    private void createPlayer()
    {
        player = new Player("Joe", roseGarden, 30,false, false);
    }


    /**
     *  Main play routine. Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
            calliou.moveEntityRandom();

            if (checkIfWon()){
                printWinningScreen();
            }
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println("\"Congratulations to winning the election, Mr. President!\"");
        System.out.println("It is the year 3456 and the elections for the new president on your home, the planet Cromulon have taken place.");
        System.out.println("Cromulon used to be one of the most important planets in the galaxy but their last president, Calliou Anderson,");
        System.out.println("wasn’t really capable of fulfilling his task and kind of messed things up.");
        System.out.println("Maybe the media was going to hard on him but it’s is a fact that he was not capable of being the president.");
        System.out.println();
        System.out.println("It was a close call, but the polls now show that the Cromulians have definitely voted in favour of You.");
        System.out.println("But the former President Calliou Anderson has decided that he will never leave the presidential residence. ");
        System.out.println();
        System.out.println("Many people have tried to kick him out, but he starts screaming, pulls hair and even bites when he is asked to leave, so he has stayed in the residence until now. ");
        System.out.println("But since he really has to move out soon, YOU are going to make sure you can start your presidency and kick him out yourself!");
        System.out.println();
        System.out.println("Type help to get more information.");
        System.out.println("Type map to see the map.");

        System.out.println(player.getCurrentRoom().getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        switch (commandWord) {
            case UNKNOWN:
                System.out.println("I don't know what you mean...");
                break;
            case HELP:
                printHelp(command);
                break;
            case GO:
                goRoom(command);
                break;
            case QUIT:
                wantToQuit = quit(command);
                break;
            case BACK:
                goBack();
                break;
            case TAKE:
                take(command);
                break;
            case PUT:
                put(command);
                break;
            case SEARCH:
                searchRoom();
                break;
            case BACKPACK:
                showBackpack();
                break;
            case CONSUME:
                consume(command);
                break;
            case GIVE:
                giveItem(command);
                break;
            case MAP:
                showMap();
                break;
        }

        return wantToQuit;
    }


    // implementations of user commands:

    /**
     * Print out some help information and the command words.
     * If the command has been specified, print out more information
     */
    private void printHelp(Command command)
    {
        if (command.getSecondWord() == null) {
            System.out.println("You are the new president and have to kick out the old president that doesn't want to leave.");
            System.out.println();
            System.out.println("Your command words are:");
            parser.showCommands();
            System.out.println("Type \"help *command*\" to get further information about the command.");
        }
        else {
            switch (command.getSecondWord()) {
                case "go":
                    System.out.println("Go *direction*: Use to go into the room in the specified direction.");
                    break;
                case "quit":
                    System.out.println("Quit: Quit the game.");
                    break;
                case "back":
                    System.out.println("Back: Go back to the last room.");
                    break;
                case "take":
                    System.out.println("Take *item*: Takes the specified item into your backpack. Watch out that is doesn't get too heavy");
                    break;
                case "put":
                    System.out.println("Put *item*: Puts the item from your backpack into the current room.");
                    break;
                case "backpack":
                    System.out.println("Backpack: Shows the items you have in the backpack.");
                    break;
                case "search":
                    System.out.println("Search: Searches the room for items.");
                    break;
                case "consume":
                    System.out.println("Consume *item*: Consume the item. You can only consume certain things and they have different effects on you.");
                    break;
                case "give":
                    System.out.println("Give *item* *person*: Give an item to a person. You need to have the item in your backpack and the person has to be in the same room as you.");
                    break;
            }
        }

    }

    /**
     * Try to go in to one direction. If there is an exit, enter the new
     * room, otherwise print an error message. Update the previous room in case the 
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = player.getCurrentRoom().getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }

        else {
            Room newRoom = player.changeRoom(nextRoom);
            System.out.println(newRoom.getLongDescription());
            checkForSpecialCases();
        }
    }

    /**
     * Check special cases of room changes that need to be taken into consideration.
     * Regards
     *      1. Meeting the President (Steals something from the backpack and puts it back into its location)
     *      2. Going into the Cinema (Random transportation). The visited rooms are reset since the player can't teleport back into the cinema.
     *      3. Leaving the room the BBCC is in (Stops Music)
     *
     */
    private void checkForSpecialCases()
    {
        processEntityAction(checkForEntities());

        if(player.getCurrentRoom() == cinema){
            player.resetVisitedRooms();
            randomlyChangeRoom();
        }

        if (player.getCurrentRoom() != bbcc.getRoom() && musicPlayer.isPlaying()) {
            musicPlayer.stopMusic();
        }
    }



    // Methods relating to the goRoom method (= change of room)
    /**
     * Check for entities in the same room as the player.
     * @return Entities currently in the same room.
     */
    private HashSet<Entity> checkForEntities()
    {
        HashSet<Entity> entitiesInRoom = new HashSet<>();
        for (Entity currentEntity : entityHashSet) {
            if (currentEntity.getRoom() == player.getCurrentRoom()) {
                entitiesInRoom.add(currentEntity);
            }
        }
        return entitiesInRoom;
    }

    /**
     * Processes the different actions that need to be taken for different entities in the same room as the player.
     * @param currentEntities The entities currently in the same room.
     */
    private void processEntityAction(HashSet<Entity> currentEntities)
    {
        if (!currentEntities.isEmpty()) {
            for (Entity currentEntity : currentEntities){

                if (secretary.getName().equals(currentEntity.getName())) {
                    printSecretary();
                }

                else if (bbcc.getName().equals(currentEntity.getName())){
                    printBBCC();
                    playBBCC();
                }

                else if (calliou.getName().equals(currentEntity.getName())){
                    metWithPresident();
                }
            }
        }
    }

    /**
     * Called when the president is in the same room as the player. Removes one random item from the players backpack
     * and puts it back to its original place.
     */
    private void metWithPresident()
    {
        if (player.getCurrentRoom() == calliou.getRoom()) {
            Item removedItem = player.removeRandomItemFromBackpack();
            if (removedItem != null) {
                System.out.println("Oops, Calliou was in the same room as you. He is so angry that he stole " +
                                    removedItem.getName() +" out of your backpack :(");
                placeItemBackInRoom(removedItem);
            }
            else{
                System.out.println("Calliou: ");
                System.out.println("\033[3m     Ahhh there you are, fuck you I'm the president. Get out of here!\033[0m");
            }
        }
    }

    /**
     * Places the item back into its old room.
     * @param removedItem The item removed to be placed back.
     */
    public void placeItemBackInRoom(Item removedItem) {
        String itemName = removedItem.getName();
        switch (itemName) {
            case "smartphone":
                pressBriefingRoom.addItem(removedItem);
                break;
            case "teddy":
                playingRoom.addItem(removedItem);
                break;
            case "juice":
                kitchen.addItem(removedItem);
                break;
            case "vodka":
                cabinetRoom.addItem(removedItem);
                break;
            case "spacedrugs":
                rooseveltRoom.addItem(removedItem);
                break;
            case "struwwelpeter book":
                ovalOffice.addItem(removedItem);
                break;
            default:
                roseGarden.addItem(removedItem);
                break;
        }

    }

    /**
     * Printed when player is in the same room as the bad boy chiller crew
     */
    private void printBBCC()
    {
        System.out.println("Ayoooo its the Bad Boy Chiller Crew");
        if(bbcc.getRoom() != secretary.getRoom() && player.getCurrentRoom() == bbcc.getRoom())
        {
            System.out.println("Bad Boy Chiller Crew: ");
            if (player.backpackContains(vodka))
            {
                System.out.println("\033[3m     Ayooo I see you got the vodka. If you give us that, well go chill with that secretary.\033[0m");
                System.out.println();
                System.out.println("Type give vodka bbcc to give them the vodka.");
            }
            else
            {
                System.out.println("\033[3m     You want WHAT? We have to go hang out with that boring ass secretary?!\033[0m");
                timeBreakDuringText(NORMAL_TEXT_TIME);
                System.out.println("\033[3m     Okay I guess we'll go if you can find us some vodka. I think these \"politicians\" used to keep some in the cabinet.\033[0m");
            }
        }
    }

    /**
     * Plays a song when the player is in the same room as the BBCC.
     */
    private void playBBCC()
    {
        String filepath = "BBCC 450.wav";
        musicPlayer = new MusicPlayer();
        musicPlayer.playMusic(filepath);
    }

    /**
     * Printed when player is in the same room as the secretary. Text is formatted and has breaks included to make it seem more realistic.
     */
    private void printSecretary() {
        if(!(bbcc.getRoom() == secretary.getRoom() && player.getCurrentRoom() == secretary.getRoom()))
        {
            System.out.println("\nSecretary: ");
            System.out.println("\033[3m     Hello Mr. President. Congratulations on winning the election!\033[0m");
            timeBreakDuringText(NORMAL_TEXT_TIME);
            System.out.println("\033[3m     The old president is a really childish person.\033[0m");
            timeBreakDuringText(SHORT_TEXT_TIME);
            System.out.println("\033[3m     He told me he would ONLY leave the house, if all of his favourite things are in the Rose Garden.\033[0m");
            timeBreakDuringText(NORMAL_TEXT_TIME);
            System.out.println("\033[3m     If you want to get rid of the president, I think you will have to get all of his belongings out of the house.\033[0m");
            timeBreakDuringText(NORMAL_TEXT_TIME);
            System.out.println("\033[3m     But...\033[0m");
            timeBreakDuringText(SHORT_TEXT_TIME);
            System.out.println("\033[3m     He wont leave without me as well.\033[0m");
            timeBreakDuringText(SHORT_TEXT_TIME);
            System.out.println("\033[3m     And I want something too... The famous Bad Boy Chiller Crew is residing in the Black House\033[0m");
            timeBreakDuringText(NORMAL_TEXT_TIME);
            System.out.println("\033[3m     and you will have to bring me one of those good looking lads, otherwise I wont leave!\033[0m");
        }
        else if(bbcc.getRoom() == secretary.getRoom() && player.getCurrentRoom() == secretary.getRoom() && secretary.getRoom() == palmRoom)
        {
            System.out.println("\nSecretary: ");
            System.out.println("\033[3m     Ahaha I see you have brought what I seek.\033[0m");
            timeBreakDuringText(SHORT_TEXT_TIME);
            System.out.println("\033[3m     Thank you so much, you will be a great president!\033[0m");
            timeBreakDuringText(SHORT_TEXT_TIME);
            System.out.println("     Smiling at the Bad Boy Chiller Crew: \033[3m\"Now come with me, you handsome musicians, I need to show you something in the shed.\"\033[0m");
            secretary.setRoom(roseGarden);
            bbcc.setRoom(roseGarden);
        }
    }

    /**
     * Randomly changes your location, creates an arrayList of the current rooms and randomly chooses one
     */
    public void randomlyChangeRoom()
    {
        ArrayList<Room> roomArray = new ArrayList<>();
        roomArray.add(palmRoom);
        roomArray.add(pressBriefingRoom);
        roomArray.add(westColonnade);
        roomArray.add(roseGarden);
        roomArray.add(cabinetRoom);
        roomArray.add(presidentsSecretaryRoom);
        roomArray.add(ovalOffice);
        roomArray.add(kitchen);
        roomArray.add(rooseveltRoom);
        roomArray.add(playingRoom);
        roomArray.add(bbccRoom);

        Room newRoom = player.randomlyChangePlayerRoom(roomArray);

        System.out.println("You went into the cinema and had to watch the FAKE NEWS. They were so hopeless that you started drinking heavily.");
        System.out.println("You wake up in " + newRoom.getShortDescription() + " the next day.");
        System.out.println(newRoom.getExitString());
    }




    /**
     * "Back" was entered. Go to the previous room. Method checks, if no previous room exists, stay in the room and
     * tell the player. Implemented using a Stack.
     */
    private void goBack() {
        Room newRoom = player.goBack();
        if(newRoom != null) {
            System.out.println(newRoom.getLongDescription());
        }
        else {
            System.out.println("You cant go back");
        }
    }


    /**
     * "Take" was entered. Picks up the item in the current room and puts it in the backpack. Checks if it is carryable and exists in the room.
     */
    public void take(Command command)
    {
        if(!command.hasSecondWord()) {
            System.out.println("Take what?");
            return;
        }
        String itemName = command.getSecondWord();
        Item requestedItem = player.getCurrentRoom().removeItem(itemName);
        if (requestedItem != null){
            if (requestedItem.isCarryable()) {
                if (player.canCarryAdditionalItem(requestedItem)) {
                    player.addItemToBackpack(player.getCurrentRoom().removeItem(requestedItem));
                    System.out.println(itemName + " is now in your backpack.");
                }
                else {
                    System.out.println("Your backpack is too heavy. If you take some of substances enhancing physical ability, you can carry more. Go and check the Roosevelt Room.");
                }
            }
            else {
                System.out.println("You cant pick up this item. Try something else.");
            }
        }
        else {
            System.out.println(itemName + " isn't in this room.");
        }
    }

    /**
     * "put" was entered. Put the specified item from the players backpack into the current room.
     */
    public void put(Command command)
    {
        if(!command.hasSecondWord()) {
            System.out.println("Put what?");
            return;
        }

        String itemName = command.getSecondWord();
        Item requestedItem = player.getItemFromBackpack(itemName);
        if (requestedItem != null)
        {
            player.getCurrentRoom().addItem(player.removeItemFromBackpack(requestedItem));
            System.out.println("The " + requestedItem.getName() + " is now in the " + player.getCurrentRoom().getShortDescription());
        }
        else {
            System.out.println(itemName + " isn't in your backpack");
        }
    }

    /**
     * "Backpack" was entered. Prints the contents of the players backpack
     */
    public void showBackpack()
    {
        if (!player.getBackpack().isEmpty()){
            HashSet<Item> tempListOfItems = player.getItemsInBackpack();
            System.out.print("The items in your backpack are: ");

            for (Item currentItem : tempListOfItems)
            {
                System.out.print(currentItem.getName() + " ");
            }
        }
        else{
            System.out.print("You have no items in you backpack.");
        }
        System.out.println();
    }

    /**
     * "Search" was entered. Prints the items currently in the room.
     */
    public void searchRoom()
    {
         if (!player.getCurrentRoom().getItemsInRoom().isEmpty()){
            HashSet<Item> tempListOfItems = player.getCurrentRoom().getItemsInRoom();
             System.out.println("The items in this room are: ");
            for (Item currentItem : tempListOfItems)
            {
                 System.out.print(currentItem.getName() + " ");
            }
        }
        else{
            System.out.println("There are not items in this room.");
        }
    }


    /** "give" was entered. Gives an item to a person.
     * @param command has to consist of three words: command, item, entity.
     *                No need to check for null since this is already done reading the command
     */
    private void giveItem(Command command) {

        String itemToGiveString = command.getSecondWord();
        String entityString = command.getThirdWord();

        if (itemToGiveString == null || entityString == null) {
            System.out.println("To give an item type : consume *item* *person*");
        }
        else {
            if (player.backpackContains(itemToGiveString)) {
                if (player.getCurrentRoom() == bbcc.getRoom() && entityString.equals("bbcc")) {
                    giveItemToBBCC(itemToGiveString);
                }
            } else {
                System.out.println("You don't have " + itemToGiveString + " in your backpack.");
            }
        }
    }

    // Methods relating to the giveItem method

    /**
     * Called when an item is given to the BBCC
     * @param itemName the Item to be given to the BBCC
     */
    private void giveItemToBBCC(String itemName)
    {
        if(itemName.equals("vodka") && player.backpackContains(vodka) && player.getCurrentRoom() == bbcc.getRoom())
        {
            bbcc.setRoom(secretary.getRoom());
            player.removeItemFromBackpack(vodka);
            System.out.println("Thank you so much man, we'll go to the lady in the Palm Room now.");
        }
    }


    /**
     * "consume" was entered. The player eats/ drinks something and gets different effects.
     * Checks if the item is in the backpack and if it can be consumed.
     * @param command The command specifying the item to be consumed.
     */
    private void consume(Command command) {
        String itemToConsumeString = command.getSecondWord();

        if (itemToConsumeString == null) {
            System.out.println("Consume what?");
        }
        else {
            if (player.backpackContains(itemToConsumeString)) {
                if (player.getItemFromBackpack(itemToConsumeString).isConsumable()) {
                    switch (itemToConsumeString) {
                        case "spacedrugs":
                            consumeSpaceDrugs();
                            break;
                        case "vodka":
                            consumeVodka();
                            break;
                        case "juice":
                            consumeJuice();
                            break;
                    }
                } else {
                    System.out.println("You cant consume " + itemToConsumeString);
                }
            } else {
                System.out.println("You don't have " + itemToConsumeString + " in your backpack.");
            }
        }

    }

    //methods relating to the consume method

    /**
     * Called when juice is trying to be consumed
     */
    private void consumeJuice()
    {
        if (player.backpackContains(juice)) {
            player.removeItemFromBackpack(juice);
            System.out.println("Mhhh tasty, I guess you aren't drunk anymore.");
            player.setDrunk(false);
        }
        else{
            System.out.println("You don't have this item in your backpack");
        }

    }

    /**
     * Called when vodka is trying to be consumed
     */
    private void consumeVodka()
    {
        System.out.println("Oh no, you know you where supposed to give this to the Bad Boy Chiller Crew?");
        timeBreakDuringText(NORMAL_TEXT_TIME);
        System.out.println("This is going to be way harder now, do you even know what exit leads to which room?");
        System.out.println();
        timeBreakDuringText(NORMAL_TEXT_TIME);
        System.out.println("Drink some juice to get sober again.");
        player.setDrunk(true);
    }

    /**
     * Called when the player tries to consume space drugs.
     */
    private void consumeSpaceDrugs(){
        if(player.backpackContains(spaceDrugs) && !player.isHighOnSpaceDrugs()) {
            player.raiseMaxWeight();
            player.setHighOnSpaceDrugs(true);
            player.removeItemFromBackpack(spaceDrugs);
            System.out.println("YEEEHAW you can now carry " + player.getMaxWeight() + "kg.");
        }
        else {
            if (player.isHighOnSpaceDrugs()){
                System.out.println("You are already high on space drugs and can carry " + player.getMaxWeight() + "kg.");
            }
            else if (!player.backpackContains(spaceDrugs)){
                System.out.println("Why would you do that? P.S. check the Roosevelt Room for substances enhancing physical ability. You might be able to carry more then.");
            }
        }
    }


    private void showMap()
    {
        String filename = "Map.png";
        new ImageDisplayer(filename);
    }

    /**
     * Checks if the player has won the game.
     * @return true if the player has won the game.
     */
    private boolean checkIfWon(){
        return roseGarden.getItemsInRoom().contains(smartphone) && roseGarden.getItemsInRoom().contains(teddy)
                && roseGarden.getItemsInRoom().contains(stone) && secretary.getRoom() == roseGarden;
    }

    /**
     * Printed when the player has won the game.
     */
    private void printWinningScreen()
    {
        System.out.println("Amazing, you have successfully thrown out the old president, now go on and lead your country!");
        System.exit(0);
    }

    /**
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command)
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }


    /**
     * Let the thread sleep for some time to make text more realistic
     */
    public void timeBreakDuringText(long time)
    {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}