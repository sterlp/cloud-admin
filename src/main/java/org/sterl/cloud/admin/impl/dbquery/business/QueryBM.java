package org.sterl.cloud.admin.impl.dbquery.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sterl.cloud.admin.api.db.DbConnectionId;
import org.sterl.cloud.admin.impl.db.business.DbConnectionBM;
import org.sterl.cloud.admin.impl.db.business.JdbcQueryExecutor;
import org.sterl.cloud.admin.impl.dbdriver.model.DbMetaDataBE;
import org.sterl.cloud.admin.impl.dbdriver.postgresql.PostgreSqlDriver;
import org.sterl.cloud.admin.impl.dbquery.model.QueryCommandBE;
import org.sterl.cloud.admin.impl.dbquery.model.QueryResultBE;

@Service
public class QueryBM {
    private static final Logger LOG = LoggerFactory.getLogger(QueryBM.class);
    @Autowired private DbConnectionBM connectionBM;
    @Autowired private PostgreSqlDriver postgreSqlDriver;
    
    public QueryResultBE query(DbConnectionId db, QueryCommandBE query) {
        JdbcQueryExecutor queryBA = connectionBM.connect(db);
        QueryResultBE result = queryBA.query(query);
        return result;
    }
    
    // TODO
    public DbMetaDataBE readMetaData(DbConnectionId db) {
        JdbcQueryExecutor queryBA = connectionBM.connect(db);
        DbMetaDataBE metaData = postgreSqlDriver.readMetaData(queryBA);
        return metaData;
    }
}