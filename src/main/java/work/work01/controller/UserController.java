package work.work01.controller;


import work.work01.annotation.MyRequestMapping;
import work.work01.annotation.MyRestController;
import work.work01.model.User;


import java.util.ArrayList;
import java.util.List;

@MyRestController
public class UserController {

    private static List<User> userList = new ArrayList<>();

    static {
        userList.add(new User(1, "Jim"));
        userList.add(new User(2, "Lily"));
    }

    @MyRequestMapping("/get")
    public String get(int id ) {
//        return userList.stream().filter(u -> u.getId() == id).findAny().orElse(null).toString();
         return userList.get(id-1).getName();
    }

    @MyRequestMapping("/getAll")
    public String getAll() {
        return userList.toString();
    }

    @MyRequestMapping("/dd")
    public String dd(int id ,String name , Double money){
        return "id:"+id+"\n"+"name:"+name+"\n"+"money:"+money;
    }

}
