import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 35930 on 2017/5/15.
 */
public class lab {
    public static void main(String[] args) {
        List<Person> arrayList=new ArrayList<>();
        arrayList.add(new Person("老王", 24, 167));
        arrayList.add(new Person("老王", 24, 167));
        arrayList.add(new Person("老王", 24, 167));
        arrayList.add(new Person("老王", 24, 167));
        arrayList.add(new Person("老王", 24, 167));
        arrayList.add(new Person("老王", 24, 167));

        System.out.println(new Gson().toJson(arrayList));
    }

}
class Person{
    String name;
    int age;
    int height;

    Person(String name, int age, int height) {
        this.name=name;
        this.age=age;
        this.height=height;
    }
}