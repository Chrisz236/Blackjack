/*
  Author: Haolin Zhang
  File:   Message.java
  Date:   July 11, 2022
  Ver:    1.0

  Description:
          Message class for sending messages through the network
          via Java socket connection.
 */

import java.io.Serializable;

public class Message implements Serializable {
    private String data;

    /*
     * default constructor for Message
     * @param: null
     * @return: null
     */
    public Message() {
        this.data = "[UNDEFINED]";
    }

    /*
     * constructor for Message with parameter
     * @param msg
     */
    public Message(String msg) {
        this.data = msg;
    }

    /*
     * member function to get data from Message object
     * @return String
     */
    public String getData() {
        return this.data;
    }

    /*
     * member function to set content of the Message object
     * @param msg
     */
    public void setData(String msg) {
        this.data = msg;
    }
}
