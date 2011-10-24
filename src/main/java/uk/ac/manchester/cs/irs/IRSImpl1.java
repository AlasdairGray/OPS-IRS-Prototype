package uk.ac.manchester.cs.irs;

public class IRSImpl1 implements IRS1 {
    
    @Override
    public String hello1(String name, String last)
    {
        System.out.println("here");
        System.out.println(name);
        System.out.println(last);
        if (name == null){
           if (last == null){
               return "Hello How rude not to give your name";
           } else{
               return "Hello Mr or Mrs " + last + "!";
           }
        } else 
           if (last == null){
               return "Hello " + name + "! How informal not to give your last name";
           } else{
                return "Hello " + name + " " + last + "!";
           }
    }
    
}
