package dataSet;

import dataSet.dataSetSQL.DataSetSQL;
import dataSet.dataSetSQL.DataSetSQLs;
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

    private DataSetSQL getDataSetSQL(){
        return DataSetSQLs.getDataSetSQL(this.getClass());
    }
    public final String getUpdateSQL(){
        return getDataSetSQL().getUpdateSQL(getId());
    }
    public final String getInsertSQL(){
        return getDataSetSQL().getInsertSQL();
    }

}
