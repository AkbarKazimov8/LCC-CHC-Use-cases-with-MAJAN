package dummy;

import org.xenei.jena.entities.annotations.Predicate;
import org.xenei.jena.entities.annotations.Subject;

@Subject(namespace = "http://example.com/PA4RDF#")
public class Feedback {
    private String name;
    private int ID;
    private String address;
    private String username;

    @Predicate
    public String getUsername() {
        return username;
    }

    @Predicate
    public void setUsername(String username) {
        this.username = username;
    }

    @Predicate
    public String getAddress() {
        return address;
    }

    @Predicate
    public void setAddress(String address) {
        this.address = address;
    }

    @Predicate
    public int getID() {
        return ID;
    }

    @Predicate
    public void setID(int ID) {
        this.ID = ID;
    }

    @Predicate
    public String getName() {
        return name;
    }

    @Predicate
    public void setName(String name) {
        this.name = name;
    }
}
