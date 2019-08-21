import java.util.ArrayList;

public class Box<T extends Fruit>{
    private ArrayList<T> box = new ArrayList<>();

    public void add(T fruit) {
        this.box.add(fruit);
    }


    public float getWeight(){
        float weight = 0.0f;
        for(T o : box){
            weight += o.getWeight();
        }return weight;
    }


    public boolean CompareBox(Box anotherBox) {
        boolean res = false;

        if (this.getWeight() == anotherBox.getWeight()){
            res = true;
        }

        return res;
    }

    public void fromBoxToBox(Box <T>anotherBox){
        //anotherBox.box.addAll(box);
        this.box.addAll(anotherBox.box);
        anotherBox.box.clear();
       // this.box.clear();
    }

}
