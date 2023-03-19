public class Randomizer {
    private String[] names={"petko","marija","bosko","stojan"};

    private int num(){
        return (int)Math.floor(Math.random()*(3+1)+0);
    }
    public String random(){
        return names[num()];
    }
}
