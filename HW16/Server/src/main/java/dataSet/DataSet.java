package dataSet;


import javax.persistence.*;

@MappedSuperclass
public abstract class DataSet {

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

}
