package chatbotbob.task.service;

import java.util.List;

public interface TaskEncoderInterface {

    /**
     * Encodes and joins attributes of a Task together to form an Encoded String, ready
     * to be written to a file.
     *
     * @param strings The List of attributes to encode together
     * @return the Task as an encoded String
     */
    public String encodeAttributesOfTask(List<String> strings);

    /**
     * Decodes an Encoded Task (a String) into different attributes
     *
     * @param encodedTask The encoded String that represents a Task
     * @return the attributes of the decoded Task
     */
    public String[] decodeEncodedTaskIntoAttributes(String encodedTask);

}
