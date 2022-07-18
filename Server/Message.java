
/**
 * Author: Haolin Zhang
 * File:   Message.java
 * Date:   July 11, 2022
 * Ver:    1.0
 * 
 * Description:
 *         Message class for sending messages through the network
 *         via Java socket connection.
 */

import java.io.Serializable;

public class Message implements Serializable {
    protected String data;
    protected Client fromClient;
    protected Client toClient;
    protected Server fromServer;
    protected Server toServer;

    /**
     * default constructor for Message
     * @param: null
     * @return: null
     */
    public Message() {
        this.data = "[NO MESSAGES]";
    }

    public String getData() {
        return this.data;
    }

}
