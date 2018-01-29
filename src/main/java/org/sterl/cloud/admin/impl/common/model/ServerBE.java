package org.sterl.cloud.admin.impl.common.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class ServerBE {
    @Column(name = "server_name")
    private String serverName;
    private int port;
}