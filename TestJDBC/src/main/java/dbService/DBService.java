package dbService;

import dataSet.DataSet;

import java.sql.Connection;

public interface DBService {
    <T extends DataSet> void save(T dataset);
    <T extends DataSet> T load(long id, Class<T> datasetClass);
    void disconnect();
}

