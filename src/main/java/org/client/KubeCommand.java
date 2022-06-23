package org.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KubeCommand {
    private String clientID;
    private String adminCommand;
    private String namespace;
    private String nodeName;
    private String cmdType;
}
