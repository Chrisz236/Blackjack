
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
    private String data;

    /**
     * default constructor for Message
     * @param: null
     * @return: null
     */
    public Message() {
        this.data = "[UNDEFINED]";
    }

    public Message(String msg) {
        this.data = msg;
    }

    public String getData() {
        return this.data;
    }

    public void setDate(String msg) {
        this.data = msg;
    }
}
