package org.sterl.cloud.admin.impl.dbdriver.postgresql;

import java.sql.Driver;
import java.util.List;
import java.util.Properties;

import org.springframework.stereotype.Component;
import org.sterl.cloud.admin.api.dbdriver.DbDriverId;
import org.sterl.cloud.admin.impl.db.business.JdbcQueryExecutor;
import org.sterl.cloud.admin.impl.db.model.DbConnectionBE;
import org.sterl.cloud.admin.impl.dbdriver.model.DbDriver;
import org.sterl.cloud.admin.impl.dbdriver.model.DbMetaDataBE;
import org.sterl.cloud.admin.impl.dbquery.model.QueryCommandBE;
import org.sterl.cloud.admin.impl.dbquery.model.QueryResultBE;
import org.sterl.cloud.admin.impl.dbquery.model.QueryCommandBE.CommitType;

@Component
public class PostgreSqlDriver implements DbDriver {
    private static final DbDriverId NAME = new DbDriverId("PostgreSQL");

    /**
     * https://www.postgresql.org/docs/9.3/static/infoschema-tables.html
     */
    private static final String SQL_READ_META_DATA =
            "select table_schema, table_type, table_name " + 
            "from information_schema.tables order by table_schema, table_type, table_name";
    
    private final org.postgresql.Driver driver = new org.postgresql.Driver();

    public DbMetaDataBE readMetaData(JdbcQueryExecutor queryExecutor) {
        QueryResultBE queryResult = queryExecutor.query(new QueryCommandBE(CommitType.ROLLBACK, -1, SQL_READ_META_DATA));
        DbMetaDataBE result = new DbMetaDataBE();
        for (List<Object> row : queryResult.getData()) {
            final String schema = String.valueOf(row.get(0));
            final String type = String.valueOf(row.get(1));
            final String name = String.valueOf(row.get(2));
            if ("VIEW".equalsIgnoreCase(type)) {
                result.addView(schema, name);
            } else if ("BASE TABLE".equalsIgnoreCase(type)) {
                result.addTable(schema, name);
            } else {
                result.addOther(schema, name, type);
            }
        }
        return result;
    }

    @Override
    public DbDriverId getName() {
        return NAME;
    }

    @Override
    public Driver getDriver() {
        return driver;
    }

    @Override
    public Properties newConnectionProperties(DbConnectionBE dbConnection) {
        Properties p = new Properties();
        p.setProperty("ApplicationName", "easyAdmin");
        return p;
    }
}
