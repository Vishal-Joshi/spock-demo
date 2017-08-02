package com.ebi.bank.service;

import java.util.Random;
import java.util.UUID;

public class IdGenerator {
    public enum Type {UUID, NUMERIC}

    /**
     * @return a generated id with the default type UUID
     */
    public String generate() {
        return generate(Type.UUID);
    }

    /**
     * @param type the type of id to generate
     * @return a generated id with the specified type
     */
    public String generate(Type type) {
        switch (type) {
            case UUID:
                return UUID.randomUUID().toString();
            case NUMERIC:
                return Integer.toString(new Random().nextInt(100));
        }
        throw new IllegalArgumentException("The type " + type + " is not defined");
    }
}
