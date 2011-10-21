package uk.ac.manchester.cs;

public class IRSImpl implements IRS {
    
    @Override
    public String hello(String name)
    {
        return "Hello " + name + "!";
    }
    
}
