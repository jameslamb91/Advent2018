public class day14 {

    static String puzzleInput = "209231";
    static StringBuilder recipeList =  new StringBuilder();
    static int totalNumOfRecipes = Integer.valueOf(puzzleInput) + 10;
    static int indexOfElf1 = 0;
    static int indexOfElf2 = 1;

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        recipeList.append("37");
        while(recipeList.length() < totalNumOfRecipes){
            getNextRecipe();
        }
        System.out.println("Part 1: " + recipeList.substring( recipeList.length() - 10));
        while(recipeList.substring(recipeList.length() - 10).indexOf(puzzleInput) < 0){
            getNextRecipe();
        }
        System.out.println("Part 2: " + Integer.valueOf(recipeList.indexOf(puzzleInput)) + ". Total time taken: " + Long.valueOf(System.currentTimeMillis() - start) + "ms");
    }

    private static void getNextRecipe() {

        int currentRecipeElf1 = Integer.valueOf(Character.toString(recipeList.charAt(indexOfElf1)));
        int currentRecipeElf2 = Integer.valueOf(Character.toString(recipeList.charAt(indexOfElf2)));
        int newRecipe = currentRecipeElf1 + currentRecipeElf2;

        recipeList = recipeList.append(String.valueOf(newRecipe));

        indexOfElf1 = (indexOfElf1 + currentRecipeElf1 + 1);
        indexOfElf2 = (indexOfElf2 + currentRecipeElf2 + 1);

        if(indexOfElf1 >= recipeList.length()){
            indexOfElf1 %= recipeList.length();
        }
        if(indexOfElf2 >= recipeList.length()){
            indexOfElf2 %= recipeList.length();
        }
    }
}