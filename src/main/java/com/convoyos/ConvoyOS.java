package com.convoyos;

import com.convoyos.memory.SharedMemoryReader;

public class ConvoyOS {

    public static void main(String[] args) {

        System.out.println("================================");
        System.out.println("         ConvoyOS");
        System.out.println("================================");

        SharedMemoryReader reader =
                new SharedMemoryReader();

        System.out.println(
                "Telemetry Available: "
                + reader.isAvailable());

    }

}