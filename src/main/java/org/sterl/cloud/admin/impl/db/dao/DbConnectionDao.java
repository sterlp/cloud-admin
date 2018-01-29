package org.sterl.cloud.admin.impl.db.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.sterl.cloud.admin.impl.db.model.DbConnectionBE;

public interface DbConnectionDao extends JpaRepository<DbConnectionBE, Long> {

}
