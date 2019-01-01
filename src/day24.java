import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class day24 {
    static private List<Group> immuneGroups = new ArrayList<>();
    static private List<Group> infectionGroups = new ArrayList<>();
    static private List<Group> allGroups = new ArrayList<>();

    public static void main(String[] args) {
        File file = new File("/Users/corey.lamb/Documents/workspace-sts-3.9.2.RELEASE/Advent2018/src/day24input");
        try {
            parseInput(file);
            sortGroupsByEffectivePower();
            sortGroupsByInitiative();

            while(immuneGroups.size() > 0 && infectionGroups.size() > 0){
                targetingPhase();

                attackPhase();
            }
            int result = 0;

            for(Group g : allGroups){
                if(g.numOfUnits < 1){
                    continue;
                }
                result += g.numOfUnits;
            }
            System.out.println(result);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void attackPhase() {

        for(Group g : allGroups){
            if(g.numOfUnits < 1){
                continue;
            }
            if(g.currentTarget != null) {
                g.currentTarget.takeDamage(g.damageToTarget);
                g.currentTarget.targeted = false;
                g.currentTarget = null;
            }
        }

        sortGroupsByEffectivePower();
    }

    private static void targetingPhase() {
        System.out.println("Immune System:");
        for(Group attacking : immuneGroups){
            int maxDamage = Integer.MIN_VALUE;
            int maxPower = Integer.MIN_VALUE;
            int maxInitiative = Integer.MIN_VALUE;
            for(Group defending : infectionGroups){
                if(defending.targeted){
                    continue;
                }
                int currentDamage = attacking.calculateDamage(defending);
                int currentPower = defending.getEffectivePower();
                int currentInitiative = defending.initiative;
                System.out.println("Group " + attacking.groupNumber +
                        " would deal Infection group " + defending.groupNumber + " " + currentDamage + " damage");
                if((currentDamage > maxDamage)
                || ((currentDamage == maxDamage) && (currentPower > maxPower))
                || ((currentDamage == maxDamage) && (currentPower == maxPower) && (currentInitiative > maxInitiative))){
                    maxDamage = currentDamage;
                    maxPower = currentPower;
                    maxInitiative = currentInitiative;
                    setTarget(attacking, defending, currentDamage);
                }
            }
        }
        System.out.println("Infection System:");
        for(Group attacking : infectionGroups){
            int maxDamage = Integer.MIN_VALUE;
            int maxPower = Integer.MIN_VALUE;
            int maxInitiative = Integer.MIN_VALUE;
            for(Group defending : immuneGroups){
                if(defending.targeted){
                    continue;
                }
                int currentDamage = attacking.calculateDamage(defending);
                int currentPower = defending.getEffectivePower();
                int currentInitiative = defending.initiative;
                System.out.println("Group " + attacking.groupNumber +
                        " would deal Immune group " + defending.groupNumber + " " + currentDamage + " damage");
                if((currentDamage > maxDamage)
                || ((currentDamage == maxDamage) && (currentPower > maxPower))
                || ((currentDamage == maxDamage) && (currentPower == maxPower) && (currentInitiative > maxInitiative))){
                    maxDamage = currentDamage;
                    maxPower = currentPower;
                    maxInitiative = currentInitiative;
                    setTarget(attacking, defending, currentDamage);
                }
            }
        }
    }

    private static void setTarget(Group attacking, Group defending, int currentDamage) {
        if(attacking.currentTarget != null){
            attacking.currentTarget.targeted = false;
        }
        attacking.currentTarget = defending;
        attacking.damageToTarget = currentDamage;
        defending.targeted = true;
    }

    private static void sortGroupsByEffectivePower() {
        List<Group> sorted = new ArrayList<>();

        while(sorted.size() < allGroups.size()){
            int maxEffectivePower = Integer.MIN_VALUE;
            int maxInitiative = Integer.MIN_VALUE;
            Group toAdd = new Group();
            for(Group g : allGroups){
                if(sorted.contains(g)){
                    continue;
                }
                if(g.getEffectivePower() > maxEffectivePower){
                    maxEffectivePower = g.getEffectivePower();
                    maxInitiative = g.initiative;
                    toAdd = g;
                }
                else if (g.getEffectivePower() == maxEffectivePower && g.initiative > maxInitiative){
                    maxEffectivePower = g.getEffectivePower();
                    maxInitiative = g.initiative;
                    toAdd = g;
                }
            }
            sorted.add(toAdd);
        }
        allGroups = sorted;
    }

    private static void sortGroupsByInitiative(){
        List<Group> sortedImmune = new ArrayList<>();
        while(sortedImmune.size() < immuneGroups.size()){
            int maxInitiative = Integer.MIN_VALUE;
            Group toAdd = new Group();
            for(Group g : immuneGroups){
                if(sortedImmune.contains(g)){
                    continue;
                }
                if (g.initiative > maxInitiative){
                    maxInitiative = g.initiative;
                    toAdd = g;
                }
            }
            sortedImmune.add(toAdd);
        }
        immuneGroups = sortedImmune;

        List<Group> sortedInfection = new ArrayList<>();
        while(sortedInfection.size() < infectionGroups.size()){
            int maxInitiative = Integer.MIN_VALUE;
            Group toAdd = new Group();
            for(Group g : infectionGroups){
                if(sortedInfection.contains(g)){
                    continue;
                }
                if (g.initiative > maxInitiative){
                    maxInitiative = g.initiative;
                    toAdd = g;
                }
            }
            sortedInfection.add(toAdd);
        }
        infectionGroups = sortedInfection;
    }

    private static void parseInput(File file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String input;

        while((input = br.readLine()) != null){
            if(input.contains("Immune")) {
                int number = 1;
                while (!(input = br.readLine()).isEmpty()) {
                    int numOfUnitsToCreate = Integer.valueOf(input.substring(0, input.indexOf(' ')));
                    int initiative = Integer.valueOf(input.substring(input.indexOf("initiative ") + 11));
                    List<String> weaknesses = Arrays.asList(input.substring(input.indexOf("weak to ") + 8, input.indexOf(')')).split(", "));
                    String[] attackDetails = input.substring(input.indexOf("that does ") + 10, input.indexOf("damage")).split(" ");
                    int damage = Integer.valueOf(attackDetails[0]);
                    String damageType = attackDetails[1];
                    int hitPoints = Integer.valueOf(input.substring(input.indexOf("each with ") + 10, input.indexOf(" hit")));
                    List<String> immunities = new ArrayList<>();
                    if (input.contains("immune")) {
                        immunities = Arrays.asList(input.substring(input.indexOf("immune to ") + 10, input.indexOf(';')).split(", "));
                    }
                    Group groupToAdd = new Group(number, initiative, damage, numOfUnitsToCreate, hitPoints, weaknesses, immunities, damageType);
                    allGroups.add(groupToAdd);
                    immuneGroups.add(groupToAdd);
                    number++;
                }
            }
            input = br.readLine();
            if(input.contains("Infection")){
                int number = 1;
                while((input = br.readLine()) != null) {
                    int numOfUnitsToCreate = Integer.valueOf(input.substring(0, input.indexOf(' ')));
                    int initiative = Integer.valueOf(input.substring(input.indexOf("initiative ") + 11));
                    List<String> weaknesses = Arrays.asList(input.substring(input.indexOf("weak to ") + 8, input.indexOf(')')).split(", "));
                    String[] attackDetails = input.substring(input.indexOf("that does ") + 10, input.indexOf("damage")).split(" ");
                    int damage = Integer.valueOf(attackDetails[0]);
                    String damageType = attackDetails[1];
                    int hitPoints = Integer.valueOf(input.substring(input.indexOf("each with ") + 10, input.indexOf(" hit")));
                    List<String> immunities = new ArrayList<>();
                    if(input.contains("immune")){
                        immunities = Arrays.asList(input.substring(input.indexOf("immune to ") + 10, input.indexOf(';')).split(", "));
                    }
                    Group groupToAdd = new Group(number, initiative, damage, numOfUnitsToCreate, hitPoints, weaknesses, immunities, damageType);
                    allGroups.add(groupToAdd);
                    infectionGroups.add(groupToAdd);
                    number++;
                }
            }
        }
    }

    private static class Group{
        int groupNumber;
        int initiative;
        int attackDamage;
        int numOfUnits;
        int hitPoints;
        int damageToTarget;
        List<String> weaknesses;
        List<String> immunities;
        String damageType;
        Group currentTarget;
        boolean targeted = false;

        public Group(int groupNumber, int initiative, int attackDamage, int numOfUnits, int hitPoints, List<String> weaknesses, List<String> immunities, String damageType) {
            this.groupNumber = groupNumber;
            this.attackDamage = attackDamage;
            this.initiative = initiative;
            this.weaknesses = weaknesses;
            this.immunities = immunities;
            this.damageType = damageType;
            this.numOfUnits = numOfUnits;
            this.hitPoints = hitPoints;
        }

        public Group() {

        }

        private int getEffectivePower(){
            if(numOfUnits < 1){
                return 0;
            }
            return attackDamage * numOfUnits;
        }

        private int calculateDamage(Group g){
            int damage = 0;
            if(g.immunities.contains(this.damageType)){
                return 0;
            }

            damage = this.getEffectivePower();

            if(g.weaknesses.contains(this.damageType)){
                return damage*2;
            }

            return damage;
        }

        private void takeDamage(int damageTaken){
            int numOfUnitsToLose = damageTaken/this.hitPoints;
            this.numOfUnits -= numOfUnitsToLose;
            if(this.numOfUnits <= 0){
                this.destroy();
            }
        }

        private void destroy(){
            if(immuneGroups.contains(this)){
                immuneGroups.remove(this);
            }
            else if (infectionGroups.contains(this)){
                infectionGroups.remove(this);
            }
        }
    }
}