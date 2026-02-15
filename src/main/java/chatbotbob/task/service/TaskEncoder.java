package chatbotbob.task.service;

import java.util.List;

/**
 * Represents an Encoder that encodes Task Attributes into the Save File
 */
public class TaskEncoder implements TaskEncoderInterface {
    private static final String ENCODER_DELIMITER = " | ";
    private static final String ENCODER_DELIMIT_REGEX = " \\| ";
    private static final String ENCODER_DELIMITER_CHAR = "|";

    /**
     * Joins the encoded attributes of a Task together to form an Encoded String, ready
     * to be written to a file.
     * This function exists as a wrapper for String.join(), abstracting the behaviour of
     * how the Encoding works away from the subclasses
     *
     * @param strings The List of attributes to encode together
     * @return the Task as an encoded String
     */
    public String encodeAttributesOfTask(List<String> strings) {
        List<String> encodedAttributes = strings.stream().map(this::encodeSingleAttribute).toList();
        return String.join(ENCODER_DELIMITER, encodedAttributes);
    }

    /**
     * Decodes an Encoded Task into different attributes
     * This function exists as a wrapper for String.split(), abstracting the behaviour of
     * how the Decoding works away from the subclasses
     *
     * @param encodedTask The encoded String that represents a Task
     * @return the attributes of the decoded Task
     */
    public String[] decodeEncodedTaskIntoAttributes(String encodedTask) {
        String[] attributes = encodedTask.split(ENCODER_DELIMIT_REGEX);
        for (int i = 0; i < attributes.length; i += 1) {
            attributes[i] = decodeSingleAttribute(attributes[i]);
        }

        return attributes;
    }

    /**
     * Encodes a Single attribute to be written into a file
     *
     * @param s the encoded attribute as a String
     * @return the encoded attribute
     */
    private String encodeSingleAttribute(String s) {
        return s.replace(ENCODER_DELIMITER_CHAR, "\\" + ENCODER_DELIMITER_CHAR);
    }

    /**
     * Decodes an Encoded Single attribute to be used for Task Creation
     *
     * @param s the attribute to be decoded as a String
     * @return the attribute decoded
     */
    private String decodeSingleAttribute(String s) {
        return s.replace("\\" + ENCODER_DELIMITER_CHAR, ENCODER_DELIMITER_CHAR);
    }
}
