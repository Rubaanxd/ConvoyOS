package com.convoyos.memory;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.WinNT.HANDLE;

public class SharedMemoryReader {

    private static final String MMF_NAME = "Local\\SCSTelemetry";

    // Tamaño total documentado de la estructura
    private static final int MEMORY_SIZE = 21620;

    private HANDLE handle;

    private Pointer memoryPointer;

    public boolean connect() {

        handle = Kernel32.INSTANCE.OpenFileMapping(
                Kernel32.FILE_MAP_READ,
                false,
                MMF_NAME);

        if (handle == null) {
            System.out.println("OpenFileMapping devolvió null");
            return false;
        }

        long pointerValue =
                Pointer.nativeValue(handle.getPointer());

        if (pointerValue == 0) {
            System.out.println("Handle inválido");
            return false;
        }

        System.out.println("Handle obtenido correctamente");

        return true;
    }

    public boolean mapMemory() {

        if (handle == null) {
            return false;
        }

        memoryPointer =
                Kernel32.INSTANCE.MapViewOfFile(
                        handle,
                        Kernel32.FILE_MAP_READ,
                        0,
                        0,
                        MEMORY_SIZE);

        if (memoryPointer == null) {

            System.out.println("MapViewOfFile devolvió null");

            return false;
        }

        System.out.println("Memoria mapeada correctamente");

        return true;
    }

    public byte[] readMemory() {

        if (memoryPointer == null) {
            return null;
        }

        byte[] data = new byte[MEMORY_SIZE];

        memoryPointer.read(
                0,
                data,
                0,
                MEMORY_SIZE);

        return data;
    }

    public void close() {

        if (memoryPointer != null) {

            Kernel32.INSTANCE.UnmapViewOfFile(
                    memoryPointer);

            memoryPointer = null;
        }

        if (handle != null) {

            Kernel32.INSTANCE.CloseHandle(
                    handle);

            handle = null;
        }
    }
}