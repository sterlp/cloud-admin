package org.sterl.cloud.admin.impl.db.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.sterl.cloud.admin.api.db.DbConnectionId;
import org.sterl.cloud.admin.api.db.DbConnectionInfo;
import org.sterl.cloud.admin.api.db.UpdateDbConnectionCmd;
import org.sterl.cloud.admin.impl.common.api.ResponseBuilder;
import org.sterl.cloud.admin.impl.db.api.DbConverter.ToDbConnectionBE;
import org.sterl.cloud.admin.impl.db.api.DbConverter.ToDbConnectionInfo;
import org.sterl.cloud.admin.impl.db.business.DbConnectionBM;
import org.sterl.cloud.admin.impl.db.model.DbConnectionBE;
import org.sterl.cloud.admin.impl.dbdriver.model.DbMetaDataBE;
import org.sterl.cloud.admin.impl.dbquery.business.QueryBM;
import org.sterl.cloud.admin.impl.dbquery.model.QueryCommandBE;
import org.sterl.cloud.admin.impl.dbquery.model.QueryResultBE;

@RestController
@RequestMapping("/api")
public class DbConnectionBF {
    @Autowired QueryBM queryBM;
    @Autowired DbConnectionBM dbConnectionBM;
    
    @RequestMapping(value = "/connections", method = RequestMethod.GET)
    public List<DbConnectionInfo> get() {
        List<DbConnectionBE> cons = dbConnectionBM.getConnections();
        return ToDbConnectionInfo.INSTANCE.convert(cons);
    }
    
    @RequestMapping(value = "/connections/validate", method = {RequestMethod.POST, RequestMethod.PUT})
    public ResponseEntity<?> validate(@RequestBody UpdateDbConnectionCmd updateDbConnection) {
        final DbConnectionBE convert = ToDbConnectionBE.INSTANCE.convert(updateDbConnection);
        dbConnectionBM.validateConnection(convert);
        return ResponseEntity.accepted().build();
    }

    @RequestMapping(value = "/connections", method = {RequestMethod.POST, RequestMethod.PUT})
    public ResponseEntity<?> create(@RequestBody UpdateDbConnectionCmd updateDbConnection) {
        final DbConnectionBE convert = ToDbConnectionBE.INSTANCE.convert(updateDbConnection);
        if (convert.getId() == null) {
            final DbConnectionBE created = dbConnectionBM.save(convert);
            return ResponseBuilder.created(created);
        } else {
            final DbConnectionBE saved = dbConnectionBM.save(convert);
            return ResponseBuilder.okWithLocation(saved);
        }
    }

    @RequestMapping(value = "/connections/{id}", method = RequestMethod.GET)
    public DbConnectionInfo get(@PathVariable DbConnectionId id) {
        DbConnectionBE c = dbConnectionBM.getOrFail(id);
        return ToDbConnectionInfo.INSTANCE.convert(c);
    }
    
    @RequestMapping("/connections/{id}/query")
    public QueryResultBE query(@PathVariable DbConnectionId id, @RequestBody QueryCommandBE queryCommand) {
        QueryResultBE result = queryBM.query(id, queryCommand);
        return result;
    }
    
    @RequestMapping("/connections/{id}")
    public DbMetaDataBE readMetaData(@PathVariable DbConnectionId id) {
        return queryBM.readMetaData(id);
    }

    @RequestMapping("/connections/{id}/disconnect")
    public ResponseEntity<?> disconnect(DbConnectionId id) {
        dbConnectionBM.disconnect(id);
        return ResponseEntity.noContent().build();
    }
}